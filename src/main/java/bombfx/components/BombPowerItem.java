package bombfx.components;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BombPowerItem extends ItemCell {
    public BombPowerItem(Point2D pos, Player player, Level level) {
        super(pos, player, level);
        this.collRect = new Rectangle(pos.getX() + 4, pos.getY() + 4, SIZE - 8, SIZE - 8);
    }

    @Override
    public void update(double delta) {
        if (this.overlap(player.getCollRect())) {
            player.addBombPower();
            Point2D newPos = new Point2D(pos.getX() + SIZE / 2, pos.getY() + SIZE / 2);
            level.removeItem(newPos);
        }
    }

    @Override
    public void draw(GraphicsContext gContext) {
        super.draw(gContext);

        Color c = Color.DARKRED;
        gContext.setFill(c);
        gContext.beginPath();
        gContext.rect(pos.getX() + 8, pos.getY() + 8, SIZE - 16, SIZE - 16);
        gContext.closePath();
        gContext.fill();
        gContext.setStroke(Color.BLACK);
        gContext.setLineWidth(1);
        gContext.stroke();
    }

    @Override
    public boolean collide(Rectangle rect) {
        return false;
    }
}
