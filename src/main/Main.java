package main;

import controllers.MoodController;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    @Override
    public void start(Stage primaryStage) {
        MoodController controller = new MoodController();

        Scene scene = new Scene(controller.getView(), 800, 500);
        primaryStage.setTitle("MoodBloom - Virtual Garden");
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args); // Launch the JavaFX application
    }
}
