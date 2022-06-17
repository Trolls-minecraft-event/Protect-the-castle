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
        plugin.LOGGER.info("inside event");
        LivingEntity target = event.getEntity();
        LivingEntity killer;
        plugin.LOGGER.info(target.getType().name());
        if(target.getType() != EntityType.COW)
            return;

        if(target.getKiller().getType() != EntityType.PLAYER) {
            plugin.LOGGER.info("killer isnt a player");
            return;
        }

        killer = target.getKiller().getPlayer();
        if(killer == null) {
            plugin.LOGGER.severe("killer is null");
            return;
        }
        try {
            PlayerModel player = PlayerModel.getPlayer(killer.getUniqueId());
            if(player == null) {
                player = new PlayerModel(killer.getUniqueId(), killer.getName(), 100);
            }
            player.money += 10;
            plugin.LOGGER.info(String.format("player money = %s", player.money));
            if(player.save()) {
                plugin.LOGGER.info("player saved");
                PlayerModel p = PlayerModel.getPlayer(player.uuid);
                plugin.LOGGER.info(String.valueOf(p.money));
            }else {
                plugin.LOGGER.info("player not saved");
            }
        } catch (SQLException e) {
            plugin.LOGGER.severe(e.getMessage());
        }
    }
}
