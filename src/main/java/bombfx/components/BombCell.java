package bombfx.components;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class BombCell extends EmptyCell {
    private Player player;
    private Level level;
    private double timeLeft = 2.0;

    public BombCell(Point2D pos, Player player, Level level) {
        super(pos);
        this.player = player;
        this.level = level;
        this.collRect = new Rectangle(pos.getX() + 4, pos.getY() + 4, SIZE - 8, SIZE - 8);
    }

    @Override
    public void update(double delta) {
        timeLeft -= delta;
        if (timeLeft <= 0) {
            Point2D position = getPos().add(SIZE / 2, SIZE / 2);
            level.removeBomb(position, player.getBombPower(), Point2D.ZERO);
        }
    }

    @Override
    public void draw(GraphicsContext gContext) {
        super.draw(gContext);

        Color c = Color.CRIMSON;
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
        return overlap(rect);
    }
}
