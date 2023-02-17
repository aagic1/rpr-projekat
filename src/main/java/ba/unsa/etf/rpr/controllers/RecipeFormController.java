package ba.unsa.etf.rpr.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.VBox;

import java.io.IOException;

public class RecipeFormController {
    public VBox boxIngredients;

    public void addIngredient(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ingredientTemplate.fxml"));
            loader.setController(new IngredientTemplateController(boxIngredients));
            Node node = loader.load();
            boxIngredients.getChildren().add(node);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
