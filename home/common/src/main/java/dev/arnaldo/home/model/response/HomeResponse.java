package dev.arnaldo.home.model.response;

import dev.arnaldo.home.model.Home;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public interface HomeResponse {

    Optional<Home> getHome();

    boolean isSuccessful();

    @Nullable
    String getMessage();

}