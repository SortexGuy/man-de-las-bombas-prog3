package bombfx.components;

import javafx.geometry.Point2D;
import javafx.scene.canvas.GraphicsContext;

/**
 * Clase abstracta que representa un personaje en el juego.
 * Esta clase sirve como base para definir diferentes tipos de personajes con
 * comportamientos específicos.
 */
public abstract class Character extends Object {
    protected final int SIZE = 24;
    protected int speed = 200;
    protected Point2D facing = new Point2D(0, 1);
    protected Point2D pos;
    protected Point2D dir;

    /**
     * Constructor que inicializa la posición del personaje.
     *
     * @param pos La posición inicial del personaje.
     */
    public Character(Point2D pos) {
        this.pos = pos.subtract(SIZE / 2, SIZE / 2);
    }

    /**
     * Constructor que inicializa la posición y dirección del personaje.
     *
     * @param pos La posición inicial del personaje.
     * @param dir La dirección inicial del personaje.
     */
    public Character(Point2D pos, Point2D dir) {
        this.pos = pos.subtract(SIZE / 2, SIZE / 2);
        this.dir = dir;
    }

    /**
     * Método abstracto para actualizar el estado del personaje en cada fotograma
     * del juego.
     *
     * @param delta El tiempo transcurrido desde el último fotograma, en segundos.
     */
    public abstract void update(double delta);

    /**
     * Método abstracto para dibujar el personaje en el contexto gráfico dado.
     *
     * @param gContext El contexto gráfico en el que se dibujará el personaje.
     */
    public abstract void draw(GraphicsContext gContext);
}
