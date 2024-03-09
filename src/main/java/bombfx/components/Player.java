package bombfx.components;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import bombfx.engine.InputHandler;
import bombfx.engine.InputHandler.InputOrder;

/**
 * Clase que representa al jugador en el juego.
 * El jugador puede moverse por el nivel, colocar bombas y tiene una cantidad limitada de vidas.
 */
public class Player extends Character {
    private Level level;
    private InputHandler inputHandler;// Manejador de entrada para controlar las acciones del jugador
    private int lives = 3;
    private int bombPower = 1;
    private double bombTimer = 0;// Temporizador para limitar la frecuencia de colocación de bombas
    private Rectangle collRect;
    private boolean invulnerable;// Indica si el jugador está temporalmente invulnerable
    private double invulnerabilityDuration;// Duración de la invulnerabilidad del jugador
    private double invulnerabilityTimer;// Temporizador para controlar la duración de la invulnerabilidad

    /**
     * Constructor que inicializa la posición del jugador y el nivel en el que se encuentra.
     * la entrada de acciones del jugador y el tiempo de invulnerabilidad del jugador.
     * @param pos La posición inicial del jugador.
     * @param level El nivel en el que se encuentra el jugador.
     */
    public Player(Point2D pos, Level level) {
        super(pos);
        this.level = level;
        this.inputHandler = new InputHandler();
        this.invulnerable = false;
        this.invulnerabilityDuration = 1.0;
        this.invulnerabilityTimer = 0.0;
    }

    /**
     * Método para actualizar el estado del jugador en cada fotograma del juego.
     * @param delta El tiempo transcurrido desde el último fotograma, en segundos.
     */
    @Override
    public void update(double delta) {
        boolean up = inputHandler.getInput(InputOrder.UP).pressed;
        boolean down = inputHandler.getInput(InputOrder.DOWN).pressed;
        boolean left = inputHandler.getInput(InputOrder.LEFT).pressed;
        boolean right = inputHandler.getInput(InputOrder.RIGHT).pressed;
        boolean bomb = inputHandler.getInput(InputOrder.BOMB).pressed;

        // Invulnerabilidad del jugador
        if (invulnerable) {
            invulnerabilityTimer -= delta;
            if (invulnerabilityTimer <= 0) {
                invulnerable = false;
            }
        }

        // Movimiento del jugador
        double x = (left) ? -1.0 : (right) ? 1.0 : 0;
        double y = (up) ? -1.0 : (down) ? 1.0 : 0;
        dir = new Point2D(x, y).normalize();
        Point2D vel = dir.multiply(delta * speed);
        pos = pos.add(vel);
        if (dir.magnitude() > 0.5 && (dir.getX() == 0 || dir.getY() == 0)) {
            facing = new Point2D(dir.getX(), dir.getY());
        }

        // Colisiones con paredes
        collRect = new Rectangle(pos.getX(), pos.getY(), SIZE, SIZE);
        vel = level.collideAndMove(collRect);
        if (vel != Point2D.ZERO)
            pos = pos.subtract(vel.normalize().multiply(delta * speed));

        // Manejo de bombas
        Point2D facingPoint = facing.multiply(32).add(pos.add(SIZE / 2, SIZE / 2));
        bombTimer -= delta;
        if (bomb && bombTimer <= 0) {
            if (level.addBomb(facingPoint))
                bombTimer = 0.5;
            else
                bombTimer = 0.1;
        }
    }

    /**
     * Método para dibujar al jugador en el contexto gráfico dado.
     * @param gContext El contexto gráfico en el que se dibujará el jugador.
     */
    @Override
    public void draw(GraphicsContext gContext) {
        Color c = Color.ORANGE;
        if (invulnerable)
            c = Color.CYAN;
        gContext.setFill(c);
        gContext.beginPath();
        gContext.rect(pos.getX(), pos.getY(), SIZE, SIZE);
        gContext.closePath();
        gContext.fill();
        gContext.setStroke(Color.BLACK);
        gContext.setLineWidth(1);
        gContext.stroke();

        // Debug de dirección
        Point2D center = pos.add(SIZE / 2.0, SIZE / 2.0);
        gContext.beginPath();
        gContext.moveTo(center.getX(), center.getY());
        gContext.lineTo(center.getX() + facing.getX() * 32.0, center.getY() + facing.getY() * 32.0);
        gContext.closePath();
        gContext.setStroke(Color.BLACK);
        gContext.setLineWidth(4);
        gContext.stroke();
    }

    /**
     * Método para manejar el daño recibido por el jugador.
     * Si el jugador no está invulnerable, pierde una vida y se vuelve invulnerable temporalmente.
     */
    public void handleDamage() {
        if (!invulnerable) {
            lives--;
            System.out.println("¡El jugador ha sido golpeado! Vidas restantes: " + lives);

            invulnerable = true;
            invulnerabilityTimer = invulnerabilityDuration;
        }
    }

    /**
     * Método para agregar una vida extra al jugador.
     */
    public void addLife() {
        lives++;
    }

    public void addBombPower() {
        if (bombPower < 4)
            bombPower++;
    }

    /**
     * Método para activar la invulnerabilidad del jugador durante un tiempo específico.
     * @param duration La duración de la invulnerabilidad, en segundos.
     */
    public void activateInvulnerability(double duration) {
        invulnerable = true;
        invulnerabilityDuration = duration;
        invulnerabilityTimer = duration;
    }

    /**
     * Método para manejar el evento de presionar una tecla por parte del jugador.
     * @param event El evento de teclado asociado a la tecla presionada.
     */
    public void handleKeyPress(KeyEvent event) {
        inputHandler.inputPressed(event.getCode());
    }

    /**
     * Método para manejar el evento de soltar una tecla por parte del jugador.
     * @param event El evento de teclado asociado a la tecla soltada.
     */
    public void handleKeyRelease(KeyEvent event) {
        inputHandler.inputReleased(event.getCode());
    }

    /**
     * Método para verificar si el jugador ha perdido todas sus vidas.
     * @return true si el jugador ha perdido todas sus vidas, de lo contrario false.
     */
    public boolean isDead() {
        return lives <= 0;
    }

    /**
     * Método para obtener el rectángulo de colisión del jugador.
     * @return El rectángulo de colisión del jugador.
     */
    public Rectangle getCollRect() {
        return collRect;
    }

    public String getLives() {
        return Integer.toString(lives);
    }

    public int getBombPower() {
        return bombPower;
    }
}
