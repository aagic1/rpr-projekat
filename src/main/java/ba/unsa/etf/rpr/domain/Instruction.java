package ba.unsa.etf.rpr.domain;

import java.util.Objects;

/**
 * Java bean for instruction
 * @author Ahmed Agic
 */
public class Instruction implements Idable{
    private int id;
    private Recipe recipe;
    private int step;
    private String description;

    public Instruction() {
    }

    public Instruction(int id, Recipe recipe, int step, String description) {
        this.id = id;
        this.recipe = recipe;
        this.step = step;
        this.description = description;
    }

    public int getStep() {
        return step;
    }

    public void setStep(int step) {
        this.step = step;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Instruction that = (Instruction) o;
        return id == that.id && step == that.step && Objects.equals(recipe, that.recipe) && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, recipe, step, description);
    }

    @Override
    public String toString() {
        return step + ".\t" + description;
    }
}
