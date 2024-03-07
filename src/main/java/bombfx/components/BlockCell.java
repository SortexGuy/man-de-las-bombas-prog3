package bombfx.components;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BlockCell extends Cell {
    public BlockCell(Point2D pos) {
        super(pos);
        this.collRect = new Rectangle(pos.getX(), pos.getY(), SIZE, SIZE);
    }

    @Override
    public void update(double delta) {
    }

    @Override
    public void draw(GraphicsContext gContext) {
        Color c = Color.DIMGRAY;
        gContext.setFill(c);
        gContext.beginPath();
        gContext.rect(pos.getX(), pos.getY(), SIZE, SIZE);
        gContext.closePath();
        gContext.fill();
        gContext.setStroke(Color.BLACK);
        gContext.setLineWidth(1);
        gContext.stroke();
    }

    @Override
    public boolean collide(Rectangle rect) {
        // this.rect
        return false;
    }
}
