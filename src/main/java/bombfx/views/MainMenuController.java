package bombfx.views;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;

import bombfx.App;

public class MainMenuController implements Initializable {
    @FXML                  // fx:id="mainPane"
    private VBox mainPane; // Value injected by FXMLLoader

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
    }
}
