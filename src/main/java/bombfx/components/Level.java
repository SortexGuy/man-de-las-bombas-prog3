package bombfx.components;

import java.util.ArrayList;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;

public class Level extends Object {
    private final int GRID_SIZE = 32;
    private final int GRID_NUM = 13;
    private ArrayList<Cell> cells;

    public Level() {
        for (int i = 0; i < GRID_NUM; i++) {
            for (int j = 0; j < GRID_NUM; j++) {
                Cell cell;
                if (i == 0 || j == 0 || i == GRID_NUM - 1 || j == GRID_NUM - 1
                        || (i % 2 == 0 && j % 2 == 0)) {
                    cell = new WallCell(new Point2D(i * GRID_SIZE, j * GRID_SIZE));
                } else if (Math.random() < 0.4) {
                    cell = new EmptyCell(new Point2D(i * GRID_SIZE, j * GRID_SIZE));
                } else {
                    cell = new EmptyCell(new Point2D(i * GRID_SIZE, j * GRID_SIZE));
                }
                cells.add(cell);
            }
        }
    }

    public void update(double delta) {
        cells.forEach(c -> c.update(delta));
    }

    public void draw(GraphicsContext gContext) {
        cells.forEach(c -> c.draw(gContext));
    }

    public boolean collide(Point2D point) {
        return false;
    }

    public Point2D collideAndMove(Rectangle rect) {
        Point2D retDir = Point2D.ZERO;
        for (Cell cell : cells) {
            boolean isColliding = cell.collide(rect);
            if (!isColliding)
                continue;

            retDir = cell.getPos().subtract(rect.getX(), rect.getY());
        }
        return retDir;
    }
}