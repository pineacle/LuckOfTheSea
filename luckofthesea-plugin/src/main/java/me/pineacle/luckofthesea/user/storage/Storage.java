package me.pineacle.luckofthesea.user.storage;

import lombok.Getter;
import lombok.SneakyThrows;
import me.pineacle.luckofthesea.LuckOfTheSeaPlugin;
import me.pineacle.luckofthesea.api.Fisher;
import me.pineacle.luckofthesea.user.FisherImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public abstract class Storage {

    protected final LuckOfTheSeaPlugin plugin;
    @Getter private final Cache<FisherImpl> cache;

    @Getter private final Connection connection;

    /* Queries */
    public final String CREATE_IF_NOT_EXIST = "CREATE TABLE IF NOT EXISTS `lots_players` (`uuid` varchar(64) NOT NULL, `fish_caught` int NOT NULL, `scales` int NOT NULL, `level` int NOT NULL, `deliveries_completed` int NOT NULL, PRIMARY KEY (`uuid`))";

    public final String INSERT = "INSERT INTO `lots_players` VALUES(?,?,?,?,?)";
    public final String UPDATE = "UPDATE `lots_players` SET fish_caught=?, scales=?, level=?, deliveries_completed=? WHERE uuid=?";

    protected Storage(final LuckOfTheSeaPlugin plugin) {
        this.plugin = plugin;
        this.cache = new Cache<>();

        connection = establishConnection();

        if (isConnected()) plugin.async(() -> execute(connection, CREATE_IF_NOT_EXIST));

    }

    public abstract Connection establishConnection();

    public boolean isConnected() {
        return connection != null;
    }

    /**
     * Requests User data from the database and stores to cache
     *
     * @param uuid {@link UUID} of the player
     */
    public void request(UUID uuid) {
        Objects.requireNonNull(uuid, "UUID cannot be null");
        query("SELECT * FROM `lots_players` WHERE uuid='" + uuid + "'").thenApply(resultSet -> {
            try {
                if (resultSet.next()) {
                    FisherImpl user = new FisherImpl(UUID.fromString(resultSet.getString(1)), resultSet.getInt(2), resultSet.getInt(3), resultSet.getInt(4), resultSet.getInt(5));
                    cache.put(uuid, user);
                    return user;
                }
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return null;
        });

    }

    /**
     * Create a user in the database
     *
     * @param uuid {@link UUID} of the player
     */
    public void create(UUID uuid) {
        Objects.requireNonNull(uuid, "UUID cannot be null");
        try {
            PreparedStatement statement = connection.prepareStatement(INSERT);
            statement.setString(1, uuid.toString()); // uuid
            statement.setInt(2, 0); // fish caught
            statement.setInt(3, 0); // scales
            statement.setInt(4, 0); // levels
            statement.setInt(5, 0); // deliveries completed
            statement.addBatch();
            update(statement);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            FisherImpl user = new FisherImpl(uuid, 0, 0, 0, 0);
            cache.put(uuid, user);
        }
    }

    /**
     * Save users data from the cache
     */
    @SneakyThrows
    public void save(Fisher user) {
        Objects.requireNonNull(user, "User cannot be null");
        PreparedStatement statement = connection.prepareStatement(UPDATE);
        try {
            statement.setInt(1, user.getTotalFishCaught());
            statement.setInt(2, user.getScales());
            statement.setInt(3, user.getLevel());
            statement.setInt(4, user.getDeliveriesCompleted());
            statement.setString(5, user.getUuid().toString());
            update(statement);
        } catch (Exception ignored) {
        } finally {
            cache.remove(user.getUuid());
        }
    }

    /**
     * Queries if a player is stored in database
     *
     * @param uuid player uuid
     * @return if player is stored in database
     */
    @SneakyThrows
    public boolean isStored(UUID uuid) {
        if (!isConnected())
            throw new SQLException("Database was not able to connect, and therefore cannot process request.");

        ResultSet resultSet = query("SELECT * FROM lots_players WHERE uuid= '" + uuid.toString() + "'").get();

        if (resultSet != null)
            try {
                if (resultSet.next())
                    return (resultSet.getString("uuid") != null);
            } catch (SQLException throwable) {
                throwable.printStackTrace();
            }
        return false;
    }

    /**
     * disconnects from the database
     * does this need to be done async?
     */
    public void shutdown() {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException ignored) {
            }
        }
    }

    /**
     * Updates table
     *
     * @param statement SQL statement
     */
    @SneakyThrows
    public void update(PreparedStatement statement) {
        if (!isConnected()) return;
        try {
            statement.executeUpdate();
            statement.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * Executes query
     *
     * @param connection sql connection
     * @param query      query to execute
     */
    @SneakyThrows
    public void execute(Connection connection, String query) {
        connection.createStatement().execute(query);
    }

    /**
     * Query the database asynchronously
     *
     * @param qry query to run
     * @return {@link CompletableFuture} of the {@link ResultSet}
     */
    public CompletableFuture<ResultSet> query(final String qry) {
        if (!isConnected()) return null;

        return CompletableFuture.supplyAsync(() -> {
            try {
                PreparedStatement ps = connection.prepareStatement(qry);
                return ps.executeQuery();
            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
            return null;
        });
    }

}
