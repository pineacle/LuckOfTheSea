package me.pineacle.luckofthesea.utils;

import org.bukkit.inventory.Inventory;

import java.lang.reflect.Field;
import java.util.function.Consumer;

public class InventoryUtils {

    private static final Field CB_INVENTORY;
    private static final Field CB_INVENTORY_TITLE;

    static {
        CB_INVENTORY = ReflectionUtils.getDeclaredField(ReflectionUtils.getCBClass("inventory.CraftInventory"), "inventory");
        CB_INVENTORY_TITLE = ReflectionUtils.getDeclaredField(ReflectionUtils.getCBClass("inventory.CraftInventoryCustom$MinecraftInventory"), "title");
    }

    public static void setTitle(final Inventory inventory, final String title) {
        try {
            Object value = title;

            CB_INVENTORY_TITLE.set(CB_INVENTORY.get(inventory), value);
        } catch (IllegalAccessException ex) {
            ex.printStackTrace();
        }
    }

    private InventoryUtils() {}

    public static void run(final int from, final int to, final int height, final Consumer<Integer> action) {
        for (int h = 0; h < height; h++) {
            for (int slot = from; slot < to; slot++) {
                action.accept(slot + h * 9);
            }
        }
    }

    public static void run(final int from, final int to, final Consumer<Integer> action) {
        for (int slot = from; slot < to; slot++) {
            action.accept(slot);
        }
    }

}
