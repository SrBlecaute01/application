package dev.arnaldo.home.api.model;

import dev.arnaldo.home.api.model.response.HomeResponse;
import dev.arnaldo.home.api.util.Position;
import lombok.NonNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

/**
 * Represents a user that can have multiple homes.
 */
public interface HomeUser {

    /**
     * Gets the name of the user.
     *
     * @return the username.
     */
    @NonNull
    String getName();

    /**
     * Gets the homes of the user.
     *
     * @return the homes.
     */
    @Unmodifiable
    Collection<Home> getHomes();

    /**
     * Gets a home by its name.
     *
     * @param name the name of the home.
     * @return the home.
     */
    Optional<Home> getHome(@NonNull String name);

    /**
     * Adds a home to the user.
     *
     * @param name the name of the home.
     * @param position the position of the home.
     * @return the future response.
     */
    CompletableFuture<HomeResponse> addHome(@NonNull String name, @NonNull Position position);

    /**
     * Add a created home.
     *
     * @param home The home.
     */
    void addHome(@NonNull Home home);

    /**
     * Deletes a home from the user.
     *
     * @param name the name of the home.
     * @return the response.
     */
    @NonNull
    HomeResponse deleteHome(@NonNull String name);

    /**
     * Removes a home from the user.
     *
     * @param name the name of the home.
     */
    void removeHome(@NonNull String name);

}