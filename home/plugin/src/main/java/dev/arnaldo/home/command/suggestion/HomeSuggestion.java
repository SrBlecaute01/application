package dev.arnaldo.home.command.suggestion;

import dev.arnaldo.home.api.model.Home;
import dev.arnaldo.home.api.service.HomeService;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import revxrsal.commands.autocomplete.SuggestionProvider;
import revxrsal.commands.autocomplete.SuggestionProviderFactory;
import revxrsal.commands.command.CommandParameter;

import java.util.Collections;
import java.util.stream.Collectors;

public class HomeSuggestion implements SuggestionProviderFactory {

    @Override
    public @Nullable SuggestionProvider createSuggestionProvider(@NotNull CommandParameter parameter) {
        if (!Home.class.isAssignableFrom(parameter.getType())) return null;

        final HomeService service = HomeService.getInstance();
        return (args, sender, command) -> service.getUser(sender.getName())
                .map(user -> user.getHomes().stream()
                        .map(Home::getName)
                        .collect(Collectors.toSet()))
                .orElse(Collections.emptySet());
    }

}