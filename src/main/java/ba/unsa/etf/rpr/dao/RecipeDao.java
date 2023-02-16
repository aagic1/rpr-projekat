package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Recipe;
import ba.unsa.etf.rpr.domain.User;
import ba.unsa.etf.rpr.exception.RecipeException;

import java.util.List;

public interface RecipeDao extends Dao<Recipe> {

    List<Recipe> searchByTitle(String text) throws RecipeException;

    List<Recipe> searchByOwner(User owner) throws RecipeException;
}
