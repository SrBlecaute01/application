package dev.arnaldo.home.configuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

@Getter
@RequiredArgsConstructor
public enum HomeSettings {

    ;

    private final String path;

    @Delegate
    private final Configuration configuration = new Configuration();

    public static void setup(@NotNull FileConfiguration configuration) {
        for (HomeSettings value : values()) {
            value.configuration.setObject(configuration.get(value.path));
        }
    }

}