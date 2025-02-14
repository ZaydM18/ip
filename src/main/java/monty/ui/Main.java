package monty;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * The main entry point for the Monty chatbot GUI application.
 */
public class Main extends Application {

    @Override
    public void start(Stage stage) {
        Label label = new Label("Hello! I'm Monty, your chatbot.");
        StackPane layout = new StackPane(label);
        Scene scene = new Scene(layout, 400, 300);

        stage.setTitle("Monty Chatbot");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
