package ba.unsa.etf.rpr.business;

import ba.unsa.etf.rpr.dao.DaoFactory;
import ba.unsa.etf.rpr.domain.Instruction;
import ba.unsa.etf.rpr.domain.Recipe;
import ba.unsa.etf.rpr.domain.User;
import ba.unsa.etf.rpr.exception.RecipeException;

import java.util.List;

public class RecipeManager {

    public List<Recipe> getRecipesByUser(User user) throws RecipeException {
        return DaoFactory.recipeDao().searchByOwner(user);
    }

    public List<Recipe> getRecipesByTitle(String text) throws RecipeException {
        return DaoFactory.recipeDao().searchByTitle(text);
    }

    public Recipe add(Recipe recipe) throws RecipeException {
        return DaoFactory.recipeDao().add(recipe);
    }

    public Recipe update(Recipe recipe) throws RecipeException {
        return DaoFactory.recipeDao().update(recipe);
    }

    public void delete(Recipe recipe) throws RecipeException {
        DaoFactory.recipeDao().delete(recipe);
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
