package dev.arnaldo.home.model;

import dev.arnaldo.home.util.Position;
import lombok.NonNull;

public interface Home {

    @NonNull
    String getOwner();

    @NonNull
    String getName();

    @NonNull
    Position getPosition();

}