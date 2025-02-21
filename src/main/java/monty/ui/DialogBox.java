package monty.ui;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Represents a dialog box for displaying user and chatbot messages.
 */
public class DialogBox extends HBox {
    private Label messageLabel;
    private ImageView profileImage;

    /**
     * Constructs a DialogBox containing a message and a profile image.
     * The dialog box consists of a text label with a styled background
     * and a corresponding user or chatbot profile image.
     *
     * @param text The message text to be displayed in the dialog box.
     * @param img  The profile image associated with the message sender.
     */
    public DialogBox(String text, Image img) {
        this.setSpacing(8);
        this.setPadding(new Insets(4, 8, 4, 8));

        messageLabel = new Label(text);
        messageLabel.setWrapText(true);
        messageLabel.setFont(Font.font("Courier New", FontWeight.BOLD, 14));
        messageLabel.setTextFill(Color.WHITE);
        messageLabel.setPadding(new Insets(8));
        messageLabel.setMaxWidth(300);

        BackgroundFill backgroundFill = new BackgroundFill(
                Color.rgb(10, 10, 90, 0.85),
                new CornerRadii(8),
                Insets.EMPTY
        );
        messageLabel.setBackground(new Background(backgroundFill));

        messageLabel.setBorder(new Border(new BorderStroke(
                Color.WHITE, BorderStrokeStyle.SOLID, new CornerRadii(8), new BorderWidths(2)
        )));

        profileImage = new ImageView(img);
        profileImage.setFitWidth(60);
        profileImage.setFitHeight(60);
        profileImage.setClip(new Rectangle(60, 60));

        StackPane messageContainer = new StackPane(messageLabel);
        messageContainer.setMaxWidth(320);

        this.getChildren().addAll(profileImage, messageContainer);
    }

    /**
     * Returns a styled dialog box for the user.
     */
    public static DialogBox getUserDialog(String text, Image img) {
        DialogBox db = new DialogBox(text, img);
        db.setAlignment(Pos.CENTER_RIGHT);
        db.messageLabel.setBackground(new Background(new BackgroundFill(
                Color.rgb(0, 50, 120, 0.85), new CornerRadii(8), Insets.EMPTY
        )));
        return db;
    }

    /**
     * Returns a styled dialog box for Monty.
     */
    public static DialogBox getMontyDialog(String text, Image img) {
        DialogBox db = new DialogBox(text, img);
        db.setAlignment(Pos.CENTER_LEFT);
        return db;
    }
}
