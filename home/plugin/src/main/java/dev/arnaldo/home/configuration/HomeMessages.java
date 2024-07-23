package dev.arnaldo.home.configuration;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.experimental.Delegate;
import org.bukkit.configuration.file.FileConfiguration;
import org.jetbrains.annotations.NotNull;

@Getter
@RequiredArgsConstructor
public enum HomeMessages {

    HOME_NOT_FOUND("home-not-found"),
    HOME_ALREADY_EXISTS("home-already-exists"),
    HOME_LIMIT_REACHED("home-limit-reached"),
    HOME_NAME_LENGTH("home-name-length"),
    HOME_CREATED("home-created"),
    HOME_DELETED("home-deleted"),

    TELEPORTING("teleporting"),
    TELEPORT_CANCELLED("teleport-cancelled"),
    TELEPORT_BLOCKED("teleport-blocked"),
    TELEPORTED("teleported")

    ;

    private final String path;

    @Delegate
    private final Configuration configuration = new Configuration();

    public static void setup(@NotNull FileConfiguration configuration) {
        for (HomeMessages value : values()) {
            value.configuration.setup(configuration, "messages." + value.path);
        }
    }

}
