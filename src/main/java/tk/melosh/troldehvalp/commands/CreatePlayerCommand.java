package tk.melosh.troldehvalp.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tk.melosh.troldehvalp.Troldehvalp;
import tk.melosh.troldehvalp.database.models.PlayerModel;

import java.sql.SQLException;
import java.util.UUID;

public class CreatePlayerCommand implements CommandExecutor {
    Troldehvalp plugin;

    public CreatePlayerCommand(Troldehvalp plugin) {
        this.plugin = plugin;
        plugin.getCommand("createplayer").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 0) {
            sender.sendMessage("Arg 1 missing. Please provide a player.");
            return false;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if(target == null) {
            sender.sendMessage("Player not found.");
            return false;
        }
        UUID playerUUID = target.getUniqueId();
        String username = target.getName();

        sender.sendMessage(playerUUID.toString());
        sender.sendMessage(username);

        try {
            if(PlayerModel.getPlayer(playerUUID) != null) {
                sender.sendMessage("Player already exists.");
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        PlayerModel newPlayer = new PlayerModel(playerUUID, username, 100);

        try {
            if(newPlayer.save()) {
                sender.sendMessage("Player saved.");
            } else {
                sender.sendMessage("Failed to save player. Are they already in the database?");
            }
        } catch (RuntimeException e) {
            sender.sendMessage("Failed to run query.");
            sender.sendMessage(e.getMessage());
            return true;
        }
        return true;
    }
}
