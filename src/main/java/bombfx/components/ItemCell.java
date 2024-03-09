package bombfx.components;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ItemCell extends EmptyCell {
    private Player player;
    private Level level;

    public ItemCell(Point2D pos, Player player, Level level) {
        super(pos);
        this.player = player;
        this.level = level;
        this.collRect = new Rectangle(pos.getX() + 4, pos.getY() + 4, SIZE - 8, SIZE - 8);
    }

    @Override
    public void update(double delta) {
    }

    @Override
    public void draw(GraphicsContext gContext) {
        super.draw(gContext);
    }

    @Override
    public boolean collide(Rectangle rect) {
        return false;
    }
}
