package dev.arnaldo.home.api.model;

import dev.arnaldo.home.api.model.response.HomeResponse;
import dev.arnaldo.home.api.util.Position;
import lombok.NonNull;
import org.bukkit.entity.Player;

import java.util.concurrent.CompletableFuture;

/**
 * Represents a home.
 */
public interface Home {

    /**
     * Gets the owner of the home.
     *
     * @return the owner name.
     */
    @NonNull
    String getOwner();

    /**
     * Gets the name of the home.
     *
     * @return the home name.
     */
    @NonNull
    String getName();

    /**
     * Gets the position of the home.
     *
     * @return the home position.
     */
    @NonNull
    Position getPosition();

    /**
     * Teleports a player to the home.
     *
     * @param player the player.
     * @return the future response.
     */
    CompletableFuture<HomeResponse> teleport(@NonNull Player player);

}