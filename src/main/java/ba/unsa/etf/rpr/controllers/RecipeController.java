package ba.unsa.etf.rpr.controllers;

import ba.unsa.etf.rpr.domain.Recipe;
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
    
    
}
