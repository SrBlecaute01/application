package dev.arnaldo.home.command.resolver;

import dev.arnaldo.home.configuration.HomeMessages;
import dev.arnaldo.home.api.model.Home;
import dev.arnaldo.home.api.service.HomeService;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import revxrsal.commands.command.CommandParameter;
import revxrsal.commands.exception.CommandErrorException;
import revxrsal.commands.process.ValueResolver;
import revxrsal.commands.process.ValueResolverFactory;

@RequiredArgsConstructor
public class HomeResolver implements ValueResolverFactory {

    @Override
    public @Nullable ValueResolver<Home> create(@NotNull CommandParameter parameter) {
        if (!Home.class.isAssignableFrom(parameter.getType())) return null;

        final HomeService service = HomeService.getInstance();
        return context -> service.getUser(context.actor().getName())
                .flatMap(user -> user.getHome(context.pop()))
                .orElseThrow(() -> new CommandErrorException(HomeMessages.HOME_NOT_FOUND.asString()));
    }

}