package me.pineacle.luckofthesea.utils;

import org.bukkit.entity.Entity;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

public class MetadataUtil {

    private MetadataUtil() {}

    public static Object get(final Plugin plugin, final Entity entity, final String key) {
        return entity.getMetadata(key).stream().filter(value -> value.getOwningPlugin().equals(plugin)).findFirst().map(MetadataValue::value).orElse(null);
    }

    public static void put(final Plugin plugin, final Entity entity, final String key, final Object data) {
        entity.setMetadata(key, new FixedMetadataValue(plugin, data));
    }

    public static void remove(final Plugin plugin, final Entity entity, final String key) {
        entity.removeMetadata(key, plugin);
    }

}
