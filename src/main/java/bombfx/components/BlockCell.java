package bombfx.components;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Clase que representa una celda de bloque en un entorno de juego.
 * Las celdas de bloque (rompibles) son obstáculos que bloquean el paso de otros
 * elementos del juego.
 */
public class BlockCell extends Cell {
    /**
     * Constructor que inicializa la posición y el rectángulo de colisión de la
     * celda de bloque.
     *
     * @param pos La posición inicial de la celda de bloque.
     */
    public BlockCell(Point2D pos) {
        super(pos);
        this.collRect = new Rectangle(pos.getX(), pos.getY(), SIZE, SIZE);
    }

    /**
     * Método para actualizar el estado de la celda de bloque. No realiza ninguna
     * acción en este caso.
     *
     * @param delta El tiempo transcurrido desde el último fotograma, en segundos.
     */
    @Override
    public void update(double delta) {
    }

    /**
     * Método para dibujar la celda de bloque en el contexto gráfico dado.
     *
     * @param gContext El contexto gráfico en el que se dibujará la celda de bloque.
     */
    @Override
    public void draw(GraphicsContext gContext) {
        Color c = Color.GOLD;
        gContext.setFill(c);
        gContext.beginPath();
        gContext.rect(pos.getX(), pos.getY(), SIZE, SIZE);
        gContext.closePath();
        gContext.fill();
        gContext.setStroke(Color.BLACK);
        gContext.setLineWidth(1);
        gContext.stroke();
    }

    /**
     * Método para verificar si la celda de bloque colisiona con un rectángulo dado.
     *
     * @param rect El rectángulo con el que se comprueba la colisión.
     * @return true si la celda de bloque colisiona con el rectángulo dado, de lo
     *         contrario false.
     */
    @Override
    public boolean collide(Rectangle rect) {
        return overlap(rect);
    }
}
