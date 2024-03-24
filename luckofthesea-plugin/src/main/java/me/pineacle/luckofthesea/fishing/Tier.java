package me.pineacle.luckofthesea.fishing;

import lombok.Getter;

public enum Tier {

    // Adjust weights as needed

    COMMON(5, 10),
    TROPHY(11, 100),
    UNIQUE(101, 500),
    ANCIENT(501, 1000),
    MYTHICAL(1001, 5000);

    @Getter private final int minWeight;
    @Getter private final int maxWeight;

    Tier(int minWeight, int maxWeight) {
        this.minWeight = minWeight;
        this.maxWeight = maxWeight;
    }

}
