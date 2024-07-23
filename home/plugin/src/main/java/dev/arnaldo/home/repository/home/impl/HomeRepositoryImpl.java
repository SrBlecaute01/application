package dev.arnaldo.home.repository.home.impl;

import com.jaoow.sql.executor.SQLExecutor;
import com.jaoow.sql.executor.function.StatementConsumer;
import dev.arnaldo.home.model.Home;
import dev.arnaldo.home.repository.home.HomeAdapter;
import dev.arnaldo.home.repository.home.HomeQueryType;
import dev.arnaldo.home.repository.home.HomeRepository;
import lombok.NonNull;
import org.jetbrains.annotations.NotNull;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class HomeRepositoryImpl implements HomeRepository {

    private final SQLExecutor executor;

    public HomeRepositoryImpl(@NonNull SQLExecutor executor) {
        this.executor = executor;
        this.executor.registerAdapter(Home.class, new HomeAdapter());
    }

    @Override
    public void createTable() {
        this.executor.execute(HomeQueryType.CREATE_TABLE.getQuery());
    }

    @Override
    public Collection<Home> findAllByUser(@NotNull String user) {
        return this.executor.queryMany(HomeQueryType.SELECT_BY_USER.getQuery(),
                statement -> statement.setString(1, user),
                Home.class);
    }

    @Override
    public Optional<Home> findByUserAndName(@NotNull String user, @NotNull String name) {
        return this.executor.query(HomeQueryType.SELECT_BY_USER_AND_NAME.getQuery(),
                statement -> {
                    statement.setString(1, user);
                    statement.setString(2, name);
                },
                Home.class);
    }

    @NotNull
    @Override
    public Set<Home> findAll() {
        return Set.of();
    }

    @Override
    public void save(@NotNull Home home) {
        this.executor.execute(HomeQueryType.INSERT_OR_UPDATE.getQuery(),
                (StatementConsumer) statement -> setStatement(statement, home));
    }

    @Override
    public void save(@NotNull List<Home> homes) {
        if (homes.isEmpty()) return;

        final var batch = this.executor.batch(HomeQueryType.INSERT_OR_UPDATE.getQuery());
        homes.forEach(home -> batch.batch(statement -> setStatement(statement, home)));
        batch.execute();
    }

    @Override
    public void delete(@NotNull Home home) {
        this.deleteByUserAndName(home.getOwner(), home.getName());
    }

    @Override
    public void deleteByUserAndName(@NotNull String user, @NotNull String name) {
        this.executor.execute(HomeQueryType.DELETE_BY_USER_AND_NAME.getQuery(),
                (StatementConsumer) statement -> {
                    statement.setString(1, user);
                    statement.setString(2, name);
                });
    }

    private void setStatement(@NotNull PreparedStatement statement, @NotNull Home home) throws SQLException {
        final var position = home.getPosition();

        statement.setString(1, home.getOwner());
        statement.setString(2, home.getName());
        statement.setString(3, position.getWorld());
        statement.setDouble(4, position.getX());
        statement.setDouble(5, position.getY());
        statement.setDouble(6, position.getZ());
    }

}
