package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.business.IngredientManager;
import ba.unsa.etf.rpr.business.InstructionManager;
import ba.unsa.etf.rpr.business.RecipeManager;
import ba.unsa.etf.rpr.domain.Ingredient;
import ba.unsa.etf.rpr.domain.Instruction;
import ba.unsa.etf.rpr.domain.Recipe;
import ba.unsa.etf.rpr.domain.User;
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
    public Label lblValidation;
    private Recipe recipeToUpdate;
    private User owner;
    private RecipeManager recipeManager = new RecipeManager();
    private IngredientManager ingredientManager = new IngredientManager();
    private InstructionManager instructionManager = new InstructionManager();

    public TextField fldIngredient;
    public TextArea textInstruction;
    public VBox boxIngredients;
    public VBox boxInstructions;
    public Spinner<Integer> spinnerPrepHours;
    public Spinner<Integer> spinnerCookHours;
    public Spinner<Integer> spinnerPrepMinutes;
    public Spinner<Integer> spinnerCookMinutes;
    public Spinner<Integer> spinnerServings;
    public TextArea textTitle;
    public TextArea textDescription;
    public TextArea textNotes;

    public RecipeFormController(Recipe recipe, User user) {
        this.recipeToUpdate = recipe;
        this.owner = user;
    }

    @FXML
    public void initialize() {
        initSpinners();
        initListeners();
        if (recipeToUpdate != null) {
            populateForm();
        }
    }

    private void populateForm() {
        populateRecipe(recipeToUpdate);
        populateIngredients(recipeToUpdate);
        populateInstructions(recipeToUpdate);
    }

    private void populateIngredients(Recipe recipe) {
        try {
            List<Ingredient> ingredients = ingredientManager.getIngredientsByRecipe(recipe);
            for (int i = 1; i < ingredients.size(); i++) {
                addIngredient(null);
            }
            List<Node> nodes = boxIngredients.getChildren();
            for (int i = 0; i < ingredients.size(); i++) {
                HBox currentRow = (HBox) nodes.get(i);
                ((TextField)currentRow.getChildren().get(0)).setText(ingredients.get(i).getAmount());
                ((TextField)currentRow.getChildren().get(1)).setText(ingredients.get(i).getMeasurementUnit());
                ((TextField)currentRow.getChildren().get(2)).setText(ingredients.get(i).getName());
            }
        } catch (RecipeException e) {
            throw new RuntimeException(e);
        }
    }

    private void populateInstructions(Recipe recipe) {
        try {
            List<Instruction> instructions = instructionManager.getInstructionsByRecipe(recipe);
            for (int i = 1; i < instructions.size(); i++) {
                addStep(null);
            }
            List<Node> nodes = boxInstructions.getChildren();
            for (int i = 0; i < instructions.size(); i++) {
                HBox currentRow = (HBox) nodes.get(i);
                ((TextArea)currentRow.getChildren().get(1)).setText(instructions.get(i).getDescription());
            }
        } catch (RecipeException e) {
            throw new RuntimeException(e);
        }
    }

    private void populateRecipe(Recipe recipe) {
        textTitle.setText(recipe.getTitle());
        textDescription.setText(recipe.getDescription());
        spinnerServings.getValueFactory().setValue(recipe.getServings());
        spinnerCookHours.getValueFactory().setValue(recipe.getCookTime() / 60);
        spinnerCookMinutes.getValueFactory().setValue(recipe.getCookTime() % 60);
        spinnerPrepHours.getValueFactory().setValue(recipe.getPreparationTime() / 60);
        spinnerPrepMinutes.getValueFactory().setValue(recipe.getPreparationTime() % 60);
        textNotes.setText(recipe.getNotes());
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
            lblValidation.setVisible(true);
            return;
        }

        lblValidation.setVisible(false);
        Recipe recipe = getRecipeData();
        recipe.setOwner(owner);
        try {
            if (recipeToUpdate != null) {
                recipe.setId(recipeToUpdate.getId());
                recipe = recipeManager.update(recipe);
                ingredientManager.removeIngredientsByRecipe(recipe);
                instructionManager.removeInstructionsByRecipe(recipe);
            } else {
                recipe = recipeManager.add(recipe);
            }

            List<Ingredient> ingredients = getAllIngredients();
            for (Ingredient ingredient : ingredients) {
                ingredient.setRecipe(recipe);
                ingredientManager.add(ingredient);
            }
            List<Instruction> instructions = getAllInstructions();
            for (Instruction instruction : instructions) {
                instruction.setRecipe(recipe);
                instructionManager.add(instruction);
            }
            closeWindow();

        } catch (RecipeException e) {
            throw new RuntimeException(e);
        }
    }

    private void updateRecipe() {
        Recipe recipe = getRecipeData();

    }

    private void addRecipe() {
        Recipe recipe = getRecipeData();
        recipe.setOwner(owner);

        try {
            recipe = recipeManager.add(recipe);
            List<Ingredient> ingredients = getAllIngredients();
            for (Ingredient ingredient : ingredients) {
                ingredient.setRecipe(recipe);
                ingredientManager.add(ingredient);
            }

            List<Instruction> instructions = getAllInstructions();
            for (Instruction instruction : instructions) {
                instruction.setRecipe(recipe);
                instructionManager.add(instruction);
            }
            closeWindow();
        } catch (RecipeException e) {
            throw new RuntimeException(e);
        }
    }

    private Recipe getRecipeData() {
        Recipe recipe = new Recipe();
        recipe.setTitle(textTitle.getText());
        recipe.setDescription(textDescription.getText());
        recipe.setServings(Integer.parseInt(spinnerServings.getEditor().getText()));
        recipe.setPreparationTime(getPrepTime());
        recipe.setCookTime(getCookTime());
        recipe.setNotes(textNotes.getText());
        return recipe;
    }

    private void closeWindow() {
        ((Stage) boxInstructions.getScene().getWindow()).close();
    }

    private boolean validateForm() {
        Recipe recipe = getRecipeData();

        List<Ingredient> ingredients = getAllIngredients();
        List<Instruction> instructions = getAllInstructions();

        try {
            recipeManager.validateRecipe(recipe);
            ingredientManager.validateIngredients(ingredients);
            instructionManager.validateInstructions(instructions);
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
