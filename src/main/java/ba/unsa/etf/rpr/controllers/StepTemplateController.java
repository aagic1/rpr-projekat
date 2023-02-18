package ba.unsa.etf.rpr.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;

public class StepTemplateController {
    public TextArea textDescription;
    RecipeFormController recipeFormController;

    @FXML
    public void initialize() {
        textDescription.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (oldVal) {
                if (textDescription.getText().isBlank()) {
                    textDescription.getStyleClass().add("invalidField");
                } else {
                    textDescription.getStyleClass().removeAll("invalidField");
                }
            }
        });
    }
    public StepTemplateController(RecipeFormController recipeFormController) {
        this.recipeFormController = recipeFormController;
    }

    public void deleteStep(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getSource();
        HBox step = (HBox) button.getParent();
        recipeFormController.removeStep(step);
    }
}
