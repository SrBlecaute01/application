package dev.arnaldo.home.command.replacer;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.lang.StringUtils;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import revxrsal.commands.annotation.DefaultFor;
import revxrsal.commands.annotation.Description;
import revxrsal.commands.annotation.Usage;
import revxrsal.commands.annotation.dynamic.Annotations;
import revxrsal.commands.bukkit.annotation.CommandPermission;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@RequiredArgsConstructor
public class FileCommandReplacer {

    private final File dataFolder;
    private final LoadingCache<String, FileConfiguration> cache = CacheBuilder.newBuilder()
            .expireAfterAccess(1, TimeUnit.MINUTES)
            .build(new CacheLoader<>() {
                @Override
                public @NotNull FileConfiguration load(@NotNull String s) {
                    return YamlConfiguration.loadConfiguration(new File(dataFolder, s));
                }
            });

    @SneakyThrows
    public List<Annotation> replace(@Nullable String file, @NotNull String path, Class<? extends Annotation> command) {
        final var config = cache.get(StringUtils.isBlank(file) ? "config.yml" : file);
        final var section = config.getConfigurationSection(path);
        if (section == null) return null;

        final var annotations = new ArrayList<Annotation>();

        if (section.isSet("command")) {
            final var values = section.getStringList("command").toArray(new String[0]);
            annotations.add(Annotations.create(command, "value", values));
        }

        if (section.isSet("usage")) {
            final var usage = Objects.requireNonNull(section.getString("usage"));
            annotations.add(Annotations.create(Usage.class, "value", usage));
        }

        if (section.isSet("description")) {
            final var description = Objects.requireNonNull(section.getString("description"));
            annotations.add(Annotations.create(Description.class, "value", description));
        }

        if (section.isSet("permission")) {
            final var permission = Objects.requireNonNull(section.getString("description"));
            annotations.add(Annotations.create(CommandPermission.class, "value", permission));
        }

        if (section.isSet("defaultFor")) {
            final var values = section.getStringList("defaultFor").toArray(new String[0]);
            annotations.add(Annotations.create(DefaultFor.class, "value", values));
        }

        return annotations;
    }

    public void invalidate() {
        this.cache.invalidateAll();
    }


}