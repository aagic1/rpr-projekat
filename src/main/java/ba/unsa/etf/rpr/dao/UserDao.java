package ba.unsa.etf.rpr.dao;

import ba.unsa.etf.rpr.domain.User;
import ba.unsa.etf.rpr.exception.RecipeException;

public interface UserDao extends Dao<User> {
    /**
     * Returns user with given username
     * @param username search string for users
     * @return User
     */
    User getByUsername(String username) throws RecipeException;

    User getByEmail(String email) throws RecipeException;
}
