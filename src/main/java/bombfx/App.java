package bombfx;

import java.io.IOException;
import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App extends Application {
    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        Parent hierarchy = App.loadFromFXML("views/MainMenuUI");
        scene = new Scene(hierarchy, 960, 540, Color.BLACK);

        stage.setTitle("Man de las Bombas FX");
        stage.setFullScreen(false);
        stage.setMaximized(false);
        stage.setResizable(false);
        stage.centerOnScreen();

        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    public static void setRoot(String fxml) {
        try {
            Parent hierarchy = App.loadFromFXML(fxml);
            scene.setRoot(hierarchy);
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("[!!Error] Could not load FXML: " + fxml);
            System.err.println("[!!Error] Error message: " + e.getMessage());
        }
    }

    private static Parent loadFromFXML(String fxml) throws IOException {
        URL location = App.class.getResource(fxml + ".fxml");
        Parent hierarchy = new AnchorPane();
        hierarchy = FXMLLoader.load(location);
        return hierarchy;
    }

    public static Scene getScene() {
        return scene;
    }
}
