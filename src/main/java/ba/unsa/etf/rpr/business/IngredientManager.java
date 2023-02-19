package ba.unsa.etf.rpr.business;

import ba.unsa.etf.rpr.dao.DaoFactory;
import ba.unsa.etf.rpr.domain.Ingredient;
import ba.unsa.etf.rpr.domain.Recipe;
import ba.unsa.etf.rpr.exception.RecipeException;

import java.util.List;

public class IngredientManager {
    public Ingredient add(Ingredient ingredient) throws RecipeException {
        return DaoFactory.ingredientDao().add(ingredient);
    }

    public void removeIngredientsByRecipe(Recipe recipe) throws RecipeException {
        DaoFactory.ingredientDao().deleteIngredientsByRecipe(recipe);
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
}
