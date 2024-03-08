package bombfx.components;

import java.util.Random;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Enemy extends Character {
    private Random random;
    private double changeDirTimer;
    private Level level;

    public Enemy(Point2D pos, Level level) {
        super(pos);
        this.level = level;
        this.random = new Random();
        speed = 100;
        changeDirTimer = 1.5;
    }

    public void update(double deltaTime) {
        changeDirTimer -= deltaTime;

        if (dir == null)
            dir = Point2D.ZERO;

        if (changeDirTimer <= 0) {
            changeDirection();
            changeDirTimer = 1.5;
        }

        Point2D newPos = pos.add(dir.multiply(speed * deltaTime));
        Rectangle collRect = new Rectangle(newPos.getX(), newPos.getY(), SIZE, SIZE);

        Point2D vel = level.collideAndMove(collRect);
        if (vel != Point2D.ZERO) {
            changeDirection();
        } else {
            pos = newPos;
        }
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

    // private boolean isValidMove(Point2D newPos) {
    // // le falta todavia
    // return true; // Devuelve true si la nueva posición es válida
    // }

    public void checkCollisions() {
    }

    /*
     * public boolean checkPlayerCollision(Player player) {
     * double distance = pos.distance(player.getPosition());
     * return distance < SIZE;
     * }
     */
}
