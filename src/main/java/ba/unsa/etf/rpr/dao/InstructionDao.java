package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Instruction;
import ba.unsa.etf.rpr.domain.Recipe;
import ba.unsa.etf.rpr.exception.RecipeException;

import java.util.List;

/**
 * Dao interface for Instruction domain bean
 */
public interface InstructionDao extends Dao<Instruction> {
    List<Instruction> getInstructionsByRecipe(Recipe recipe) throws RecipeException;

    void deleteInstructionsByRecipe(Recipe recipe) throws RecipeException;
}
