package me.pineacle.luckofthesea.guis.menus.codex;

import me.pineacle.luckofthesea.LuckOfTheSeaPlugin;
import me.pineacle.luckofthesea.fishing.Tier;
import me.pineacle.luckofthesea.guis.MultiPage;

import java.util.stream.Collectors;

public class FishDirectory extends MultiPage<LuckOfTheSeaPlugin> {

    public FishDirectory(LuckOfTheSeaPlugin plugin, String title, int rows, Tier tier) {
        super(plugin, title, rows, plugin.getFishHandler().getFishList().stream().filter(fish -> fish.getTier().equals(tier)).map(fish -> new FishInfoButton(plugin, fish)).collect(Collectors.toList()));
        calculatePages();
    }

}
