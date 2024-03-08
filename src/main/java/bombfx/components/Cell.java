package bombfx.components;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;

public abstract class Cell {
    protected static final int SIZE = 32;
    protected Point2D pos;
    protected Rectangle collRect;

    public Cell(Point2D pos) {
        this.pos = pos;
        this.collRect = new Rectangle(pos.getX(), pos.getY(), SIZE, SIZE);
    }

    public boolean overlap(Rectangle rect) {
        return collRect.intersects(rect.getLayoutBounds());
    }

    public boolean contains(Point2D point) {
        return collRect.contains(point);
    }

    public Point2D getPos() {
        return pos;
    }

    public abstract void update(double delta);

    public abstract void draw(GraphicsContext gContext);

    // public abstract boolean collide(Point2D point);

    public abstract boolean collide(Rectangle rect);
}
