package bombfx.engine;

import javafx.scene.input.KeyCode;

/**
 * Clase para manejar las entradas del usuario en el juego.
 */
public class InputHandler {

    /**
     * Clase interna que representa una entrada de teclado.
     */
    public class Input {
        public KeyCode kCode;
        public Boolean pressed = false;

        /**
         * Constructor para inicializar una entrada de teclado con el código de tecla proporcionado.
         * @param kCode El código de la tecla.
         */
        public Input(KeyCode kCode) {
            this.kCode = kCode;
        }
    }

    /**
     * Enumeración que define el orden de las entradas.
     */
    public enum InputOrder {
        UP,
        DOWN,
        LEFT,
        RIGHT,
        BOMB,
        // SPRINT,
        // SPECIAL;
    }

    // Arreglo que almacena las entradas de teclado
    public Input[] inputs = new Input[InputOrder.values().length];

    /**
     * Constructor que inicializa las entradas de teclado predeterminadas.
     */
    public InputHandler() {
        inputs[InputOrder.UP.ordinal()] = new Input(KeyCode.W);
        inputs[InputOrder.DOWN.ordinal()] = new Input(KeyCode.S);
        inputs[InputOrder.LEFT.ordinal()] = new Input(KeyCode.A);
        inputs[InputOrder.RIGHT.ordinal()] = new Input(KeyCode.D);

        inputs[InputOrder.BOMB.ordinal()] = new Input(KeyCode.SPACE);
        // inputs[InputOrder.SPRINT.ordinal()] = new Input(KeyCode.K);
        // inputs[InputOrder.SPECIAL.ordinal()] = new Input(KeyCode.L);
    }

    /**
     * Método para manejar el evento de tecla presionada.
     * @param kCode El código de la tecla que se presionó.
     */
    public void inputPressed(KeyCode kCode) {
        for (Input i : inputs) {
            if (i.kCode != kCode)
                continue;

            if (!i.pressed)
                i.pressed = true;
        }
    }

    /**
     * Método para manejar el evento de tecla liberada.
     * @param kCode El código de la tecla que se liberó.
     */
    public void inputReleased(KeyCode kCode) {
        for (Input i : inputs) {
            if (i.kCode != kCode)
                continue;

            if (i.pressed)
                i.pressed = false;
        }
    }

    /**
     * Obtiene la entrada de teclado para un orden específico.
     * @param action El orden de la entrada.
     * @return La entrada de teclado asociada al orden especificado.
     */
    public Input getInput(InputOrder action) {
        return inputs[action.ordinal()];
    }
}
