import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class SteganographyApp extends Application {

    private File imageFile;
    private File secretMessageFile;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Steganography Application");

        VBox vBox = new VBox();
        vBox.setPadding(new Insets(10, 10, 10, 10));
        vBox.setSpacing(10);

        ImageView imageView = new ImageView();
        Label infoLabel = new Label("Select an image and a text file for secret message:");

        Button selectImageButton = new Button("Select Image");
        selectImageButton.setOnAction(e -> selectImage(primaryStage, imageView));

        Button selectMessageButton = new Button("Select Secret Message");
        selectMessageButton.setOnAction(e -> selectMessage(primaryStage));

        Button embedButton = new Button("Embed Message");
        embedButton.setOnAction(e -> embedMessageAndWatermark(imageView));

        vBox.getChildren().addAll(infoLabel, selectImageButton, selectMessageButton, embedButton, imageView);

        Scene scene = new Scene(vBox, 400, 400);
        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private void selectImage(Stage stage, ImageView imageView) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg", "*.gif")
        );

        imageFile = fileChooser.showOpenDialog(stage);

        if (imageFile != null) {
            Image image = new Image(imageFile.toURI().toString());
            imageView.setImage(image);
        }
    }

    private void selectMessage(Stage stage) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Text Files", "*.txt")
        );

        secretMessageFile = fileChooser.showOpenDialog(stage);
    }

    private void embedMessageAndWatermark(ImageView imageView) {
        if (imageFile == null || secretMessageFile == null) {
            showAlert("Error", "Please select both an image and a text file.");
            return;
        }

        BufferedImage originalImage = readImage(imageFile);
        String secretMessage = readTextFile(secretMessageFile);

        if (originalImage == null || secretMessage == null) {
            showAlert("Error", "Failed to read image or secret message.");
            return;
        }

        String encryptedMessage = encryptMessage(secretMessage);
        BufferedImage stegoImage = embedMessageAndWatermark(originalImage, encryptedMessage);

        Image stegoFxImage = SwingFXUtils.toFXImage(stegoImage, null);
        imageView.setImage(stegoFxImage);

        // Optionally, save the stegoImage to a file if needed.
        // saveImage(stegoImage, "path/to/output/image_stego.jpg");

        showAlert("Success", "Message embedded and watermark added.");
    }

    private BufferedImage readImage(File imageFile) {
        try {
            return ImageIO.read(imageFile);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private String readTextFile(File filePath) {
        // Placeholder implementation, replace it with your logic to read text from a file
        return "This is a placeholder for reading text from a file.";
    }

    private String encryptMessage(String message) {
        // Placeholder implementation, replace it with your encryption logic
        return "Encrypted: " + message;
    }

    private BufferedImage embedMessageAndWatermark(BufferedImage image, String message) {
        // Placeholder implementation, replace it with your embedding logic
        return image;
    }

    private void showAlert(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
