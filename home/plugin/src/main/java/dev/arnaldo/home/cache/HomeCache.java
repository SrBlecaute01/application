package dev.arnaldo.home.cache;

import dev.arnaldo.home.model.HomeUser;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class HomeCache {

    private final Map<String, HomeUser> cache = new ConcurrentHashMap<>();

    @Contract(" -> new")
    public static @NotNull HomeCache create() {
        return new HomeCache();
    }

    public void insert(@NotNull HomeUser user) {
        this.cache.put(user.getName().toLowerCase(), user);
    }

    public void invalidate(@NotNull String user) {
        this.cache.remove(user.toLowerCase());
    }

    public void invalidate(@NotNull HomeUser user) {
        this.cache.remove(user.getName().toLowerCase());
    }

    public HomeUser get(@NotNull String name) {
        return this.cache.get(name.toLowerCase());
    }

    public boolean contains(@NotNull String name) {
        return this.cache.containsKey(name.toLowerCase());
    }

    public void clear() {
        this.cache.clear();
    }

}