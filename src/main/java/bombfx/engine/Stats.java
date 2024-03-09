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

    public Stats() {
    }

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

    public void addGames() {
        games++;
    }

    public void addWins() {
        wins++;
    }

    public void addLosses() {
        losses++;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname.toLowerCase();
    }
}
