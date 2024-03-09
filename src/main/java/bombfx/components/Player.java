package bombfx.components;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import bombfx.engine.InputHandler;
import bombfx.engine.InputHandler.InputOrder;

public class Player extends Character {
    private Level level;
    private InputHandler inputHandler;
    private int lives = 3;
    private double bombTimer = 0;
    private Rectangle collRect;
    private boolean invulnerable;
    private double invulnerabilityDuration;
    private double invulnerabilityTimer;

    public Player(Point2D pos, Level level) {
        super(pos);
        this.level = level;
        this.inputHandler = new InputHandler();
        this.invulnerable = false;
        this.invulnerabilityDuration = 1.0;
        this.invulnerabilityTimer = 0.0;
    }

    @Override
    public void update(double delta) {
        boolean up = inputHandler.getInput(InputOrder.UP).pressed;
        boolean down = inputHandler.getInput(InputOrder.DOWN).pressed;
        boolean left = inputHandler.getInput(InputOrder.LEFT).pressed;
        boolean right = inputHandler.getInput(InputOrder.RIGHT).pressed;
        boolean bomb = inputHandler.getInput(InputOrder.BOMB).pressed;

        // Invulnerability del player
        if (invulnerable) {
            invulnerabilityTimer -= delta;
            if (invulnerabilityTimer <= 0) {
                invulnerable = false;
            }
        }

        // Movement
        double x = (left) ? -1.0 : (right) ? 1.0 : 0;
        double y = (up) ? -1.0 : (down) ? 1.0 : 0;
        dir = new Point2D(x, y).normalize();
        Point2D vel = dir.multiply(delta * speed);
        pos = pos.add(vel);
        if (dir.magnitude() > 0.5 && (dir.getX() == 0 || dir.getY() == 0)) {
            facing = new Point2D(dir.getX(), dir.getY());
        }

        // Wall Collisions
        collRect = new Rectangle(pos.getX(), pos.getY(), SIZE, SIZE);
        vel = level.collideAndMove(collRect);
        if (vel != Point2D.ZERO)
            pos = pos.subtract(vel.normalize().multiply(delta * speed));

        // Bomb handling
        Point2D facingPoint = facing.multiply(32).add(pos.add(SIZE / 2, SIZE / 2));
        bombTimer -= delta;
        if (bomb && bombTimer <= 0) {
            if (level.addBomb(facingPoint))
                bombTimer = 0.5;
            else
                bombTimer = 0.1;
        }
    }

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

        // Facing debug
        Point2D center = pos.add(SIZE / 2.0, SIZE / 2.0);
        gContext.beginPath();
        gContext.moveTo(center.getX(), center.getY());
        gContext.lineTo(center.getX() + facing.getX() * 32.0, center.getY() + facing.getY() * 32.0);
        gContext.closePath();
        gContext.setStroke(Color.BLACK);
        gContext.setLineWidth(4);
        gContext.stroke();
    }

    public void handleDamage() {
        if (!invulnerable) {
            lives--;
            System.out.println("¡El jugador ha sido golpeado! Vidas restantes: " + lives);

            invulnerable = true;
            invulnerabilityTimer = invulnerabilityDuration;
        }
    }

    public void addLife() {
        lives++;
        System.out.println("¡Vida extra otorgada! Vidas restantes: " + lives);
    }

    public void activateInvulnerability(double duration) {
        invulnerable = true;
        invulnerabilityDuration = duration;
        invulnerabilityTimer = duration;
    }

    public void handleKeyPress(KeyEvent event) {
        inputHandler.inputPressed(event.getCode());
    }

    public void handleKeyRelease(KeyEvent event) {
        inputHandler.inputReleased(event.getCode());
    }

    public boolean isDead() {
        return lives <= 0;
    }

    public Rectangle getCollRect() {
        return collRect;
    }
}
