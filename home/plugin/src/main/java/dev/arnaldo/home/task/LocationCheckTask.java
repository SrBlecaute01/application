package dev.arnaldo.home.task;

import dev.arnaldo.home.HomePlugin;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;
import java.util.logging.Level;

@RequiredArgsConstructor
public class LocationCheckTask implements Runnable {

    private final Player player;

    private final Consumer<Integer> onTime;
    private final Consumer<Boolean> onAccept;
    private final Location location;
    private final BukkitTask task;

    private int delay;

    public LocationCheckTask(@NotNull Player player, int delay, Consumer<Integer> onTime, Consumer<Boolean> onAccept) {
        this.player = player;
        this.onAccept = onAccept;
        this.onTime = onTime;
        this.location = player.getLocation();
        this.delay = delay;
        this.task = Bukkit.getScheduler().runTaskTimerAsynchronously(HomePlugin.getInstance(), this, 0, 20);
    }

    @NotNull
    public static LocationCheckTask of(@NotNull Player player, int delay, @NotNull Consumer<Integer> onTime, @NotNull Consumer<Boolean> onAccept) {
        return new LocationCheckTask(player, delay, onTime, onAccept);
    }

    @Override
    public void run() {
        try {
            if (!player.isOnline()) {
                this.task.cancel();
                Bukkit.getScheduler().runTask(HomePlugin.getInstance(), () -> this.onAccept.accept(false));
                return;
            }

            final var current = player.getLocation();
            final var world = player.getWorld();

            if (!world.equals(location.getWorld()) || current.distanceSquared(location) > 0.1) {
                this.task.cancel();
                Bukkit.getScheduler().runTask(HomePlugin.getInstance(), () -> this.onAccept.accept(false));
                return;
            }

            if (this.delay <= 0) {
                this.task.cancel();
                Bukkit.getScheduler().runTask(HomePlugin.getInstance(), () -> this.onAccept.accept(true));
                return;
            }

            this.onTime.accept(this.delay);
            this.delay--;

        } catch (Exception exception) {
            this.task.cancel();
            this.onAccept.accept(false);
            HomePlugin.getInstance().getLogger().log(Level.SEVERE, "failed teleport", exception);
        }
    }

}