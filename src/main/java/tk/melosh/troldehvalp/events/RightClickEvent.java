package tk.melosh.troldehvalp.events;

import io.github.bananapuncher714.nbteditor.NBTEditor;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import tk.melosh.troldehvalp.Troldehvalp;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class RightClickEvent implements Listener {
    Troldehvalp plugin;

    private Map<UUID, String> cooldowns = new HashMap<>();

    public RightClickEvent(Troldehvalp plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        ItemStack item = event.getItem();
        if(item == null)
            return;

        if(!(NBTEditor.contains(item, "event"))) {
            return;
        }
        if(!(NBTEditor.getString(item, "event", "type").equals("gun"))) {
            return;
        }
        event.setCancelled(true);
        int damage = NBTEditor.getInt(item, "event", "gun", "damage");
        int range = NBTEditor.getInt(item, "event", "gun", "range");
        int size = NBTEditor.getInt(item, "event", "gun", "size");

        Player player = event.getPlayer();



        Block block = player.getTargetBlockExact(50);
        World world = player.getWorld();
        if(block == null)
            return;

        world.getNearbyEntities(block.getLocation(), range, range, range, e -> e instanceof Mob).forEach(e -> {
            if(e instanceof Mob) {
                ((Mob) e).damage(damage);
            }
        });



        world.spawnParticle(Particle.EXPLOSION_NORMAL, block.getLocation().add(0d, 2d, 0d), size);

    }
}
