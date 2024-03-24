package me.pineacle.luckofthesea.guis.menus;

import lombok.Getter;
import lombok.SneakyThrows;
import me.pineacle.luckofthesea.LuckOfTheSeaPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;

public class MenuConfig {

    private final LuckOfTheSeaPlugin plugin;
    private FileConfiguration configuration;
    @Getter private final File configFile;

    public MenuConfig(LuckOfTheSeaPlugin plugin) {
        this.plugin = plugin;
        configFile = new File(plugin.getDataFolder(), "menus.yml");
        this.configuration = YamlConfiguration.loadConfiguration(configFile);
        load();
    }

    public void load() {
        File menuFile = new File(this.plugin.getDataFolder(), "menus.yml");
        if (!menuFile.exists())
            try {
                File defaultMenuFile = new File(this.plugin.getDataFolder(), "menus.yml");
                InputStream stream = this.plugin.getResource("menus.yml");
                byte[] buffer = new byte[stream.available()];
                stream.read(buffer);
                OutputStream outStream = new FileOutputStream(defaultMenuFile);
                outStream.write(buffer);
            } catch (IOException e) {
                this.plugin.getLogger().warning("Couldn't copy menus file!");
            }
        configuration = YamlConfiguration.loadConfiguration(menuFile);
    }

    public FileConfiguration getMenus() {
        return configuration;
    }

    public String get(String path) {
        return getMenus().getString(path);
    }

    @SneakyThrows
    public void reload() {
        this.configuration = YamlConfiguration.loadConfiguration(configFile);
    }

}
