package ba.unsa.etf.rpr.domain;

import java.util.Objects;

/**
 * Java bean for instruction
 * @author Ahmed Agic
 */
public class Instruction implements Idable{
    private int id;
    private int step;
    private String description;

    public Instruction() {
    }

    public Instruction(int id, int step, String description) {
        this.id = id;
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

    @Override
    public void setId(int id) {

    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Instruction that = (Instruction) o;
        return id == that.id && step == that.step && Objects.equals(description, that.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, step, description);
    }

    @Override
    public String toString() {
        return step + ".\t" + description;
    }
}
