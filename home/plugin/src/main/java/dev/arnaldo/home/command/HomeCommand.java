package dev.arnaldo.home.command;

import dev.arnaldo.home.configuration.HomeMessages;
import dev.arnaldo.home.model.Home;
import dev.arnaldo.home.command.annotation.CommandPath;
import dev.arnaldo.home.service.HomeService;
import dev.arnaldo.home.util.Position;
import lombok.RequiredArgsConstructor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;

import javax.annotation.ParametersAreNonnullByDefault;
import java.text.MessageFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@ParametersAreNonnullByDefault
public class HomeCommand {

    private final HomeService service;
    private final Logger logger;

    @CommandPath("homes-command")
    public void onHomesCommand(Player player) {
        this.service.loadUser(player.getName())
                .whenComplete((user, failure) -> {
                    if (failure != null) logger.log(Level.SEVERE, "Failed to load home", failure);
                    if (user == null) return;

                    final var homes = user.getHomes().stream().map(Home::getName).collect(Collectors.joining(", "));
                    player.sendMessage(MessageFormat.format(HomeMessages.PLAYER_HOMES.asString(), homes));
                });
    }

    @CommandPath("home-command")
    public void onHomeCommand(Player player, Home home) {
        this.service.teleport(player, home).whenComplete((response, failure) -> {
            if (failure != null) logger.log(Level.SEVERE, "Failed to teleport player " + player.getName(), failure);
            if (response != null && response.getMessage() != null) player.sendMessage(response.getMessage());
        });
    }

    @CommandPath("set-home-command")
    public void onSetHomeCommand(Player player, String home) {
        this.service.loadUser(player.getName())
                .thenCompose(user -> user.addHome(home, Position.of(player.getLocation())))
                .whenComplete((response, failure) -> {
                    if (failure != null) logger.log(Level.SEVERE, "Failed to set home", failure);
                    if (response != null && response.getMessage() != null) player.sendMessage(response.getMessage());
                });
    }

    @CommandPath("del-home-command")
    public void onDelHomeCommand(Player player, Home home) {
        final var response = this.service.deleteHome(home);
        if (response.getMessage() != null) {
            final var component = new TextComponent(response.getMessage());
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, component);
        }
    }

}