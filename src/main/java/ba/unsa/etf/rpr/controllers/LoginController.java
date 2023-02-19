package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.business.UserManager;
import ba.unsa.etf.rpr.dao.DaoFactory;
import ba.unsa.etf.rpr.domain.User;
import ba.unsa.etf.rpr.exception.RecipeException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class LoginController {
    UserManager userManager = new UserManager();

    public TextField fldEmail;
    public PasswordField fldPassword;
    public Label lblValidationEmail;
    public Label lblValidationPassword;
    public Button btnLogin;
    public Button btnSignup;

    //TODO
    // Dodati binding na email i password zbog lakšeg rada (da se ne mora uvijek korisiti fldEmail.getText(), fldPassword.getText()...

    public void actionLogin(ActionEvent actionEvent) {
        if (validateCredentials(fldEmail.getText(), fldPassword.getText())) {
            Stage thisStage = ((Stage) btnLogin.getScene().getWindow());
            try {
                User user = DaoFactory.userDao().getByEmail(fldEmail.getText());
                Stage homeStage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/home.fxml"));
                homeStage.setScene(new Scene(loader.load(), Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE));
                HomeController homeController = loader.getController();
                homeController.initUser(user);
                homeStage.setTitle("E-CookBook");
                homeStage.setResizable(true);
                homeStage.show();
                homeStage.setMinWidth(thisStage.getWidth());
                homeStage.setMinHeight(thisStage.getHeight());
                thisStage.close();
            } catch (IOException | RecipeException e) {
                new Alert(Alert.AlertType.NONE, e.getMessage(), ButtonType.OK).show();
            }
        }
    }

    public void actionOpenSignupForm(ActionEvent actionEvent) {
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

    private boolean validateCredentials(String email, String password) {
        lblValidationPassword.setText("");
        lblValidationEmail.setText("");
        fldEmail.getStyleClass().removeAll("invalidField");
        fldPassword.getStyleClass().removeAll("invalidField");

        if (email.isBlank()) {
            fldEmail.getStyleClass().add("invalidField");
            lblValidationEmail.setText("Enter email");
            return false;
        }

        try {
            userManager.validateLoginEmail(email);
            if (password.isBlank()) {
                lblValidationPassword.setText("Enter password");
                fldPassword.getStyleClass().add("invalidField");
                return false;
            }
        } catch (RecipeException e) {
            lblValidationEmail.setText(e.getMessage());
            fldEmail.getStyleClass().add("invalidField");
            return false;
        }

        try {
            userManager.validateLoginPassword(email, password);
        } catch (RecipeException e) {
            fldPassword.getStyleClass().add("invalidField");
            lblValidationPassword.setText(e.getMessage());
            return false;
        }

        return true;
    }
}
