package me.pineacle.luckofthesea.augments;

import me.pineacle.luckofthesea.LuckOfTheSeaPlugin;
import me.pineacle.luckofthesea.api.augments.Augment;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public record AugmentCrafter(LuckOfTheSeaPlugin plugin,
                             Augment augment) {

    public void craft(@NotNull ItemStack rod) {
        if (!rod.getType().equals(Material.FISHING_ROD)) return;
    }

}
