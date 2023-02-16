package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Ingredient;
import ba.unsa.etf.rpr.domain.Instruction;
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
public class InstructionDaoSQLImpl extends AbstractDao<Instruction> implements InstructionDao {
    private static InstructionDaoSQLImpl instance = null;

    public static InstructionDaoSQLImpl getInstance() {
        if (instance == null) {
            instance = new InstructionDaoSQLImpl();
        }
        return instance;
    }

    public static void removeInstance() {
        if (instance != null) {
            instance = null;
        }
    }

    public InstructionDaoSQLImpl() {
        super("instruction");
    }

    @Override
    public Instruction row2object(ResultSet rs) throws RecipeException {
        Instruction instruction = new Instruction();
        try {
            instruction.setId(rs.getInt("id"));
            instruction.setRecipe(DaoFactory.recipeDao().getById(rs.getInt("recipe_id")));
            instruction.setStep(rs.getInt("step"));
            instruction.setDescription(rs.getString("description"));
            return instruction;
        } catch (SQLException e) {
            throw new RecipeException(e.getMessage(), e);
        }
    }

    @Override
    public Map<String, Object> object2row(Instruction object) {
        Map<String, Object> row = new TreeMap<>();
        row.put("id", object.getId());
        row.put("recipe_id", object.getRecipe().getId());
        row.put("step", object.getStep());
        row.put("description", object.getDescription());
        return row;
    }

    @Override
    public List<Instruction> getInstructionsByRecipe(Recipe recipe) throws RecipeException {
        String sql = "SELECT * FROM instruction WHERE recipe_id=?";
        return executeQuery(sql, new Object[]{recipe.getId()});
    }
}
