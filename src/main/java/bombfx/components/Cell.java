package bombfx.components;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;

public abstract class Cell {
    protected static final int SIZE = 32;
    protected Point2D pos;
    protected Rectangle collRect;

    public Cell(Point2D pos) {
        this.pos = pos;
    }

    public boolean overlap(Rectangle rect) {
        Bounds lRectBounds = rect.getLayoutBounds();
        // System.err.println("Bounds: " + lRectBounds);
        return collRect.intersects(rect.getLayoutBounds());
    }

    public Point2D getPos() {
        return pos;
    }

    public abstract void update(double delta);

    public abstract void draw(GraphicsContext gContext);

    // public abstract boolean collide(Point2D point);

    public abstract boolean collide(Rectangle rect);
}
