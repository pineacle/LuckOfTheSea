package me.pineacle.luckofthesea.user.storage;

import me.pineacle.luckofthesea.LuckOfTheSeaPlugin;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class SQLite extends Storage {

    public SQLite(LuckOfTheSeaPlugin plugin) {
        super(plugin);
    }

    @Override
    public Connection establishConnection() {
        File dataFolder = new File(plugin.getDataFolder(), "database.db");
        if (!dataFolder.exists()) {
            try {
                dataFolder.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            Class.forName("org.sqlite.JDBC");
            CompletableFuture<Connection> conFuture = CompletableFuture.supplyAsync(() -> {
                try {
                    return DriverManager.getConnection("jdbc:sqlite:" + dataFolder.getAbsolutePath());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                return null;
            });

            return conFuture.get();

        } catch (ClassNotFoundException | ExecutionException | InterruptedException e) {
            plugin.getLogger().warning("We had trouble connecting to the database..");
            e.printStackTrace();
        }
        return null;
    }
}
