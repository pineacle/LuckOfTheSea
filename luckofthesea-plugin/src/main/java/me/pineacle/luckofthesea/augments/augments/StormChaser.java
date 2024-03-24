package me.pineacle.luckofthesea.augments.augments;

import me.pineacle.luckofthesea.LuckOfTheSeaPlugin;
import me.pineacle.luckofthesea.api.Fisher;
import me.pineacle.luckofthesea.api.augments.Augment;
import me.pineacle.luckofthesea.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Objects;

public class StormChaser implements Augment {

    @Override
    public String getID() {
        return "stormchaser";
    }

    @Override
    public Material getType() {
        return Material.WATER_BUCKET;
    }

    @Override
    public List<String> getDescription() {
        return StringUtils.color(LuckOfTheSeaPlugin.getInstance().getAugmentsConfig().getAugmentsConfig().getStringList("augments.stormchaser.description"));
    }

    @Override
    public String getDisplayName() {
        return StringUtils.format(LuckOfTheSeaPlugin.getInstance().getAugmentsConfig().getAugmentsConfig().getString("augments.stormchaser.name"));
    }

    @Override
    public int getMaxLevel() {
        return LuckOfTheSeaPlugin.getInstance().getAugmentsConfig().getAugmentsConfig().getInt("augments.stormchaser.max_level");
    }

    @Override
    public int requiredScales() {
        return 0;
    }

    @Override
    public int requiredFishingLevel() {
        return 0;
    }

    @Override
    public boolean checkConditions(Fisher fisher) {
        return Objects.requireNonNull(Bukkit.getPlayer(fisher.getUuid())).getWorld().hasStorm();
    }

    @Override
    public List<ItemStack> getIngredients() {
        return null;
    }

    @Override
    public void run() {

    }
}
