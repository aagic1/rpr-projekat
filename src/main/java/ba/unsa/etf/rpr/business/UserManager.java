package ba.unsa.etf.rpr.business;

import ba.unsa.etf.rpr.dao.DaoFactory;
import ba.unsa.etf.rpr.domain.Recipe;
import ba.unsa.etf.rpr.domain.User;
import ba.unsa.etf.rpr.exception.RecipeException;
import javafx.util.Pair;

public class UserManager {

    public User getById(int id) throws RecipeException {
        return DaoFactory.userDao().getById(id);
    }

    public User getByEmail(String email) throws RecipeException {
        return DaoFactory.userDao().getByEmail(email);
    }

    public User getByUsername(String username) throws RecipeException {
        return DaoFactory.userDao().getByUsername(username);
    }

    public User update(User user) throws RecipeException {
        return DaoFactory.userDao().update(user);
    }

    public User add(User user) throws RecipeException {
        return DaoFactory.userDao().add(user);
    }

    public void delete(User user) throws RecipeException {
        DaoFactory.userDao().delete(user);
    }


    public void validateLoginEmail(String email) throws RecipeException {
        try {
            DaoFactory.userDao().getByEmail(email);
        } catch (RecipeException e) {
            throw new RecipeException("Email is not connected to an account");
        }
    }

    public void validateLoginPassword(String email, String password) throws RecipeException {
        try {
            User user = DaoFactory.userDao().getByEmail(email);
            if (!user.getPassword().equals(password)) {
                throw new RecipeException("Incorrect password");
            }
        } catch (RecipeException e) {
            throw new RecipeException(e.getMessage());
        }
    }
}
