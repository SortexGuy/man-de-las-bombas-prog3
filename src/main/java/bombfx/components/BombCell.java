package bombfx.components;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BombCell extends Cell {
    private Player player;
    private Level level;

    public BombCell(Point2D pos, Player player, Level level) {
        super(pos);
        this.collRect = new Rectangle(pos.getX() + 4, pos.getY() + 4, SIZE - 8, SIZE - 8);
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
