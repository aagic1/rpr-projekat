package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.domain.Ingredient;
import ba.unsa.etf.rpr.domain.Instruction;
import ba.unsa.etf.rpr.domain.Recipe;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.w3c.dom.Text;

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
        if (!validateForm()) {
            return;
        }
        // todo
        // save data to database
    }

    private boolean validateForm() {
        Recipe recipe = new Recipe();
        recipe.setTitle(textTitle.getText());
        recipe.setDescription(textDescription.getText());
        recipe.setNotes(textNotes.getText());
        recipe.setPreparationTime(getPrepTime());
        recipe.setCookTime(getCookTime());
        recipe.setServings(Integer.parseInt(spinnerServings.getEditor().getText()));
        List<Ingredient> ingredients = getIngredients();
        List<Instruction> instructions = getInstructions();

        if (!isValidRecipe(recipe) || !areValidIngredients(ingredients) || !areValidInstructions(instructions)) {
            showInvalidRecipeFields();
            showInvalidIngredientFields();
            showInvalidInstructionFields();
            return false;
        }
        return true;
    }

    private void showInvalidInstructionFields() {
        for (Node node : boxInstructions.getChildren()) {
            HBox row = (HBox) node;
            TextArea textDescription = (TextArea) row.getChildren().get(1);
            if (textDescription.getText().isBlank()) {
                textDescription.getStyleClass().add("invalidField");
            } else {
                textDescription.getStyleClass().removeAll("invalidField");
            }
        }
    }

    private void showInvalidIngredientFields() {
        for (Node node : boxIngredients.getChildren()) {
            HBox row = (HBox) node;
            TextField fldName = (TextField) row.getChildren().get(2);
            if (fldName.getText().isBlank()) {
                fldName.getStyleClass().add("invalidField");
            } else {
                fldName.getStyleClass().removeAll("invalidField");
            }
        }
    }

    private void showInvalidRecipeFields() {
        if (textTitle.getText().isBlank()) {
            textTitle.getStyleClass().add("invalidField");
        } else {
            textTitle.getStyleClass().removeAll("invalidField");
        }

        if (textDescription.getText().isBlank()) {
            textDescription.getStyleClass().add("invalidField");
        } else {
            textDescription.getStyleClass().removeAll("invalidField");
        }

        if ((Integer) spinnerServings.getValue() == 0) {
            spinnerServings.getStyleClass().add("invalidField");
        } else {
            spinnerServings.getStyleClass().removeAll("invalidField");
        }

        if ((Integer) spinnerPrepHours.getValue() == 0 && (Integer) spinnerPrepMinutes.getValue() == 0) {
            spinnerPrepMinutes.getStyleClass().add("invalidField");
            spinnerPrepHours.getStyleClass().add("invalidField");
        } else {
            spinnerPrepMinutes.getStyleClass().removeAll("invalidField");
            spinnerPrepHours.getStyleClass().removeAll("invalidField");
        }
    }

    private boolean isValidRecipe(Recipe recipe) {
        return isValidTitle(recipe.getTitle()) && isValidDescription(recipe.getDescription())
                && isValidServings(recipe.getServings()) && isValidPreparationTime(recipe.getPreparationTime());
    }

    private boolean isValidTitle(String title) {
        return !title.isBlank();
    }

    private boolean isValidDescription(String description) {
        return !description.isBlank();
    }

    private boolean isValidServings(int servings) {
        return servings > 0;
    }

    private boolean isValidPreparationTime(int prepTime) {
        return prepTime > 0;
    }

    private boolean areValidIngredients(List<Ingredient> ingredients) {
        for (Ingredient ingredient : ingredients) {
            if (ingredient.getName().isBlank()) {
                return false;
            }
        }
        return true;
    }

    private boolean areValidInstructions(List<Instruction> instructions) {
        for (Instruction instruction : instructions) {
            if (instruction.getDescription().isBlank()) {
                return false;
            }
        }
        return true;
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
            String amount = ((TextField) hbox.getChildren().get(0)).getText();
            String measurementUnit = ((TextField) hbox.getChildren().get(1)).getText();
            String name = ((TextField) hbox.getChildren().get(2)).getText();
            ingredients.add(new Ingredient(0, null, name, amount, measurementUnit));
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
