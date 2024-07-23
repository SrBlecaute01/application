package dev.arnaldo.home.service.impl;

import dev.arnaldo.home.HomePlugin;
import dev.arnaldo.home.cache.HomeCache;
import dev.arnaldo.home.configuration.HomeMessages;
import dev.arnaldo.home.configuration.HomeSettings;
import dev.arnaldo.home.model.Home;
import dev.arnaldo.home.model.HomeImpl;
import dev.arnaldo.home.model.HomeUser;
import dev.arnaldo.home.model.HomeUserImpl;
import dev.arnaldo.home.model.response.HomeResponse;
import dev.arnaldo.home.model.response.HomeResponseImpl;
import dev.arnaldo.home.repository.home.HomeRepository;
import dev.arnaldo.home.service.HomeService;
import dev.arnaldo.home.task.LocationCheckTask;
import dev.arnaldo.home.util.Position;
import dev.arnaldo.home.util.Services;
import lombok.Getter;
import lombok.NonNull;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.text.MessageFormat;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

public class HomeServiceImpl implements HomeService {

    @Getter
    private final HomeCache cache;
    private final HomeRepository repository;

    protected HomeServiceImpl(@NonNull HomeRepository repository) {
        this.repository = repository;
        this.cache = HomeCache.create();

        if (Services.get(HomeService.class).isEmpty()) {
            Services.provide(HomePlugin.getInstance(), HomeService.class, this);
        }
    }

    @Contract("_ -> new")
    public static @NotNull HomeService create(@NonNull HomeRepository repository) {
        return new HomeServiceImpl(repository);
    }

    @Override
    public Optional<HomeUser> getUser(@NonNull String name) {
        return Optional.ofNullable(this.cache.get(name));
    }

    @Override
    public CompletableFuture<HomeUser> loadUser(@NonNull String name) {
        return this.getUser(name)
                .map(CompletableFuture::completedFuture)
                .orElseGet(() -> CompletableFuture.supplyAsync(() -> {
                    final var user = new HomeUserImpl(name);
                    final var homes = this.repository.findAllByUser(name);

                    homes.forEach(user::addHome);
                    this.cache.insert(user);

                    return user;
                }));
    }

    @Override
    public CompletableFuture<HomeResponse> createHome(@NonNull HomeUser user, @NonNull String name, @NonNull Position position) {
        final var minName = HomeSettings.HOME_MIN_NAME.asInt();
        final var maxName = HomeSettings.HOME_MAX_NAME.asInt();

        if (name.length() < minName || name.length() > maxName) {
            return CompletableFuture.completedFuture(
                    new HomeResponseImpl(false, null, HomeMessages.HOME_NAME_LENGTH.asString()));
        }

        final var limit = HomeSettings.HOME_LIMIT.asInt();
        if (limit > 0 && user.getHomes().size() >= limit) {
            return CompletableFuture.completedFuture(
                    new HomeResponseImpl(false, null, HomeMessages.HOME_LIMIT_REACHED.asString()));
        }

        if (user.getHome(name).isPresent()) {
            return CompletableFuture.completedFuture(
                    new HomeResponseImpl(false, null, HomeMessages.HOME_ALREADY_EXISTS.asString()));
        }

        return CompletableFuture.supplyAsync(() -> {
            final var home = new HomeImpl(user.getName(), name, position);

            this.repository.save(home);
            user.addHome(home);

            final var message = MessageFormat.format(HomeMessages.HOME_CREATED.asString(), name);
            return new HomeResponseImpl(true, home, message);
        });
    }

    @Override
    public HomeResponse deleteHome(@NonNull Home home) {
        this.getUser(home.getOwner()).ifPresent(user -> user.removeHome(home.getName()));
        CompletableFuture.runAsync(() -> this.repository.delete(home));

        final var message = MessageFormat.format(HomeMessages.HOME_DELETED.asString(), home.getName());
        return new HomeResponseImpl(true, home, message);
    }

    @Override
    public CompletableFuture<HomeResponse> teleport(@NonNull Player player, @NonNull Home home) {
        final var future = new CompletableFuture<HomeResponse>();

        new LocationCheckTask(player, HomeSettings.TELEPORT_DELAY.asInt(), time -> {
            final var message = HomeMessages.TELEPORTING.asString();
            final var component = new TextComponent(MessageFormat.format(message, time));
            player.spigot().sendMessage(ChatMessageType.ACTION_BAR, component);

            }, accept -> {

            if (!accept) {
                future.complete(new HomeResponseImpl(false, home, HomeMessages.TELEPORT_CANCELLED.asString()));
                return;
            }

            if (!player.teleport(home.getPosition().toLocation(), PlayerTeleportEvent.TeleportCause.PLUGIN)) {
                future.complete(new HomeResponseImpl(false, home, HomeMessages.TELEPORT_BLOCKED.asString()));
                return;
            }

            final var particle = HomeSettings.TELEPORT_PARTICLES.asEnum(Particle.class);
            final var count = HomeSettings.TELEPORT_PARTICLES_COUNT.asInt();
            final var sound = HomeSettings.TELEPORT_SOUND.asEnum(Sound.class);
            final var location = player.getLocation();

            player.spawnParticle(particle, location, count);
            player.playSound(location, sound, 1.0F, 1.0F);

            future.complete(new HomeResponseImpl(true, home, HomeMessages.TELEPORTED.asString()));
        });

        return future;
    }

}