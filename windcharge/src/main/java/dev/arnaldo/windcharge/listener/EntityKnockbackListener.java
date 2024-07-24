package dev.arnaldo.windcharge.listener;

import dev.arnaldo.windcharge.configuration.WindChargeSettings;
import org.bukkit.entity.WindCharge;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityKnockbackByEntityEvent;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("UnstableApiUsage")
public class EntityKnockbackListener implements Listener {

    @EventHandler
    public void onKnockback(@NotNull EntityKnockbackByEntityEvent event) {
        final var entity = event.getSourceEntity();
        if (entity instanceof WindCharge) {
            final var power = WindChargeSettings.POWER.asDouble();
            final var knockback = new Vector(power, power, power);

            event.setFinalKnockback(event.getFinalKnockback().multiply(knockback));
        }
    }

}