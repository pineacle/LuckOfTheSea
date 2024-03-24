package me.pineacle.luckofthesea.guis;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class MenuListener<P extends JavaPlugin> implements Listener {

    private final Multimap<UUID, AbstractMenu<P>> privateGuis = HashMultimap.create();

    public MenuListener(final P plugin) {
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public <T extends AbstractMenu<P>> T addGui(final Player player, final T gui, final boolean removeSameType) {
        if (removeSameType) {
            final Collection<AbstractMenu<P>> guis = privateGuis.asMap().get(player.getUniqueId());

            if (guis != null) {
                guis.removeIf(cached -> gui.getClass().isInstance(cached));
            }
        }

        privateGuis.put(player.getUniqueId(), gui);
        return gui;
    }

    public void removeGui(final Player player, final AbstractMenu<P> gui) {
        gui.clear();

        final Collection<AbstractMenu<P>> guis = privateGuis.asMap().get(player.getUniqueId());

        if (guis != null) {
            guis.remove(gui);
        }
    }

    private List<AbstractMenu<P>> get(final Player player) {
        final List<AbstractMenu<P>> guis = new ArrayList<>();

        if (privateGuis.containsKey(player.getUniqueId())) {
            guis.addAll(privateGuis.get(player.getUniqueId()));
        }

        return guis;
    }

    @EventHandler
    public void on(final InventoryClickEvent event) {
        final Player player = (Player) event.getWhoClicked();
        final Inventory top = player.getOpenInventory().getTopInventory();

        for (final AbstractMenu<P> gui : get(player)) {
            if (gui.isPart(top)) {
                gui.on(player, top, event);
                break;
            }
        }
    }

    @EventHandler
    public void on(final InventoryDragEvent event) {
        final Player player = (Player) event.getWhoClicked();
        final Inventory inventory = event.getInventory();

        for (final AbstractMenu<P> gui : get(player)) {
            if (gui.isPart(inventory)) {
                gui.on(player, event.getRawSlots(), event);
                break;
            }
        }
    }

    @EventHandler
    public void on(final InventoryCloseEvent event) {
        final Player player = (Player) event.getPlayer();
        final Inventory inventory = event.getInventory();

        for (final AbstractMenu<P> gui : get(player)) {
            if (gui.isPart(inventory)) {
                gui.on(player, event.getInventory(), event);
                break;
            }
        }
    }

    @EventHandler
    public void on(final PlayerQuitEvent event) {
        privateGuis.removeAll(event.getPlayer().getUniqueId());
    }

}
