package dev.arnaldo.home.provider;

import dev.arnaldo.home.HomePlugin;
import dev.arnaldo.home.api.HomeAPI;
import dev.arnaldo.home.api.service.HomeService;
import dev.arnaldo.home.api.util.Services;
import lombok.NonNull;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

public class HomeProvider implements HomeAPI {

    private final HomeService service;

    public HomeProvider(@NonNull HomeService service) {
        this.service = service;

        if (Services.get(HomeAPI.class).isEmpty()) {
            Services.provide(HomePlugin.getInstance(), HomeAPI.class, this);
        }
    }

    @NotNull
    @Contract("_ -> new")
    public static HomeAPI create(@NonNull HomeService service) {
        return new HomeProvider(service);
    }

    @Override
    public @NonNull HomeService getHomeService() {
        return this.service;
    }

}