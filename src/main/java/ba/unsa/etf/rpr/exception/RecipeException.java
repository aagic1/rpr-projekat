package ba.unsa.etf.rpr.exception;

public class RecipeException extends Exception {

    public RecipeException(String msg, Exception reason) {
        super(msg, reason);
    }

    public RecipeException(String msg) {
        super(msg);
    }
}
