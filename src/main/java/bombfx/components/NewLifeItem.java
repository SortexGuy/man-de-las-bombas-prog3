package bombfx.components;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Clase que representa un ítem de vida nueva en el juego.
 * Los ítems de vida nueva otorgan al jugador una vida extra cuando son recogidos.
 */
public class NewLifeItem extends ItemCell {

    /**
     * Constructor que inicializa la posición del ítem de vida nueva, el jugador y el nivel.
     * @param pos La posición inicial del ítem de vida nueva.
     * @param player El jugador que puede recoger el ítem de vida nueva.
     * @param level El nivel en el que se encuentra el ítem de vida nueva.
     */
    public NewLifeItem(Point2D pos, Player player, Level level) {
        super(pos, player, level);
        this.collRect = new Rectangle(pos.getX() + 4, pos.getY() + 4, SIZE - 8, SIZE - 8);
    }

    /**
     * Método para actualizar el estado del ítem de vida nueva en cada fotograma del juego.
     * Si el jugador se superpone con el ítem de vida nueva, se agrega una vida extra al jugador.
     * Además, se elimina el ítem de vida nueva del nivel.
     * @param delta El tiempo transcurrido desde el último fotograma, en segundos.
     */
    @Override
    public void update(double delta) {
        if (this.overlap(player.getCollRect())) {
            player.addLife(); // Agregar una vida extra al jugador
            Point2D newPos = new Point2D(pos.getX() + SIZE / 2, pos.getY() + SIZE / 2);
            level.removeItem(newPos); // Eliminar el ítem de vida nueva del nivel
        }
    }

    /**
     * Método para dibujar el ítem de vida nueva en el contexto gráfico dado.
     * @param gContext El contexto gráfico en el que se dibujará el ítem de vida nueva.
     */
    @Override
    public void draw(GraphicsContext gContext) {
        super.draw(gContext);

        Color c = Color.DARKORANGE;
        gContext.setFill(c);
        gContext.beginPath();
        gContext.rect(pos.getX() + 8, pos.getY() + 8, SIZE - 16, SIZE - 16);
        gContext.closePath();
        gContext.fill();
        gContext.setStroke(Color.BLACK);
        gContext.setLineWidth(1);
        gContext.stroke();
    }

}
