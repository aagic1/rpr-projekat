package ba.unsa.etf.rpr.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.Map;

/**
 * Abstract class that implements core DAO CRUD methods for every entity
 *
 * @author Ahmed Agic
 */
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

    public abstract T row2object(ResultSet rs);


    @Override
    public T getById(int id) {
        String sql = "SELECT * FROM " + this.tableName + " WHERE id=?";
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            ArrayList<T> resultList = new ArrayList<>();
            while (rs.next()) {
                resultList.add(row2object(rs));
            }
            return resultList.get(0);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


}
