package me.pineacle.luckofthesea.events;

import me.pineacle.luckofthesea.api.Fisher;
import org.bukkit.event.Event;

/**
 * Base class for all ChatGame events
 */
public abstract class LuckOfTheSeaEvent extends Event {

    protected Fisher player;

    public LuckOfTheSeaEvent(Fisher player, boolean async) {
        super(async);
        this.player = player;
    }

}
