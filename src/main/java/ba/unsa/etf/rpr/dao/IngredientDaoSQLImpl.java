package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Ingredient;

import java.sql.ResultSet;
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
        return null;
    }

    @Override
    public Map<String, Object> object2row(Ingredient object) {
        return null;
    }
}
