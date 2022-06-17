package tk.melosh.troldehvalp.events;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import tk.melosh.troldehvalp.Troldehvalp;
import tk.melosh.troldehvalp.database.models.PlayerModel;

import java.sql.SQLException;


public class MobKillEvent implements Listener {

    Troldehvalp plugin;

    public MobKillEvent(Troldehvalp plugin) {
        this.plugin = plugin;
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent event) {
        LivingEntity target = event.getEntity();
        LivingEntity killer;
        if(target.getType() != EntityType.COW)
            return;

        if(target.getKiller().getType() != EntityType.PLAYER) {
            return;
        }

        killer = target.getKiller().getPlayer();
        if(killer == null) {
            return;
        }
        try {
            PlayerModel player = PlayerModel.getPlayer(killer.getUniqueId());
            if(player == null) {
                player = new PlayerModel(killer.getUniqueId(), killer.getName(), 100);
            }
            player.money += 10;

            player.save();
        } catch (SQLException e) {
            plugin.LOGGER.info("exception reached ");
            plugin.LOGGER.severe(e.getMessage());
        }
    }
}
