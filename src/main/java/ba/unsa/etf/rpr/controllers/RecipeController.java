package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.domain.Recipe;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class RecipeController {
    public Label lblTitle;
    public Label lblDescription;
    public Label lblUsername;
    public Label lblPreparationTime;
    public Label lblCookTime;
    public Label lblTotalTime;
    public Label lblServings;
    public Label lblNotes;
    private Recipe recipe;

    public RecipeController(Recipe recipe) {
        this.recipe = recipe;
    }

    @FXML
    public void initialize() {
        populateRecipe();
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
