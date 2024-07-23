package dev.arnaldo.home.configuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

@Getter
@RequiredArgsConstructor
public enum HomeMessages {

    HOME_NOT_FOUND("home-not-found")

    ;

    private final String path;

    @Delegate
    private final Configuration configuration = new Configuration();

    public static void setup(@NotNull FileConfiguration configuration) {
        for (HomeMessages value : values()) {
            value.configuration.setObject(configuration.get("messages." + value.path));
        }
    }

}
