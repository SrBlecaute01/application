package dev.arnaldo.home.model;

import dev.arnaldo.home.api.model.Home;
import dev.arnaldo.home.api.model.HomeUser;
import dev.arnaldo.home.api.model.response.HomeResponse;
import dev.arnaldo.home.model.response.HomeResponseImpl;
import dev.arnaldo.home.api.service.HomeService;
import dev.arnaldo.home.api.util.Position;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Unmodifiable;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@RequiredArgsConstructor
public class HomeUserImpl implements HomeUser {

    private final String name;
    private final Map<String, Home> homes = new ConcurrentHashMap<>();

    @Override
    @Unmodifiable
    public Collection<Home> getHomes() {
        return Collections.unmodifiableCollection(this.homes.values());
    }

    @Override
    public Optional<Home> getHome(@NonNull String name) {
        return Optional.ofNullable(this.homes.get(name.toLowerCase()));
    }

    @Override
    public CompletableFuture<HomeResponse> addHome(@NonNull String name, @NonNull Position position) {
        return HomeService.getInstance().createHome(this, name, position);
    }

    @Override
    public void addHome(@NonNull Home home) {
        this.homes.put(home.getName().toLowerCase(), home);
    }

    @NotNull
    @Override
    public HomeResponse deleteHome(@NonNull String name) {
        final var home = this.homes.remove(name.toLowerCase());
        return home != null ?
                HomeService.getInstance().deleteHome(home) :
                new HomeResponseImpl(false, null, null);
    }

    @Override
    public void removeHome(@NonNull String name) {
        this.homes.remove(name.toLowerCase());
    }

}