package me.pineacle.luckofthesea.api.augments;

import me.pineacle.luckofthesea.api.Fisher;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

public interface Augment extends Serializable {

    /**
     * Gets id of the Augment, must have no spaces and all lowercase i.e "calmbeforethestorm"
     *
     * @since 1.0.0
     */
    String getID();

    /**
     * What item should represent this augment
     *
     * @since 1.0.0
     */
    Material getType();

    /**
     * Gets what this augments does
     *
     * @since 1.0.0
     */
    List<String> getDescription();

    /**
     * Gets name of the Augment
     *
     * @since 1.0.0
     */
    String getDisplayName();

    /**
     * Gets the max level of the Augment
     *
     * @since 1.0.0
     */
    int getMaxLevel();

    /**
     * Gets the required scales to craft Augment
     *
     * @since 1.0.0
     */
    int requiredScales();

    /**
     * Gets the required level to be able to craft this Augment
     *
     * @since 1.0.0
     */
    int requiredFishingLevel();

    /**
     * Check to see if the player is eligible to activate the Augment
     *
     * @param fisher User object
     * @since 1.0.0
     */
    boolean checkConditions(Fisher fisher);

    /**
     * Get the required items used to craft the Augment
     *
     * @since 1.0.0
     */
    List<ItemStack> getIngredients();

    /**
     * Called when the augment is picked to activate.
     *
     * @since 1.0.0
     */
    void run();

}
