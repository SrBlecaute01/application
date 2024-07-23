package dev.arnaldo.home.repository.home;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum HomeQueryType {

    CREATE_TABLE(
            "CREATE TABLE IF NOT EXISTS `homes` (" +
            "`owner` VARCHAR(16) NOT NULL," +
            "`name` VARCHAR(16) NOT NULL," +
            "`world` VARCHAR(32) NOT NULL," +
            "`x` DOUBLE NOT NULL," +
            "`y` DOUBLE NOT NULL," +
            "`z` DOUBLE NOT NULL," +
            "PRIMARY KEY (`owner`, `name`))"
    ),

    SELECT_BY_USER("SELECT * FROM `homes` WHERE `owner` = ?"),
    SELECT_BY_USER_AND_NAME("SELECT * FROM `homes` WHERE `owner` = ? AND `name` = ?"),
    DELETE_BY_USER_AND_NAME("DELETE FROM `homes` WHERE `owner` = ? AND `name` = ?"),

    INSERT_OR_UPDATE(
            "INSERT INTO `homes` (`owner`, `name`, `world`, `x`, `y`, `z`) VALUES (?, ?, ?, ?, ?, ?) " +
            "ON DUPLICATE KEY UPDATE `world` = VALUES(`world`), `x` = VALUES(`x`), `y` = VALUES(`y`), `z` = VALUES(`z`)"
    )



    ;

    private final String query;

}