package ba.unsa.etf.rpr.controllers;

import com.mysql.cj.result.IntegerValueFactory;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

public class RecipeFormController {
    public VBox boxIngredients;
    public VBox boxInstructions;
    public Spinner prepTimeHours;
    public Spinner cookTimeHours;
    public Spinner prepTimeMinutes;
    public Spinner cookTimeMinutes;
    public Spinner servings;

    @FXML
    public void initialize() {
        initSpinnerMinutes(prepTimeMinutes);
        initSpinnerMinutes(cookTimeMinutes);
        initSpinnerHours(prepTimeHours);
        initSpinnerHours(cookTimeHours);
        initSpinnerServings(servings);
    }

    private void initSpinnerMinutes(Spinner spinner) {
        spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 59));
    }

    private void initSpinnerHours(Spinner spinner) {
        spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 23));
    }

    private void initSpinnerServings(Spinner spinner) {
        spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 999));
    }

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

    public void addStep(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/stepTemplate.fxml"));
            loader.setController(new StepTemplateController(this));
            HBox hbox = loader.load();
            Label number = (Label) hbox.getChildren().get(0);
            number.setText((boxInstructions.getChildren().size() + 1) + ".");
            boxInstructions.getChildren().add(hbox);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeStep(Node n) {
        boxInstructions.getChildren().remove(n);
        fixStepNumbering();
    }

    private void fixStepNumbering() {
        int counter = 1;
        for (Node node : boxInstructions.getChildren()) {
            HBox row = (HBox) node;
            Label lblNumber = (Label) row.getChildren().get(0);
            lblNumber.setText(counter + ".");
            counter++;
        }
    }

    public void actionCancel(ActionEvent actionEvent) {
        ((Stage) boxInstructions.getScene().getWindow()).close();
    }
}
