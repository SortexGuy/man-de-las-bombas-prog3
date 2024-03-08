package bombfx.views;

import java.net.URL;
import java.util.ArrayList;
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
    @FXML                      // fx:id="gameCanvas"
    private Canvas gameCanvas; // Value injected by FXMLLoader
    private GraphicsContext gContext;

    private GameLoop gameLoop;
    private Player player;
    private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
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

        int numEnemies = (int) (Math.random() * 4) + 3;
        for (int i = 0; i < numEnemies; i++) {
            Point2D enemyPos;
            do {
                // Generar una posición aleatoria dentro de las celdas válidas
                double x = Math.random() * 416;
                double y = Math.random() * 416;
                
                // Ajustar las coordenadas para que estén centradas en la celda
                double cellX = Math.floor(x / level.getGridSize()) * level.getGridSize() + level.getGridSize() / 2;
                double cellY = Math.floor(y / level.getGridSize()) * level.getGridSize() + level.getGridSize() / 2;
                
                enemyPos = new Point2D(cellX, cellY);
            } while (!level.isEmptyCell(enemyPos) || level.isPlayerNear(enemyPos) );
            enemies.add(new Enemy(enemyPos, level, player));
        }

        level.setPlayer(player);
        level.setEnemies(enemies);

        // Input events
        App.getScene().addEventHandler(KeyEvent.KEY_PRESSED, e -> { player.handleKeyPress(e); });
        App.getScene().addEventHandler(KeyEvent.KEY_RELEASED, e -> { player.handleKeyRelease(e); });
        gameLoop.start();
    }

    private void update(double deltaTime) {
        level.update(deltaTime);
        player.update(deltaTime);
        enemies.forEach(enemy -> enemy.update(deltaTime));
    }

    private void draw(double width, double height) {
        gContext.setFill(Color.GRAY);
        gContext.fillRect(0, 0, width, height);

        level.draw(gContext);

        player.draw(gContext);
        enemies.forEach(enemy -> enemy.draw(gContext));
    }
}
