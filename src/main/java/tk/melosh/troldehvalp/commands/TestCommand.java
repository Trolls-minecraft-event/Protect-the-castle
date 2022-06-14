package tk.melosh.troldehvalp.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import tk.melosh.troldehvalp.Troldehvalp;
import tk.melosh.troldehvalp.Utils;

public class TestCommand implements CommandExecutor {
    private Troldehvalp plugin;

    public TestCommand(Troldehvalp plugin) {
        this.plugin = plugin;
        plugin.getCommand("test").setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("denne command er kun til ingame brug!");
            return false;
        }

        sender.sendMessage(Utils.format_msg("&4everything good here"));
        return true;
    }
}
