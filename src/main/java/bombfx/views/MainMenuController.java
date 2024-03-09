package bombfx.views;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import bombfx.App;
import bombfx.engine.Stats;

/**
 * Controlador para la vista del menú principal.
 * Gestiona la lógica y la interfaz de usuario de la pantalla de menú principal
 * del juego.
 */
public class MainMenuController implements Initializable {
    @FXML // fx:id="creditsPane"
    private AnchorPane creditsPane; // Value injected by FXMLLoader
    @FXML // fx:id="helpPane"
    private AnchorPane helpPane; // Value injected by FXMLLoader
    @FXML // fx:id="loossesText"
    private Text loossesText; // Value injected by FXMLLoader
    @FXML // fx:id="mainPane"
    private VBox mainPane; // Value injected by FXMLLoader
    @FXML // fx:id="nickField"
    private TextField nickField; // Value injected by FXMLLoader
    @FXML // fx:id="nickText"
    private Text nickText; // Value injected by FXMLLoader
    @FXML // fx:id="statsPane"
    private AnchorPane statsPane; // Value injected by FXMLLoader
    @FXML // fx:id="totalText"
    private Text totalText; // Value injected by FXMLLoader
    @FXML // fx:id="winText"
    private Text winText; // Value injected by FXMLLoader

    private Stats stats;
    private boolean isHelpVisible = false;
    private boolean isStatsVisible = false;
    private boolean isCreditsVisible = false;

    /**
     * Inicializa el controlador.
     * Oculta los paneles de ayuda y créditos y muestra el panel principal.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        stats = App.getStats();
        helpPane.setVisible(false);
        helpPane.setDisable(true);
        creditsPane.setVisible(false);
        creditsPane.setDisable(true);
        statsPane.setVisible(false);
        statsPane.setDisable(true);

        mainPane.setVisible(true);
        mainPane.setDisable(false);
    }

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
            helpPane.setVisible(false);
            helpPane.setDisable(true);
            statsPane.setVisible(false);
            statsPane.setDisable(true);
            isHelpVisible = false;
            isStatsVisible = false;

            creditsPane.setVisible(true);
            creditsPane.setDisable(false);
        } else {
            // Ocultar el panel de créditos y volver a mostrar el contenedor principal
            helpPane.setVisible(false);
            helpPane.setDisable(true);
            creditsPane.setVisible(false);
            creditsPane.setDisable(true);
            statsPane.setVisible(false);
            statsPane.setDisable(true);

            mainPane.setVisible(true);
            mainPane.setDisable(false);
        }
        isCreditsVisible = !isCreditsVisible; // Cambiar el estado de visibilidad de los créditos
    }

    /**
     * Maneja el evento de clic en el botón de estadísticas.
     * Muestra u oculta el panel de estadísticas según su estado actual.
     */
    @FXML
    void OnStatsButtonClicked(ActionEvent event) {
        if (!isStatsVisible) {
            // Mostrar el panel de estadísticas y deshabilitar el contenedor principal
            mainPane.setVisible(false);
            mainPane.setDisable(true);
            creditsPane.setVisible(false);
            creditsPane.setDisable(true);
            helpPane.setVisible(false);
            helpPane.setDisable(true);
            isHelpVisible = false;
            isCreditsVisible = false;

            statsPane.setVisible(true);
            statsPane.setDisable(false);

            String nick = nickField.getText();
            if (nick.isEmpty()) {
                nick = nickField.getPromptText();
            }
            stats.setNickname(nick);
            stats.loadStats();
            nickText.setText(stats.getNickname());
            totalText.setText(Integer.toString(stats.getGames()));
            winText.setText(Integer.toString(stats.getWins()));
            loossesText.setText(Integer.toString(stats.getLosses()));

        } else {
            // Ocultar el panel de estadísticas y volver a mostrar el contenedor principal
            helpPane.setVisible(false);
            helpPane.setDisable(true);
            creditsPane.setVisible(false);
            creditsPane.setDisable(true);
            statsPane.setVisible(false);
            statsPane.setDisable(true);

            mainPane.setVisible(true);
            mainPane.setDisable(false);
        }
        isStatsVisible = !isStatsVisible; // Cambiar el estado de visibilidad de la estadísticas
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
            creditsPane.setVisible(false);
            creditsPane.setDisable(true);
            statsPane.setVisible(false);
            statsPane.setDisable(true);
            isCreditsVisible = false;
            isStatsVisible = false;

            helpPane.setVisible(true);
            helpPane.setDisable(false);
        } else {
            // Ocultar el panel de ayuda y volver a mostrar el contenedor principal
            helpPane.setVisible(false);
            helpPane.setDisable(true);
            creditsPane.setVisible(false);
            creditsPane.setDisable(true);
            statsPane.setVisible(false);
            statsPane.setDisable(true);

            mainPane.setVisible(true);
            mainPane.setDisable(false);
        }
        isHelpVisible = !isHelpVisible; // Cambiar el estado de visibilidad de la ayuda
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
        String nick = nickField.getText();
        if (nick.isEmpty()) {
            nick = nickField.getPromptText();
        }
        stats.setNickname(nick);
        stats.loadStats();
        stats.addGames();
        App.setRoot("views/MainGameUI");
    }
}
