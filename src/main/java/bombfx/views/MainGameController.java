package bombfx.views;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;

import bombfx.App;
import bombfx.components.Player;
import bombfx.components.Enemy; //cambio joey
import bombfx.engine.GameLoop;

public class MainGameController implements Initializable {
    @FXML                      // fx:id="gameCanvas"
    private Canvas gameCanvas; // Value injected by FXMLLoader
    private GraphicsContext gContext;

    private GameLoop gameLoop;
    private Player player;
    private Enemy enemy; //cambio joey

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gContext = gameCanvas.getGraphicsContext2D();
        gameLoop = new GameLoop() {
            @Override
            public void tick(double deltaTime) {
                update(deltaTime);
                draw(gameCanvas.getWidth(), gameCanvas.getHeight());
            }
        };

        player = new Player(new Point2D(46, 46));
        enemy = new Enemy(new Point2D(200, 200)); //cambio joey
        // Input events
        App.getScene().addEventHandler(KeyEvent.KEY_PRESSED, e -> { player.handleKeyPress(e); });
        App.getScene().addEventHandler(KeyEvent.KEY_RELEASED, e -> { player.handleKeyRelease(e); });
        gameLoop.start();
    }

    private void update(double deltaTime) {
        player.update(deltaTime);
        enemy.update(deltaTime); //cambio joey
    }

    private void draw(double width, double height) {
        gContext.setFill(Color.GRAY);
        gContext.fillRect(0, 0, width, height);

        player.draw(gContext);
        enemy.draw(gContext);
    }
}
