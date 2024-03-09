package bombfx.views;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

import bombfx.App;
import bombfx.components.Enemy; //cambio joey
import bombfx.components.Level;
import bombfx.components.NewLifeItem;
import bombfx.components.Player;
import bombfx.engine.GameLoop;

public class MainGameController implements Initializable {
    @FXML                          // fx:id="resultPane"
    private AnchorPane resultPane; // Value injected by FXMLLoader
    @FXML                          // fx:id="resultText"
    private Text resultText;       // Value injected by FXMLLoader
    @FXML                          // fx:id="gameCanvas"
    private Canvas gameCanvas;     // Value injected by FXMLLoader
    private GraphicsContext gContext;

    private GameLoop gameLoop;
    private Player player;
    private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    private Level level;
    NewLifeItem newLifeItem;

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

        newLifeItem = new NewLifeItem(new Point2D(100, 100), player, level);//para probar
        level.addItem(newLifeItem, new Point2D(100, 100));//para probar

        int numEnemies = (int) (Math.random() * 4) + 3;
        for (int i = 0; i < numEnemies; i++) {
            Point2D enemyPos;
            do {
                // Generar una posición aleatoria dentro de las celdas válidas
                double x = Math.random() * 416;
                double y = Math.random() * 416;

                // Ajustar las coordenadas para que estén centradas en la celda
                double cellX = Math.floor(x / level.getGridSize()) * level.getGridSize()
                        + level.getGridSize() / 2;
                double cellY = Math.floor(y / level.getGridSize()) * level.getGridSize()
                        + level.getGridSize() / 2;

                enemyPos = new Point2D(cellX, cellY);
            } while (!level.isEmptyCell(enemyPos) || level.isPlayerNear(enemyPos));
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
        newLifeItem.update(deltaTime);//para probar

        if (enemies.isEmpty()) {
            System.out.println("YOU WIN!");
            // Mostrar panel de Victoria
            gameLoop.stop();
            App.setRoot("views/MainMenuUI");
        }

        if (player.isDead()) {
            System.out.println("YOU LOSE!!!");
            // Mostrar panel de Derrota
            gameLoop.stop();
            App.setRoot("views/MainMenuUI");
        }
    }

    private void draw(double width, double height) {
        gContext.setFill(Color.GRAY);
        gContext.fillRect(0, 0, width, height);

        level.draw(gContext);
        newLifeItem.draw(gContext);//para probar

        player.draw(gContext);
        enemies.forEach(enemy -> enemy.draw(gContext));
    }

    @FXML
    void onBackButtonClicked(ActionEvent event) {
        App.setRoot("views/MainMenuUI");
    }
}
