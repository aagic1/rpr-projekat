package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Ingredient;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Map;

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
            ingredient.setAmount(rs.getInt("amount"));
            ingredient.setMeasurementUnit(rs.getString("measurement_unit"));
            return ingredient;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Map<String, Object> object2row(Ingredient object) {
        return null;
    }
}
