package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.business.IngredientManager;
import ba.unsa.etf.rpr.business.InstructionManager;
import ba.unsa.etf.rpr.domain.Ingredient;
import ba.unsa.etf.rpr.domain.Instruction;
import ba.unsa.etf.rpr.domain.Recipe;
import ba.unsa.etf.rpr.exception.RecipeException;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.List;

public class RecipeController {
    public Label lblTitle;
    public Label lblDescription;
    public Label lblUsername;
    public Label lblPreparationTime;
    public Label lblCookTime;
    public Label lblTotalTime;
    public Label lblServings;
    public Label lblNotes;
    public VBox boxIngredients;
    public VBox boxInstructions;
    private Recipe recipe;
    private IngredientManager ingredientManager = new IngredientManager();
    private InstructionManager instructionManager = new InstructionManager();

    public RecipeController(Recipe recipe) {
        this.recipe = recipe;
    }

    @FXML
    public void initialize() {
        populateRecipe();
        populateIngredients();
        populateInstructions();
    }

    private void populateInstructions() {
        try {
            List<Instruction> instructions = instructionManager.getInstructionsByRecipe(recipe);
            for (int i = 0; i < instructions.size(); i++) {
                HBox box = new HBox();
                Label lblNumber = new Label("" + (i+1) + ".\t");
                Label lblText = new Label(instructions.get(i).getDescription());
                lblText.setWrapText(true);
                lblText.prefWidth(640);
                lblText.setMaxWidth(640);
                box.getChildren().add(lblNumber);
                box.getChildren().add(lblText);
                boxInstructions.getChildren().add(box);
            }
        } catch (RecipeException e) {
            throw new RuntimeException(e);
        }
    }

    private void populateIngredients() {
        try {
            List<Ingredient> ingredients = ingredientManager.getIngredientsByRecipe(recipe);
            for (int i = 0; i < ingredients.size(); i++) {
                Label lbl = new Label(formatIngredient(ingredients.get(i)));
                lbl.setWrapText(true);
                lbl.prefWidth(640);
                lbl.setMaxWidth(640);
                boxIngredients.getChildren().add(lbl);
            }
        } catch (RecipeException e) {
            throw new RuntimeException(e);
        }
    }

    private String formatIngredient(Ingredient ingredient) {
        String text = "\t- ";
        if (ingredient.getAmount() != null && !ingredient.getAmount().isBlank()) {
            text += ingredient.getAmount() + " ";
        }
        if (ingredient.getMeasurementUnit() != null && !ingredient.getMeasurementUnit().isBlank()) {
            text += ingredient.getMeasurementUnit() + " ";
        }
        text += ingredient.getName();
        return text;
    }
    
    private void populateRecipe() {
        lblTitle.setText(recipe.getTitle());
        lblDescription.setText(recipe.getDescription());
        lblUsername.setText(recipe.getOwner().getUsername());
        lblNotes.setText(recipe.getNotes());
        lblServings.setText("" + recipe.getServings());
        lblPreparationTime.setText(getTimeHoursMinutes(recipe.getPreparationTime()));
        lblCookTime.setText(getTimeHoursMinutes(recipe.getCookTime()));
        lblTotalTime.setText(getTimeHoursMinutes(recipe.getPreparationTime() + recipe.getCookTime()));
    }

    private String getTimeHoursMinutes(int time) {
        int hours = time / 60;
        int minutes = time % 60;
        String text = "";
        if (hours > 0) {
            text += hours + "h ";
        }
        if (minutes > 0) {
            text += minutes + "min";
        }
        return text;
    }

}
