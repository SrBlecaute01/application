package dev.arnaldo.windcharge.listener;

import dev.arnaldo.windcharge.configuration.WindChargeSettings;
import org.bukkit.Particle;
import org.bukkit.entity.WindCharge;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("UnstableApiUsage")
public class ProjectileHitListener implements Listener {

    @EventHandler
    public void onHit(@NotNull ProjectileHitEvent event) {
        final var entity = event.getEntity();
        if (!(entity instanceof WindCharge))
            return;

        var location = event.getHitBlock() != null ? event.getHitBlock().getLocation() : null;
        if (location == null && event.getHitEntity() != null) {
            location = event.getHitEntity().getLocation();
        }

        if (location != null) {
            final var particle = WindChargeSettings.HIT_PARTICLE.asEnum(Particle.class);
            final var count = WindChargeSettings.HIT_PARTICLE_COUNT.asInt();

            entity.getWorld().spawnParticle(particle, location, count);
        }
    }

}