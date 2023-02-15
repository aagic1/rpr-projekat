package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Ingredient;
import ba.unsa.etf.rpr.domain.Recipe;

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
    public Ingredient row2object(ResultSet rs) {
        try {
            Ingredient ingredient = new Ingredient();
            ingredient.setId(rs.getInt("id"));
            ingredient.setName(rs.getString("name"));
            ingredient.setAmount(rs.getInt("amount"));
            ingredient.setMeasurementUnit(rs.getString("measurement_unit"));
            ingredient.setRecipe(RecipeDaoSQLImpl.getInstance().getById(rs.getInt("recipe_id")));
            return ingredient;
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
    public List<Ingredient> getIngredientsByRecipe(Recipe recipe) {
        String sql = "SELECT * FROM ingredient WHERE recipe_id=?";
        try {
            PreparedStatement pstmt = getConnection().prepareStatement(sql);
            pstmt.setInt(1, recipe.getId());
            ResultSet rs = pstmt.executeQuery();
            ArrayList<Ingredient> ingredients = new ArrayList<>();
            while (rs.next()) {
                ingredients.add(row2object(rs));
            }
            return ingredients;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
