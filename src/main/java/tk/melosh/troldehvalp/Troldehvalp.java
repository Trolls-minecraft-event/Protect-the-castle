package tk.melosh.troldehvalp;

import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.java.JavaPlugin;
import tk.melosh.troldehvalp.commands.*;
import tk.melosh.troldehvalp.database.DB;
import tk.melosh.troldehvalp.events.MobKillEvent;
import tk.melosh.troldehvalp.events.RightClickEvent;

import java.io.File;
import java.sql.Connection;
import java.util.logging.Logger;

public final class Troldehvalp extends JavaPlugin {
    public Logger LOGGER = this.getLogger();
    public Configuration CONFIG = this.getConfig();
    public DB db = DB.getInstance();

    @Override
    public void onEnable() {

        LOGGER.info("Event plugin starter");


        // Register commands
        new TestCommand(this);
        new GetPlayerCommand(this);
        new CreatePlayerCommand(this);
        new FireTestCommand(this);
        new MakeGunCommand(this);

        // Register event listeners
        new MobKillEvent(this);
        new RightClickEvent(this);
        // Config Stuff
        this.saveDefaultConfig();

        // Data management
        if(!getDataFolder().isDirectory())
            getDataFolder().mkdirs();

        db.setDbname(CONFIG.getString("db.database"));
        db.setHost(CONFIG.getString("db.host"));
        db.setPort(CONFIG.getString("db.port"));
        db.setUsername(CONFIG.getString("db.username"));
        db.setPassword(CONFIG.getString("db.password"));
        db.setSchema(CONFIG.getString("db.schema"));
        db.setPlugin(this);
        db.init();

        Connection connection = db.getConnection();
        if(connection == null) {
            LOGGER.severe("db connection is null. Disabling");
            this.getPluginLoader().disablePlugin(this);
        }
    }

    @Override
    public void onDisable() {
        LOGGER.warning("event plugin stopper");
        // Plugin shutdown logic
    }
}
