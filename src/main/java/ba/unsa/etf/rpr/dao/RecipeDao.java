package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.Recipe;
import ba.unsa.etf.rpr.domain.User;

import java.util.List;

public interface RecipeDao extends Dao<Recipe> {

    List<Recipe> searchByTitle(String text);

    List<Recipe> searchByOwner(User owner);
}
