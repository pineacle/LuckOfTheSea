package me.pineacle.luckofthesea.guis.menus.codex;

import me.pineacle.luckofthesea.LuckOfTheSeaPlugin;
import me.pineacle.luckofthesea.fishing.Tier;
import me.pineacle.luckofthesea.guis.SinglePage;
import me.pineacle.luckofthesea.utils.StringUtils;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;

public class FDMainMenu extends SinglePage<LuckOfTheSeaPlugin> {

    public FDMainMenu(LuckOfTheSeaPlugin plugin, String title, int rows) {
        super(plugin, title, rows);

        FileConfiguration config = plugin.getMenuConfig().getMenus();

        config.getConfigurationSection("guis.codex_main_menu.buttons").getKeys(false).forEach(key -> set(config.getInt("guis.codex_main_menu.buttons." + key + ".slot"), new MainMenuButton(plugin,
                new ItemStack(Material.valueOf(config.getString("guis.codex_main_menu.buttons." + key + ".material"))),
                StringUtils.format(config.getString("guis.codex_main_menu.buttons." + key + ".name")),
                StringUtils.color(config.getStringList("guis.codex_main_menu.buttons." + key + ".lore")),
                config.getBoolean("guis.codex_main_menu.buttons." + key + ".glow"), Tier.valueOf(key.toUpperCase()))));

    }
}
