package tk.melosh.troldehvalp.database;

import tk.melosh.troldehvalp.Troldehvalp;

import java.sql.*;
import java.util.logging.Logger;

public class DB {
    public String path;
    public String table;
    public Troldehvalp plugin;
    public DB(String path, String table, Troldehvalp plugin) {
        if(plugin.CONFIG.getBoolean("debug")) {
            this.path = "jdbc:sqlite::memory:";
        } else {
            this.path = String.format("jdbc:sqlite:%s", path);
        }
        plugin.LOGGER.info(String.format("path = %s", this.path));
        this.table = table;
        this.plugin = plugin;

        Connection conn = getConnection();
        String sql = "SELECT * FROM main.sqlite_master WHERE type = \"table\" AND name = \"player\"";
        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            if(!(rs.next())) {
                plugin.LOGGER.severe("creating new database");
                sql = "CREATE TABLE players(uuid TEXT PRIMARY KEY NOT NULL , username TEXT NOT NULL, money INT DEFAULT 100)";
                PreparedStatement statement = conn.prepareStatement(sql);
                statement.execute();
                statement.close();
                conn.close();
            }
        } catch (SQLException e) {
            plugin.LOGGER.info(e.getSQLState());
            throw new RuntimeException(e);
        }

    }

    public Connection getConnection() {

        try {
            Class.forName("org.sqlite.JDBC");
            Connection connection = DriverManager.getConnection(path);
            if(connection == null) {
                throw new RuntimeException("failed to get connection");
            }
            connection.setAutoCommit(true);
            return connection;
        } catch (SQLException e) {
            plugin.LOGGER.severe("failed to connect to db. disabling!");
            plugin.getPluginLoader().disablePlugin(plugin);
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            plugin.LOGGER.severe("failed: sqlite missing. disabling!");
            throw new RuntimeException(e);
        }

    }
}
