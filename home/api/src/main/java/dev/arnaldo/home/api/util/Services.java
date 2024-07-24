package dev.arnaldo.home.api.util;

import lombok.NonNull;
import lombok.experimental.UtilityClass;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.ServicePriority;

import java.util.Optional;

import javax.annotation.Nonnull;

@UtilityClass
public final class Services {

    /**
     * Loads a service instance, throwing a {@link IllegalStateException} if no registration is
     * present.
     *
     * @param clazz the service class
     * @param <T> the service class type
     * @return the service instance
     */
    @Nonnull
    public <T> T load(@NonNull Class<T> clazz) {
        return get(clazz).orElseThrow(() -> new IllegalStateException("No registration present for service '" + clazz.getName() + "'"));
    }

    /**
     * Loads a service instance
     *
     * @param clazz the service class
     * @param <T> the service class type
     * @return the service instance, as an optional
     */
    @Nonnull
    public <T> Optional<T> get(@NonNull Class<T> clazz) {
        final var registration = Bukkit.getServicesManager().getRegistration(clazz);
        return registration == null ? Optional.empty() : Optional.of(registration.getProvider());
    }

    /**
     * Provides a service.
     *
     * @param clazz the service class
     * @param instance the service instance
     * @param plugin the plugin to register the service to
     * @param priority the priority to register the service instance at
     * @param <T> the service class type
     * @return the same service instance
     */
    @Nonnull
    public <T> T provide(@NonNull Class<T> clazz, @NonNull T instance, @NonNull Plugin plugin, @NonNull ServicePriority priority) {
        Bukkit.getServicesManager().register(clazz, instance, plugin, priority);
        return instance;
    }

    /**
     * Provides a service.
     *
     * @param clazz the service class
     * @param instance the service instance
     * @param priority the priority to register the service instance at
     * @param <T> the service class type
     * @return the same service instance
     */
    @Nonnull
    public <T> T provide(@NonNull Plugin plugin, @Nonnull Class<T> clazz, @Nonnull T instance, @Nonnull ServicePriority priority) {
        return provide(clazz, instance, plugin, priority);
    }

    /**
     * Provides a service.
     *
     * @param clazz the service class
     * @param instance the service instance
     * @param <T> the service class type
     * @return the same service instance
     */
    @Nonnull
    public <T> T provide(@NonNull Plugin plugin, @Nonnull Class<T> clazz, @Nonnull T instance) {
        return provide(plugin, clazz, instance, ServicePriority.Normal);
    }

}