package ba.unsa.etf.rpr.business;

import ba.unsa.etf.rpr.dao.DaoFactory;
import ba.unsa.etf.rpr.domain.Ingredient;
import ba.unsa.etf.rpr.domain.Instruction;
import ba.unsa.etf.rpr.domain.Recipe;
import ba.unsa.etf.rpr.exception.RecipeException;

import java.util.List;

public class RecipeManager {
    public Recipe addRecipe(Recipe recipe) throws RecipeException {
        return DaoFactory.recipeDao().add(recipe);
    }

    public Recipe updateRecipe(Recipe recipe) throws RecipeException {
        return DaoFactory.recipeDao().update(recipe);
    }

    public Ingredient addIngredient(Ingredient ingredient) throws RecipeException {
        return DaoFactory.ingredientDao().add(ingredient);
    }

    public void removeIngredientsByRecipe(Recipe recipe) throws RecipeException {
        DaoFactory.ingredientDao().deleteIngredientsByRecipe(recipe);
    }

    public Instruction addInstruction(Instruction instruction) throws RecipeException {
        return DaoFactory.instructionDao().add(instruction);
    }

    public void removeInstructionsByRecipe(Recipe recipe) throws RecipeException {
        DaoFactory.instructionDao().deleteInstructionsByRecipe(recipe);
    }

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

    public void validatePreparationTime(int time) throws RecipeException {
        if (time == 0) {
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

    public void validateIngredients(List<Ingredient> ingredientList) throws RecipeException {
        for (Ingredient ingredient : ingredientList) {
            validateIngredient(ingredient);
        }
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

    public void validateRecipe(Recipe recipe) throws RecipeException {
        try {
            validateTitle(recipe.getTitle());
            validateDescription(recipe.getDescription());
            validateServings(recipe.getServings());
            validatePreparationTime(recipe.getPreparationTime());
        } catch (RecipeException e) {
            throw new RecipeException("Invalid recipe");
        }
    }
}
