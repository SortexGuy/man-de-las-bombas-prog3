package bombfx.views;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

import bombfx.App;

/**
 * Controlador para la vista del menú principal.
 * Gestiona la lógica y la interfaz de usuario de la pantalla de menú principal del juego.
 */
public class MainMenuController implements Initializable {
    @FXML                           // fx:id="creditsPane"
    private AnchorPane creditsPane; // Value injected by FXMLLoader
    @FXML                           // fx:id="helpPane"
    private AnchorPane helpPane;    // Value injected by FXMLLoader
    @FXML                           // fx:id="mainPane"
    private VBox mainPane;          // Value injected by FXMLLoader
    private boolean isHelpVisible = false;
    private boolean isCreditsVisible = false;

    /**
     * Maneja el evento de clic en el botón de créditos.
     * Muestra u oculta el panel de créditos según su estado actual.
     */
    @FXML
    void OnCreditsButtonClicked(ActionEvent event) {
        if (!isCreditsVisible) {
            // Mostrar el panel de créditos y deshabilitar el contenedor principal
            mainPane.setVisible(false);
            mainPane.setDisable(true);

            creditsPane.setVisible(true);
            creditsPane.setDisable(false);
        } else {
            // Ocultar el panel de créditos y volver a mostrar el contenedor principal
            helpPane.setVisible(false);
            helpPane.setDisable(true);
            creditsPane.setVisible(false);
            creditsPane.setDisable(true);

            mainPane.setVisible(true);
            mainPane.setDisable(false);
        }
        isCreditsVisible = !isCreditsVisible;// Cambiar el estado de visibilidad de los créditos
    }

    /**
     * Maneja el evento de clic en el botón de ayuda.
     * Muestra u oculta el panel de ayuda según su estado actual.
     */
    @FXML
    void OnHelpButtonClicked(ActionEvent event) {
        if (!isHelpVisible) {
            // Mostrar el panel de ayuda y deshabilitar el contenedor principal
            mainPane.setVisible(false);
            mainPane.setDisable(true);

            helpPane.setVisible(true);
            helpPane.setDisable(false);
        } else {
            // Ocultar el panel de ayuda y volver a mostrar el contenedor principal
            helpPane.setVisible(false);
            helpPane.setDisable(true);
            creditsPane.setVisible(false);
            creditsPane.setDisable(true);

            mainPane.setVisible(true);
            mainPane.setDisable(false);
        }
        isHelpVisible = !isHelpVisible;// Cambiar el estado de visibilidad de la ayuda
    }

    /**
     * Maneja el evento de clic en el botón de salir.
     * Cierra la aplicación.
     */
    @FXML
    void OnExitButtonClicked(ActionEvent event) {
        Platform.exit();
    }

    /**
     * Maneja el evento de clic en el botón de jugar.
     * Cambia a la vista del juego principal.
     */
    @FXML
    void OnPlayButtonClicked(ActionEvent event) {
        App.setRoot("views/MainGameUI");
    }

    /**
     * Inicializa el controlador.
     * Oculta los paneles de ayuda y créditos y muestra el panel principal.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        helpPane.setVisible(false);
        helpPane.setDisable(true);
        creditsPane.setVisible(false);
        creditsPane.setDisable(true);

        mainPane.setVisible(true);
        mainPane.setDisable(false);
    }
}
