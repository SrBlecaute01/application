package dev.arnaldo.home.command;

import dev.arnaldo.home.model.Home;
import dev.arnaldo.home.command.annotation.CommandPath;
import dev.arnaldo.home.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

import javax.annotation.ParametersAreNonnullByDefault;

@RequiredArgsConstructor
@ParametersAreNonnullByDefault
public class HomeCommand {

    private final HomeService service;

    @CommandPath("home-command")
    public void onHomeCommand(Player player, Home home) {

    }

}