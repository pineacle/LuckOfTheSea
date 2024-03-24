package me.pineacle.luckofthesea.guis;

import me.pineacle.luckofthesea.LuckOfTheSeaPlugin;
import org.bukkit.inventory.ItemStack;

public abstract class BaseButton extends Button<LuckOfTheSeaPlugin> {

    protected BaseButton(final LuckOfTheSeaPlugin plugin, final ItemStack displayed) {
        super(plugin, displayed);
    }

}
