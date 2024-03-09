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

public class MainMenuController implements Initializable {
    @FXML                           // fx:id="creditsPane"
    private AnchorPane creditsPane; // Value injected by FXMLLoader
    @FXML                           // fx:id="helpPane"
    private AnchorPane helpPane;    // Value injected by FXMLLoader
    @FXML                           // fx:id="mainPane"
    private VBox mainPane;          // Value injected by FXMLLoader
    private boolean isHelpVisible = false;
    private boolean isCreditsVisible = false;

    @FXML
    void OnCreditsButtonClicked(ActionEvent event) {
        if (!isCreditsVisible) {
            mainPane.setVisible(false);
            mainPane.setDisable(true);
            helpPane.setVisible(false);
            helpPane.setDisable(true);
            isHelpVisible = false;

            creditsPane.setVisible(true);
            creditsPane.setDisable(false);
        } else {
            helpPane.setVisible(false);
            helpPane.setDisable(true);
            creditsPane.setVisible(false);
            creditsPane.setDisable(true);

            mainPane.setVisible(true);
            mainPane.setDisable(false);
        }
        isCreditsVisible = !isCreditsVisible;
    }

    @FXML
    void OnHelpButtonClicked(ActionEvent event) {
        if (!isHelpVisible) {
            mainPane.setVisible(false);
            mainPane.setDisable(true);
            creditsPane.setVisible(false);
            creditsPane.setDisable(true);
            isCreditsVisible = false;

            helpPane.setVisible(true);
            helpPane.setDisable(false);
        } else {
            helpPane.setVisible(false);
            helpPane.setDisable(true);
            creditsPane.setVisible(false);
            creditsPane.setDisable(true);

            mainPane.setVisible(true);
            mainPane.setDisable(false);
        }
        isHelpVisible = !isHelpVisible;
    }

    @FXML
    void OnExitButtonClicked(ActionEvent event) {
        Platform.exit();
    }

    @FXML
    void OnPlayButtonClicked(ActionEvent event) {
        App.setRoot("views/MainGameUI");
    }

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
