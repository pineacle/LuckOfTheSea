package me.pineacle.luckofthesea.fishing.game;

import lombok.Getter;
import me.pineacle.luckofthesea.LuckOfTheSeaPlugin;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class FishingModeTask implements Runnable {

    @Getter private final LuckOfTheSeaPlugin plugin;
    @Getter private final FishingMode fishingMode;
    @Getter private final double maxTension;

    private double x = 0.0D;
    private double y = 0.0D;
    private double z = 0.0D;

    // private Rod rod;

    protected FishingModeTask(LuckOfTheSeaPlugin plugin, FishingMode fishingMode) {
        this.plugin = plugin;
        if (fishingMode == null)
            throw new IllegalArgumentException();
        this.fishingMode = fishingMode;
        this.maxTension = 20D; // get from Rod
    }

    public void run() {
        FishingMode playerFishingMode = getFishingMode();
        Player player = playerFishingMode.getFisher();
        Entity hook = playerFishingMode.getHook();
        Entity fishEntity = playerFishingMode.getFishEntity();
        if (plugin.getFisherManager().isFishing(player)) {
        }
    }
}
