package me.pineacle.luckofthesea.guis.menus;

import me.pineacle.luckofthesea.LuckOfTheSeaPlugin;
import me.pineacle.luckofthesea.guis.BaseButton;
import me.pineacle.luckofthesea.utils.ItemBuilder;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.List;

public class HeadButton extends BaseButton {

    public HeadButton(final LuckOfTheSeaPlugin plugin, final Player owner, String displayName, List<String> lore) {
        super(plugin, new ItemBuilder(Material.PLAYER_HEAD)
                .name(displayName).lore(lore)
                .build()
        );
        setOwner(owner);
    }

}
