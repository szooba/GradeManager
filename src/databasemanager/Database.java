package databasemanager;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    public static Connection conn = null;

    private Database() {
    }

    @SuppressWarnings("CallToPrintStackTrace")
    public static Connection connect() {
        if(conn == null) {
            String url = "jdbc:sqlite:database/gradedatabase.db?foreign_keys=on";
            try {
                conn = DriverManager.getConnection(url);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return conn;
    }
}

