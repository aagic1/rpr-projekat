package ba.unsa.etf.rpr.domain;

import java.util.Objects;

/**
 * Java bean for ingredient
 */
public class Ingredient implements Idable{
    private int id;
    private String name;
    private int amount;
    private String measurementUnit;
    private Recipe recipe;

    public Ingredient() {
    }

    public Ingredient(int id, String name, int amount, String measurementUnit, Recipe recipe) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.measurementUnit = measurementUnit;
        this.recipe = recipe;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getMeasurementUnit() {
        return measurementUnit;
    }

    public void setMeasurementUnit(String measurementUnit) {
        this.measurementUnit = measurementUnit;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ingredient that = (Ingredient) o;
        return id == that.id && amount == that.amount && Objects.equals(name, that.name) && Objects.equals(measurementUnit, that.measurementUnit) && Objects.equals(recipe, that.recipe);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, amount, measurementUnit, recipe);
    }

    @Override
    public String toString() {
        return amount + " " + measurementUnit + " " + name;
    }
}
