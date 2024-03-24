package me.pineacle.luckofthesea.user;

import lombok.Getter;
import me.pineacle.luckofthesea.LuckOfTheSeaPlugin;
import me.pineacle.luckofthesea.api.Fisher;
import me.pineacle.luckofthesea.api.FisherManager;
import me.pineacle.luckofthesea.fishing.Fish;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.UUID;

public class FisherManagerImpl implements FisherManager {

    private final LuckOfTheSeaPlugin plugin;

    @Getter private final HashMap<Player, Fish> hooked = new HashMap<>();

    public FisherManagerImpl(final LuckOfTheSeaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public Fisher getFisher(@NotNull UUID uuid) {
        return plugin.getStorage().getCache().get(uuid);
    }

    @Override
    public void setLevel(@NotNull UUID uuid, int level) {
        plugin.getStorage().getCache().get(uuid).setLevel(level);
    }

    @Override
    public void addScales(@NotNull UUID uuid, int amount) {
        plugin.getStorage().getCache().get(uuid).setScales(getScales(uuid) + 1);
    }

    @Override
    public int getLevel(@NotNull UUID uuid) {
        return plugin.getStorage().getCache().get(uuid).getLevel();
    }

    @Override
    public int getScales(@NotNull UUID uuid) {
        return plugin.getStorage().getCache().get(uuid).getScales();
    }

    @Override
    public int getTotalFishCaught(@NotNull UUID uuid) {
        return plugin.getStorage().getCache().get(uuid).getTotalFishCaught();
    }

    @Override
    public int getDeliveriesCompleted(@NotNull UUID uuid) {
        return plugin.getStorage().getCache().get(uuid).getDeliveriesCompleted();
    }

    public boolean isFishing(Player player) {
        return hooked.containsKey(player);
    }
}
