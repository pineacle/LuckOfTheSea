package me.pineacle.luckofthesea.guis.menus.codex;

import lombok.Setter;
import me.pineacle.luckofthesea.LuckOfTheSeaPlugin;
import me.pineacle.luckofthesea.fishing.Tier;
import me.pineacle.luckofthesea.guis.BaseButton;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class MainMenuButton extends BaseButton {

    private final Tier tier;

    protected MainMenuButton(LuckOfTheSeaPlugin plugin, ItemStack displayed, String displayName, List<String> lore, boolean glow, Tier tier) {
        super(plugin, displayed);
        this.tier = tier;
        setDisplayName(displayName);
        setLore(lore);
        setGlow(glow);
    }

    @Override
    public void onClick(Player player) {
        player.closeInventory();
        plugin.getMenuListener().addGui(player, new FishDirectory(plugin, plugin.getMenuConfig().get("guis.codex.title"), plugin.getMenuConfig().getMenus().getInt("guis.codex.rows"), tier), false).open(player);
    }
}
