package dev.arnaldo.home.command.reader;

import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.locales.LocaleReader;

import java.io.File;
import java.util.*;

public class YamlLocaleReader implements LocaleReader {

    private final File file;
    private final Locale locale;

    private final Map<String, String> messages = new HashMap<>();

    public YamlLocaleReader(@NotNull File file, @NotNull Locale locale) {
        this.file = file;
        this.locale = locale;

        loadMessages();
    }

    @Override
    public boolean containsKey(String s) {
        return messages.containsKey(s);
    }

    @Override
    public String get(String s) {
        return messages.get(s);
    }

    @Override
    public Locale getLocale() {
        return this.locale;
    }

    @SuppressWarnings("unchecked")
    public void loadMessages() {
        final var config = YamlConfiguration.loadConfiguration(file);
        final var section = config.getConfigurationSection("messages");
        if (section == null) return;

        for (String key : section.getKeys(false)) {
            final var object = section.get(key);
            if (object == null) continue;

            var message = object.toString();
            if (object instanceof List) {
                message = coloredList((List<Object>) object);
            }

            messages.put(key, message);
        }
    }

    @NotNull
    private static String coloredList(@NotNull List<Object> object) {
        final var builder = new StringBuilder();

        final var iterator = object.iterator();
        while (iterator.hasNext()) {
            final var value = iterator.next();
            if (value != null) {
                builder.append(value);
            }

            if (iterator.hasNext()) {
                builder.append(System.lineSeparator());
            }
        }

        return builder.toString();
    }

}