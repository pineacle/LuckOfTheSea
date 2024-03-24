package me.pineacle.luckofthesea.afk;

import me.pineacle.luckofthesea.LuckOfTheSeaPlugin;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Silverfish;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class AFKHandler {

    private LuckOfTheSeaPlugin plugin;

    public AFKHandler(final LuckOfTheSeaPlugin plugin) {
        this.plugin = plugin;
    }

    public void spawnCrab(Player player, Location location, Entity entity) {

        if ((new Random()).nextInt(100) < 4) { // edit spawn chance
            Silverfish silverfish = (Silverfish) player.getWorld().spawnEntity(location, EntityType.SILVERFISH);
            silverfish.setCustomName(ChatColor.translateAlternateColorCodes('&', "&cCrab"));
            silverfish.setCustomNameVisible(true);
            silverfish.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(20D);
            silverfish.setHealth(20D);
            silverfish.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 1000, 1));
            silverfish.setRemoveWhenFarAway(true);
            player.sendMessage("You caught a Crab!");
            entity.addPassenger(silverfish);
            (new BukkitRunnable() {
                public void run() {
                    entity.removePassenger(silverfish);
                }
            }).runTaskLater(plugin, 20L);
        }
    }
}
