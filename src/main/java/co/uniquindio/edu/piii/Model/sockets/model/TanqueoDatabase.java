package co.uniquindio.edu.piii.Model.sockets.model;


import java.io.FileWriter;
import java.io.IOException;

public class TanqueoDatabase {
    private static final String FILE_PATH = "registros_tanqueo.txt";

    public static void guardarTanqueo(Tanqueo tanqueo) {
        try (FileWriter escritor = new FileWriter(FILE_PATH, true)) {
            escritor.write(tanqueo.toString() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}