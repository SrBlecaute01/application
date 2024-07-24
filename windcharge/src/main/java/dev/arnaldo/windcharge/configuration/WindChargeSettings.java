package dev.arnaldo.windcharge.configuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

@Getter
@RequiredArgsConstructor
public enum WindChargeSettings {

    VELOCITY("velocity"),
    POWER("power"),
    HIT_PARTICLE("hit-particle"),
    HIT_PARTICLE_COUNT("hit-particle-count");

    private final String path;

    @Delegate
    private final Configuration configuration = new Configuration();

    public static void setup(@NotNull FileConfiguration configuration) {
        for (WindChargeSettings value : values()) {
            value.configuration.setup(configuration, value.path);
        }
    }

}