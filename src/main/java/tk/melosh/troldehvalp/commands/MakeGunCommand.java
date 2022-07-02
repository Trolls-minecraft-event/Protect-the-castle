package tk.melosh.troldehvalp.commands;

import io.github.bananapuncher714.nbteditor.NBTEditor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import tk.melosh.troldehvalp.Troldehvalp;
import tk.melosh.troldehvalp.item.ItemManager;

import java.net.Inet4Address;

public class MakeGunCommand implements CommandExecutor {

    Troldehvalp plugin;

    public MakeGunCommand(Troldehvalp plugin) {
        this.plugin = plugin;
        plugin.getCommand("makegun").setExecutor(this);
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("This command is only available ingame.");
            return true;
        }
        if(args.length < 3 || args.length > 3) {
            sender.sendMessage("missing arguments");
            return true;
        }

        Player player = (Player) sender;

        ItemStack item = player.getInventory().getItemInMainHand();

        if(item == null) {
            sender.sendMessage("You must hold an item in your hand.");
            return true;
        }
        int size;
        int range;
        int damage;

        try {
            range = Integer.parseInt(args[0]);
            size = Integer.parseInt(args[1]);
            damage = Integer.parseInt(args[2]);

        } catch (NumberFormatException e) {
            plugin.LOGGER.info(String.format("Failed to parse int: %s", e.getMessage()));
            return false;
        }

        ItemStack newItem = NBTEditor.set(item, "gun", "event", "type");
        newItem = NBTEditor.set(newItem, range, "event", "gun", "range");
        newItem = NBTEditor.set(newItem, size, "event", "gun", "size");
        newItem = NBTEditor.set(newItem, damage, "event", "gun", "damage");

        player.getInventory().setItem(player.getInventory().getHeldItemSlot(), newItem);

        return true;
    }
}
