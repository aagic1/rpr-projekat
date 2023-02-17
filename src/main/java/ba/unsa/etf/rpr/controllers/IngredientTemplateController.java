package ba.unsa.etf.rpr.controllers;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.layout.Pane;

public class IngredientTemplateController {
    private RecipeFormController recipeFormController;

    public IngredientTemplateController(RecipeFormController recipeFormController) {
        this.recipeFormController = recipeFormController;
    }


    public void deleteIngredient(ActionEvent actionEvent) {
        Node button = (Node) actionEvent.getSource();
        Node ingredient = button.getParent();
        recipeFormController.removeIngredient(ingredient);
    }
}
