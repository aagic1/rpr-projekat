package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.dao.DaoFactory;
import ba.unsa.etf.rpr.domain.User;
import ba.unsa.etf.rpr.exception.RecipeException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.util.Pair;

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
        fldEmail.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if(newVal) {
                return;
            }
            Pair<Boolean, String> valid = validateEmail();
            if (valid.getKey()) {
                lblValidationEmail.getStyleClass().removeAll("invalid");
                lblValidationEmail.setText("");
            } else {
                lblValidationEmail.getStyleClass().add("invalid");
                lblValidationEmail.setText(valid.getValue());
            }
        });
    }

    public Pair<Boolean, String> validateEmail() {
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

    public void actionSignup(ActionEvent actionEvent) {

    }
}
