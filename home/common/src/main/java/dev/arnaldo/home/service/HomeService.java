package dev.arnaldo.home.service;

import dev.arnaldo.home.model.Home;
import dev.arnaldo.home.model.HomeUser;
import dev.arnaldo.home.model.response.HomeResponse;
import dev.arnaldo.home.util.Position;
import dev.arnaldo.home.util.Services;
import lombok.NonNull;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface HomeService {

    @NotNull
    static HomeService getInstance() {
        return Services.load(HomeService.class);
    }

    Optional<HomeUser> getUser(@NonNull String name);

    CompletableFuture<HomeUser> loadUser(@NonNull String name);

    CompletableFuture<HomeResponse> createHome(@NonNull HomeUser user, @NonNull String name, @NonNull Position position);

    HomeResponse deleteHome(@NonNull Home home);

    CompletableFuture<HomeResponse> teleport(@NonNull Player player, @NonNull Home home);

}