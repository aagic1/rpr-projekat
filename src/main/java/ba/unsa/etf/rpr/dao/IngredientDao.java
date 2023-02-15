package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Ingredient;
import ba.unsa.etf.rpr.domain.Recipe;

import java.util.List;

/**
 * Dao interface for Ingredient domain bean
 */
public interface IngredientDao extends Dao<Ingredient> {

    List<Ingredient> getIngredientsByRecipe(Recipe recipe);
}
