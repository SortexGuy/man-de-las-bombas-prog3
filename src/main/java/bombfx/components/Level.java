package bombfx.components;

import java.util.ArrayList;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;

public class Level extends Object {
    private final int GRID_SIZE = 32;
    private final int GRID_NUM = 13;
    private ArrayList<Cell> cells;
    private Player player;
    private ArrayList<Enemy> enemies;

    public Level() {
        this.cells = new ArrayList<Cell>();
        for (int i = 0; i < GRID_NUM; i++) {
            for (int j = 0; j < GRID_NUM; j++) {
                Cell cell;
                if (i == 0 || j == 0 || i == GRID_NUM - 1 || j == GRID_NUM - 1
                        || (i % 2 == 0 && j % 2 == 0)) {
                    cell = new WallCell(new Point2D(i * GRID_SIZE, j * GRID_SIZE));
                } else if (Math.random() < 0.7
                        && !isPlayerNear(new Point2D(i * GRID_SIZE, j * GRID_SIZE))) {
                    cell = new BlockCell(new Point2D(i * GRID_SIZE, j * GRID_SIZE));
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
        boolean ret = false;
        // for (Cell cell : cells) {
        // boolean ret = cell.collide(point);
        // if (!ret)
        // continue;
        // }
        return ret;
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

    public boolean isPlayerNear(Point2D pos) {
        Point2D playerPos = new Point2D(46, 46); // player.getPos();
        double distanceThreshold = GRID_SIZE;

        double distance = pos.distance(playerPos);

        return distance < distanceThreshold;
    }

    public void addBomb(Point2D position) {
        for (int i = 0; i < cells.size(); i++) {
            Cell cell = cells.get(i);
            if (!cell.contains(position))
                continue;

            cell = new BombCell(cell.getPos(), player, this);
            cells.set(i, cell);
            break;
        }
    }

    public void removeBomb(Point2D position, int times, Point2D dir) {
        for (int i = 0; i < cells.size(); i++) {
            Cell cell = cells.get(i);
            if (!cell.contains(position))
                continue;

            if (i < 0 || cell instanceof WallCell)
                return;

            cell = new DangerCell(cell.getPos(), player, enemies, this);
            cells.set(i, cell);
            break;
        }

        for (int i = 0; i < times; i++) {
            if (dir == Point2D.ZERO) {
                Point2D newDir = new Point2D(1, 0);
                removeBomb(position.add(newDir.multiply(32)), times - 1, newDir);
                newDir = new Point2D(0, 1);
                removeBomb(position.add(newDir.multiply(32)), times - 1, newDir);
                newDir = new Point2D(-1, 0);
                removeBomb(position.add(newDir.multiply(32)), times - 1, newDir);
                newDir = new Point2D(0, -1);
                removeBomb(position.add(newDir.multiply(32)), times - 1, newDir);
            } else {
                removeBomb(position.add(dir.multiply(32)), times - 1, dir);
            }
        }
    }

    public void removeDanger(Point2D position) {
        for (int i = 0; i < cells.size(); i++) {
            Cell cell = cells.get(i);
            if (!cell.contains(position))
                continue;

            cell = new EmptyCell(cell.getPos());
            cells.set(i, cell);
            break;
        }
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setEnemies(ArrayList<Enemy> enemies) {
        this.enemies = enemies;
    }
}
