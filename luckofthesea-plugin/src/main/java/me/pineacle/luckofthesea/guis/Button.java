package me.pineacle.luckofthesea.guis;

import lombok.Getter;
import lombok.Setter;
import me.pineacle.luckofthesea.utils.ReflectionUtils;
import me.pineacle.luckofthesea.utils.StringUtils;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

public class Button<P extends JavaPlugin> {

    protected final P plugin;
    @Getter @Setter private ItemStack display;

    public Button(final P plugin, final ItemStack display) {
        this.plugin = plugin;
        this.display = display;
    }

    protected void editMeta(final Consumer<ItemMeta> consumer) {
        final ItemMeta meta = getDisplay().getItemMeta();
        consumer.accept(meta);
        getDisplay().setItemMeta(meta);
    }

    protected void setDisplayName(final String name) {
        editMeta(meta -> meta.setDisplayName(StringUtils.format(name)));
    }

    protected void setLore(final List<String> lore) {
        editMeta(meta -> meta.setLore(StringUtils.color(lore)));
    }

    protected void setLore(final String... lore) {
        setLore(Arrays.asList(lore));
    }

    protected void setOwner(final Player player) {
        if (display.getType().equals(Material.PLAYER_HEAD)) {
            editMeta(meta -> ((SkullMeta) meta).setOwnerProfile(player.getPlayerProfile()));
        }
    }

    protected void setGlow(final boolean glow) {
        editMeta(meta -> {
            if (glow) {
                meta.addEnchant(Enchantment.DURABILITY, 1, false);
                if (ReflectionUtils.getClassUnsafe("org.bukkit.inventory.ItemFlag") != null) {
                    meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                }
            } else {
                meta.removeEnchant(Enchantment.DURABILITY);
                if (ReflectionUtils.getClassUnsafe("org.bukkit.inventory.ItemFlag") != null) {
                    meta.removeItemFlags(ItemFlag.HIDE_ENCHANTS);
                }
            }
        });
    }

    public void update(final Player player) {
    }

    public void onClick(final Player player) {
    }

}
