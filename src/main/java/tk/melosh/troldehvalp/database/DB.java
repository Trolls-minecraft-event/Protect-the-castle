package tk.melosh.troldehvalp.database;

import org.apache.commons.dbcp2.BasicDataSource;
import tk.melosh.troldehvalp.Troldehvalp;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DB {
    private static DB instance = null;
    private String username;
    private String password;
    private String host;
    private String port;

    private String dbname;
    private String schema;
    private Troldehvalp plugin;
    private Boolean hasStarted = false;
    private BasicDataSource pool;

    private DB() {

    }

    public static DB getInstance() {
        if (instance == null) {
            instance = new DB();
        }

        return instance;
    }

    public void init() {
        try {
            if (hasStarted)
                throw new RuntimeException("(DB) database has already been initiated");
            instance.hasStarted = true;
            plugin.LOGGER.info(String.format("(DB) connecting to %s:%s/%s username:%s password:%s schema%s", host, port, dbname, username, password, schema));
            if (username == null || password == null || host == null || port == null || dbname == null || schema == null)
                throw new RuntimeException("(DB) database credentials are not set");
            URI uri = new URI(String.format("jdbc:postgresql://%s:%s/%s", host, port, dbname));
            pool = new BasicDataSource();

            pool.setUsername(username);
            pool.setPassword(password);
            pool.setDefaultSchema(schema);
            pool.setDriverClassName("org.postgresql.Driver");
            pool.setUrl(uri.toString());
            pool.setInitialSize(1);

            String sql = "" +
                    "CREATE TABLE IF NOT EXISTS players(uuid TEXT PRIMARY KEY, username TEXT NOT NULL, money INT NOT NULL DEFAULT 100);";

            Connection conn = pool.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql);
            stmt.execute();

        } catch (URISyntaxException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        if (!hasStarted)
            throw new RuntimeException("(DB) database has not been initiated");
        try {
            return pool.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public void setDbname(String dbname) {
        this.dbname = dbname;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public void setPlugin(Troldehvalp plugin) {
        this.plugin = plugin;
    }

}
