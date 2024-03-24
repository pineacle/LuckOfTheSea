package me.pineacle.luckofthesea.guis.menus.codex;

import lombok.Getter;
import lombok.Setter;
import me.pineacle.luckofthesea.LuckOfTheSeaPlugin;
import me.pineacle.luckofthesea.fishing.Fish;
import me.pineacle.luckofthesea.guis.BaseButton;
import me.pineacle.luckofthesea.utils.ItemBuilder;
import me.pineacle.luckofthesea.utils.StringUtils;
import org.bukkit.Material;
import org.bukkit.block.Biome;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class FishInfoButton extends BaseButton {

    @Getter private final Fish fish;

    public FishInfoButton(final LuckOfTheSeaPlugin plugin, final Fish fish) {
        super(plugin, new ItemBuilder(Material.valueOf(fish.getType())).build());
        this.fish = fish;
        setDisplayName(StringUtils.format(fish.getName()));

        // To add multiple lines to lore for each possible biome
        List<String> biomeLore = new ArrayList<>();
        Optional<String> biomeLine = fish.getDescription().stream().filter(s -> s.contains("%biomes%")).findFirst();
        if (fish.getBiomes() != null) {
            if (biomeLine.isPresent()) {
                for (Biome biome : fish.getBiomes()) {
                    biomeLore.add(biomeLine.get().replace("%biomes%", StringUtils.fixMinecraftFormat(biome.name())));
                }
            }
        }

        // replace placeholders
        List<String> temp = fish.getDescription().stream().map(s ->
                s.replaceAll("%catch_phrase%", Objects.requireNonNull(plugin.getFishHandler().getFishConfig().getString("fish." + fish.getId() + ".catch_phrase")))
                        .replaceAll("%price%", String.valueOf(fish.getPrice()))
                        .replaceAll("%weight%", "???")
                        .replaceAll("%tier%", StringUtils.fixMinecraftFormat(fish.getTier().name()))).collect(Collectors.toList());

        temp.removeIf(s -> s.contains("%biomes%")); //remove original placeholder, is there better way to do all this?

        // add the biome stuff at the end, maybe one day add it so it can be inserted anywhere? For now add it at the end.
        temp.addAll(biomeLore);

        setLore(temp);

    }

}
