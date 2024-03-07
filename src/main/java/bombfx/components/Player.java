package bombfx.components;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import bombfx.engine.InputHandler;
import bombfx.engine.InputHandler.InputOrder;

public class Player extends Character {
    private Level level;
    private InputHandler inputHandler;
    private int lives = 3;

    public Player(Point2D pos, Level level) {
        super(pos);
        this.level = level;
        this.inputHandler = new InputHandler();
    }

    @Override
    public void update(double delta) {
        boolean up = inputHandler.getInput(InputOrder.UP).pressed;
        boolean down = inputHandler.getInput(InputOrder.DOWN).pressed;
        boolean left = inputHandler.getInput(InputOrder.LEFT).pressed;
        boolean right = inputHandler.getInput(InputOrder.RIGHT).pressed;
        // boolean bomb = inputHandler.getInput(InputOrder.BOMB).pressed;

        // Movement
        double x = (left) ? -1.0 : (right) ? 1.0 : 0;
        double y = (up) ? -1.0 : (down) ? 1.0 : 0;
        dir = new Point2D(x, y).normalize();
        // lastDir = dir;
        // lastDelta = delta;
        Point2D vel = dir.multiply(delta * speed);
        pos = pos.add(vel);
        if (dir.getX() == 0 || dir.getY() == 0) {
            facing = dir;
        }

        // Point2D facingPoint = facing.multiply(SIZE / 2.0).add(pos);
        Rectangle collRect = new Rectangle(pos.getX(), pos.getY(), SIZE, SIZE);
        vel = level.collideAndMove(collRect);
        pos = pos.add(vel.multiply(delta * speed));
    }

    @Override
    public void draw(GraphicsContext gContext) {
        Color c = Color.ORANGE;
        gContext.setFill(c);
        // gContext.fillRect(realPos.getX(), realPos.getY(), size.getX(), size.getY());
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

    public void handleKeyPress(KeyEvent event) {
        inputHandler.inputPressed(event.getCode());
    }

    public void handleKeyRelease(KeyEvent event) {
        inputHandler.inputReleased(event.getCode());
    }
}
