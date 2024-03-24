package me.pineacle.luckofthesea.fishing;

import lombok.Getter;
import lombok.SneakyThrows;
import me.pineacle.luckofthesea.LuckOfTheSeaPlugin;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.block.Biome;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerFishEvent;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class FishHandler {

    private final LuckOfTheSeaPlugin plugin;
    private FileConfiguration configuration;
    private final File file;

    @Getter private final List<Fish> fishList = new ArrayList<>();

    public FishHandler(LuckOfTheSeaPlugin plugin) {
        this.plugin = plugin;
        file = new File(plugin.getDataFolder(), "fish.yml");
        this.configuration = YamlConfiguration.loadConfiguration(file);
        load();
    }

    public void load() {
        File fishFile = new File(this.plugin.getDataFolder(), "fish.yml");
        if (!fishFile.exists())
            try {
                File defaultFishFile = new File(this.plugin.getDataFolder(), "fish.yml");
                InputStream stream = this.plugin.getResource("fish.yml");
                byte[] buffer = new byte[stream.available()];
                stream.read(buffer);
                OutputStream outStream = new FileOutputStream(defaultFishFile);
                outStream.write(buffer);
            } catch (IOException e) {
                this.plugin.getLogger().warning("Couldn't copy file!");
            }
        configuration = YamlConfiguration.loadConfiguration(fishFile);

        for (String key : configuration.getConfigurationSection("fish").getKeys(false)) {
            if (key.equals("defaults")) continue;

            List<String> lore;
            String tier = (String) get("fish." + key + ".tier");

            // Check if we should use default description
            if (!getFishConfig().contains("fish." + key + ".lore")) {
                lore = getFishConfig().getStringList("fish.defaults.lore." + tier.toLowerCase());
            } else {
                lore = getFishConfig().getStringList("fish." + key + ".lore");
            }

            fishList.add(new Fish((String) get("fish." + key + ".identifier"),
                    (String) get("fish." + key + ".material"),
                    (String) get("fish." + key + ".name"),
                    (Integer) get("fish." + key + ".model_data"),
                    Tier.valueOf(tier),
                    getFishConfig().getDouble("fish.defaults.prices." + tier.toLowerCase()),
                    lore,
                    getFishConfig().getStringList("fish." + key + ".biomes").stream().map(Biome::valueOf).collect(Collectors.toList()), 0));
        }

    }


    @SneakyThrows
    public void pickFish(Player player, Entity hook, Fish fish, PlayerFishEvent event) {

        Random random = new Random();

    }

    public FileConfiguration getFishConfig() {
        return configuration;
    }

    public Object get(String path) {
        return getFishConfig().get(path);
    }

    public void reload() {
        this.configuration = YamlConfiguration.loadConfiguration(file);
    }

}
