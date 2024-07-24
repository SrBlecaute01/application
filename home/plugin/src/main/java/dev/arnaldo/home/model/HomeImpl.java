package dev.arnaldo.home.model;

import dev.arnaldo.home.api.model.Home;
import dev.arnaldo.home.api.model.response.HomeResponse;
import dev.arnaldo.home.api.service.HomeService;
import dev.arnaldo.home.api.util.Position;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.bukkit.entity.Player;

import java.util.concurrent.CompletableFuture;

@Getter @Setter
@RequiredArgsConstructor
public class HomeImpl implements Home {

    private final String owner;
    private final String name;
    private final Position position;

    @Override
    public CompletableFuture<HomeResponse> teleport(@NonNull Player player) {
        return HomeService.getInstance().teleport(player, this);
    }

}