package dev.arnaldo.home;

import com.jaoow.sql.connector.type.impl.MySQLDatabaseType;
import com.jaoow.sql.executor.SQLExecutor;
import dev.arnaldo.home.command.HomeCommand;
import dev.arnaldo.home.command.adapter.CustomExceptionAdapter;
import dev.arnaldo.home.command.annotation.CommandPath;
import dev.arnaldo.home.command.reader.YamlLocaleReader;
import dev.arnaldo.home.command.replacer.CommandPathReplacer;
import dev.arnaldo.home.command.replacer.FileCommandReplacer;
import dev.arnaldo.home.command.resolver.HomeResolver;
import dev.arnaldo.home.command.suggestion.HomeSuggestion;
import dev.arnaldo.home.configuration.HomeMessages;
import dev.arnaldo.home.configuration.HomeSettings;
import dev.arnaldo.home.listener.PlayerJoinListener;
import dev.arnaldo.home.listener.PlayerQuitListener;
import dev.arnaldo.home.provider.HomeProvider;
import dev.arnaldo.home.repository.home.impl.HomeRepositoryImpl;
import dev.arnaldo.home.api.service.HomeService;
import dev.arnaldo.home.service.HomeServiceImpl;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;
import revxrsal.commands.bukkit.BukkitCommandHandler;
import revxrsal.commands.locales.Locales;

import java.io.File;
import java.sql.SQLException;
import java.util.Objects;
import java.util.logging.Level;

public class HomePlugin extends JavaPlugin {

    private SQLExecutor executor;

    @Override
    public void onEnable() {
        saveDefaultConfig();

        try {
            initDatabase();
            initConfig();
            initServices();
            initListeners();
            initCommands();

        } catch (SQLException exception) {
            getLogger().log(Level.SEVERE, "An error occurred while trying to connect to the database.", exception);
            getServer().getPluginManager().disablePlugin(this);
        }
    }

    @NotNull
    public static HomePlugin getInstance() {
        return getPlugin(HomePlugin.class);
    }

    private void initConfig() {
        HomeSettings.setup(this.getConfig());
        HomeMessages.setup(this.getConfig());
    }

    private void initDatabase() throws SQLException {
        final var section = Objects.requireNonNull(getConfig().getConfigurationSection("MySQL"));
        final var type = MySQLDatabaseType.builder()
                .address(Objects.requireNonNull(section.getString("host")))
                .password(Objects.requireNonNull(section.getString("password")))
                .username(Objects.requireNonNull(section.getString("username")))
                .database(Objects.requireNonNull(section.getString("database")))
                .build();

        type.configureDataSource(source -> source.addDataSourceProperty("useSSL", "false"));
        this.executor = new SQLExecutor(type.connect());
    }

    private void initServices() {
        final var repository = new HomeRepositoryImpl(this.executor);
        repository.createTable();

        final var service = HomeServiceImpl.create(repository);
        HomeProvider.create(service);
    }

    private void initListeners() {
        final var service = (HomeServiceImpl) HomeService.getInstance();
        final var manager = Bukkit.getPluginManager();

        manager.registerEvents(new PlayerJoinListener(service), this);
        manager.registerEvents(new PlayerQuitListener(service.getCache()), this);
    }

    private void initCommands() {
        final var handler = BukkitCommandHandler.create(this);
        final var reader= new YamlLocaleReader(new File(getDataFolder(), "config.yml"), Locales.PORTUGUESE);
        final var replacer = new FileCommandReplacer(this.getDataFolder());

        handler.registerAnnotationReplacer(CommandPath.class, new CommandPathReplacer(replacer));
        handler.registerValueResolverFactory(new HomeResolver());
        handler.getAutoCompleter().registerSuggestionFactory(new HomeSuggestion());

        handler.setSwitchPrefix("--");
        handler.setHelpWriter((command, actor) -> String.format("§e/%s §7- %s", command.getUsage(), command.getDescription()));
        handler.setExceptionHandler(new CustomExceptionAdapter());
        handler.disableStackTraceSanitizing();

        handler.getTranslator().setLocale(Locales.PORTUGUESE);
        handler.getTranslator().add(reader);

        handler.register(new HomeCommand(HomeService.getInstance(), this.getLogger()));
        replacer.invalidate();
    }

}