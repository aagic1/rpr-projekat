package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.domain.Ingredient;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RecipeFormController {
    public VBox boxIngredients;
    public VBox boxInstructions;
    public Spinner spinnerPrepHours;
    public Spinner spinnerCookHours;
    public Spinner spinnerPrepMinutes;
    public Spinner spinnerCookMinutes;
    public Spinner spinnerServings;
    public TextArea textTitle;
    public TextArea textDescription;
    public TextArea textNotes;

    @FXML
    public void initialize() {
        initSpinnerMinutes(spinnerPrepMinutes);
        initSpinnerMinutes(spinnerCookMinutes);
        initSpinnerHours(spinnerPrepHours);
        initSpinnerHours(spinnerCookHours);
        initSpinnerServings(spinnerServings);
    }

    private void initSpinnerMinutes(Spinner spinner) {
        spinner.setValueFactory(new SpinnerValueFactory.IntegerSpinnerValueFactory(0, 55, 0, 5));
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
            loader.setController(new IngredientTemplateController(this));
            HBox hbox = loader.load();
            boxIngredients.getChildren().add(hbox);
        } catch (IOException e) {
            new Alert(Alert.AlertType.NONE, e.getMessage(), ButtonType.OK).show();
        }
    }

    public void removeIngredient(Node n) {
        boxIngredients.getChildren().remove(n);
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
            new Alert(Alert.AlertType.NONE, e.getMessage(), ButtonType.OK).show();
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
