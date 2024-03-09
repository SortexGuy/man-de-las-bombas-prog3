package bombfx.components;

import java.util.ArrayList;
import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

/**
 * Clase que representa una celda de peligro en un entorno de juego.
 * Las celdas de peligro son celdas especiales que infligen daño al jugador y
 * eliminan enemigos cercanos.
 */
public class DangerCell extends EmptyCell {
    private Player player;
    private ArrayList<Enemy> enemies;
    private Level level;
    private double timeLeft = 0.7;

    /**
     * Constructor de la clase DangerCell.
     *
     * @param pos     La posición de la celda de peligro.
     * @param player  El jugador.
     * @param enemies Los enemigos.
     * @param level   El nivel.
     */
    public DangerCell(Point2D pos, Player player, ArrayList<Enemy> enemies, Level level) {
        super(pos);
        this.player = player;
        this.enemies = enemies;
        this.level = level;
        // Ajustar el rectángulo de colisión de la celda de peligro para que sea más
        // pequeño que el de una celda vacía
        this.collRect = new Rectangle(pos.getX() + 4, pos.getY() + 4, SIZE - 8, SIZE - 8);
    }

    /**
     * Método para actualizar el estado de la celda de peligro en cada fotograma del
     * juego.
     *
     * @param delta El tiempo transcurrido desde el último fotograma, en segundos.
     */
    @Override
    public void update(double delta) {
        timeLeft -= delta;

        // Si el jugador se superpone con la celda de peligro, el jugador sufre daño
        if (this.overlap(player.getCollRect())) {
            player.handleDamage();
        }

        // Eliminar los enemigos que se superponen con la celda de peligro
        for (int i = 0; i < enemies.size(); i++) {
            Enemy e = enemies.get(i);
            if (!this.overlap(e.getCollRect()))
                continue;

            enemies.remove(i);
        }

        // Si el tiempo restante es menor o igual a cero, la celda de peligro desaparece
        // del nivel
        if (timeLeft <= 0) {
            Point2D position = getPos().add(SIZE / 2, SIZE / 2);
            level.removeDanger(position);
        }
    }

    /**
     * Método para dibujar la celda de peligro en el contexto gráfico dado.
     *
     * @param gContext El contexto gráfico en el que se dibujará la celda de
     *                 peligro.
     */
    @Override
    public void draw(GraphicsContext gContext) {
        super.draw(gContext);

        // Dibujar el indicador de peligro (cuatro pequeños rectángulos rojos en las
        // esquinas de la celda de peligro)
        int realSize = SIZE / 5;
        Color c = Color.CRIMSON;
        gContext.setFill(c);
        gContext.beginPath();
        gContext.rect(pos.getX() + (SIZE / 2 - realSize / 2),
                pos.getY() + (SIZE / 2 - realSize / 2), realSize, realSize);
        gContext.rect(pos.getX() + 4, pos.getY() + 4, realSize, realSize);
        gContext.rect(pos.getX() + (SIZE - 4 - realSize), pos.getY() + 4, realSize, realSize);
        gContext.rect(pos.getX() + 4, pos.getY() + (SIZE - 4 - realSize), realSize, realSize);
        gContext.rect(pos.getX() + (SIZE - 4 - realSize), pos.getY() + (SIZE - 4 - realSize),
                realSize, realSize);
        gContext.closePath();
        gContext.fill();
        gContext.setStroke(Color.BLACK);
        gContext.setLineWidth(1);
        gContext.stroke();
    }

    /**
     * Método para verificar si la celda de peligro colisiona con un rectángulo
     * dado.
     *
     * @param rect El rectángulo con el que se comprueba la colisión.
     * @return Siempre devuelve falso ya que una celda de peligro no puede
     *         colisionar.
     */
    @Override
    public boolean collide(Rectangle rect) {
        return false;
    }
}
