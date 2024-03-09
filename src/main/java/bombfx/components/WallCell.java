package bombfx.components;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 * Clase que representa una celda de pared en el juego.
 * Las celdas de pared son obstáculos que bloquean el movimiento de los
 * personajes.
 */
public class WallCell extends Cell {
    /**
     * Constructor que inicializa la posición de la celda de pared.
     *
     * @param pos La posición inicial de la celda de pared.
     */
    public WallCell(Point2D pos) {
        super(pos);
    }

    /**
     * Método para actualizar el estado de la celda de pared en cada fotograma del
     * juego.
     * No realiza ninguna operación ya que las celdas de pared no tienen
     * comportamiento dinámico.
     *
     * @param delta El tiempo transcurrido desde el último fotograma, en segundos.
     */
    @Override
    public void update(double delta) {
    }

    /**
     * Método para dibujar la celda de pared en el contexto gráfico dado.
     *
     * @param gContext El contexto gráfico en el que se dibujará la celda de pared.
     */
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

    /**
     * Método para verificar si la celda de pared se superpone con un rectángulo
     * dado.
     *
     * @param rect El rectángulo con el que se verifica la superposición.
     * @return true si la celda de pared se superpone con el rectángulo, de lo
     *         contrario false.
     */
    @Override
    public boolean collide(Rectangle rect) {
        return overlap(rect);
    }
}
