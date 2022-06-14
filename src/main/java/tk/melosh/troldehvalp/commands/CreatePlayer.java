package tk.melosh.troldehvalp.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tk.melosh.troldehvalp.Troldehvalp;
import tk.melosh.troldehvalp.database.models.PlayerModel;

import java.util.UUID;

public class CreatePlayer implements CommandExecutor {
    Troldehvalp plugin;

    public CreatePlayer(Troldehvalp plugin) {
        this.plugin = plugin;
        plugin.getCommand("createplayer").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(args.length == 0) {
            sender.sendMessage("arg 1 missing. please provide a player");
            return false;
        }
        Player target = Bukkit.getPlayer(args[0]);
        if(target == null) {
            sender.sendMessage("player not found");
            return false;
        }
        UUID playerUUID = target.getUniqueId();
        String username = target.getName();

        sender.sendMessage(playerUUID.toString());
        sender.sendMessage(username);

        PlayerModel newPlayer = new PlayerModel(playerUUID, username, 100, plugin);

        try {
            newPlayer.save();
        } catch (RuntimeException e) {
            sender.sendMessage("failed to run query");
            sender.sendMessage(e.getMessage());
            return true;
        }
        return true;
    }
}
