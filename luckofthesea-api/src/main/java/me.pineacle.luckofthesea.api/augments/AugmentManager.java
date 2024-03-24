package me.pineacle.luckofthesea.api.augments;

import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface AugmentManager {

    /**
     * Check to see if a fishing rod has an augment
     *
     * @param rod     Users fishing rod
     * @param augment Augment object
     * @since 1.0.0
     */
    boolean hasAugment(ItemStack rod, String augment);

    /**
     * Check to see if a fishing rod has an augment
     *
     * @param rod     Users fishing rod
     * @param augment Augment object
     * @since 1.0.0
     */
    int getAugmentValue(ItemStack rod, String augment);

    /**
     * Register a custom augment
     * <b>Must register augment in order for it to be recognized by the plugin</b>
     *
     * @param augment Augment object
     * @since 1.0.0
     */
    void registerAugment(Augment augment);

    /**
     * Removes an augment from a fishing rod
     *
     * @param rod     Users fishing rod
     * @param augment Augment object
     * @since 1.0.0
     */
    void removeAugment(ItemStack rod, String augment);

    /**
     * Add an augment to a fishing rod
     *
     * @param rod     Users fishing rod
     * @param augment Augment object
     * @since 1.0.0
     * @return Copy of rod with the added augment data.
     */
    ItemStack addAugment(ItemStack rod, String augment, int level);

    /**
     * Get all augments equiped
     *
     * @param rod Users fishing rod
     * @return List of Augments on fishing rod. Returns null if no augments exist.
     * @since 1.0.0
     */
    List<Augment> getAugments(ItemStack rod);

}
