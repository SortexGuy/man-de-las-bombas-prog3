package bombfx.components;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Representa un ítem en el juego que otorga al jugador más poder para sus bombas.
 * Cuando el jugador lo recoge, incrementa el radio de la explotación de la bomba.
 */
public class BombPowerItem extends ItemCell {

    /**
     * Representa un ítem en el juego que otorga al jugador más poder para sus bombas.
     * Cuando el jugador lo recoge, su poder de bomba se incrementa.
     */
    public BombPowerItem(Point2D pos, Player player, Level level) {
        super(pos, player, level);
        this.collRect = new Rectangle(pos.getX() + 4, pos.getY() + 4, SIZE - 8, SIZE - 8);
    }

    /**
     * Método para actualizar el estado del ítem de BombPowerItem en cada fotograma del juego.
     * Si el jugador entra en contacto con el elemento, se incrementa el radio de la explotación de bombas y el elemento desaparece del nivel.
     * @param delta El tiempo transcurrido desde la última actualización.
     */
    @Override
    public void update(double delta) {
        if (this.overlap(player.getCollRect())) {
            player.addBombPower();
            Point2D newPos = new Point2D(pos.getX() + SIZE / 2, pos.getY() + SIZE / 2);
            level.removeItem(newPos);
        }
    }

    /**
     * Método para dibujar el ítem de BombPowerItem en el contexto gráfico dado.
     * @param gContext El contexto gráfico en el que se dibujará el ítem de BombPowerItem.
     */
    @Override
    public void draw(GraphicsContext gContext) {
        super.draw(gContext);

        Color c = Color.DARKRED;
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
