package dev.arnaldo.home.command;

import dev.arnaldo.home.command.annotation.HelpPosition;
import dev.arnaldo.home.model.Home;
import dev.arnaldo.home.command.annotation.CommandPath;
import dev.arnaldo.home.service.HomeService;
import dev.arnaldo.home.util.Position;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import revxrsal.commands.annotation.Command;
import revxrsal.commands.annotation.Range;
import revxrsal.commands.command.CommandActor;
import revxrsal.commands.help.CommandHelp;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.logging.Level;
import java.util.logging.Logger;

@RequiredArgsConstructor
@ParametersAreNonnullByDefault
public class HomeCommand {

    private final HomeService service;
    private final Logger logger;

    @HelpPosition(1)
    @CommandPath("home-command")
    public void onHomeCommand(Player player, Home home) {
        this.service.teleport(player, home).whenComplete((response, failure) -> {
            if (failure != null) logger.log(Level.SEVERE, "Failed to teleport player " + player.getName(), failure);
            if (response != null && response.getMessage() != null) player.sendMessage(response.getMessage());
        });
    }

    @HelpPosition(2)
    @CommandPath("set-home-command")
    public void onSetHomeCommand(Player player, String home) {
        this.service.loadUser(player.getName())
                .thenCompose(user -> user.addHome(home, Position.of(player.getLocation())))
                .whenComplete((response, failure) -> {
                    if (failure != null) logger.log(Level.SEVERE, "Failed to set home for player " + player.getName(), failure);
                    if (response != null && response.getMessage() != null) player.sendMessage(response.getMessage());
                });
    }

    @HelpPosition(3)
    @CommandPath("del-home-command")
    public void onDelHomeCommand(Player player, Home home) {
        final var response = this.service.deleteHome(home);
        if (response.getMessage() != null)
            player.sendMessage(response.getMessage());
    }

    @HelpPosition(10)
    @CommandPath("help-command")
    public void help(CommandActor actor, CommandHelp<String> help) {
        actor.reply("");
        actor.reply(String.join("\n", help));
        actor.reply("");
    }

}