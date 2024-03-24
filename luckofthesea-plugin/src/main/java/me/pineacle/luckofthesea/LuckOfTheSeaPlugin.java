package me.pineacle.luckofthesea;

import fr.skytasul.glowingentities.GlowingEntities;
import lombok.Getter;
import me.pineacle.luckofthesea.afk.AFKHandler;
import me.pineacle.luckofthesea.api.LuckOfTheSea;
import me.pineacle.luckofthesea.augments.AugmentManagerImpl;
import me.pineacle.luckofthesea.augments.AugmentsConfig;
import me.pineacle.luckofthesea.commands.CommandHandler;
import me.pineacle.luckofthesea.fishing.FishHandler;
import me.pineacle.luckofthesea.fishing.FishListener;
import me.pineacle.luckofthesea.guis.MenuListener;
import me.pineacle.luckofthesea.guis.menus.MenuConfig;
import me.pineacle.luckofthesea.user.FisherManagerImpl;
import me.pineacle.luckofthesea.user.storage.JoinQuitListener;
import me.pineacle.luckofthesea.user.storage.SQLite;
import me.pineacle.luckofthesea.user.storage.Storage;
import me.pineacle.luckofthesea.utils.Config;
import me.pineacle.luckofthesea.utils.Language;
import me.pineacle.luckofthesea.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import static org.bukkit.Bukkit.getPluginManager;

public final class LuckOfTheSeaPlugin extends JavaPlugin implements LuckOfTheSea {

    private static LuckOfTheSeaPlugin instance;

    @Getter private CommandHandler commandHandler;
    @Getter private FishHandler fishHandler;
    @Getter private FisherManagerImpl fisherManager;
    @Getter private MenuListener<LuckOfTheSeaPlugin> menuListener;
    @Getter private AFKHandler afkHandler;
    @Getter private AugmentManagerImpl augmentManager;

    // GlowingEntities
    @Getter private GlowingEntities glowingEntities;

    // Configs
    @Getter private Config pluginConfig;
    @Getter private MenuConfig menuConfig;
    @Getter private AugmentsConfig augmentsConfig;
    @Getter private Language language;
    @Getter private Storage storage;

    public static LuckOfTheSeaPlugin getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        this.pluginConfig = new Config(this);
        this.language = new Language(this, pluginConfig);

        // load important stuff
        this.fishHandler = new FishHandler(this);
        this.fisherManager = new FisherManagerImpl(this);
        this.augmentManager = new AugmentManagerImpl(this);

        // other
        this.afkHandler = new AFKHandler(this);
        this.menuConfig = new MenuConfig(this);
        this.augmentsConfig = new AugmentsConfig(this);
        this.glowingEntities = new GlowingEntities(this);

        // register listeners
        this.menuListener = new MenuListener<>(this);
        getPluginManager().registerEvents(new FishListener(this), this);
        getPluginManager().registerEvents(new JoinQuitListener(this), this);

        // register base command
        Objects.requireNonNull(getCommand("luckofthesea")).setExecutor(commandHandler = new CommandHandler(this));

        // connect to player storage
        this.storage = new SQLite(this);

    }

    @Override
    public void onDisable() {
        this.glowingEntities.disable();
        this.storage.shutdown();
    }

    public void message(Player p, String language) {
        p.sendMessage(StringUtils.format(getLanguage().get(language)));
    }

    public static ItemStack setValue(ItemStack item, String key, Object value, PersistentDataType dataType) {
        NamespacedKey namespacedKey = new NamespacedKey(getPlugin(LuckOfTheSeaPlugin.class), key);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.getPersistentDataContainer().set(namespacedKey, dataType, value);
        item.setItemMeta(itemMeta);
        return item;
    }

    public static Object getKey(ItemStack item, String key, PersistentDataType dataType) {
        NamespacedKey namespacedKey = new NamespacedKey(getPlugin(LuckOfTheSeaPlugin.class), key);
        ItemMeta itemMeta = item.getItemMeta();
        return itemMeta.getPersistentDataContainer().get(namespacedKey, dataType);
    }

    public static boolean hasKey(ItemStack item, String key, PersistentDataType dataType) {
        NamespacedKey namespacedKey = new NamespacedKey(getPlugin(LuckOfTheSeaPlugin.class), key);
        ItemMeta itemMeta = item.getItemMeta();
        return itemMeta.getPersistentDataContainer().has(namespacedKey, dataType);
    }

    public static void removeKey(ItemStack item, String key, PersistentDataType dataType) {
        if (!hasKey(item, key, dataType))
            return;
        NamespacedKey namespacedKey = new NamespacedKey(getPlugin(LuckOfTheSeaPlugin.class), key);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.getPersistentDataContainer().remove(namespacedKey);
        item.setItemMeta(itemMeta);
    }

    @Override
    public BukkitTask sync(@NotNull final Runnable task) {
        Objects.requireNonNull(task, "task");
        return Bukkit.getScheduler().runTask(this, task);
    }

    @Override
    public BukkitTask syncAfter(@NotNull final Runnable task, final long delay) {
        Objects.requireNonNull(task, "task");
        return Bukkit.getScheduler().runTaskLater(this, task, delay);
    }

    @Override
    public BukkitTask syncRepeating(@NotNull final Runnable task, final long delay, final long period) {
        Objects.requireNonNull(task, "task");
        return Bukkit.getScheduler().runTaskTimer(this, task, delay, period);
    }

    @Override
    public BukkitTask async(@NotNull final Runnable task) {
        Objects.requireNonNull(task, "task");
        return Bukkit.getScheduler().runTaskAsynchronously(this, task);
    }

    @Override
    public BukkitTask asyncAfter(@NotNull final Runnable task, final long delay) {
        Objects.requireNonNull(task, "task");
        return Bukkit.getScheduler().runTaskLaterAsynchronously(this, task, delay);
    }

    @Override
    public BukkitTask asyncRepeating(@NotNull final Runnable task, final long delay, final long period) {
        Objects.requireNonNull(task, "task");
        return Bukkit.getScheduler().runTaskTimerAsynchronously(this, task, delay, period);
    }

    @Override
    public void cancelTask(@NotNull final BukkitTask task) {
        Objects.requireNonNull(task, "task");
        task.cancel();
    }

    @Override
    public void cancelTask(final int id) {
        Bukkit.getScheduler().cancelTask(id);
    }

}
