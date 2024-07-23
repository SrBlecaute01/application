package dev.arnaldo.home.service.impl;

import dev.arnaldo.home.model.Home;
import dev.arnaldo.home.model.HomeUser;
import dev.arnaldo.home.model.response.HomeResponse;
import dev.arnaldo.home.service.HomeService;
import dev.arnaldo.home.util.Position;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@RequiredArgsConstructor
public class HomeServiceImpl implements HomeService {

    @Override
    public Optional<HomeUser> getUser(@NonNull String name) {
        return Optional.empty();
    }

    @Override
    public CompletableFuture<HomeUser> loadUser(@NonNull String name) {
        return null;
    }

    @Override
    public CompletableFuture<HomeResponse> createHome(@NonNull HomeUser user, @NonNull String name, @NonNull Position position) {
        return null;
    }

    @Override
    public Optional<Home> getHomeIfPresent(@NonNull String owner, @NonNull String name) {
        return Optional.empty();
    }

    @Override
    public HomeResponse deleteHome(@NonNull String owner, @NonNull String name) {
        return null;
    }

    @Override
    public CompletableFuture<HomeResponse> teleport(@NonNull Player player, @NonNull Home home) {
        return null;
    }
}