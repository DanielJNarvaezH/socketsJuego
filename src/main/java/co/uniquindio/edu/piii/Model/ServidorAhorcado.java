package co.uniquindio.edu.piii.Model;

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

        // Campo de texto para mostrar la palabra oculta
        campoPalabra = new JTextField(20);
        campoPalabra.setEditable(false);
        add(campoPalabra);

        // Botón para seleccionar una palabra al azar
        botonSeleccionarPalabra = new JButton("Seleccionar palabra");
        add(botonSeleccionarPalabra);

        // Acción del botón para seleccionar y mostrar la palabra oculta
        botonSeleccionarPalabra.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seleccionarPalabra();
            }
        });

        // Inicializar lista de palabras y clientes
        palabras = new ArrayList<>();
        clientes = new CopyOnWriteArrayList<>();
        cargarPalabras();

        // Iniciar el servidor en un hilo separado
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

    // Inicia el servidor y acepta conexiones de clientes
    private void iniciarServidor() {
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            System.out.println("Servidor iniciado en el puerto 12345.");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Cliente conectado.");
                clientes.add(new PrintWriter(socket.getOutputStream(), true));

                // Crear un nuevo hilo para manejar al cliente
                new Thread(() -> manejarCliente(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Maneja la comunicación con cada cliente
    private void manejarCliente(Socket socket) {
        try (BufferedReader entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String letra;
            while ((letra = entrada.readLine()) != null) {
                procesarLetra(letra.charAt(0));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Procesa la letra enviada por el cliente y actualiza el estado actual de la palabra
    private synchronized void procesarLetra(char letra) {
        boolean letraEncontrada = false;
        for (int i = 0; i < palabraOculta.length(); i++) {
            if (palabraOculta.charAt(i) == letra) {
                estadoActual.setCharAt(i, letra);
                letraEncontrada = true;
            }
        }
        campoPalabra.setText(estadoActual.toString());
        enviarActualizacionATodos();

        // Si la palabra fue adivinada, cierra las conexiones
        if (!estadoActual.toString().contains("*")) {
            finalizarJuego();
        }
    }

    // Envía el estado actual de la palabra a todos los clientes
    private void enviarActualizacionATodos() {
        for (PrintWriter cliente : clientes) {
            cliente.println(estadoActual.toString());
        }
    }

    // Finaliza el juego y cierra las conexiones
    private void finalizarJuego() {
        enviarActualizacionATodos();
        for (PrintWriter cliente : clientes) {
            cliente.close();
        }
        clientes.clear();
        System.out.println("Juego finalizado.");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ServidorAhorcado servidor = new ServidorAhorcado();
            servidor.setVisible(true);
        });
    }
}
