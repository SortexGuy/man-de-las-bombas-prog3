package bombfx.components;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.shape.Rectangle;

/**
 * Clase abstracta que representa una celda en un entorno de juego.
 * Las celdas pueden tener diferentes tipos de comportamiento y pueden ser
 * ocupadas por otros elementos del juego.
 */
public abstract class Cell {
    protected static final int SIZE = 32;
    protected Point2D pos;
    protected Rectangle collRect;

    /**
     * Constructor que inicializa la posición y el rectángulo de colisión de la
     * celda.
     *
     * @param pos La posición inicial de la celda.
     */
    public Cell(Point2D pos) {
        this.pos = pos;
        this.collRect = new Rectangle(pos.getX(), pos.getY(), SIZE, SIZE);
    }

    /**
     * Comprueba si esta celda se superpone con un rectángulo dado.
     *
     * @param rect El rectángulo con el que se comprueba la superposición.
     * @return true si esta celda se superpone con el rectángulo dado, de lo
     *         contrario false.
     */
    public boolean overlap(Rectangle rect) {
        return collRect.intersects(rect.getLayoutBounds());
    }

    /**
     * Comprueba si esta celda contiene un punto dado.
     *
     * @param point El punto que se comprueba si está contenido en la celda.
     * @return true si esta celda contiene el punto dado, de lo contrario false.
     */
    public boolean contains(Point2D point) {
        return collRect.contains(point);
    }

    /**
     * Obtiene la posición de esta celda.
     *
     * @return La posición de esta celda.
     */
    public Point2D getPos() {
        return pos;
    }

    /**
     * Método abstracto para actualizar el estado de la celda en cada fotograma del
     * juego.
     *
     * @param delta El tiempo transcurrido desde el último fotograma, en segundos.
     */
    public abstract void update(double delta);

    /**
     * Método abstracto para dibujar la celda en el contexto gráfico dado.
     *
     * @param gContext El contexto gráfico en el que se dibujará la celda.
     */
    public abstract void draw(GraphicsContext gContext);

    // public abstract boolean collide(Point2D point);

    /**
     * Método abstracto para detectar colisiones con un rectángulo dado.
     *
     * @param rect El rectángulo con el que se comprueba la colisión.
     * @return true si la celda colisiona con el rectángulo dado, de lo contrario
     *         false.
     */
    public abstract boolean collide(Rectangle rect);
}
