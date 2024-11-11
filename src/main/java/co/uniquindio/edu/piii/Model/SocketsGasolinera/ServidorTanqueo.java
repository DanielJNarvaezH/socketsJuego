package co.uniquindio.edu.piii.Model.SocketsGasolinera;

import java.io.*;
import java.net.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServidorTanqueo {
    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(10); // Manejo de múltiples conexiones
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Servidor iniciado, esperando conexiones...");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Cliente conectado");
                pool.execute(new ManejadorCliente(clientSocket));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

class ManejadorCliente implements Runnable {
    private Socket clientSocket;

    public ManejadorCliente(Socket socket) {
        this.clientSocket = socket;
    }

    @Override
    public void run() {
        try (ObjectInputStream in = new ObjectInputStream(clientSocket.getInputStream());
             PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true)) {

            // Leer datos enviados por el cliente
            RegistroTanqueo registro = (RegistroTanqueo) in.readObject();

            // Aquí podemos procesar los datos (guardar en archivo o base de datos)
            System.out.println("Registro recibido: " + registro.getIdCliente() + ", " + registro.getTipoVehiculo() +
                               ", " + registro.getGalones() + ", " + registro.getFechaHora());

            // Enviar respuesta al cliente
            out.println("Registro procesado correctamente");

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
