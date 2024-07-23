package dev.arnaldo.home.model;

import dev.arnaldo.home.model.response.HomeResponse;
import dev.arnaldo.home.util.Position;
import lombok.NonNull;
import org.bukkit.entity.Player;

import java.util.concurrent.CompletableFuture;

public interface Home {

    @NonNull
    String getOwner();

    @NonNull
    String getName();

    @NonNull
    Position getPosition();

    CompletableFuture<HomeResponse> teleport(@NonNull Player player);

}