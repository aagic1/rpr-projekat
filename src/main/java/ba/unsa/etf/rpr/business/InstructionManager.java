package ba.unsa.etf.rpr.business;

import ba.unsa.etf.rpr.dao.DaoFactory;
import ba.unsa.etf.rpr.domain.Instruction;
import ba.unsa.etf.rpr.domain.Recipe;
import ba.unsa.etf.rpr.exception.RecipeException;

import java.util.List;

public class InstructionManager {

    public Instruction add(Instruction instruction) throws RecipeException {
        return DaoFactory.instructionDao().add(instruction);
    }

    public void removeInstructionsByRecipe(Recipe recipe) throws RecipeException {
        DaoFactory.instructionDao().deleteInstructionsByRecipe(recipe);
    }

    public List<Instruction> getInstructionsByRecipe(Recipe recipe) throws RecipeException {
        return DaoFactory.instructionDao().getInstructionsByRecipe(recipe);
    }

    public void validateInstruction(Instruction instruction) throws RecipeException {
        if (instruction.getDescription().isBlank()) {
            throw new RecipeException("Instruction description can not be empty");
        }
    }

    public void validateInstructions(List<Instruction> instructionList) throws RecipeException {
        for (Instruction instruction : instructionList) {
            validateInstruction(instruction);
        }
    }
}
