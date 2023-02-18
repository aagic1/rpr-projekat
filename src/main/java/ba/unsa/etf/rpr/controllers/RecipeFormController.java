package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.domain.Ingredient;
import ba.unsa.etf.rpr.domain.Instruction;
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


    public void actionSave(ActionEvent actionEvent) {
        String title = textTitle.getText();
        String description = textDescription.getText();
        String notes = textNotes.getText();
        int prepTime = getPrepTime();
        int cookTime = getCookTime();
        int servings = Integer.parseInt(spinnerServings.getEditor().getText());
        List<Ingredient> ingredients = getIngredients();
        List<Instruction> instructions = getInstructions();
    }

    private int getCookTime() {
        int minutes = Integer.parseInt(spinnerCookMinutes.getEditor().getText());
        int hours = Integer.parseInt(spinnerCookHours.getEditor().getText());
        return hours*60 + minutes;
    }

    private int getPrepTime() {
        int minutes = Integer.parseInt(spinnerPrepMinutes.getEditor().getText());
        int hours = Integer.parseInt(spinnerPrepHours.getEditor().getText());
        return hours*60 + minutes;
    }

    private List<Ingredient> getIngredients() {
        List<Ingredient> ingredients = new ArrayList<>();
        for (Node n : boxIngredients.getChildren()) {
            HBox hbox = (HBox) n;
            int amount;
            try {
                amount = Integer.parseInt(((TextField) hbox.getChildren().get(0)).getText());
            } catch (NumberFormatException e) {
                amount = 0;
            }
            String measurementUnit = ((TextField) hbox.getChildren().get(1)).getText();
            String name = ((TextField) hbox.getChildren().get(2)).getText();
            ingredients.add(new Ingredient(0, name, amount, measurementUnit, null));
        }
        return ingredients;
    }

    private List<Instruction> getInstructions() {
        List<Instruction> instructions = new ArrayList<>();
        int counter = 1;
        for (Node n : boxInstructions.getChildren()) {
            HBox hbox = (HBox) n;
            int step = counter;
            String description = ((TextArea) hbox.getChildren().get(1)).getText();
            instructions.add(new Instruction(0, null, step, description));
            counter++;
        }
        System.out.println(instructions);
        return instructions;
    }

}
