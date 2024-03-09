package bombfx.components;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Clase que representa una celda de bomba en un entorno de juego.
 * Las celdas de bomba son celdas especiales que pueden explotar después de un
 * cierto tiempo.
 */
public class BombCell extends EmptyCell {
    private Player player;
    private Level level;
    private double timeLeft = 2.0; // Tiempo restante hasta que la bomba explote

    /**
     * Constructor de la clase BombCell.
     *
     * @param pos    La posición de la celda de bomba.
     * @param player El jugador.
     * @param level  El nivel.
     */
    public BombCell(Point2D pos, Player player, Level level) {
        super(pos);
        this.player = player;
        this.level = level;
        // Ajustar el rectángulo de colisión de la celda de bomba para que sea más
        // pequeño que el de una celda vacía
        this.collRect = new Rectangle(pos.getX() + 4, pos.getY() + 4, SIZE - 8, SIZE - 8);
    }

    /**
     * Método para actualizar el estado de la celda de bomba en cada fotograma del
     * juego.
     *
     * @param delta El tiempo transcurrido desde el último fotograma, en segundos.
     */
    @Override
    public void update(double delta) {
        timeLeft -= delta;
        // Si el tiempo restante es menor o igual a cero, la bomba explota y se elimina
        // del nivel
        if (timeLeft <= 0) {
            Point2D position = getPos().add(SIZE / 2, SIZE / 2);
            level.removeBomb(position, player.getBombPower(), Point2D.ZERO);
        }
    }

    /**
     * Método para dibujar la celda de bomba en el contexto gráfico dado.
     *
     * @param gContext El contexto gráfico en el que se dibujará la celda de bomba.
     */
    @Override
    public void draw(GraphicsContext gContext) {
        super.draw(gContext);

        Color c = Color.CRIMSON;
        gContext.setFill(c);
        gContext.beginPath();
        gContext.rect(pos.getX() + 8, pos.getY() + 8, SIZE - 16, SIZE - 16);
        gContext.closePath();
        gContext.fill();
        gContext.setStroke(Color.BLACK);
        gContext.setLineWidth(1);
        gContext.stroke();
    }

    /**
     * Método para verificar si la celda de bomba colisiona con un rectángulo dado.
     *
     * @param rect El rectángulo con el que se comprueba la colisión.
     * @return true si la celda de bomba colisiona con el rectángulo dado, de lo
     *         contrario false.
     */
    @Override
    public boolean collide(Rectangle rect) {
        return overlap(rect); // Comprobar si la celda de bomba se superpone con el rectángulo dado
    }
}
