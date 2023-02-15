package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Recipe;

import java.util.ArrayList;

public interface RecipeDao extends Dao<Recipe> {

    ArrayList<Recipe> searchByTitle(String text);


}
