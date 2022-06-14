package tk.melosh.troldehvalp;

import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.java.JavaPlugin;
import tk.melosh.troldehvalp.commands.CreatePlayer;
import tk.melosh.troldehvalp.commands.GetPlayer;
import tk.melosh.troldehvalp.commands.TestCommand;
import tk.melosh.troldehvalp.database.DB;

import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.util.logging.Logger;

public final class Troldehvalp extends JavaPlugin {
    public Logger LOGGER = this.getLogger();
    public Configuration CONFIG = this.getConfig();
    public DB db;
    @Override
    public void onEnable() {

        LOGGER.info("Event plugin starter");


        // Register commands
        new TestCommand(this);
        new GetPlayer(this);
        new CreatePlayer(this);

        // Config Stuff
        this.saveDefaultConfig();

        // Data management
        if(!getDataFolder().isDirectory())
            getDataFolder().mkdirs();

        File sqliteDbFile = new File(String.format("%s/%s", getDataFolder(), CONFIG.getString("sqlite.DBpath")));

        db = new DB(sqliteDbFile.getAbsolutePath(), CONFIG.getString("sqlite.table"), this);
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
