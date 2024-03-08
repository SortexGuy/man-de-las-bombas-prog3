package bombfx.components;

import java.util.Random;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Enemy extends Character {
    private Player player;
    private Level level;
    private Random random;
    private Rectangle collRect;
    private double changeDirTimer;

    public Enemy(Point2D pos, Level level, Player player) {
        super(pos);
        this.level = level;
        this.random = new Random();
        this.player = player;
        speed = 100;
        changeDirTimer = 1.5;
    }

    public void update(double deltaTime) {
        changeDirTimer -= deltaTime;

        // Movement
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

        // Collisions
        if (isTouchingPlayer()) {
            player.handleDamage();
        }
    }

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

    private boolean isTouchingPlayer() {
        // Calcular el área de colisión del enemigo
        Rectangle playerBounds = player.getCollRect();
        return collRect.getBoundsInParent().intersects(playerBounds.getBoundsInParent());
    }

    public Rectangle getCollRect() {
        return collRect;
    }
}
