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

import bombfx.engine.Stats;

/**
 * Clase principal de la aplicación
 */
public class App extends Application {
    private static Scene scene;
    private static Stats stats;

    /**
     * Inicia la aplicación JavaFX, configurando la escena principal y mostrando la
     * ventana
     * principal.
     *
     * @param stage El objeto Stage que representa la ventana principal de la
     *              aplicación.
     * @throws IOException Si ocurre un error de E/S durante la ejecución de la
     *                     función.
     */
    @Override
    public void start(Stage stage) throws IOException {
        // Cargar la jerarquía de nodos desde un archivo FXML
        Parent hierarchy = App.loadFromFXML("views/MainMenuUI");

        // Crear una escena con la jerarquía de nodos y un tamaño específico
        scene = new Scene(hierarchy, 960, 540, Color.BLACK);
        scene.getStylesheets().add(App.class.getResource("views/fonts.css").toExternalForm());

        // Configurar propiedades de la ventana principal
        stage.setTitle("Man de las Bombas FX"); // Titulo de la ventana
        stage.setFullScreen(false); // Desactivar pantalla completa
        stage.setMaximized(false); // Desactivar maximizar
        stage.setResizable(false); // Desactivar redimensionar
        stage.centerOnScreen(); // Centrar la ventana en la pantalla

        // Establecer la escena en la ventana principal y mostrarla
        stage.setScene(scene);
        try {
            Thread.sleep(1500, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
        hierarchy.applyCss();
        stage.show();
    }

    /**
     * Método principal de la aplicación Java. Inicia la ejecución del programa.
     *
     * @param args Argumentos de la línea de comandos (no se utilizan en esta
     *             aplicación).
     */
    public static void main(String[] args) {
        launch(); // Inicia la ejecución de la aplicación JavaFX
    }

    /**
     * Establece la raíz de la escena en la jerarquía FXML cargada desde el archivo
     * FXML dado.
     *
     * @param fxml La ruta al archivo FXML que se va a cargar.
     */
    public static void setRoot(String fxml) {
        try {
            // Cargar la jerarquía de nodos desde el archivo FXML
            Parent hierarchy = App.loadFromFXML(fxml);

            // Establecer la raíz de la escena como la jerarquía de nodos cargada
            scene.setRoot(hierarchy);
        } catch (Exception e) {
            // Manejar cualquier excepción ocurrida durante el proceso
            e.printStackTrace();
            System.err.println("[!!Error] Could not load FXML: " + fxml);
            System.err.println("[!!Error] Error message: " + e.getMessage());
        }
    }

    /**
     * Carga y devuelve la jerarquía de nodos desde un archivo FXML dado.
     *
     * @param fxml La ruta relativa al archivo FXML que se va a cargar (sin la
     *             extensión .fxml).
     * @return La jerarquía de nodos cargada desde el archivo FXML.
     * @throws IOException Si ocurre un error de E/S durante la carga del archivo
     *                     FXML.
     */
    private static Parent loadFromFXML(String fxml) throws IOException {
        // Obtener la ubicación del archivo FXML usando la clase App como referencia
        URL location = App.class.getResource(fxml + ".fxml");

        // Crear una jerarquía de nodos como un AnchorPane por defecto
        Parent hierarchy = new AnchorPane();

        // Cargar la jerarquía de nodos desde el archivo FXML
        hierarchy = FXMLLoader.load(location);
        return hierarchy;
    }

    /**
     * Obtiene la escena actualmente configurada en la aplicación.
     *
     * @return La escena actual de la aplicación.
     */
    public static Scene getScene() {
        return scene;
    }

    public static Stats getStats() {
        return stats;
    }
}
