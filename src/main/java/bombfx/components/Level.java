package bombfx.components;

import java.util.ArrayList;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;

/**
 * Clase que representa un nivel en el juego.
 * Un nivel consiste en una matriz de celdas que forman el entorno del juego, junto con el jugador y los enemigos presentes en el nivel.
 */
public class Level extends Object {
    private final int GRID_SIZE = 32;
    private final int GRID_NUM = 13;
    private ArrayList<Cell> cells; //
    private Player player;
    private ArrayList<Enemy> enemies;

    /**
     * Constructor de la clase Level.
     * Inicializa el nivel creando una matriz de celdas con diferentes tipos de celdas, como celdas vacías, celdas de pared y celdas de bloque.
     * Además, inicializa las posiciones del jugador y los enemigos en el nivel.
     */
    public Level() {
        this.cells = new ArrayList<Cell>();
        for (int i = 0; i < GRID_NUM; i++) {
            for (int j = 0; j < GRID_NUM; j++) {
                Cell cell;
                if (i == 0 || j == 0 || i == GRID_NUM - 1 || j == GRID_NUM - 1
                        || (i % 2 == 0 && j % 2 == 0)) {
                    cell = new WallCell(new Point2D(i * GRID_SIZE, j * GRID_SIZE));
                } else if (Math.random() < 0.55
                        && !isPlayerNear(new Point2D(i * GRID_SIZE, j * GRID_SIZE))) {
                    cell = new BlockCell(new Point2D(i * GRID_SIZE, j * GRID_SIZE));
                } else {
                    cell = new EmptyCell(new Point2D(i * GRID_SIZE, j * GRID_SIZE));
                }
                cells.add(cell);
            }
        }
    }

    /**
     * Método para actualizar el estado del nivel en cada fotograma del juego.
     * Llama al método `update` de todas las celdas en el nivel para que actualicen su estado.
     * @param delta El tiempo transcurrido desde el último fotograma, en segundos.
     */
    public void update(double delta) {
        cells.forEach(c -> c.update(delta));
    }

    /**
     * Método para dibujar el nivel en el contexto gráfico dado.
     * Llama al método `draw` de todas las celdas en el nivel para que se dibujen en el contexto gráfico.
     * @param gContext El contexto gráfico en el que se dibujará el nivel.
     */
    public void draw(GraphicsContext gContext) {
        cells.forEach(c -> c.draw(gContext));
    }

    /**
     * Método para verificar si un punto dado colisiona con alguna celda en el nivel.
     * Este método se utiliza para detectar colisiones del jugador con las celdas del nivel.
     * @param point El punto a verificar la colisión.
     * @return true si el punto colisiona con alguna celda, false en caso contrario.
     */
    public boolean collide(Point2D point) {
        boolean ret = false;
        return ret;
    }

    /**
     * Método para calcular la dirección en la que mover un rectángulo para evitar colisiones con las celdas en el nivel.
     * @param rect El rectángulo que se moverá para evitar colisiones.
     * @return La dirección en la que mover el rectángulo para evitar colisiones.
     */
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

    /**
     * Verifica si el jugador está cerca de una posición dada en el nivel.
     * Esto se usa para determinar si el jugador puede ser afectado por elementos cercanos en el juego.
     * @param pos La posición a verificar.
     * @return true si el jugador está cerca de la posición dada, false en caso contrario.
     */
    public boolean isPlayerNear(Point2D pos) {
        Point2D playerPos = new Point2D(46, 46); // player.getPos();
        double distanceThreshold = GRID_SIZE;

        double distance = pos.distance(playerPos);
        return distance < distanceThreshold;
    }

    /**
     * Agrega una bomba a la posición especificada en el nivel.
     * @param position La posición donde se agregará la bomba.
     * @return true si se pudo agregar la bomba correctamente, false si la posición no es válida para colocar una bomba.
     */
    public boolean addBomb(Point2D position) {
        for (int i = 0; i < cells.size(); i++) {
            Cell cell = cells.get(i);
            if (!cell.contains(position))
                continue;
            if (cell instanceof WallCell || cell instanceof BlockCell)
                return false;

            cell = new BombCell(cell.getPos(), player, this);
            cells.set(i, cell);
            break;
        }
        return true;
    }

    /**
     * Elimina una bomba del nivel en una posición dada y su dirección.
     * @param position La posición de la bomba a eliminar.
     * @param times  el tiempo de la explosion.
     * @param dir La dirección de la explosión.
     */
    public void removeBomb(Point2D position, int times, Point2D dir) {
        for (int i = 0; i < cells.size(); i++) {
            Cell cell = cells.get(i);
            if (!cell.contains(position))
                continue;

            if (i < 0 || cell instanceof WallCell)
                return;
            if (cell instanceof BlockCell) {
                int random = ((int) (Math.random() * 8)) % 4;
                switch (random) {
                    case 0:
                        cell = new NewLifeItem(cell.getPos(), player, this);
                        break;
                    case 1:
                        cell = new InvulnerabilityItem(cell.getPos(), player, this);
                        break;
                    case 2:
                        cell = new NewLifeItem(cell.getPos(), player, this);
                        break;
                    default:
                        cell = new EmptyCell(cell.getPos());
                        break;
                }
            } else
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

    /**
     * Elimina item del nivel en una posición dada.
     * @param position La posición del item a eliminar.
     */
    public void removeItem(Point2D position) {
        for (int i = 0; i < cells.size(); i++) {
            Cell cell = cells.get(i);
            if (!cell.contains(position))
                continue;

            cell = new EmptyCell(cell.getPos());
            cells.set(i, cell);
            break;
        }
    }

    /**
     * Elimina las celdas de peligro del nivel en una posición dada.
     * @param position La posición del peligro a eliminar.
     */
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

    /**
     * Verifica si una celda en una posición dada está vacía.
     * @param pos La posición a verificar.
     * @return true si la celda en la posición dada está vacía, false en caso contrario.
     */
    public boolean isEmptyCell(Point2D pos) {
        for (Cell cell : cells) {
            if (cell instanceof EmptyCell && cell.contains(pos)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Obtiene el tamaño del grid del nivel.
     * @return El tamaño del grid del nivel.
     */
    public int getGridSize() {
        return GRID_SIZE;
    }

    /**
     * Establece el jugador en el nivel.
     * @param player El jugador a establecer en el nivel.
     */
    public void setPlayer(Player player) {
        this.player = player;
    }

    /**
     * Establece la lista de enemigos en el nivel.
     * @param enemies La lista de enemigos a establecer en el nivel.
     */
    public void setEnemies(ArrayList<Enemy> enemies) {
        this.enemies = enemies;
    }
}
