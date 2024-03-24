package me.pineacle.luckofthesea.api;

import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public interface FisherManager {

    /**
     * @return User object
     *
     * @param uuid   UUID of player
     * @since 1.0.0
     */
    Fisher getFisher(@NotNull UUID uuid);

    /**
     * Sets total wins of the user
     *
     * @param uuid   UUID of player
     * @param level amount of wins to add to player
     * @since 1.0.0
     */
    void setLevel(@NotNull UUID uuid, @NotNull int level);

    /**
     * Sets total wins of the user
     *
     * @param uuid   UUID of player
     * @param amount amount of wins to add to player
     * @since 1.0.0
     */
    void addScales(@NotNull UUID uuid, @NotNull int amount);

    /**
     * Gets the level user
     *
     * @param uuid UUID of player
     * @since 1.0.0
     */
    int getLevel(@NotNull UUID uuid);

    /**
     * Gets the total scales of the user
     *
     * @param uuid UUID of player
     * @since 1.0.0
     */
    int getScales(@NotNull UUID uuid);

    /**
     * Gets the total fish the player caught
     *
     * @param uuid UUID of player
     * @since 1.0.0
     */
    int getTotalFishCaught(@NotNull UUID uuid);

    /**
     * Gets the total deliveries user completed
     *
     * @param uuid UUID of player
     * @since 1.0.0
     */
    int getDeliveriesCompleted(@NotNull UUID uuid);

}
