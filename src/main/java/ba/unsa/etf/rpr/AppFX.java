package ba.unsa.etf.rpr;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class AppFX extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/login.fxml"));
        primaryStage.setTitle("Login");
        primaryStage.setScene(new Scene(root, Region.USE_COMPUTED_SIZE, Region.USE_COMPUTED_SIZE));
        primaryStage.show();
//        primaryStage.setMinHeight(primaryStage.getHeight());
//        primaryStage.setMinWidth(primaryStage.getWidth());
    }

    public static void main(String[] args) {
        launch(args);
    }
}
