package dev.arnaldo.home.model;

import dev.arnaldo.home.model.response.HomeResponse;
import dev.arnaldo.home.util.Position;
import lombok.NonNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.Collection;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public interface HomeUser {

    @NonNull
    String getName();

    @Unmodifiable
    Collection<Home> getHomes();
    
    Optional<Home> getHome(@NonNull String name);

    CompletableFuture<HomeResponse> addHome(@NonNull String name, @NonNull Position position);

    void addHome(@NonNull Home home);

    @NonNull
    HomeResponse deleteHome(@NonNull String name);

    void removeHome(@NonNull String name);

}