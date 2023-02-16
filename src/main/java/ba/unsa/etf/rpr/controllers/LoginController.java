package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.dao.DaoFactory;
import ba.unsa.etf.rpr.domain.User;
import ba.unsa.etf.rpr.exception.RecipeException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Pair;

import java.io.IOException;

public class LoginController {

    public TextField fldEmail;
    public PasswordField fldPassword;
    public Label lblValidationEmail;
    public Label lblValidationPassword;
    public Button btnLogin;
    public Button btnSignup;


    public void actionLogin(ActionEvent actionEvent) {
        if (validateCredentials(fldEmail, fldPassword)) {
            System.out.println("valid credentials. Continue to main page");
        }
    }

    public void actionSignup(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/signup.fxml"));
//            loader.setController(new SignUpController());
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load(), Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE));
            stage.setTitle("SignUp");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner((Stage)fldEmail.getScene().getWindow());
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            new Alert(Alert.AlertType.NONE, e.getMessage(), ButtonType.OK).show();
        }
    }

    private boolean validateCredentials(TextField fldEmail, TextField fldPassword) {
        Pair<Boolean, String> responseEmail = validateEmail(fldEmail);
        boolean validEmail = responseEmail.getKey();
        String messageEmail = responseEmail.getValue();
        lblValidationEmail.setText(messageEmail);
        if (!validEmail) {
            return false;
        }

        Pair<Boolean, String> responsePassword = validatePassword(fldEmail, fldPassword);
        boolean validPassword = responsePassword.getKey();
        String messagePassword = responsePassword.getValue();
        lblValidationPassword.setText(messagePassword);
        if (!validPassword) {
            return false;
        }
        return true;
    }

    private Pair<Boolean, String> validateEmail(TextField fldEmail) {
        String email = fldEmail.getText();
        if (email.isBlank()) {
            return new Pair<>(false, "Enter an email");
        }
        try {
            User user = DaoFactory.userDao().getByEmail(email);
            return new Pair<>(true, "");
        } catch (RecipeException e) {
            return new Pair<>(false, "Email is not connected to an account.");
        }
    }

    private Pair<Boolean, String> validatePassword(TextField fldEmail, TextField fldPassword) {
        String password = fldPassword.getText();
        if (password.isBlank()) {
            return new Pair<>(false, "Enter password");
        }
        String email = fldEmail.getText();
        try {
            User user = DaoFactory.userDao().getByEmail(email);
            if (user.getPassword().equals(password)) {
                return new Pair<>(true, "");
            } else {
                return new Pair<>(false, "Incorrect password");
            }
        } catch (RecipeException e) {
            new Alert(Alert.AlertType.NONE, e.getMessage(), ButtonType.OK).show();
            return new Pair<>(false, "");
        }
    }
}
