package ba.unsa.etf.rpr.business;

import ba.unsa.etf.rpr.domain.Ingredient;
import ba.unsa.etf.rpr.domain.Instruction;
import ba.unsa.etf.rpr.exception.RecipeException;

public class RecipeManager {

    public void validateTitle(String title) throws RecipeException {
        if (title.isBlank()) {
            throw new RecipeException("Recipe title can not be empty");
        }
    }

    public void validateDescription(String description) throws RecipeException {
        if (description.isBlank()) {
            throw new RecipeException("Recipe description can not be empty");
        }
    }

    public void validatePreparationTime(int minutes, int hours) throws RecipeException {
        if (minutes == 0 && hours == 0) {
            throw new RecipeException("Preparation time can not be 0");
        }
    }

    public void validateServings(int servings) throws RecipeException {
        if (servings == 0) {
            throw new RecipeException("Number of servings can not be 0");
        }
    }

    public void validateIngredient(Ingredient ingredient) throws RecipeException {
        if (ingredient.getName().isBlank()) {
            throw new RecipeException("Ingredient name can not be empty");
        }
    }

    public void validateInstruction(Instruction instruction) throws RecipeException {
        if (instruction.getDescription().isBlank()) {
            throw new RecipeException("Instruction description can not be empty");
        }
    }
}
