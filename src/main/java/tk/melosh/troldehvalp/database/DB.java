package tk.melosh.troldehvalp.database;

import tk.melosh.troldehvalp.Troldehvalp;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class DB {
    private String path = null;
    private Troldehvalp plugin = null;
    private Boolean hasStarted = false;
    private static DB instance = null;

    private DB() {

    }

    public static DB getInstance() {
        if(instance == null) {
            instance = new DB();
        }

        return instance;
    }

    public void init() {
        try {
            System.out.print(Class.forName("org.sqlite.JDBC"));
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        if(hasStarted)
            throw new RuntimeException("(DB) database has already been initiated");
        instance.hasStarted = true;
        Connection connection = getConnection();
        if(connection == null) {
            hasStarted = false;
            throw new RuntimeException("failed to get connection");
        }
        String sql = "CREATE TABLE IF NOT EXISTS players(uuid TEXT PRIMARY KEY, username TEXT NOT NULL, money INT NOT NULL DEFAULT 100)";
        try {
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.execute();
            connection.close();
        } catch (SQLException e) {
            hasStarted = false;
            throw new RuntimeException(e);
        }
    }

    public Connection getConnection() {
        try {
            Class.forName("org.sqlite.JDBC");
            if(!hasStarted)
                throw new RuntimeException("(DB) db Has not been initiated yet");
            if(path == null)
                throw new RuntimeException("(DB) path has to be set");
            if(plugin == null)
                throw new RuntimeException("(DB) plugin has to be set");

            return  DriverManager.getConnection(path);

        } catch (ClassNotFoundException | SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void setPath(String path) {
        this.path = path;
    }

    public void setPlugin(Troldehvalp plugin) {
        this.plugin = plugin;
    }
}
