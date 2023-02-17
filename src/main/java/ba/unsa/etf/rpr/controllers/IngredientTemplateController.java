package ba.unsa.etf.rpr.controllers;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class IngredientTemplateController {
    private Pane parentPane;

    public IngredientTemplateController(Pane boxIngredients) {
        parentPane = boxIngredients;
    }


    public void deleteIngredient(ActionEvent actionEvent) {
        Node button = (Node) actionEvent.getSource();
        Node row = button.getParent();
        parentPane.getChildren().remove(row);
    }
}
