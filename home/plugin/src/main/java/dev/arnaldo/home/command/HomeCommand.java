package dev.arnaldo.home.command;

import dev.arnaldo.home.model.Home;
import dev.arnaldo.home.command.annotation.CommandPath;
import dev.arnaldo.home.service.HomeService;
import dev.arnaldo.home.util.Position;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.logging.Level;
import java.util.logging.Logger;

@RequiredArgsConstructor
@ParametersAreNonnullByDefault
public class HomeCommand {

    private final HomeService service;
    private final Logger logger;

    @CommandPath("home-command")
    public void onHomeCommand(Player player, Home home) {
        this.service.teleport(player, home).whenComplete((response, failure) -> {
            if (failure != null) logger.log(Level.SEVERE, "Failed to teleport player " + player.getName(), failure);
            if (response != null) player.sendMessage(response.getMessage());
        });
    }

    @CommandPath("set-home-command")
    public void onSetHomeCommand(Player player, String home) {
        this.service.createHome(player.getName(), home, Position.of(player.getLocation()))
                .whenComplete((response, failure) -> {
                    if (failure != null) logger.log(Level.SEVERE, "Failed to set home for player " + player.getName(), failure);
                    if (response != null) player.sendMessage(response.getMessage());
                });
    }

    @CommandPath("del-home-command")
    public void onDelHomeCommand(Player player, Home home) {
        this.service.deleteHome(player.getName(), home.getName())
                .whenComplete((response, failure) -> {
                    if (failure != null) logger.log(Level.SEVERE, "Failed to delete home for player " + player.getName(), failure);
                    if (response != null) player.sendMessage(response.getMessage());
                });
    }

}