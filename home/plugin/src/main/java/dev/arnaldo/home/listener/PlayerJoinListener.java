package dev.arnaldo.home.listener;

import dev.arnaldo.home.HomePlugin;
import dev.arnaldo.home.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.jetbrains.annotations.NotNull;

import java.util.logging.Level;

@RequiredArgsConstructor
public class PlayerJoinListener implements Listener {

    private final HomeService service;

    @EventHandler
    public void onJoin(@NotNull PlayerJoinEvent event) {
        this.service.loadUser(event.getPlayer().getName())
                .whenComplete((user, failure) -> {
                    final var plugin = HomePlugin.getInstance();
                    if (failure != null) {
                        final var logger = plugin.getLogger();
                        logger.log(Level.SEVERE, "An error occurred while loading the user homes", failure);
                    }

                    if (user == null) {
                        final var player = event.getPlayer();
                        Bukkit.getScheduler().runTask(plugin, () -> player.kickPlayer("An error occurred while loading your data"));
                    }
                });
    }

}