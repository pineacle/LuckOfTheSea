package me.pineacle.luckofthesea.guis.menus.augments;

import me.pineacle.luckofthesea.LuckOfTheSeaPlugin;
import me.pineacle.luckofthesea.guis.SinglePage;

public class AugmentMenu extends SinglePage<LuckOfTheSeaPlugin> {

    public AugmentMenu(LuckOfTheSeaPlugin plugin, String title, int rows) {
        super(plugin, title, rows);
        plugin.getAugmentManager().getRegisteredAugments().forEach((s, augment) -> {
            set(plugin.getAugmentsConfig().getAugmentsConfig().getInt("augments."+s+".slot"), new AugmentButton(plugin, augment));
        });
    }

}
