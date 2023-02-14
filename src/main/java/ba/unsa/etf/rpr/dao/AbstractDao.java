package ba.unsa.etf.rpr.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public abstract class AbstractDao<T> implements Dao<T> {
    private static Connection connection = null;
    private String tableName;

    public AbstractDao(String tableName) {
        this.tableName = tableName;
        createConnection();
    }

    private static void createConnection() {
        if (connection == null) {
            try {
                String url = "jdbc:mysql://localhost:3306/e_kuharica";
                String username = "root";
                String password = "password";
                connection = DriverManager.getConnection(url, username, password);
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }));
            }
        }
    }

    public static Connection getConnection() {
        if (connection == null) {
            createConnection();
        }
        return connection;
    }
}
