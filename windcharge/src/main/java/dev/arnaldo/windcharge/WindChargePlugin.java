package dev.arnaldo.windcharge;

import dev.arnaldo.windcharge.configuration.WindChargeSettings;
import dev.arnaldo.windcharge.listener.EntityKnockbackListener;
import dev.arnaldo.windcharge.listener.ProjectileHitListener;
import dev.arnaldo.windcharge.listener.ProjectileLaunchListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class WindChargePlugin extends JavaPlugin {

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        this.initConfig();
        this.initListeners();
    }

    private void initConfig() {
        WindChargeSettings.setup(this.getConfig());
    }

    private void initListeners() {
        final var manager = Bukkit.getPluginManager();

        manager.registerEvents(new ProjectileLaunchListener(), this);
        manager.registerEvents(new ProjectileHitListener(), this);
        manager.registerEvents(new EntityKnockbackListener(), this);
    }

}