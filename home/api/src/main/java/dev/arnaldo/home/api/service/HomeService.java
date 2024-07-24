package dev.arnaldo.home.api.service;

import dev.arnaldo.home.api.model.Home;
import dev.arnaldo.home.api.model.HomeUser;
import dev.arnaldo.home.api.model.response.HomeResponse;
import dev.arnaldo.home.api.util.Position;
import dev.arnaldo.home.api.util.Services;
import lombok.NonNull;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Service responsible for managing homes.
 */
public interface HomeService {

    /**
     * Gets the instance of the service.
     *
     * @return the service instance.
     */
    @NotNull
    static HomeService getInstance() {
        return Services.load(HomeService.class);
    }

    /**
     * Gets a user by name.
     *
     * @param name the username.
     * @return the optional user.
     */
    Optional<HomeUser> getUser(@NonNull String name);

    /**
     * Loads a user by name.
     *
     * @param name the username.
     * @return the future user.
     */
    CompletableFuture<HomeUser> loadUser(@NonNull String name);

    /**
     * Creates a home for a user.
     *
     * @param user the user.
     * @param name the home name.
     * @param position the home position.
     * @return the future response.
     */
    CompletableFuture<HomeResponse> createHome(@NonNull HomeUser user, @NonNull String name, @NonNull Position position);

    /**
     * Deletes a home.
     *
     * @param home the home.
     * @return the response.
     */
    HomeResponse deleteHome(@NonNull Home home);

    /**
     * Teleports a player to a home.
     *
     * @param player the player.
     * @param home the home.
     * @return the future response.
     */
    CompletableFuture<HomeResponse> teleport(@NonNull Player player, @NonNull Home home);

}