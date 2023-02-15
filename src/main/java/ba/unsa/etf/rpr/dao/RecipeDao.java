package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Recipe;

public interface RecipeDao extends Dao<Recipe> {

    Recipe searchByTitle(String text);


}
