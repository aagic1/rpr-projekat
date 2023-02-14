package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Idable;

import java.sql.*;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Abstract class that implements core DAO CRUD methods for every entity
 *
 * @author Ahmed Agic
 */
public abstract class AbstractDao<T extends Idable> implements Dao<T> {
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

    public abstract Map<String, Object> object2row(T object);

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

    @Override
    public List<T> getAll() {
        String sql = "SELECT * FROM " + this.tableName;
        try {
            PreparedStatement pstmt = connection.prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            ArrayList<T> resultList = new ArrayList<>();
            while (rs.next()) {
                resultList.add(row2object(rs));
            }
            return resultList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public T add(T item) {
        Map<String, Object> row = object2row(item);
        Map.Entry<String, String> columns = prepareInsertParts(row);

        StringBuilder builder = new StringBuilder();
        builder.append("INSERT INTO ").append(this.tableName)
                .append(" (").append(columns.getKey()).append(") ")
                .append(" VALUES(").append(columns.getValue()).append(");");

        try {
            PreparedStatement pstmt = connection.prepareStatement(builder.toString(), Statement.RETURN_GENERATED_KEYS);
            int counter = 1;
            for (Map.Entry<String, Object> entry : row.entrySet()) {
                if (entry.getKey().equals("id")) {
                    continue;
                }
                pstmt.setObject(counter, entry.getValue());
                counter++;
            }
            pstmt.executeUpdate();

            ResultSet rs = pstmt.getGeneratedKeys();
            rs.next();
            item.setId(rs.getInt(1));

            return item;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public T update(T item) {
        Map<String, Object> row = object2row(item);
        String updateColumns = prepareUpdateParts(row);
        StringBuilder builder = new StringBuilder();
        builder.append("UPDATE").append(this.tableName)
                .append(" SET ").append(updateColumns)
                .append(" WHERE id=?");
        try {
            PreparedStatement pstmt = connection.prepareStatement(builder.toString());
            int counter = 1;
            for (Map.Entry<String, Object> entry : row.entrySet()) {
                if (entry.getKey().equals("id")) {
                    continue;
                }
                pstmt.setObject(counter, entry.getValue());
                counter++;
            }
            pstmt.setObject(counter, item.getId());
            pstmt.executeUpdate();
            return item;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(T item) {
        StringBuilder builder = new StringBuilder();
        builder.append("DELETE FROM ").append(this.tableName)
                .append("WHERE id=?");
        try {
            PreparedStatement pstmt = connection.prepareStatement(builder.toString());
            pstmt.setObject(1, item.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private String prepareUpdateParts(Map<String, Object> row) {
        StringBuilder columns = new StringBuilder();

        int counter = 0;
        for (Map.Entry<String, Object> entry : row.entrySet()) {
            counter++;
            if (entry.getKey().equals("id")) {
                continue;
            }
            columns.append(entry.getKey()).append("=?");
            if (row.size() != counter) {
                columns.append(",");
            }
        }
        return columns.toString();
    }

    private Map.Entry<String, String> prepareInsertParts(Map<String, Object> row) {
        StringBuilder columns = new StringBuilder();
        StringBuilder questions = new StringBuilder();

        int counter = 0;
        for (Map.Entry<String, Object> entry : row.entrySet()) {
            counter++;
            if (entry.getKey().equals("id")) {
                continue;
            }
            columns.append(entry.getKey());
            questions.append("?");
            if (row.size() != counter) {
                columns.append(",");
                questions.append(",");
            }
        }
        return new AbstractMap.SimpleEntry<>(columns.toString(), questions.toString());
    }

}
