package dev.arnaldo.home.service;

import dev.arnaldo.home.model.Home;
import dev.arnaldo.home.model.response.HomeResponse;
import dev.arnaldo.home.util.Position;
import lombok.NonNull;
import org.bukkit.entity.Player;

import java.util.concurrent.CompletableFuture;

public interface HomeService {

    CompletableFuture<HomeResponse> createHome(@NonNull String owner, @NonNull String name, @NonNull Position position);

    CompletableFuture<HomeResponse> deleteHome(@NonNull String owner, @NonNull String name);

    CompletableFuture<HomeResponse> teleport(@NonNull Player player, @NonNull Home home);


}