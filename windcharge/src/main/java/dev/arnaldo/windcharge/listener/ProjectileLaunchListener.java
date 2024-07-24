package dev.arnaldo.windcharge.listener;

import dev.arnaldo.windcharge.configuration.WindChargeSettings;
import org.bukkit.entity.WindCharge;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.*;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("UnstableApiUsage")
public class ProjectileLaunchListener implements Listener {

    @EventHandler
    public void onLaunch(@NotNull ProjectileLaunchEvent event) {
        final var entity = event.getEntity();
        if (entity instanceof WindCharge charge) {
            final var velocity = WindChargeSettings.VELOCITY.asDouble();
            final var power = new Vector(velocity, velocity, velocity);

            charge.setVelocity(charge.getVelocity().normalize().multiply(power));
        }
    }

}