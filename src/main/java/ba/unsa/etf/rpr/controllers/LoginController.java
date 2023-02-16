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

import java.io.IOException;

public class LoginController {

    public TextField fldEmail;
    public PasswordField fldPassword;
    public Label lblValidationEmail;
    public Label lblValidationPassword;
    public Button btnLogin;
    public Button btnSignup;


    public void actionLogin(ActionEvent actionEvent) {
        boolean enteredData = true;

        String email = fldEmail.getText();
        String password = fldPassword.getText();
        if (email.isBlank()) {
            enteredData = false;
            lblValidationEmail.setText("Enter an email");
        } else {
            lblValidationEmail.setText("");
        }
        if (password.isBlank()) {
            enteredData = false;
            lblValidationPassword.setText("Enter a password");
        } else {
            lblValidationPassword.setText("");
        }
        if (!enteredData) {
            return;
        }


        try {
            User user = DaoFactory.userDao().getByEmail(email);
            if (!user.getPassword().equals(password)) {
                lblValidationPassword.setText("Incorrect password.");
            } else {
                lblValidationPassword.setText("");
            }
        } catch (RecipeException e) {
            lblValidationEmail.setText("Email is not linked with an account.");
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
}
