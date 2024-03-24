package me.pineacle.luckofthesea.user.storage;

import me.pineacle.luckofthesea.LuckOfTheSeaPlugin;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public record JoinQuitListener(LuckOfTheSeaPlugin plugin) implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        plugin.async(() -> {
            if (plugin.getStorage().isStored(e.getPlayer().getUniqueId())) {
                plugin.getStorage().request(e.getPlayer().getUniqueId());
            } else {
                plugin.getStorage().create(e.getPlayer().getUniqueId());
            }
        });
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        plugin.async(() -> {
            if (plugin.getStorage().isStored(e.getPlayer().getUniqueId()))
                plugin.getStorage().save(plugin.getStorage().getCache().get(e.getPlayer().getUniqueId()));
        });
    }
}
