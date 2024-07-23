package dev.arnaldo.home.listener;

import dev.arnaldo.home.cache.HomeCache;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.jetbrains.annotations.NotNull;

@RequiredArgsConstructor
public class PlayerQuitListener implements Listener {

    private final HomeCache cache;

    @EventHandler
    public void onQuit(@NotNull PlayerQuitEvent event) {
        this.invalidate(event.getPlayer());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onKick(@NotNull PlayerKickEvent event) {
        if (!event.isCancelled()) {
            this.invalidate(event.getPlayer());
        }
    }

    private void invalidate(@NotNull Player player) {
        this.cache.invalidate(player.getName());
    }

}