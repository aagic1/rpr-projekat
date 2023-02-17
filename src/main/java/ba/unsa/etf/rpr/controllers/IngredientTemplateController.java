package ba.unsa.etf.rpr.controllers;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class IngredientTemplateController {
    private Pane ingredientsPane;

    public IngredientTemplateController(Pane ingredientsPane) {
        this.ingredientsPane = ingredientsPane;
    }


    public void deleteIngredient(ActionEvent actionEvent) {
        Node button = (Node) actionEvent.getSource();
        Node row = button.getParent();
        ingredientsPane.getChildren().remove(row);
    }
}
