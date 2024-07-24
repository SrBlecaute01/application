package dev.arnaldo.home.api.model.response;

import dev.arnaldo.home.api.model.Home;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

/**
 * Represents a response from a home operation.
 */
public interface HomeResponse {

    /**
     * Gets the home.
     *
     * @return the optional home.
     */
    Optional<Home> getHome();

    /**
     * Checks if the operation was successful.
     *
     * @return true if successful, false otherwise.
     */
    boolean isSuccessful();

    /**
     * Gets the message of the response.
     *
     * @return the message.
     */
    @Nullable
    String getMessage();

}