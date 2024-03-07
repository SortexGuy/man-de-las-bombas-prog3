package bombfx.components;

import java.util.Random;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Enemy extends Character {
    private Random random;
    private double changeDirTimer;

    public Enemy(Point2D pos) {
        super(pos);
        this.random = new Random();
        speed = 100;
        changeDirTimer = 1.5;
        // startRandomMovement();
    }

    /*
     * private void startRandomMovement() {
     * new Thread(() -> {
     * while (true) {
     * int direction = random.nextInt(4);
     * Point2D newDir = null;
     * switch (direction) {
     * case 0: // Arriba
     * newDir = new Point2D(0, -1);
     * break;
     * case 1: // Abajo
     * newDir = new Point2D(0, 1);
     * break;
     * case 2: // Izquierda
     * newDir = new Point2D(-1, 0);
     * break;
     * case 3: // Derecha
     * newDir = new Point2D(1, 0);
     * break;
     * }
     * 
     * // Cambiar la dirección si la nueva dirección no es igual a la dirección
     * actual
     * if (!newDir.equals(dir)) {
     * dir = newDir;
     * facing = newDir;
     * }
     * 
     * Point2D newPos = pos.add(dir.multiply(speed / 1000.0));
     * // Verificar si la nueva posición está dentro del área del juego
     * if (isValidMove(newPos)) {
     * pos = newPos;
     * } else {
     * dir = new Point2D(-dir.getX(), -dir.getY());
     * // dir = dir.multiply(-1);
     * }
     * 
     * // Esperar un tiempo aleatorio antes de volver a elegir una dirección
     * try {
     * Thread.sleep(random.nextInt(1000) + 500); // Esperar entre 500ms y 1500ms
     * } catch (InterruptedException e) {
     * e.printStackTrace();
     * }
     * }
     * }).start();
     * }
     */

    public void update(double deltaTime) {
        changeDirTimer -= deltaTime;

        if (changeDirTimer <= 0) {
            changeDirection();
            changeDirTimer = 1.5;
        }

        Point2D newPos = pos.add(dir.multiply(speed * deltaTime));
        // Verificar si la nueva posición está dentro del área del juego
        if (isValidMove(newPos)) {
            pos = newPos;
        } else {
            // Si la nueva posición no es válida, invertir la dirección
            dir = new Point2D(-dir.getX(), -dir.getY());
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
        }
    }

    public void draw(GraphicsContext gContext) {
        gContext.setFill(Color.RED);
        gContext.fillRect(pos.getX(), pos.getY(), SIZE, SIZE);
    }

    /*
     * private void move() {
     * Point2D newPos = pos.add(dir.multiply(speed / 1000.0));
     * // Verificar si la nueva posición está dentro del área del juego
     * if (isValidMove(newPos)) {
     * pos = newPos;
     * } else {
     * dir = new Point2D(-dir.getX(), -dir.getY());
     * // dir = dir.multiply(-1);
     * }
     * }
     */

    private boolean isValidMove(Point2D newPos) {
        // le falta todavia
        return true; // Devuelve true si la nueva posición es válida
    }

    public void checkCollisions() {
    }

    /*
     * public boolean checkPlayerCollision(Player player) {
     * double distance = pos.distance(player.getPosition());
     * return distance < SIZE;
     * }
     */
}
