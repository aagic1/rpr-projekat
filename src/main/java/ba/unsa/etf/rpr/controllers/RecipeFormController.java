package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.business.RecipeManager;
import ba.unsa.etf.rpr.dao.DaoFactory;
import ba.unsa.etf.rpr.domain.Ingredient;
import ba.unsa.etf.rpr.domain.Instruction;
import ba.unsa.etf.rpr.domain.Recipe;
import ba.unsa.etf.rpr.exception.RecipeException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class RecipeFormController {
    public TextField fldIngredient;
    public TextArea textInstruction;
    private RecipeManager recipeManager = new RecipeManager();
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
        initSpinners();
        initListeners();
    }

    public void addIngredient(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ingredientTemplate.fxml"));
            loader.setController(new IngredientTemplateController(this, recipeManager));
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
        Recipe recipe = new Recipe();
        recipe.setTitle(textTitle.getText());
        recipe.setDescription(textDescription.getText());
        recipe.setServings(Integer.parseInt(spinnerServings.getEditor().getText()));
        recipe.setPreparationTime(Integer.parseInt(spinnerPrepHours.getEditor().getText())*60 + Integer.parseInt(spinnerPrepMinutes.getEditor().getText()));
        recipe.setCookTime(Integer.parseInt(spinnerCookHours.getEditor().getText())*60 + Integer.parseInt(spinnerCookMinutes.getEditor().getText()));
        recipe.setNotes(textNotes.getText());
        try {
            // TODO
            // owner should be logged in user
            // should be implemented next
            recipe.setOwner(DaoFactory.userDao().getByEmail("aagic1@etf.unsa.ba"));
        } catch (RecipeException e) {
            throw new RuntimeException(e);
        }
        try {
            recipe = recipeManager.addRecipe(recipe);
            List<Ingredient> ingredients = getAllIngredients();
            for (Ingredient ingredient : ingredients) {
                ingredient.setRecipe(recipe);
                recipeManager.addIngredient(ingredient);
            }

            List<Instruction> instructions = getAllInstructions();
            for (Instruction instruction : instructions) {
                instruction.setRecipe(recipe);
                recipeManager.addInstruction(instruction);
            }
        } catch (RecipeException e) {
            throw new RuntimeException(e);
        }
    }

    private boolean validateForm() {
        Recipe recipe = new Recipe();
        recipe.setTitle(textTitle.getText());
        recipe.setDescription(textDescription.getText());
        recipe.setNotes(textNotes.getText());
        recipe.setPreparationTime(getPrepTime());
        recipe.setCookTime(getCookTime());
        recipe.setServings(Integer.parseInt(spinnerServings.getEditor().getText()));
        List<Ingredient> ingredients = getAllIngredients();
        List<Instruction> instructions = getAllInstructions();

        try {
            recipeManager.validateRecipe(recipe);
            recipeManager.validateIngredients(ingredients);
            recipeManager.validateInstructions(instructions);
        } catch (RecipeException e){
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

    private List<Ingredient> getAllIngredients() {
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

    private List<Instruction> getAllInstructions() {
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

    private void initListeners() {
        textTitle.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (oldVal) {
                try {
                    recipeManager.validateTitle(textTitle.getText());
                    textTitle.getStyleClass().removeAll("invalidField");
                } catch (RecipeException e) {
                    textTitle.getStyleClass().add("invalidField");
                }
            }
        });
        textDescription.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (oldVal) {
                try {
                    recipeManager.validateDescription(textDescription.getText());
                    textDescription.getStyleClass().removeAll("invalidField");
                } catch (RecipeException e) {
                    textDescription.getStyleClass().add("invalidField");
                }
            }
        });
        spinnerPrepMinutes.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (oldVal) {
                try {
                    recipeManager.validatePreparationTime(Integer.parseInt(spinnerPrepMinutes.getEditor().getText()) + 60*Integer.parseInt(spinnerPrepHours.getEditor().getText()));
                    spinnerPrepMinutes.getStyleClass().removeAll("invalidField");
                    spinnerPrepHours.getStyleClass().removeAll("invalidField");
                } catch (RecipeException e) {
                    spinnerPrepMinutes.getStyleClass().add("invalidField");
                    spinnerPrepHours.getStyleClass().add("invalidField");
                }
            }
        });
        spinnerPrepHours.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (oldVal) {
                try {
                    recipeManager.validatePreparationTime(Integer.parseInt(spinnerPrepMinutes.getEditor().getText()) + 60*Integer.parseInt(spinnerPrepHours.getEditor().getText()));
                    spinnerPrepMinutes.getStyleClass().removeAll("invalidField");
                    spinnerPrepHours.getStyleClass().removeAll("invalidField");
                } catch (RecipeException e) {
                    spinnerPrepMinutes.getStyleClass().add("invalidField");
                    spinnerPrepHours.getStyleClass().add("invalidField");
                }
            }
        });
        spinnerServings.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (oldVal) {
                try {
                    recipeManager.validateServings(Integer.parseInt(spinnerServings.getEditor().getText()));
                    spinnerServings.getStyleClass().removeAll("invalidField");
                } catch (RecipeException e) {
                    spinnerServings.getStyleClass().add("invalidField");
                }
            }
        });
        fldIngredient.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (oldVal) {
                if (fldIngredient.getText().isBlank()) {
                    fldIngredient.getStyleClass().add("invalidField");
                } else {
                    fldIngredient.getStyleClass().removeAll("invalidField");
                }
            }
        });
        textInstruction.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (oldVal) {
                if (textInstruction.getText().isBlank()) {
                    textInstruction.getStyleClass().add("invalidField");
                } else {
                    textInstruction.getStyleClass().removeAll("invalidField");
                }
            }
        });
    }

    private void initSpinners() {
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
}
