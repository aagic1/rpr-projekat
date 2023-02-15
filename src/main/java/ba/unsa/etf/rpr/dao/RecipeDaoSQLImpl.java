package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Recipe;
import ba.unsa.etf.rpr.domain.User;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class RecipeDaoSQLImpl extends AbstractDao<Recipe> implements RecipeDao {
    private static RecipeDaoSQLImpl instance = null;

    public static RecipeDaoSQLImpl getInstance() {
        if (instance == null) {
            instance = new RecipeDaoSQLImpl();
        }
        return instance;
    }

    public static void removeInstance() {
        if (instance != null) {
            instance = null;
        }
    }

    public RecipeDaoSQLImpl() {
        super("recipe");
    }

    @Override
    public Recipe row2object(ResultSet rs) {
        Recipe recipe = new Recipe();
        try {
            recipe.setId(rs.getInt("id"));
            recipe.setOwner(DaoFactory.userDao().getById(rs.getInt("owner_id")));
            recipe.setTitle(rs.getString("title"));
            recipe.setDescription(rs.getString("description"));
            recipe.setDate(rs.getDate("date").toLocalDate());
            recipe.setPreparationTime(rs.getInt("preparation_time"));
            recipe.setCookTime(rs.getInt("cook_time"));
            recipe.setServings(rs.getInt("servings"));
            recipe.setNotes(rs.getString("notes"));
            return recipe;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<String, Object> object2row(Recipe object) {
        Map<String, Object> row = new TreeMap<>();
        row.put("id", object.getId());
        row.put("owner_id", object.getOwner().getId());
        row.put("title", object.getTitle());
        row.put("description", object.getDescription());
        row.put("date", Date.valueOf(object.getDate()));
        row.put("preparation_time", object.getPreparationTime());
        row.put("cook_time", object.getCookTime());
        row.put("servings", object.getServings());
        row.put("notes", object.getNotes());

        return row;
    }

    @Override
    public List<Recipe> searchByTitle(String text) {
        String sql = "SELECT * FROM recipe WHERE title LIKE '%" + text + "%'";
        try {
            PreparedStatement pstmt = getConnection().prepareStatement(sql);
            ResultSet rs = pstmt.executeQuery();
            ArrayList<Recipe> recipes = new ArrayList<>();
            while (rs.next()) {
                recipes.add(row2object(rs));
            }
            return recipes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Recipe> searchByOwner(User owner) {
        String sql = "SELECT * FROM recipe WHERE owner_id=?";
        try {
            PreparedStatement pstmt = getConnection().prepareStatement(sql);
            pstmt.setInt(1, owner.getId());
            ResultSet rs = pstmt.executeQuery();
            ArrayList<Recipe> recipes = new ArrayList<>();
            while (rs.next()) {
                recipes.add(row2object(rs));
            }
            return recipes;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
