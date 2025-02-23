package monty.ui;

import monty.Monty;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.image.Image;

/**
 * The main entry point for the Monty GUI application.
 */
public class Main extends Application {
    private Monty monty;
    private Image userImage = new Image(this.getClass().getResourceAsStream("/images/Cloud.jpg"));
    private Image montyImage = new Image(this.getClass().getResourceAsStream("/images/Sephiroth.png"));

    @Override
    public void start(Stage stage) throws IOException {
        monty = new Monty();

        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/view/MainWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load());

        MainWindow controller = fxmlLoader.getController();
        controller.setMonty(monty, userImage, montyImage);

        stage.setScene(scene);
        stage.setTitle("Monty Chatbot");
        stage.show();
    }
}
