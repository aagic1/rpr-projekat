package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.dao.DaoFactory;
import ba.unsa.etf.rpr.domain.User;
import ba.unsa.etf.rpr.exception.RecipeException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SignUpController {

    public TextField fldEmail;
    public TextField fldUsername;
    public PasswordField fldPassword;
    public PasswordField fldPasswordRepeat;
    public Button btnSignup;
    public Label lblValidationEmail;
    public Label lblValidationUsername;
    public Label lblValidationPassword;

    @FXML
    public void initialize() {
        addFocusListener(fldEmail, lblValidationEmail, SignUpController::validateEmail);
        addFocusListener(fldUsername, lblValidationUsername, SignUpController::validateUsername);
        addFocusListener(fldPassword, lblValidationPassword, SignUpController::validatePassword);

//        fldEmail.focusedProperty().addListener((obs, oldVal, newVal) -> {
//            if(newVal) {
//                return;
//            }
//            Pair<Boolean, String> valid = validateEmail();
//            if (valid.getKey()) {
//                lblValidationEmail.setText("");
//            } else {
//                lblValidationEmail.setText(valid.getValue());
//            }
//        });
//        fldUsername.focusedProperty().addListener((obs, oldVal, newVal) -> {
//            if (newVal) {
//                return;
//            }
//            Pair<Boolean, String> validUsername = validateUsername();
//            if (validUsername.getKey()) {
//                lblValidationUsername.setText("");
//            } else {
//                lblValidationUsername.setText(validUsername.getValue());
//            }
//        });
//        fldPassword.focusedProperty().addListener((obs, oldVal, newVal) -> {
//            if(newVal) {
//                return;
//            }
//            Pair<Boolean, String> valid = validatePassword();
//            if (valid.getKey()) {
//                lblValidationPassword.setText("");
//            } else {
//                lblValidationPassword.setText(valid.getValue());
//            }
//        });
    }

    /**
     * Utility method for adding focus listener on TextFields / PasswordFields
     * @param field - field on which to add focus listener
     * @param lblMessage - label which displays message if field is not valid
     * @param funValidate - validation function
     */
    private void addFocusListener(TextField field, Label lblMessage, Function<TextField, Pair<Boolean, String>> funValidate) {
        field.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                return;
            }
            Pair<Boolean, String> valid = funValidate.apply(field);
            if (valid.getKey()) {
                lblMessage.setText("");
            } else {
                lblMessage.setText(valid.getValue());
            }
        });
    }

    /**
     * Checks if entered email is valid
     * @param fldEmail - TextField that contains entered email
     * @return Pair of Boolean and String specifiying whether email is valid and error message if not valid
     */
    private static Pair<Boolean, String> validateEmail(TextField fldEmail) {
        // validate if email is entered
        if (fldEmail.getText().isBlank()) {
            return new Pair<>(false, "Enter email");
        }

        // validate email using regex
        String regex = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(fldEmail.getText());
        if (!matcher.matches()) {
            return new Pair<>(false, "Invalid email adress");
        }

        // check if email is already in use
        try {
            User user = DaoFactory.userDao().getByEmail(fldEmail.getText());
            return new Pair<>(false, "Email is already linked to an account");
        } catch (RecipeException e) {
            return new Pair<>(true, null);
        }
    }

    /**
     * Checks if entered username is valid
     * @param fldUsername - TextField that contains entered username
     * @return Pair of Boolean and String specifiying whether username is valid and error message if not valid
     */
    private static Pair<Boolean, String> validateUsername(TextField fldUsername) {
        // validate if username is entered
        if (fldUsername.getText().isBlank()) {
            return new Pair<>(false, "Enter username");
        }

        // Validate username using regex
        String regex = "^[A-Za-z]\\w{4,20}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(fldUsername.getText());
        if (!matcher.matches()) {
            return new Pair<>(false, "Invalid username.");
        }

        // check if username is already taken
        try {
            User user = DaoFactory.userDao().getByUsername(fldUsername.getText());
            return new Pair<>(false, "Username is already taken");
        } catch (RecipeException e) {
            return new Pair<>(true, null);
        }
    }

    /**
     * Checks if entered password is valid
     * @param fldPassword - PasswordField that contains entered password
     * @return Pair of Boolean and String specifiying whether password is valid and error message if not valid
     */
    private static Pair<Boolean, String> validatePassword(TextField fldPassword) {
        // validate if password is entered
        if (fldPassword.getText().isBlank()) {
            return new Pair<>(false, "Enter password");
        }

        // validate password length
        if (fldPassword.getText().length() < 6 || fldPassword.getText().length() > 45) {
            return new Pair<>(false, "Invalid password");
        }
        return new Pair<>(true, null);
    }


    public void actionSignup(ActionEvent actionEvent) {
        if (!validateEmail(fldEmail).getKey() || !validateUsername(fldUsername).getKey() || !validatePassword(fldPassword).getKey()) {
            System.out.println("Neka polja nisu validna");
            return;
        }
        User user = new User(0, fldUsername.getText(), fldEmail.getText(), fldPassword.getText(), null);
        try {
            DaoFactory.userDao().add(user);
        } catch (RecipeException e) {
            new Alert(Alert.AlertType.NONE, e.getMessage(), ButtonType.OK).show();
        }
        closeWindow();
    }

    private void closeWindow() {
        ((Stage) fldEmail.getScene().getWindow()).close();
    }
}
