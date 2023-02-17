package ba.unsa.etf.rpr.controllers;

import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;

public class StepTemplateController {
    RecipeFormController recipeFormController;
    public StepTemplateController(RecipeFormController recipeFormController) {
        this.recipeFormController = recipeFormController;
    }

    public void deleteStep(ActionEvent actionEvent) {
        Button button = (Button) actionEvent.getSource();
        HBox step = (HBox) button.getParent();
        recipeFormController.removeStep(step);
    }
}
