package ba.unsa.etf.rpr.domain;

import java.util.Objects;

/**
 * Java bean for ingredient
 */
public class Ingredient implements Idable{
    private int id;
    private Recipe recipe;
    private String name;
    private String amount;
    private String measurementUnit;


    public Ingredient() {
    }

    public Ingredient(int id, Recipe recipe, String name, String amount, String measurementUnit) {
        this.id = id;
        this.recipe = recipe;
        this.name = name;
        this.amount = amount;
        this.measurementUnit = measurementUnit;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
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
        return id == that.id && Objects.equals(recipe, that.recipe) && Objects.equals(name, that.name) && Objects.equals(amount, that.amount) && Objects.equals(measurementUnit, that.measurementUnit);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, recipe, name, amount, measurementUnit);
    }

    @Override
    public String toString() {
        return amount + " " + measurementUnit + " " + name;
    }
}
