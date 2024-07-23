package dev.arnaldo.home.model;

import dev.arnaldo.home.model.response.HomeResponse;
import dev.arnaldo.home.util.Position;
import lombok.NonNull;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface HomeUser {
    
    String getName();

    Collection<Home> getHomes();
    
    Optional<Home> getHome(@NonNull String name);

    CompletableFuture<HomeResponse> addHome(@NonNull String name, @NonNull Position position);

    HomeResponse removeHome(@NonNull String name);

}