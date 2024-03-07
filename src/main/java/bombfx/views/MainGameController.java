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
import bombfx.components.Enemy; //cambio joey
import bombfx.components.Level;
import bombfx.components.Player;
import bombfx.engine.GameLoop;

public class MainGameController implements Initializable {
    @FXML // fx:id="gameCanvas"
    private Canvas gameCanvas; // Value injected by FXMLLoader
    private GraphicsContext gContext;

    private GameLoop gameLoop;
    private Player player;
    private Enemy enemy; // cambio joey
    private Enemy enemy2;
    private Level level;

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
        level = new Level();
        player = new Player(new Point2D(46, 46), level);
        enemy = new Enemy(new Point2D(110, 150), level); // cambio joey
        enemy2 = new Enemy(new Point2D(170, 145), level);
        // Input events
        App.getScene().addEventHandler(KeyEvent.KEY_PRESSED, e -> {
            player.handleKeyPress(e);
        });
        App.getScene().addEventHandler(KeyEvent.KEY_RELEASED, e -> {
            player.handleKeyRelease(e);
        });
        gameLoop.start();
    }

    private void update(double deltaTime) {
        level.update(deltaTime);
        player.update(deltaTime);
        enemy.update(deltaTime); // cambio joey
        enemy2.update(deltaTime);
    }

    private void draw(double width, double height) {
        gContext.setFill(Color.GRAY);
        gContext.fillRect(0, 0, width, height);

        level.draw(gContext);

        player.draw(gContext);
        enemy.draw(gContext);
        enemy2.draw(gContext);
    }
}
