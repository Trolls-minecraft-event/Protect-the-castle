package tk.melosh.troldehvalp.database.models;

import tk.melosh.troldehvalp.Troldehvalp;
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

    public static PlayerModel getPlayer(UUID uuid) throws SQLException {
        Connection connection = DB.getInstance().getConnection();
        String sql = "SELECT 1 FROM players WHERE uuid = ?";
        PreparedStatement statement = connection.prepareStatement(sql);
        statement.setString(1, uuid.toString());
        ResultSet rs = statement.executeQuery();
        if(!rs.next()) {
            return null;
        }
        connection.close();
        return new PlayerModel(UUID.fromString(rs.getString("uuid")), rs.getString("username"), rs.getInt("money"));
    }

    @Nullable
    public Boolean save() {
        Connection conn = DB.getInstance().getConnection();
        
        String sql;

        sql = "INSERT INTO players(username, money, uuid) VALUES(?,?,?)";

        try {
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.setString(1, username);
            stmt.setInt(2, money);
            stmt.setString(3, uuid.toString());
            stmt.executeUpdate();
            conn.close();
            return true;
        } catch (SQLException e) {
            return false;
        }

    }
}
