package bombfx.components;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Clase que representa una celda vacía en un entorno de juego.
 * Las celdas vacías no tienen ningún comportamiento especial y se utilizan principalmente como espacios vacíos en el juego.
 */
public class EmptyCell extends Cell {

    /**
     * Constructor que inicializa la posición de la celda vacía.
     * @param pos La posición inicial de la celda vacía.
     */
    public EmptyCell(Point2D pos) {
        super(pos);
    }

    /**
     * Método para actualizar el estado de la celda vacía. No realiza ninguna acción en este caso.
     * @param delta El tiempo transcurrido desde el último fotograma, en segundos.
     */
    @Override
    public void update(double delta) {
    }

    /**
     * Método para dibujar la celda vacía en el contexto gráfico dado.
     * @param gContext El contexto gráfico en el que se dibujará la celda vacía.
     */
    @Override
    public void draw(GraphicsContext gContext) {
        Color c = Color.WHITESMOKE;
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
     * Método para verificar si la celda vacía colisiona con un rectángulo dado. Siempre devuelve falso ya que una celda vacía no puede colisionar.
     * @param rect El rectángulo con el que se comprueba la colisión.
     * @return Siempre devuelve falso ya que una celda vacía no puede colisionar.
     */
    @Override
    public boolean collide(Rectangle rect) {
        return false;// Una celda vacía no puede colisionar
    }
}
