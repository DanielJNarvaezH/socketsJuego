package co.uniquindio.edu.piii.Model.SocketsGasolinera;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class ClienteTanqueo {
    public static void main(String[] args) {
        try (Socket socket = new Socket("localhost", 12345);
             ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {

            Scanner scanner = new Scanner(System.in);
            
            // Recoger datos del usuario
            System.out.print("Ingrese su ID de cliente: ");
            String idCliente = scanner.nextLine();

            System.out.print("Ingrese el tipo de vehículo (automóvil, motocicleta, etc.): ");
            String tipoVehiculo = scanner.nextLine();

            System.out.print("Ingrese la cantidad de gasolina cargada en galones: ");
            double galones = scanner.nextDouble();

            System.out.print("Ingrese la fecha y hora del tanqueo: ");
            scanner.nextLine(); // Consume el salto de línea
            String fechaHora = scanner.nextLine();
            
            // Crear objeto de registro
            RegistroTanqueo registro = new RegistroTanqueo(idCliente, tipoVehiculo, galones, fechaHora);
            
            // Enviar registro al servidor
            out.writeObject(registro);
            out.flush();

            // Leer respuesta del servidor
            String respuesta = in.readLine();
            System.out.println("Respuesta del servidor: " + respuesta);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

