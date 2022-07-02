package tk.melosh.troldehvalp.commands;

import org.bukkit.Bukkit;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import tk.melosh.troldehvalp.Troldehvalp;

public class FireTestCommand implements CommandExecutor {
    Troldehvalp plugin;

    public FireTestCommand(Troldehvalp plugin) {
        this.plugin = plugin;
        this.plugin.getCommand("firetest").setExecutor(this);
    }


    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(!(sender instanceof Player)) {
            sender.sendMessage("This can only be used ingame");
            return true;
        }

        Player player = (Player) sender;

        Block block = player.getTargetBlockExact(20);
        World world = player.getWorld();
        world.getNearbyEntities(block.getLocation(), 5, 5, 5, e -> e instanceof Mob).forEach(e -> {
            if(e instanceof Mob) {
                ((Mob) e).setHealth(0d);
            }
        });

        world.spawnParticle(Particle.EXPLOSION_LARGE, block.getLocation().add(0d, 2d, 0d), 10);

        return true;
    }
}
