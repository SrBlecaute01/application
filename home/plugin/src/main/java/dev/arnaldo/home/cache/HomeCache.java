package dev.arnaldo.home.cache;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import dev.arnaldo.home.model.HomeUser;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class HomeCache {

    private final Cache<String, HomeUser> cache = Caffeine.newBuilder()
            .expireAfterAccess(30, TimeUnit.MINUTES)
            .build();

    @Contract(" -> new")
    public static @NotNull HomeCache create() {
        return new HomeCache();
    }

    public void insert(@NotNull HomeUser user) {
        cache.put(user.getName().toLowerCase(), user);
    }

    public void invalidate(@NotNull HomeUser user) {
        cache.invalidate(user.getName().toLowerCase());
    }

    public HomeUser get(@NotNull String name) {
        return cache.getIfPresent(name.toLowerCase());
    }

    public boolean contains(@NotNull String name) {
        return cache.asMap().containsKey(name.toLowerCase());
    }

    public void clear() {
        cache.invalidateAll();
    }

}