package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Recipe;

import java.sql.ResultSet;
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
        return null;
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
