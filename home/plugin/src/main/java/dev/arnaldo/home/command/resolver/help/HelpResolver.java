package dev.arnaldo.home.command.resolver.help;

import dev.arnaldo.home.command.annotation.HelpPosition;
import org.bukkit.permissions.Permission;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.bukkit.BukkitCommandActor;
import revxrsal.commands.bukkit.annotation.CommandPermission;
import revxrsal.commands.command.ExecutableCommand;
import revxrsal.commands.help.CommandHelp;
import revxrsal.commands.process.ContextResolver;

import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("rawtypes")
public class HelpResolver implements ContextResolver<CommandHelp> {

    @Override
    public CommandHelp<?> resolve(@NotNull ContextResolverContext context) {
        final var handler = Objects.requireNonNull(context.commandHandler(), "No help writer is registered!");
        final var writer = handler.getHelpWriter();
        final var command = context.command();
        final var parent = command.getParent();

        final var path = parent == null ? null : parent.getPath();
        final var entries = new HashSet<ExecutableCommand>();
        final var actor = context.actor();

        if (parent != null && parent.getDefaultAction() != null) {
            entries.add(parent.getDefaultAction());
        }

        for (final var children : handler.getCommands().values()) {
            if (path == null || command == children || path.isParentOf(children.getPath()) ) {
                if (actor instanceof BukkitCommandActor commandActor) {
                    final var annotation = children.getAnnotation(CommandPermission.class);
                    if (annotation != null) {
                        final var permission = new Permission(annotation.value(), annotation.defaultAccess());
                        if (!commandActor.getSender().hasPermission(permission)) continue;
                    }
                }

                entries.add(children);
            }
        }

        return new BukkitCommandHelp<>(entries.stream()
                .sorted(Comparator.comparingInt(value -> {
                    HelpPosition position = value.getAnnotation(HelpPosition.class);
                    return position == null ? 0 : position.value();
                }))
                .map(value -> writer.generate(value, actor))
                .filter(Objects::nonNull).distinct()
                .collect(Collectors.toList()));
    }

}