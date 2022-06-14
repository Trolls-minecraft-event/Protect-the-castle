package tk.melosh.troldehvalp.database.models;

import tk.melosh.troldehvalp.Troldehvalp;

import javax.annotation.Nullable;
import java.sql.*;
import java.util.UUID;


public class PlayerModel {
    public UUID uuid;
    public String username;
    public int money;
    static Troldehvalp plugin;


    public PlayerModel(UUID uuid, String username, int money, Troldehvalp plugin) {
        this.uuid = uuid;
        this.username = username;
        this.money = money;
        PlayerModel.plugin = plugin;
    }

    @Nullable
    public Boolean save() {
        Connection conn = plugin.db.getConnection();
        ResultSet rs;
        try {
            String sql = "SELECT 1 FROM players WHERE uuid = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, uuid.toString());
            statement.execute();
            rs = statement.getResultSet();

            if(rs.next()) {
                sql = "UPDATE players SET username = ?, money = ? WHERE uuid = ?";
                statement = conn.prepareStatement(sql);
                statement.setString(1, username);
                statement.setInt(2, money);
                statement.setString(3, uuid.toString());
            }
            statement.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        String sql;
        plugin.LOGGER.info("getting player for if statement");

        sql = "INSERT INTO players(username, money, uuid) VALUES(?,?,?)";
        plugin.LOGGER.info(sql);

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setInt(2, money);
            stmt.setString(3, uuid.toString());
            stmt.executeUpdate();
            stmt.close();
            conn.close();
            plugin.LOGGER.info("executed statement");
            return true;
        } catch (SQLException e) {
            plugin.LOGGER.severe(e.getSQLState());
            return false;
        }

    }
}
