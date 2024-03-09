package bombfx.components;

import java.util.Random;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Clase que representa a un enemigo en el juego.
 * Los enemigos son personajes controlados por la inteligencia artificial que atacan al jugador.
 */
public class Enemy extends Character {
    private Player player;
    private Level level;
    private Random random;
    private Rectangle collRect;
    private double changeDirTimer;// Temporizador para cambiar la dirección del enemigo


    /**
     * Constructor que inicializa la posición del enemigo, el nivel y el jugador.
     * @param pos La posición inicial del enemigo.
     * @param level El nivel en el que se encuentra el enemigo.
     * @param player El jugador al que el enemigo persigue.
     */
    public Enemy(Point2D pos, Level level, Player player) {
        super(pos);
        this.level = level;
        this.random = new Random();
        this.player = player;
        speed = 100;// Velocidad de movimiento del enemigo
        changeDirTimer = 1.5;// Intervalo de tiempo para cambiar la dirección del enemigo
    }

    /**
     * Método para actualizar el estado del enemigo en cada fotograma del juego.
     * @param deltaTime El tiempo transcurrido desde el último fotograma, en segundos.
     */
    public void update(double deltaTime) {
        changeDirTimer -= deltaTime;

        // Movimiento
        if (dir == null)
            dir = Point2D.ZERO;

        if (changeDirTimer <= 0) {
            changeDirection();
            changeDirTimer = 1.5;
        }

        Point2D newPos = pos.add(dir.multiply(speed * deltaTime));
        collRect = new Rectangle(newPos.getX(), newPos.getY(), SIZE, SIZE);

        Point2D vel = level.collideAndMove(collRect);
        if (vel != Point2D.ZERO) {
            changeDirection();
        } else {
            pos = newPos;
        }

        // Colisiones con el jugador
        if (isTouchingPlayer()) {
            player.handleDamage();
        }
    }

    /**
     * Método para dibujar al enemigo en el contexto gráfico dado.
     * @param gContext El contexto gráfico en el que se dibujará el enemigo.
     */
    public void draw(GraphicsContext gContext) {
        Color c = Color.RED;
        gContext.setFill(c);
        gContext.beginPath();
        gContext.rect(pos.getX(), pos.getY(), SIZE, SIZE);
        gContext.closePath();
        gContext.fill();
        gContext.setStroke(Color.BLACK);
        gContext.setLineWidth(1);
        gContext.stroke();
    }

    /**
     * Método privado para cambiar la dirección del enemigo de forma aleatoria.
     */
    private void changeDirection() {
        int direction = random.nextInt(4);
        switch (direction) {
            case 0: // Arriba
                dir = new Point2D(0, -1);
                break;
            case 1: // Abajo
                dir = new Point2D(0, 1);
                break;
            case 2: // Izquierda
                dir = new Point2D(-1, 0);
                break;
            case 3: // Derecha
                dir = new Point2D(1, 0);
                break;
                // default:
                // dir = Point2D.ZERO;
                // break;
        }
    }

    /**
     * Método privado para verificar si el enemigo está tocando al jugador.
     * @return true si el enemigo está tocando al jugador, de lo contrario false.
     */
    private boolean isTouchingPlayer() {
        // Calcular el área de colisión del enemigo
        Rectangle playerBounds = player.getCollRect();
        return collRect.getBoundsInParent().intersects(playerBounds.getBoundsInParent());
    }

    /**
     * Método para obtener el rectángulo de colisión del enemigo.
     * @return El rectángulo de colisión del enemigo.
     */
    public Rectangle getCollRect() {
        return collRect;
    }
}
