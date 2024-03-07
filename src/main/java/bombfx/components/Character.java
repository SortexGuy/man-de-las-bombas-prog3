package bombfx.components;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

public abstract class Character extends Object {
    protected final int SIZE = 28;
    protected int speed = 200;
    protected Point2D facing = new Point2D(0, 1);
    protected Point2D pos;
    protected Point2D dir;

    public Character(Point2D pos) {
        this.pos = pos.subtract(SIZE / 2, SIZE / 2);
    }

    public Character(Point2D pos, Point2D dir) {
        this.pos = pos.subtract(SIZE / 2, SIZE / 2);
        this.dir = dir;
    }

    public abstract void update(double delta);

    public abstract void draw(GraphicsContext gContext);
}
