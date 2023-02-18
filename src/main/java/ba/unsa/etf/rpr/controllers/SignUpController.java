package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.business.UserManager;
import ba.unsa.etf.rpr.domain.User;
import ba.unsa.etf.rpr.exception.RecipeException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class SignUpController {
    UserManager userManager = new UserManager();

    public TextField fldEmail;
    public TextField fldUsername;
    public PasswordField fldPassword;
    public Button btnSignup;
    public Label lblValidationEmail;
    public Label lblValidationUsername;
    public Label lblValidationPassword;

    @FXML
    public void initialize() {
        // create input field listeners
        fldEmail.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (oldVal) {
                try {
                    userManager.validateSignupEmail(fldEmail.getText());
                    lblValidationEmail.setText("");
                } catch (RecipeException e) {
                    lblValidationEmail.setText(e.getMessage());
                }
            }
        });
        fldUsername.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (oldVal) {
                try {
                    userManager.validateSignupUsername(fldUsername.getText());
                    lblValidationUsername.setText("");
                } catch (RecipeException e) {
                    lblValidationUsername.setText(e.getMessage());
                }
            }
        });
        fldPassword.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (oldVal) {
                try {
                    userManager.validateSignupPassword(fldPassword.getText());
                    lblValidationPassword.setText("");
                } catch (RecipeException e) {
                    lblValidationPassword.setText(e.getMessage());
                }
            }
        });
    }

    public void actionSignup(ActionEvent actionEvent) {
        try {
            userManager.validateSignupPassword(fldPassword.getText());
            userManager.validateSignupEmail(fldEmail.getText());
            userManager.validateSignupUsername(fldUsername.getText());
            try {
                User user = new User(0, fldUsername.getText(), fldEmail.getText(), fldPassword.getText(), null);
                userManager.add(user);
                closeWindow();
            } catch (RecipeException e) {
                new Alert(Alert.AlertType.NONE, e.getMessage(), ButtonType.OK).show();
            }
        } catch (RecipeException e) {
            showValidationMessages();
        }
    }

    private void showValidationMessages() {
        fldEmail.requestFocus();
        fldUsername.requestFocus();
        fldPassword.requestFocus();
        btnSignup.requestFocus();
    }

    private void closeWindow() {
        ((Stage) fldEmail.getScene().getWindow()).close();
    }
}
