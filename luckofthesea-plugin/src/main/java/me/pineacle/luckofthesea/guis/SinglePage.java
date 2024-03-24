package me.pineacle.luckofthesea.guis;

import me.pineacle.luckofthesea.utils.StringUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.java.JavaPlugin;

public class SinglePage<P extends JavaPlugin> extends AbstractMenu<P> {

    protected final Inventory inventory;

    public SinglePage(final P plugin, final String title, final int rows) {
        super(plugin);
        this.inventory = InventoryBuilder.of(StringUtils.format(title), rows * 9).build();
    }

    protected void set(final int slot, final Button<P> button) {
        set(inventory, slot, button);
    }

    protected void set(final int from, final int to, final int height, final Button<P> button) {
        set(inventory, from, to, height, button);
    }

    public void update(final Player player, final Button<P> button) {
        update(player, inventory, button);
    }

    @Override
    public void open(final Player... players) {
        for (final Player player : players) {
            update(player);
            player.openInventory(inventory);
        }
    }

    @Override
    public boolean isPart(final Inventory inventory) {
        return inventory.equals(this.inventory);
    }

    @Override
    public void on(final Player player, final Inventory top, final InventoryClickEvent event) {
        final Inventory clicked = event.getClickedInventory();

        if (clicked == null) {
            return;
        }

        event.setCancelled(true);

        if (!clicked.equals(top)) {
            return;
        }

        final Button<P> button = get(inventory, event.getSlot());

        if (button == null) {
            return;
        }

        button.onClick(player);
    }

}