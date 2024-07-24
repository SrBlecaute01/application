package dev.arnaldo.home.api;

import dev.arnaldo.home.api.service.HomeService;
import lombok.NonNull;

/**
 * Represents an API for home plugin
 */
public interface HomeAPI {

    /**
     * Gets the home service.
     *
     * @return The service;
     */
    @NonNull
    HomeService getHomeService();

}