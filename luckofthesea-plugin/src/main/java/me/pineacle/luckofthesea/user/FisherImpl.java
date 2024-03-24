package me.pineacle.luckofthesea.user;

import lombok.Getter;
import lombok.Setter;
import me.pineacle.luckofthesea.api.Fisher;
import org.bukkit.entity.Player;

import java.util.UUID;

public class FisherImpl implements Fisher {

    @Getter private final UUID uuid;
    @Getter @Setter private int totalFishCaught;
    @Getter @Setter private int scales;
    @Getter @Setter private int level;
    @Getter @Setter private int deliveriesCompleted;

    /**
     * Default user constructor
     */
    public FisherImpl(UUID uuid, int totalFishCaught, int scales, int level, int deliveriesCompleted) {
        this.uuid = uuid;
        this.totalFishCaught = totalFishCaught;
        this.scales = scales;
        this.level = level;
        this.deliveriesCompleted = deliveriesCompleted;
    }

}
