package me.pineacle.luckofthesea.api;

import org.bukkit.entity.Player;

import java.util.UUID;

public interface Fisher {

    UUID getUuid();

    int getTotalFishCaught();

    int getScales();

    int getLevel();

    int getDeliveriesCompleted();

}
