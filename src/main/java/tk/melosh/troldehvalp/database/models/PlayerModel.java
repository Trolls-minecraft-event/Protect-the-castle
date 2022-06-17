package tk.melosh.troldehvalp.database.models;

import tk.melosh.troldehvalp.database.DB;

import javax.annotation.Nullable;
import java.sql.*;
import java.util.UUID;


public class PlayerModel {
    public UUID uuid;
    public String username;
    public int money;


    public PlayerModel(UUID uuid, String username, int money) {
        this.uuid = uuid;
        this.username = username;
        this.money = money;
    }

    public static PlayerModel getPlayer(UUID playerUUID) throws SQLException {
        Connection connection = DB.getInstance().getConnection();
        String sql = "SELECT * FROM players WHERE uuid = ? LIMIT 1";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, playerUUID.toString());
        ResultSet rs = statement.executeQuery();
        if(!rs.next()) {
            System.out.println(statement);
            System.out.println("player is null");
            return null;
        }

        PlayerModel fetchedPlayer = new PlayerModel(UUID.fromString(rs.getString("uuid")), rs.getString("username"), rs.getInt("money"));
        connection.close();
        return fetchedPlayer;
    }

    @Nullable
    public Boolean save() {
        Connection conn = DB.getInstance().getConnection();

        String sql;

        try {
            PlayerModel player = getPlayer(this.uuid);
            if(player != null) {
                sql = "UPDATE players SET money = ?, username = ? WHERE uuid = ?";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setInt(1, money);
                stmt.setString(2, username);
                stmt.setString(3, uuid.toString());
                stmt.execute();
            } else {
                sql = "INSERT INTO players(username, money, uuid) VALUES(?,?,?)";
                PreparedStatement stmt = conn.prepareStatement(sql);
                stmt.setString(1, username);
                stmt.setInt(2, money);
                stmt.setString(3, uuid.toString());
                stmt.executeUpdate();
            }
            conn.close();

        } catch (SQLException e) {
            return false;
        }
        return true;

    }
}
