package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Recipe;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

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
            recipe.setTitle(rs.getString("title"));
            recipe.setDate(rs.getDate("date").toLocalDate());
            recipe.setDescription(rs.getString("description"));
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
        return null;
    }

    @Override
    public Recipe searchByTitle(String text) {
        return null;
    }
}
