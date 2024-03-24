package me.pineacle.luckofthesea.augments;

import de.tr7zw.nbtapi.NBTCompound;
import de.tr7zw.nbtapi.NBTItem;
import lombok.Getter;
import me.pineacle.luckofthesea.LuckOfTheSeaPlugin;
import me.pineacle.luckofthesea.api.augments.Augment;
import me.pineacle.luckofthesea.api.augments.AugmentManager;
import me.pineacle.luckofthesea.augments.augments.StormChaser;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class AugmentManagerImpl implements AugmentManager {

    @Getter LuckOfTheSeaPlugin plugin;
    @Getter private final Map<String, Augment> registeredAugments = new HashMap<>();

    public AugmentManagerImpl(final LuckOfTheSeaPlugin plugin) {
        this.plugin = plugin;
        initDefaultAugments();
    }

    public void initDefaultAugments() {
        registerAugment(new StormChaser());
    }

    @Override
    public boolean hasAugment(ItemStack rod, String augment) {
        NBTItem nbtItem = new NBTItem(rod);
        NBTCompound comp = nbtItem.getCompound("augments");
        if (comp == null) return false;
        return comp.hasTag(augment);
    }

    @Override
    public int getAugmentValue(ItemStack rod, String augment) {
        if (hasAugment(rod, augment)) {
            NBTItem nbtItem = new NBTItem(rod);
            NBTCompound comp = nbtItem.getCompound("augments");
            return comp.getInteger(augment);
        }
        return 0;
    }

    @Override
    public void registerAugment(Augment augment) {
        registeredAugments.put(augment.getID(), augment);
        plugin.getLogger().info("Registered augment: " + augment.getID());
    }

    @Override
    public void removeAugment(ItemStack rod, String augment) {
        if (hasAugment(rod, augment)) {
            NBTItem nbtItem = new NBTItem(rod);
            NBTCompound comp = nbtItem.getCompound("augments");
            comp.removeKey(augment);
        }
    }

    @Override
    public ItemStack addAugment(ItemStack rod, String augment, int level) {
            NBTItem nbtItem = new NBTItem(rod);
            NBTCompound comp = nbtItem.addCompound("augments");
            comp.setInteger(augment, level);
            System.out.println("added augment");

            return nbtItem.getItem();
    }


    @Override
    public List<Augment> getAugments(ItemStack rod) {

        NBTItem nbtItem = new NBTItem(rod);
        NBTCompound comp = nbtItem.getCompound("augments");
        if (comp == null) return null;

        return comp.getKeys().stream().map(augment -> getRegisteredAugments().get(augment)).collect(Collectors.toList());


    }
}
