package dev.arnaldo.home;

import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class HomePlugin extends JavaPlugin {

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {

    }

    @NotNull
    public static HomePlugin getInstance() {
        return getPlugin(HomePlugin.class);
    }

}