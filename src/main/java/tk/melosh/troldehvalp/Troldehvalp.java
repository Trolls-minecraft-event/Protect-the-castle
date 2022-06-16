package tk.melosh.troldehvalp;

import org.bukkit.configuration.Configuration;
import org.bukkit.plugin.java.JavaPlugin;
import tk.melosh.troldehvalp.commands.CreatePlayerCommand;
import tk.melosh.troldehvalp.commands.GetPlayerCommand;
import tk.melosh.troldehvalp.commands.TestCommand;
import tk.melosh.troldehvalp.database.DB;

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

        // Register event listeners

        // Config Stuff
        this.saveDefaultConfig();

        // Data management
        if(!getDataFolder().isDirectory())
            getDataFolder().mkdirs();

        File sqliteDbFile = new File(String.format("%s/%s", getDataFolder(), CONFIG.getString("sqlite.DBpath")));

        db.setPath(String.format("jdbc:sqlite:%s", sqliteDbFile.getAbsolutePath()));
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
