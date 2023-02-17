package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.domain.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Region;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class HomeController {
    private static User loggedInUser = null;

    public void initUser(User user) {
        if (loggedInUser == null)
            loggedInUser = user;
    }

    public void logOutUser() {
        loggedInUser = null;
    }


    public void openCreateRecipe(ActionEvent actionEvent) {
        try {
            Node node = ((Node) actionEvent.getSource());
            Stage parentStage = ((Stage) node.getScene().getWindow());

            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/recipeForm.fxml"));
            Parent root = loader.load();
            stage.setScene(new Scene(root, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE));
            stage.setTitle("Create recipe");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(parentStage);
            stage.show();
        } catch (IOException e) {
            new Alert(Alert.AlertType.NONE, e.getMessage(), ButtonType.OK).show();
        }
    }


}
