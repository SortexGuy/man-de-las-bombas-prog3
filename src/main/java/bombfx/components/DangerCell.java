package bombfx.components;

import java.util.ArrayList;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class DangerCell extends EmptyCell {
    private Player player;
    private ArrayList<Enemy> enemies;
    private Level level;
    private double timeLeft = 1.0;

    public DangerCell(Point2D pos, Player player, ArrayList<Enemy> enemies, Level level) {
        super(pos);
        this.player = player;
        this.enemies = enemies;
        this.level = level;
        this.collRect = new Rectangle(pos.getX() + 4, pos.getY() + 4, SIZE - 8, SIZE - 8);
    }

    @Override
    public void update(double delta) {
        timeLeft -= delta;
        if (timeLeft <= 0) {
            Point2D position = getPos().add(SIZE / 2, SIZE / 2);
            level.removeDanger(position);
        }
    }

    @Override
    public void draw(GraphicsContext gContext) {
        super.draw(gContext);

        int realSize = SIZE / 5;
        Color c = Color.CRIMSON;
        gContext.setFill(c);
        gContext.beginPath();
        gContext.rect(pos.getX() + (SIZE / 2 - realSize / 2),
                pos.getY() + (SIZE / 2 - realSize / 2), realSize, realSize);
        gContext.rect(pos.getX() + 4, pos.getY() + 4, realSize, realSize);
        gContext.rect(pos.getX() + (SIZE - 4 - realSize), pos.getY() + 4, realSize, realSize);
        gContext.rect(pos.getX() + 4, pos.getY() + (SIZE - 4 - realSize), realSize, realSize);
        gContext.rect(pos.getX() + (SIZE - 4 - realSize), pos.getY() + (SIZE - 4 - realSize),
                realSize, realSize);
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
