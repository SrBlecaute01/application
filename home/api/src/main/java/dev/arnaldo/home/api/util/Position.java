package dev.arnaldo.home.api.util;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/**
 * Represents a position in the world.
 */
@Getter @Setter
@RequiredArgsConstructor
public class Position {

    private final double x;
    private final double y;
    private final double z;
    private final float yaw;
    private final float pitch;

    private final String world;

    @Nullable
    private transient Location location = null;

    public synchronized Location toLocation() {
        if (this.location == null) {
            final var world = Objects.requireNonNull(Bukkit.getWorld(this.world));
            this.location = new Location(world, this.x, this.y, this.z, this.yaw, this.pitch);
        }

        return this.location.clone();
    }

    public Position add(double x, double y, double z) {
        return Position.of(this.x + x, this.y + y, this.z + z, this.yaw, this.pitch, this.world);
    }

    public Position subtract(double x, double y, double z) {
        return add(-x, -y, -z);
    }

    @Contract("_, _, _, _, _, _ -> new")
    public static @NotNull Position of(double x, double y, double z,  float yaw, float pitch, @NonNull String world) {
        return new Position(x, y, z, yaw, pitch, world);
    }

    @Contract("_, _, _, _, _, _ -> new")
    public static @NotNull Position of(double x, double y, double z, float yaw, float pitch, @NonNull World world) {
        return of(x, y, z, yaw, pitch, world.getName());
    }

    @NotNull
    public static Position of(@NonNull Location location) {
        return of(location.getX(), location.getY(), location.getZ(), location.getYaw(), location.getPitch(), Objects.requireNonNull(location.getWorld()).getName());
    }

    @NotNull
    public static Position of(@NonNull Block block) {
        return of(block.getLocation());
    }


}