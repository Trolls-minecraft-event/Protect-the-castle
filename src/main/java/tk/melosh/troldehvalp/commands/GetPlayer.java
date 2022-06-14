package tk.melosh.troldehvalp.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tk.melosh.troldehvalp.Troldehvalp;
import tk.melosh.troldehvalp.database.models.PlayerModel;

import java.sql.*;
import java.util.UUID;

public class GetPlayer implements CommandExecutor {
        public Troldehvalp plugin;

    public GetPlayer(Troldehvalp plugin) {
        this.plugin = plugin;
        plugin.getCommand("getplayer").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("unavailable command in console");
            return true;
        }
        if(args.length == 0) {
            sender.sendMessage("arg 1 missing. please provide a player");
            return false;
        }
        Player player = Bukkit.getPlayer(args[0]);

        Connection conn = plugin.db.getConnection();
        ResultSet rs;
        try {
            String sql = "SELECT 1 FROM players WHERE uuid = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, player.getUniqueId().toString());
            statement.execute();
            rs = statement.getResultSet();

            if(!rs.next()) {
                sender.sendMessage("failed to fetch user rs.next()");
            }
            statement.close();
            conn.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        sender.sendMessage(String.format("%s", rs));
        return true;
    }
}
