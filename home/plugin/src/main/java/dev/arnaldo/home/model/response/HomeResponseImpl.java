package dev.arnaldo.home.model.response;

import dev.arnaldo.home.api.model.Home;
import dev.arnaldo.home.api.model.response.HomeResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

@Getter
@RequiredArgsConstructor
public class HomeResponseImpl implements HomeResponse {

    private final boolean isSuccessful;

    @Nullable private final Home home;
    @Nullable private final String message;

    public Optional<Home> getHome() {
        return Optional.ofNullable(home);
    }

}