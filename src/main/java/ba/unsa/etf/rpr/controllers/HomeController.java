package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.business.RecipeManager;
import ba.unsa.etf.rpr.domain.Recipe;
import ba.unsa.etf.rpr.domain.User;
import ba.unsa.etf.rpr.exception.RecipeException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Region;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class HomeController {
    private RecipeManager recipeManager = new RecipeManager();
    private static User loggedInUser = null;
    public TextField fldSearch;
    public TilePane paneContent;

    public void initUser(User user) {
        if (loggedInUser == null)
            loggedInUser = user;
    }

    public void logOutUser() {
        loggedInUser = null;
    }


    public void openCreateRecipe(ActionEvent actionEvent) {
        openRecipeForm(null);
    }

    public void openRecipeForm(Recipe recipe) {
        try {
            Stage homeStage = ((Stage) fldSearch.getScene().getWindow());

            Stage stage = new Stage();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/recipeForm.fxml"));
            RecipeFormController controller = new RecipeFormController(recipe, loggedInUser);
            loader.setController(controller);
            stage.setScene(new Scene(loader.load(), Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE));
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(homeStage);
            stage.setTitle("Create recipe");
            stage.show();
            stage.setMinWidth(stage.getWidth());
            stage.setMinHeight(stage.getHeight());
        } catch (IOException e) {
            new Alert(Alert.AlertType.NONE, e.getMessage(), ButtonType.OK).show();
        }
    }


    public void actionLogout(ActionEvent actionEvent) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/login.fxml"));
        stage.setScene(new Scene(loader.load(), Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE));
        stage.setTitle("Login");
        stage.show();
        Node node = (Node) actionEvent.getSource();
        ((Stage) node.getScene().getWindow()).close();
    }

    public void openMyRecipes(ActionEvent actionEvent) {
        try {
            List<Recipe> recipes = recipeManager.getRecipesByUser(loggedInUser);
            paneContent.getChildren().clear();
            for (Recipe recipe : recipes) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/recipeItemTemplate.fxml"));
                loader.setController(new RecipeItemTemplateController(this, recipe, true));
                VBox box = loader.load();
                paneContent.getChildren().add(box);
            }
        } catch (RecipeException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
