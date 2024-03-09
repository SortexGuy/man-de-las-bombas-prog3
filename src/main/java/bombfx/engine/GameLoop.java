package bombfx.engine;

import javafx.animation.AnimationTimer;

/**
 * Clase abstracta para manejar el bucle de juego.
 * Extiende AnimationTimer, que es un temporizador que proporciona una API de alto nivel para
 * la animación de la aplicación.
 */
public abstract class GameLoop extends AnimationTimer {
    private long lastNano = 0;
    private double max = -1;
    private double min = -1;
    private double timeToTick = 0;

    /**
     * Inicia el temporizador del bucle de juego.
     * Restablece los valores de tiempo a cero antes de comenzar.
     */
    @Override
    public void start() {
        lastNano = 0;
        max = -1;
        min = -1;
        super.start();
    }

    /**
     * Método de callback invocado en cada fotograma del juego.
     * Calcula el tiempo transcurrido entre fotogramas y llama al método tick del juego.
     * @param nowNano El tiempo actual en nanosegundos.
     */
    @Override
    public void handle(long nowNano) {
        if (lastNano == 0) {
            lastNano = nowNano;
            return;
        }

        long deltaNano = nowNano - lastNano;// Tiempo transcurrido entre fotogramas en nanosegundos
        double deltaS = ((double) deltaNano) / 1_000_000_000.0;// Tiempo transcurrido entre fotogramas en segundos
        lastNano = nowNano;

        double tickDur = 1 / 30.0; // Duración objetivo de cada "tick" del juego (30 ticks por segundo)
        timeToTick += deltaS;

        // Verifica si es el momento de ejecutar una "tick" del juego
        if (timeToTick >= 0.0f) {
            // Actualiza los valores máximo y mínimo de tiempo entre fotogramas
            if (timeToTick > max || max == -1)
                max = timeToTick;
            if (timeToTick < min || min == -1)
                min = timeToTick;

            // Llama al método tick del juego para realizar las actualizaciones del juego
            tick(deltaS);

            // sync(deltaS);

            // System.err.println("[DELTA> " + deltaS + " | TIMETODELTA> " + timeToTick + "
            // | MAX> "
            // + max + " | MIN> " + min + "]");

            // Ajusta el tiempo acumulado para ejecutar la próxima "tick" del juego
            timeToTick -= tickDur;
        }
    }

    /**
     * Método abstracto que debe ser implementado por las subclases para realizar las actualizaciones
     * del juego en cada fotograma.
     * @param deltaTime El tiempo transcurrido desde el último fotograma en segundos.
     */
    public abstract void tick(double deltaTime);

    // public abstract void sync(double deltaTime);
}
