package me.pineacle.luckofthesea.user.storage;

import lombok.AllArgsConstructor;
import me.pineacle.luckofthesea.LuckOfTheSeaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

@AllArgsConstructor
public class StorageTask extends BukkitRunnable {

    private final Storage database;
    private final LuckOfTheSeaPlugin plugin;

    @Override
    public void run() {
        createTables();
    }

    /**
     * Creates the table(s) if they don't exist
     */
    private void createTables() {
        database.execute(database.getConnection(), database.CREATE_IF_NOT_EXIST);
    }

}
