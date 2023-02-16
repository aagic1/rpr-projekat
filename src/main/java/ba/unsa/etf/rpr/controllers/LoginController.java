package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.dao.DaoFactory;
import ba.unsa.etf.rpr.domain.User;
import ba.unsa.etf.rpr.exception.RecipeException;
import javafx.event.ActionEvent;
import javafx.scene.control.*;

public class LoginController {

    public TextField fldEmail;
    public PasswordField fldPassword;
    public Label lblValidationEmail;
    public Label lblValidationPassword;
    public Button btnLogin;




    public void actionLogin(ActionEvent actionEvent) {
        boolean validData = true;
        lblValidationEmail.getStyleClass().removeAll("invalid");
        lblValidationPassword.getStyleClass().removeAll("invalid");

        String email = fldEmail.getText();
        String password = fldPassword.getText();
        if (email.isBlank()) {
            validData = false;
            lblValidationEmail.getStyleClass().add("invalid");
            lblValidationEmail.setText("Enter an email");
        }
        if (password.isBlank()) {
            validData = false;
            lblValidationPassword.getStyleClass().add("invalid");
            lblValidationPassword.setText("Enter a password");
        }
        if (!validData) {
            return;
        }
        User user;
        try {
            user = DaoFactory.userDao().getByEmail(email);
            if (user.getPassword() != password) {
                lblValidationPassword.getStyleClass().add("invalid");
                lblValidationPassword.setText("Incorrect password.");
            }
        } catch (RecipeException e) {
            lblValidationEmail.getStyleClass().add("invalid");
            lblValidationEmail.setText("Email is not linked with an account.");
        }

    }
}
