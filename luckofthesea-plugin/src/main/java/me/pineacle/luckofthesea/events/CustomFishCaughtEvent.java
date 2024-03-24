package me.pineacle.luckofthesea.events;

import me.pineacle.luckofthesea.api.Fisher;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class CustomFishCaughtEvent extends LuckOfTheSeaEvent implements Cancellable {

    private static final HandlerList handlers = new HandlerList();

    private boolean isCancelled;

    public CustomFishCaughtEvent(Fisher player) {
        super(player, false);
        this.isCancelled = false;
    }

    @Override
    public boolean isCancelled() {
        return this.isCancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.isCancelled = cancel;
    }

    @NotNull
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
}
