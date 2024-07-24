package dev.arnaldo.home.repository.home;

import dev.arnaldo.home.api.model.Home;
import dev.arnaldo.home.repository.Repository;
import org.jetbrains.annotations.NotNull;

import java.util.Collection;
import java.util.Optional;

public interface HomeRepository extends Repository<String, Home> {

    Collection<Home> findAllByUser(@NotNull String user);

    Optional<Home> findByUserAndName(@NotNull String user, @NotNull String name);

    void deleteByUserAndName(@NotNull String user, @NotNull String name);

    @Override
    default Optional<Home> findById(@NotNull String id) {
        throw new UnsupportedOperationException("This operation is not supported for this repository.");
    }

    @Override
    default void deleteById(@NotNull String id) {
        throw new UnsupportedOperationException("This operation is not supported for this repository.");
    }

}