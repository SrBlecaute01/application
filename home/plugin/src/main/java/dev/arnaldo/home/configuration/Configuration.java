package dev.arnaldo.home.configuration;

import lombok.Data;
import lombok.NonNull;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.util.NumberConversions;
import org.jetbrains.annotations.NotNull;

import java.util.List;

@Data
public class Configuration {

    private Object object;

    public void setup(@NonNull FileConfiguration configuration, @NonNull String path) {
        object = configuration.get(path);
        if (object == null) return;

        if (object instanceof String) {
            object = ChatColor.translateAlternateColorCodes('&', object.toString());
        }
    }

    public <T> T as(@NotNull Class<T> type) {
        return type.cast(object);
    }

    public String asString() {
        return object.toString();
    }

    public boolean asBoolean() {
        if (object instanceof Boolean) {
            return (Boolean) object;
        }

        object = Boolean.parseBoolean(object.toString());
        return (Boolean) object;
    }

    public int asInt() {
        if (object instanceof Integer) {
            return (Integer) object;
        }

        object = NumberConversions.toInt(object.toString());
        return (Integer) object;
    }

    public double asDouble() {
        if (object instanceof Double) {
            return (Double) object;
        }

        object = NumberConversions.toDouble(object.toString());
        return (Double) object;
    }

    public long asLong() {
        if (object instanceof Long) {
            return (Long) object;
        }

        object = NumberConversions.toLong(object.toString());
        return (Long) object;
    }

    public float asFloat() {
        if (object instanceof Float) {
            return (Float) object;
        }

        object = NumberConversions.toFloat(object.toString());
        return (Float) object;
    }

    public short asShort() {
        if (object instanceof Short) {
            return (Short) object;
        }

        object = NumberConversions.toShort(object.toString());
        return (Short) object;
    }

    @SuppressWarnings("unchecked")
    public <T extends Enum<T>> T asEnum(Class<T> clazz) {
        if (!(object instanceof Enum)) {
            object = Enum.valueOf(clazz, object.toString().toUpperCase());
        }

        return (T) object;
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> asList(Class<T> clazz) {
        return (List<T>) object;
    }

}