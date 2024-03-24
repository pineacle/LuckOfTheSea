package me.pineacle.luckofthesea.fishing;

import me.pineacle.luckofthesea.LuckOfTheSeaPlugin;
import me.pineacle.luckofthesea.utils.Permissions;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;

public class FishListener implements Listener {

    private final LuckOfTheSeaPlugin plugin;

    public FishListener(LuckOfTheSeaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onCatch(PlayerFishEvent event) {

        Player player = event.getPlayer();
        PlayerFishEvent.State state = event.getState();
        Entity caught = event.getCaught();

        if (event.isCancelled()) return;

        if (plugin.getConfig().getBoolean("general.disable-off-hand-fishing")) {
            ItemStack offHand = player.getInventory().getItemInOffHand();
            if (offHand.getType() == Material.FISHING_ROD) {
                event.setCancelled(true);
                plugin.message(player, "no-off-hand-fishing");
                return;
            }
        }

        if (state.equals(PlayerFishEvent.State.CAUGHT_FISH)) {

            // Check for treasure and replace with fish if disabled
            if (plugin.getConfig().getBoolean("disable-treasure") && caught != null) {
                try {
                    ItemStack itemStack = ((Item) caught).getItemStack();
                    if (itemStack.getType() != Material.COD
                            || itemStack.getType() != Material.SALMON
                            || itemStack.getType() != Material.PUFFERFISH
                            || itemStack.getType() != Material.TROPICAL_FISH) {
                        Item item = (Item) caught;
                        item.getItemStack().setType(Material.COD);
                        item.getItemStack().setItemMeta(null);
                    }
                } catch (Exception ignored) {
                }
            }

            // Check for creative fishing
            if (!plugin.getConfig().getBoolean("general.fish-in-creative") && player.getGameMode().equals(GameMode.CREATIVE))
                event.setCancelled(true);

            // Check if fishing in disabled world
            if (plugin.getConfig().getStringList("general.disabled-worlds").contains(player.getWorld().getName()))
                return;

            // Check if they have permission for custom fish
            if (!player.hasPermission(Permissions.CATCH_CUSTOM_FISH)) return;

            // Check for totem
            // Check for party

            if (caught instanceof Item) {
                if (((Item) caught).getItemStack().getType() == Material.COD
                        || ((Item) caught).getItemStack().getType() == Material.SALMON
                        || ((Item) caught).getItemStack().getType() == Material.PUFFERFISH
                        || ((Item) caught).getItemStack().getType() == Material.TROPICAL_FISH)
                    caught.remove();
            }
            event.setCancelled(true);
            //plugin.getAfkHandler().spawnCrab(player, player.getLocation(), event.getHook());
            plugin.getFishHandler().pickFish(player, event.getHook(), plugin.getFishHandler().getFishList().get(0), event);

        }
    }
}
