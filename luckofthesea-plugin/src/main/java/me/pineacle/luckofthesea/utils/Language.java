package me.pineacle.luckofthesea.utils;

import lombok.Getter;
import me.pineacle.luckofthesea.LuckOfTheSeaPlugin;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.*;
import java.util.HashMap;
import java.util.List;

public class Language  {

    private final LuckOfTheSeaPlugin plugin;
    private final Config configManager;
    private FileConfiguration yamlConfiguration;

    @Getter private final HashMap<String, String> translationMap;

    public Language(LuckOfTheSeaPlugin plugin, Config config) {
        this.plugin = plugin;
        this.configManager = config;
        this.translationMap = new HashMap<>();
        init();
    }

    public void init() {
        if (!this.configManager.getServerLocale().equals("en_US")) {
            File langFile = new File(this.plugin.getDataFolder(), "languages/" + this.configManager.getServerLocale() + ".yml");
            if (!langFile.exists()) {
                this.plugin.getLogger().warning("Defaulting language to en_US. Couldn't find the language file '" + this.configManager.getServerLocale() + ".yml'");
            } else {
                yamlConfiguration = YamlConfiguration.loadConfiguration(langFile);
                for (String s : yamlConfiguration.getKeys(false)) {
                    if (yamlConfiguration.get(s) instanceof List) continue;
                    this.translationMap.put(s, yamlConfiguration.getString(s));
                }
                this.plugin.getLogger().warning("Found language file '" + this.configManager.getServerLocale() + ".yml'");
            }
        } else {
            File langFile = new File(this.plugin.getDataFolder(), "languages/en_US.yml");
            if (!langFile.exists())
                try {
                    File defaultLanguageFile = new File(plugin.getDataFolder(), "languages/en_US.yml");
                    InputStream stream = plugin.getResource("en_US.yml");
                    byte[] buffer = new byte[stream.available()];
                    stream.read(buffer);
                    OutputStream outStream = new FileOutputStream(defaultLanguageFile);
                    outStream.write(buffer);
                } catch (IOException e) {
                    this.plugin.getLogger().warning("Couldn't copy language file!");
                }
            yamlConfiguration = YamlConfiguration.loadConfiguration(langFile);
            for (String s : yamlConfiguration.getKeys(false)) {
                if (yamlConfiguration.get(s) instanceof List) continue;
                this.translationMap.put(s, yamlConfiguration.getString(s));
            }
            this.plugin.getLogger().warning("Found language file '" + this.configManager.getServerLocale() + ".yml'");
        }
    }

    public void reload() {
        getTranslationMap().clear();
        init();
    }

    public FileConfiguration get() {
        return yamlConfiguration;
    }

    public String get(String path) {
        return StringUtils.format(getTranslationMap().get(path));
    }
}
