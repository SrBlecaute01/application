package dev.arnaldo.home.command.adapter;

import org.jetbrains.annotations.NotNull;
import revxrsal.commands.bukkit.exception.BukkitExceptionAdapter;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.exception.InvalidCommandException;
import revxrsal.commands.exception.InvalidSubcommandException;
import revxrsal.commands.exception.MissingArgumentException;

public class CustomExceptionAdapter extends BukkitExceptionAdapter {

    @Override
    public void missingArgument(@NotNull CommandActor actor, @NotNull MissingArgumentException exception) {
        actor.errorLocalized("invalid-command", exception.getParameter().getName(), exception.getCommand().getUsage());
    }

    @Override
    public void invalidCommand(@NotNull CommandActor actor, @NotNull InvalidCommandException exception) {
        final var command = actor.getCommandHandler().getCommand(exception.getPath());
        final var usage = command == null ? "" : command.getUsage();

        actor.errorLocalized("invalid-command", exception.getInput(), usage);
    }

    @Override
    public void invalidSubcommand(@NotNull CommandActor actor, @NotNull InvalidSubcommandException exception) {
        final var command = actor.getCommandHandler().getCommand(exception.getPath());
        final var usage = command == null ? "" : command.getUsage();

        actor.errorLocalized("invalid-subcommand", exception.getInput(), usage);
    }

}