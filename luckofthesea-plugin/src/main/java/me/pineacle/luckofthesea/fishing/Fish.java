package me.pineacle.luckofthesea.fishing;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.block.Biome;

import java.util.List;

@Getter
public class Fish implements Cloneable {

    private final String id;
    private final String type;
    private final String name;
    private final int modelData;
    @Setter private double price;
    @Setter private int weight;
    private final Tier tier;
    @Setter private List<String> description;
    private final List<Biome> biomes;

    public Fish(String id, String type, String name, int modelData, Tier tier, double price, List<String> description, List<Biome> biomes, int weight) {
        this.id = id;
        this.type = type;
        this.name = name;
        this.modelData = modelData;
        this.tier = tier;
        this.price = price;
        this.description = description;
        this.biomes = biomes;
        this.weight = weight;
    }

    @Override
    public Fish clone() {
        try {
            return (Fish) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
