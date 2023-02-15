package ba.unsa.etf.rpr.domain;

import java.util.Objects;

public class Recipe {
    private int id;
    private String title;
    private String description;
    private int preparationTime;
    private int cookTime;
    private int servings;
    private String notes;
    private User owner;

    public Recipe(int id, String title, String description, int preparationTime, int cookTime,
                  int servings, String notes, User owner) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.preparationTime = preparationTime;
        this.cookTime = cookTime;
        this.servings = servings;
        this.notes = notes;
        this.owner = owner;
    }

    public Recipe() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPreparationTime() {
        return preparationTime;
    }

    public void setPreparationTime(int preparationTime) {
        this.preparationTime = preparationTime;
    }

    public int getCookTime() {
        return cookTime;
    }

    public void setCookTime(int cookTime) {
        this.cookTime = cookTime;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Recipe recipe = (Recipe) o;
        return id == recipe.id && preparationTime == recipe.preparationTime && cookTime == recipe.cookTime && servings == recipe.servings && Objects.equals(title, recipe.title) && Objects.equals(description, recipe.description) && Objects.equals(notes, recipe.notes) && Objects.equals(owner, recipe.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, title, description, preparationTime, cookTime, servings, notes, owner);
    }

    @Override
    public String toString() {
        return title + "\n\tby" + owner;
    }
}
