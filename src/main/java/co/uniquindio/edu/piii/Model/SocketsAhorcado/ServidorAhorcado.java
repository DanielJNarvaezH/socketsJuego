package co.uniquindio.edu.piii.Model.SocketsAhorcado;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.CopyOnWriteArrayList;

public class ServidorAhorcado extends JFrame {

    private JTextField campoPalabra;
    private JButton botonSeleccionarPalabra;
    private ArrayList<String> palabras;
    private String palabraOculta;
    private StringBuilder estadoActual;
    private CopyOnWriteArrayList<PrintWriter> clientes; // Lista segura para manejo de múltiples clientes

    public ServidorAhorcado() {
        setTitle("Servidor Ahorcado");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        campoPalabra = new JTextField(20);
        campoPalabra.setEditable(false);
        add(campoPalabra);

        botonSeleccionarPalabra = new JButton("Seleccionar palabra");
        add(botonSeleccionarPalabra);

        botonSeleccionarPalabra.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seleccionarPalabra();
                enviarActualizacionATodos(); // Envía los asteriscos a todos los clientes después de seleccionar la palabra
            }
        });

        palabras = new ArrayList<>();
        clientes = new CopyOnWriteArrayList<>();
        cargarPalabras();

        new Thread(this::iniciarServidor).start();
    }

    private void cargarPalabras() {
        palabras.add("casa");
        palabras.add("java");
        palabras.add("servidor");
        palabras.add("multiusuario");
    }

    private void seleccionarPalabra() {
        Random rand = new Random();
        palabraOculta = palabras.get(rand.nextInt(palabras.size()));
        estadoActual = new StringBuilder("*".repeat(palabraOculta.length()));
        campoPalabra.setText(estadoActual.toString());
    }

    private void iniciarServidor() {
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Servidor iniciado en el puerto 12345.");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Cliente conectado.");
                PrintWriter clienteWriter = new PrintWriter(socket.getOutputStream(), true);
                clientes.add(clienteWriter);

                new Thread(() -> manejarCliente(socket, clienteWriter)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void manejarCliente(Socket socket, PrintWriter clienteWriter) {
        try (BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String letra;
            while ((letra = entrada.readLine()) != null) {
                procesarLetra(letra.charAt(0));
                if (!estadoActual.toString().contains("*")) {
                    clienteWriter.println("Fin del juego. ¡Ganaste!"); // Mensaje final al cliente
                    clienteWriter.close();
                    socket.close(); // Cierra la conexión del cliente
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private synchronized void procesarLetra(char letra) {
        @SuppressWarnings("unused")
        boolean letraEncontrada = false;
        for (int i = 0; i < palabraOculta.length(); i++) {
            if (palabraOculta.charAt(i) == letra) {
                estadoActual.setCharAt(i, letra);
                letraEncontrada = true;
            }
        }
        campoPalabra.setText(estadoActual.toString());
        enviarActualizacionATodos();

        if (!estadoActual.toString().contains("*")) {
            finalizarJuego();
        }
    }

    private void enviarActualizacionATodos() {
        for (PrintWriter cliente : clientes) {
            cliente.println(estadoActual.toString());
        }
    }

    private void finalizarJuego() {
        enviarActualizacionATodos();
        System.out.println("Juego finalizado.");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ServidorAhorcado servidor = new ServidorAhorcado();
            servidor.setVisible(true);
        });
    }
}