package bombfx.components;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Clase que representa una celda de objeto en el juego.
 * Las celdas de objeto pueden contener ítems que el jugador puede recoger.
 */
public class ItemCell extends EmptyCell {
    protected Player player;
    protected Level level;

    /**
     * Constructor que inicializa la posición de la celda de objeto, el jugador y el nivel.
     * @param pos La posición inicial de la celda de objeto.
     * @param player El jugador que puede recoger el ítem de esta celda.
     * @param level El nivel en el que se encuentra la celda de objeto.
     */
    public ItemCell(Point2D pos, Player player, Level level) {
        super(pos);
        this.player = player;
        this.level = level;
        this.collRect = new Rectangle(pos.getX() + 4, pos.getY() + 4, SIZE - 8, SIZE - 8);
    }

    /**
     * Método para actualizar el estado de la celda de objeto en cada fotograma del juego.
     * No realiza ninguna operación ya que las celdas de objeto no tienen comportamiento dinámico.
     * @param delta El tiempo transcurrido desde el último fotograma, en segundos.
     */
    @Override
    public void update(double delta) {
    }

    /**
     * Método para dibujar la celda de objeto en el contexto gráfico dado.
     * @param gContext El contexto gráfico en el que se dibujará la celda de objeto.
     */
    @Override
    public void draw(GraphicsContext gContext) {
        super.draw(gContext);// Se delega el dibujo al método draw de la clase padre EmptyCell
    }

    /**
     * Método para verificar si la celda de objeto se superpone con un rectángulo dado.
     * Las celdas de objeto no tienen colisión con otros objetos.
     * @param rect El rectángulo con el que se verifica la superposición.
     * @return false siempre, ya que las celdas de objeto no tienen colisión.
     */
    @Override
    public boolean collide(Rectangle rect) {
        return false;
    }
}
