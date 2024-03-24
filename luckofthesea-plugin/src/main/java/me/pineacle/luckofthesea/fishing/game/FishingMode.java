package me.pineacle.luckofthesea.fishing.game;

import lombok.Getter;
import me.pineacle.luckofthesea.LuckOfTheSeaPlugin;
import me.pineacle.luckofthesea.api.Fisher;
import me.pineacle.luckofthesea.fishing.Fish;
import me.pineacle.luckofthesea.utils.MetadataUtil;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;

public final class FishingMode {

    @Getter private final Player fisher;
    @Getter private final Entity hook;
    @Getter private final Entity fishEntity;

    private final BukkitTask task;

    public FishingMode(Player player, Entity hook, Fish fish) {

        LuckOfTheSeaPlugin plugin = JavaPlugin.getPlugin(LuckOfTheSeaPlugin.class);
        if (player == null || hook == null || fish == null) throw new IllegalArgumentException();
        if (!LuckOfTheSeaPlugin.getInstance().getFisherManager().getHooked().containsKey(player))
            throw new IllegalArgumentException();

        this.fisher = player;
        this.hook = hook;
        this.fishEntity = player.getPlayer().getWorld().spawnEntity(hook.getLocation(), EntityType.valueOf(fish.getType()));

        hook.setCustomName(fish.getName());
        hook.setCustomNameVisible(true);
        this.fishEntity.addPassenger(hook);

        MetadataUtil.put(plugin, fishEntity, "LuckOfTheSea", true);

        this.task = createFishingTask();

        plugin.getFisherManager().getHooked().put(player, fish);


    }

    private final BukkitTask createFishingTask() {
        LuckOfTheSeaPlugin plugin = JavaPlugin.getPlugin(LuckOfTheSeaPlugin.class);
        BukkitScheduler scheduler = Bukkit.getScheduler();
        Runnable runnable = new FishingModeTask(plugin, this);
        BukkitTask task = scheduler.runTaskTimer(plugin, runnable, 1L, 1L);
        return task;
    }

    public static FishingMode startFishingMode(LuckOfTheSeaPlugin plugin, Player player, Entity hook, Fish fish) {
        if (!plugin.getFisherManager().isFishing(player)) {
            return new FishingMode(player, hook, fish);
        }
        return null;
    }

}
