package ba.unsa.etf.rpr.business;

import ba.unsa.etf.rpr.dao.DaoFactory;
import ba.unsa.etf.rpr.domain.Recipe;
import ba.unsa.etf.rpr.domain.User;
import ba.unsa.etf.rpr.exception.RecipeException;
import javafx.scene.control.TextField;
import javafx.util.Pair;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public void validateSignupEmail(String email) throws RecipeException {
        // validate if email is entered
        if (email.isBlank()) {
            throw new RecipeException("Enter email");
        }

        // validate email using regex
        String regex = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if (!matcher.matches()) {
            throw new RecipeException("Invalid email adress");
        }

        // check if email is already in use
        User user = null;
        try {
            user = DaoFactory.userDao().getByEmail(email);
        } catch (RecipeException e) {

        }
        if (user != null) {
            throw new RecipeException("Email is already linked to an account");
        }
    }


    public void validateSignupUsername(String username) throws RecipeException {
        // validate if username is entered
        if (username.isBlank()) {
            throw new RecipeException("Enter username");
        }

        // Validate username using regex
        String regex = "^[A-Za-z]\\w{4,20}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(username);
        if (!matcher.matches()) {
            throw new RecipeException("Invalid username.");
        }

        // check if username is already taken
        User user = null;
        try {
            user = getByUsername(username);
        } catch (RecipeException e) {

        }
        if (user != null) {
            throw new RecipeException("Username is already taken");
        }
    }


    public void validateSignupPassword(String password) throws RecipeException{
        // validate if password is entered
        if (password.isBlank()) {
            throw new RecipeException("Enter password");
        }

        // validate password length
        if (password.length() < 6 || password.length() > 45) {
            throw new RecipeException("Invalid password length");
        }
    }

}
