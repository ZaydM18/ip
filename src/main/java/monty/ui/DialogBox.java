package monty.ui;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Represents a dialog box for displaying user and chatbot messages.
 */
public class DialogBox extends HBox {
    private Label messageLabel;
    private ImageView profileImage;

    public DialogBox(String text, Image img) {
        this.setSpacing(10);

        messageLabel = new Label(text);
        messageLabel.setWrapText(true);
        messageLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 14));
        messageLabel.setStyle("-fx-background-color: #D9FDD3; -fx-padding: 8px; -fx-background-radius: 10px;");

        profileImage = new ImageView(img);
        profileImage.setFitWidth(40);
        profileImage.setFitHeight(40);
        profileImage.setClip(new Rectangle(40, 40));

        StackPane messageContainer = new StackPane(messageLabel);
        messageContainer.setMaxWidth(300);
        messageContainer.setStyle("-fx-background-radius: 10px;");

        this.getChildren().addAll(profileImage, messageContainer);
    }

    public static DialogBox getUserDialog(String text, Image img) {
        DialogBox db = new DialogBox(text, img);
        db.setAlignment(Pos.TOP_RIGHT);
        return db;
    }

    public static DialogBox getMontyDialog(String text, Image img) {
        DialogBox db = new DialogBox(text, img);
        db.setAlignment(Pos.TOP_LEFT);
        return db;
    }
}
