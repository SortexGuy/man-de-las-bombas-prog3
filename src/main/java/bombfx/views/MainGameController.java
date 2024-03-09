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
import bombfx.components.Player;
import bombfx.engine.GameLoop;

/**
 * Controlador para la vista principal del juego.
 * Controla la lógica del juego y la interfaz de usuario durante el juego principal.
 */
public class MainGameController implements Initializable {
    @FXML                          // fx:id="resultPane"
    private AnchorPane resultPane; // Panel que muestra el resultado (victoria o derrota)
    @FXML                          // fx:id="resultText"
    private Text resultText;       // Texto que muestra el resultado (victoria o derrota
    @FXML                          // fx:id="gameCanvas"
    private Canvas gameCanvas;     // Canvas utilizado para dibujar el juego
    private GraphicsContext gContext;// Contexto gráfico para dibujar en el Canvas

    private GameLoop gameLoop;
    private Player player;
    private ArrayList<Enemy> enemies = new ArrayList<Enemy>();
    private Level level;
    // NewLifeItem newLifeItem;

    /**
     * Inicializa el controlador.
     * Configura el contexto gráfico y crea el bucle del juego.
     * Genera el nivel, el jugador y los enemigos.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gContext = gameCanvas.getGraphicsContext2D();

        // Creación del bucle del juego
        gameLoop = new GameLoop() {
            @Override
            public void tick(double deltaTime) {
                update(deltaTime);
                draw(gameCanvas.getWidth(), gameCanvas.getHeight());
            }
        };
        level = new Level();
        player = new Player(new Point2D(46, 46), level);


        // Generación de enemigos
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

        // Eventos de entrada del teclado
        App.getScene().addEventHandler(KeyEvent.KEY_PRESSED, e -> { player.handleKeyPress(e); });
        App.getScene().addEventHandler(KeyEvent.KEY_RELEASED, e -> { player.handleKeyRelease(e); });
        gameLoop.start();
    }

    /**
     * Actualiza la lógica del juego en cada fotograma.
     * @param deltaTime El tiempo transcurrido desde el último fotograma en segundos.
     */
    private void update(double deltaTime) {
        level.update(deltaTime);
        player.update(deltaTime);
        enemies.forEach(enemy -> enemy.update(deltaTime));

        // Verifica las condiciones de victoria o derrota
        if (enemies.isEmpty()) {
            // Mostrar mensaje de victoria
            System.out.println("YOU WIN!");
            // Mostrar panel de Victoria
            gameLoop.stop();
            App.setRoot("views/MainMenuUI");
        }

        if (player.isDead()) {
            // Mostrar mensaje de derrota
            System.out.println("YOU LOSE!!!");
            // Mostrar panel de Derrota
            gameLoop.stop();// Detener el bucle del juego
            App.setRoot("views/MainMenuUI");
        }
    }

    /**
     * Dibuja el juego en el Canvas.
     * @param width Ancho del Canvas.
     * @param height Alto del Canvas.
     */
    private void draw(double width, double height) {
        gContext.setFill(Color.GRAY);
        gContext.fillRect(0, 0, width, height);

        level.draw(gContext);

        player.draw(gContext);
        enemies.forEach(enemy -> enemy.draw(gContext));
    }

    /**
     * Maneja el evento de clic en el botón de retroceso.
     * Cambia a la vista del menú principal.
     */
    @FXML
    void onBackButtonClicked(ActionEvent event) {
        App.setRoot("views/MainMenuUI");// Cambiar a la vista del menú principal
    }
}
