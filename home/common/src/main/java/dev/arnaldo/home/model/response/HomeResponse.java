package dev.arnaldo.home.model.response;

import dev.arnaldo.home.model.Home;

import java.util.Optional;

public interface HomeResponse {

    Optional<Home> getHome();

    boolean isSuccessful();

    String getMessage();

}