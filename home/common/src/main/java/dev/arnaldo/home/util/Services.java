package dev.arnaldo.home.util;

import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.ServicesManager;

import java.util.Objects;
import java.util.Optional;

import javax.annotation.Nonnull;

/**
 * Utility class for interacting with the Bukkit {@link ServicesManager}.
 */
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
    public static <T> T load(@Nonnull Class<T> clazz) {
        Objects.requireNonNull(clazz, "clazz");
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
    public static <T> Optional<T> get(@Nonnull Class<T> clazz) {
        Objects.requireNonNull(clazz, "clazz");
        RegisteredServiceProvider<T> registration = Bukkit.getServicesManager().getRegistration(clazz);
        if (registration == null) {
            return Optional.empty();
        }
        return Optional.of(registration.getProvider());
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
    public static <T> T provide(@Nonnull Class<T> clazz, @Nonnull T instance, @Nonnull Plugin plugin, @Nonnull ServicePriority priority) {
        Objects.requireNonNull(clazz, "clazz");
        Objects.requireNonNull(instance, "instance");
        Objects.requireNonNull(plugin, "plugin");
        Objects.requireNonNull(priority, "priority");
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
    public static <T> T provide(@NonNull Plugin plugin, @Nonnull Class<T> clazz, @Nonnull T instance, @Nonnull ServicePriority priority) {
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
    public static <T> T provide(@NonNull Plugin plugin, @Nonnull Class<T> clazz, @Nonnull T instance) {
        return provide(plugin, clazz, instance, ServicePriority.Normal);
    }

    private Services() {
        throw new UnsupportedOperationException("This class cannot be instantiated");
    }

}