package dev.arnaldo.home.repository.home;

import com.jaoow.sql.executor.adapter.SQLResultAdapter;
import dev.arnaldo.home.model.Home;
import dev.arnaldo.home.model.HomeImpl;
import dev.arnaldo.home.util.Position;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HomeAdapter implements SQLResultAdapter<Home> {

    @Nullable
    @Override
    public Home adaptResult(@NotNull ResultSet set) throws SQLException {
        final var owner = set.getString("owner");
        final var name = set.getString("name");
        final var world = set.getString("world");
        final var x = set.getDouble("x");
        final var y = set.getDouble("y");
        final var z = set.getDouble("z");
        final var position = Position.of(x, y, z, world);

        return new HomeImpl(owner, name, position);
    }

}