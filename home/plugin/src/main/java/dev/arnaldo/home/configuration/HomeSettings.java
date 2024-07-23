package dev.arnaldo.home.configuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

@Getter
@RequiredArgsConstructor
public enum HomeSettings {

    HOME_LIMIT("home-settings.limit"),
    HOME_MIN_NAME("home-settings.min-name"),
    HOME_MAX_NAME("home-settings.max-name"),

    TELEPORT_DELAY("home-settings.teleport-delay"),
    TELEPORT_PARTICLES("home-settings.teleport-particles"),
    TELEPORT_PARTICLES_COUNT("home-settings.teleport-particles-count"),
    TELEPORT_SOUND("home-settings.teleport-sound")

    ;

    private final String path;

    @Delegate
    private final Configuration configuration = new Configuration();

    public static void setup(@NotNull FileConfiguration configuration) {
        for (HomeSettings value : values()) {
            value.configuration.setup(configuration, value.path);
        }
    }

}