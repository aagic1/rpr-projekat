package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;
import java.util.TreeMap;

public class UserDaoSQLImplementation extends AbstractDao<User> implements UserDao{
    private static UserDaoSQLImplementation instance = null;

    public static UserDaoSQLImplementation getInstance() {
        if (instance == null) {
            instance = new UserDaoSQLImplementation();
        }
        return instance;
    }

    public static void removeInstance() {
        if (instance != null) {
            instance = null;
        }
    }

    public UserDaoSQLImplementation() {
        super("user");
    }

    @Override
    public User row2object(ResultSet rs) {
        User user = new User();
        try {
            user.setId(rs.getInt("id"));
            user.setUsername(rs.getString("username"));
            user.setEmail(rs.getString("email"));
            user.setPassword(rs.getString("password"));
            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<String, Object> object2row(User object) {
        Map<String, Object> row = new TreeMap<>();
        row.put("id", object.getId());
        row.put("username", object.getUsername());
        row.put("email", object.getEmail());
        row.put("password", object.getPassword());
        return row;
    }

    @Override
    public User searchByUsername(String username) {
        String sql = "SELECT * FROM user WHERE username=?";
        try {
            PreparedStatement pstmt = getConnection().prepareStatement(sql);
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return row2object(rs);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
