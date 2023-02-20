package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Idable;
import ba.unsa.etf.rpr.domain.Recipe;
import ba.unsa.etf.rpr.exception.RecipeException;

import java.io.FileInputStream;
import java.sql.*;
import java.util.*;

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
                Properties p = new Properties();
                p.load(new FileInputStream("src/main/resources/application.properties"));
                String url = p.getProperty("db.connection_string");
                String username = p.getProperty("db.username");
                String password = p.getProperty("db.password");
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

    public abstract T row2object(ResultSet rs) throws RecipeException;

    public abstract Map<String, Object> object2row(T object);

    @Override
    public T getById(int id) throws RecipeException {
        String sql = "SELECT * FROM " + this.tableName + " WHERE id=?";
        return executeQueryUnique(sql, new Object[]{id});
    }



    @Override
    public List<T> getAll() throws RecipeException{
        String sql = "SELECT * FROM " + this.tableName;
        return executeQuery(sql, null);
    }

    @Override
    public T add(T item) throws RecipeException {
        Map<String, Object> row = object2row(item);
        Map.Entry<String, String> columns = prepareInsertParts(row);

        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("INSERT INTO ").append(this.tableName)
                .append(" (").append(columns.getKey()).append(") ")
                .append(" VALUES(").append(columns.getValue()).append(");");

        try {
            PreparedStatement pstmt = connection.prepareStatement(sqlBuilder.toString(), Statement.RETURN_GENERATED_KEYS);
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
            throw new RecipeException(e.getMessage(), e);
        }
    }

    @Override
    public T update(T item) throws RecipeException {
        Map<String, Object> row = object2row(item);
        String updateColumns = prepareUpdateParts(row);
        StringBuilder builder = new StringBuilder();
        builder.append("UPDATE ").append(this.tableName)
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
            throw new RecipeException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(T item) throws RecipeException {
        StringBuilder sqlBuilder = new StringBuilder();
        sqlBuilder.append("DELETE FROM ").append(this.tableName)
                .append(" WHERE id=?");
        try {
            PreparedStatement pstmt = connection.prepareStatement(sqlBuilder.toString());
            pstmt.setObject(1, item.getId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            throw new RecipeException(e.getMessage(), e);
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

    /**
     * Utility method for executing any kind of query
     * @param query - SQL query
     * @param params - parameters for query
     * @return List of objects from database
     * @throws RecipeException in case of error with db
     */
    public List<T> executeQuery(String query, Object[] params) throws RecipeException {
        try {
            PreparedStatement pstmt = connection.prepareStatement(query);
            if (params != null) {
                for (int i = 1; i <= params.length; i++) {
                    pstmt.setObject(i, params[i-1]);
                }
            }
            ResultSet rs = pstmt.executeQuery();
            ArrayList<T> resultList = new ArrayList<>();
            while (rs.next()) {
                resultList.add(row2object(rs));
            }
            return resultList;
        } catch (SQLException e) {
            throw new RecipeException(e.getMessage(), e);
        }
    }

    /**
     * Utility method for query execution that always returns a single record
     * @param query - SQL query that returns single record
     * @param params - parameters for query
     * @return single object from database
     * @throws RecipeException in case when object is not found
     */
    public T executeQueryUnique(String query, Object[] params) throws RecipeException{
        List<T> result = executeQuery(query, params);
        if (result != null && result.size() == 1) {
            return result.get(0);
        } else {
            throw new RecipeException("Object not found");
        }
    }
}
