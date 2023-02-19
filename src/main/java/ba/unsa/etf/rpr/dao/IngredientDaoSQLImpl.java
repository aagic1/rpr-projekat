package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Ingredient;
import ba.unsa.etf.rpr.domain.Recipe;
import ba.unsa.etf.rpr.exception.RecipeException;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * MySQL implementation of DAO
 * @author Ahmed Agic
 */
public class IngredientDaoSQLImpl extends AbstractDao<Ingredient> implements IngredientDao {
    private static IngredientDaoSQLImpl instance = null;

    private IngredientDaoSQLImpl() {
        super("ingredient");
    }

    public static IngredientDaoSQLImpl getInstance() {
        if (instance == null) {
            instance = new IngredientDaoSQLImpl();
        }
        return instance;
    }

    public static void removeInstance() {
        if (instance != null) {
            instance = null;
        }
    }

    @Override
    public Ingredient row2object(ResultSet rs) throws RecipeException {
        try {
            Ingredient ingredient = new Ingredient();
            ingredient.setId(rs.getInt("id"));
            ingredient.setName(rs.getString("name"));
            ingredient.setAmount(rs.getString("amount"));
            ingredient.setMeasurementUnit(rs.getString("measurement_unit"));
            ingredient.setRecipe(DaoFactory.recipeDao().getById(rs.getInt("recipe_id")));
            return ingredient;
        } catch (SQLException e) {
            throw new RecipeException(e.getMessage(), e);
        }
    }

    @Override
    public Map<String, Object> object2row(Ingredient object) {
        Map<String, Object> row = new TreeMap<>();
        row.put("id", object.getId());
        row.put("recipe_id", object.getRecipe().getId());
        row.put("name", object.getName());
        row.put("amount", object.getAmount());
        row.put("measurement_unit", object.getMeasurementUnit());

        return row;
    }

    @Override
    public List<Ingredient> getIngredientsByRecipe(Recipe recipe) throws RecipeException{
        String sql = "SELECT * FROM ingredient WHERE recipe_id=?";
        return executeQuery(sql, new Object[]{recipe.getId()});
    }

    @Override
    public void deleteIngredientsFromRecipe(Recipe recipe) throws RecipeException {
        String sql = "DELETE FROM ingredient WHERE recipe_id=?";
        executeQuery(sql, new Object[]{recipe.getId()});
    }
}
