package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.business.RecipeManager;
import ba.unsa.etf.rpr.domain.Ingredient;
import ba.unsa.etf.rpr.exception.RecipeException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import java.awt.*;

public class IngredientTemplateController {
    public TextField fldIngredient;
    public TextField fldMeasurementUnit;
    public TextField fldAmount;
    private RecipeFormController recipeFormController;


    public IngredientTemplateController(RecipeFormController recipeFormController) {
        this.recipeFormController = recipeFormController;
    }

    @FXML
    public void initialize() {
        fldIngredient.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (oldVal) {
                if (fldIngredient.getText().isBlank()) {
                    fldIngredient.getStyleClass().add("invalidField");
                } else {
                    fldIngredient.getStyleClass().removeAll("invalidField");
                }
            }
        });
    }

    public void deleteIngredient(ActionEvent actionEvent) {
        Node button = (Node) actionEvent.getSource();
        Node ingredient = button.getParent();
        recipeFormController.removeIngredient(ingredient);
    }
}
