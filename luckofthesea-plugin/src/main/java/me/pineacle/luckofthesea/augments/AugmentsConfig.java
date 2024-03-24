package me.pineacle.luckofthesea.augments;

import lombok.Getter;
import lombok.SneakyThrows;
import me.pineacle.luckofthesea.LuckOfTheSeaPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;

public class AugmentsConfig {

    private final LuckOfTheSeaPlugin plugin;
    private FileConfiguration configuration;
    @Getter private final File configFile;

    public AugmentsConfig(LuckOfTheSeaPlugin plugin) {
        this.plugin = plugin;
        configFile = new File(plugin.getDataFolder(), "augments.yml");
        this.configuration = YamlConfiguration.loadConfiguration(configFile);
        load();
    }

    public void load() {
        File menuFile = new File(this.plugin.getDataFolder(), "augments.yml");
        if (!menuFile.exists())
            try {
                File defaultFile = new File(this.plugin.getDataFolder(), "augments.yml");
                InputStream stream = this.plugin.getResource("augments.yml");
                byte[] buffer = new byte[stream.available()];
                stream.read(buffer);
                OutputStream outStream = new FileOutputStream(defaultFile);
                outStream.write(buffer);
            } catch (IOException e) {
                this.plugin.getLogger().warning("Couldn't copy augments file!");
            }
        configuration = YamlConfiguration.loadConfiguration(menuFile);
    }

    public FileConfiguration getAugmentsConfig() {
        return configuration;
    }

    public String get(String path) {
        return getAugmentsConfig().getString(path);
    }

    @SneakyThrows
    public void reload() {
        this.configuration = YamlConfiguration.loadConfiguration(configFile);
    }

}
