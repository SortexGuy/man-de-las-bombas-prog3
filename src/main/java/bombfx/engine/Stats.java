package bombfx.engine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;

/**
 * Estadisticas de cada partida
 */
public class Stats extends Object {
    private String nickname;
    private int games;
    private int wins;
    private int losses;

    /**
     * Creacion vacia de la clase
     */
    public Stats() {
    }

    /**
     * Carga las estadísticas del usuario especificado por {@code nickname}
     * si no existe, se crea el archivo y se inicializan los valores a 0
     * si existe, se cargan los valores
     */
    public void loadStats() {
        File file = new File(nickname + ".txt");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                System.err.println(
                        "Ocurrió un error al crear el archivo del usuario " + nickname + ".");
                e.printStackTrace();
            }
            games = 0;
            wins = 0;
            losses = 0;
            return;
        }

        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String linea = reader.readLine();
            games = Integer.parseInt(linea.trim());
            linea = reader.readLine();
            wins = Integer.parseInt(linea.trim());
            linea = reader.readLine();
            losses = Integer.parseInt(linea.trim());
            reader.close();
        } catch (Exception e) {
            System.err.println("Ocurrió un error al leer el archivo del usuario " + nickname + ".");
            e.printStackTrace();
        }
    }

    /**
     * Guarda las estadísticas del usuario especificado por {@code nickname}
     * si existe, se sobrescribe el archivo
     */
    public void saveStats() {
        File file = new File(nickname + ".txt");

        try {
            if (file.exists()) {
                file.delete();
                Thread.sleep(500);
                file.createNewFile();
            }
        } catch (Exception e) {
            System.err.println(
                    "Ocurrió un error al recrear el archivo del usuario " + nickname + ".");
            e.printStackTrace();
        }

        try {
            PrintWriter writer = new PrintWriter(new FileWriter(file, false));
            writer.println(games);
            writer.println(wins);
            writer.println(losses);
            writer.close();
        } catch (Exception e) {
            System.err.println(
                    "Ocurrió un error al escribir el archivo del usuario " + nickname + ".");
            e.printStackTrace();
        }
    }

    /**
     * Añade uno más a la cantidad de partidas jugadas
     */
    public void addGames() {
        games++;
    }

    /**
     * Añade uno más a la cantidad de partidas ganadas
     */
    public void addWins() {
        wins++;
    }

    /**
     * Añade uno más a la cantidad de partidas perdidas
     */
    public void addLosses() {
        losses++;
    }

    /**
     * Asignar el apodo del jugador a {@code nickname}
     *
     * @param nickname
     */
    public void setNickname(String nickname) {
        this.nickname = nickname.toLowerCase();
    }

    public String getNickname() {
        return nickname;
    }

    public int getGames() {
        return games;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }
}
