package co.uniquindio.edu.piii.Model;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class ClienteParticipante extends Application {

    private TextField palabraField;
    private TextField letraField;
    private Button enviarButton;
    private Socket socket;
    private BufferedReader reader;
    private PrintWriter writer;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Ahorcado - Participante");

        Label palabraLabel = new Label("Palabra:");
        palabraField = new TextField();
        palabraField.setEditable(false); // Este campo solo lo actualiza el servidor

        Label letraLabel = new Label("Ingresa una letra:");
        letraField = new TextField();
        letraField.setMaxWidth(50);

        enviarButton = new Button("Enviar");
        enviarButton.setOnAction(e -> enviarLetra());

        VBox layout = new VBox(10, palabraLabel, palabraField, letraLabel, letraField, enviarButton);
        layout.setPadding(new Insets(15));
        Scene scene = new Scene(layout, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();

        conectarServidor(); // Establece conexión con el servidor
        iniciarEscuchaServidor(); // Escucha las respuestas del servidor en un hilo
    }

    private void conectarServidor() {
        try {
            socket = new Socket("localhost", 12345); // Cambia el puerto según configuración
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            mostrarAlerta("Error de conexión", "No se pudo conectar al servidor.");
        }
    }

    private void enviarLetra() {
        String letra = letraField.getText().trim();
        if (letra.length() == 1) {
            writer.println(letra); // Envia la letra al servidor
            letraField.clear(); // Limpia el campo de entrada
        } else {
            mostrarAlerta("Entrada no válida", "Ingresa solo una letra.");
        }
    }

    private void iniciarEscuchaServidor() {
        Thread hiloEscucha = new Thread(() -> {
            try {
                String mensaje;
                while ((mensaje = reader.readLine()) != null) {
                    final String mensajeFinal = mensaje;
                    // Actualiza el campo de palabra en la interfaz
                    javafx.application.Platform.runLater(() -> palabraField.setText(mensajeFinal));
                    
                    // Verifica si el servidor envió un mensaje de finalización
                    if (mensaje.contains("¡Ganaste!") || mensaje.contains("Fin del juego")) {
                        cerrarConexion();
                        mostrarAlerta("Juego terminado", mensaje);
                    }
                }
            } catch (IOException e) {
                mostrarAlerta("Error", "Se perdió la conexión con el servidor.");
            }
        });
        hiloEscucha.setDaemon(true);
        hiloEscucha.start();
    }

    private void cerrarConexion() {
        try {
            if (socket != null) socket.close();
            if (reader != null) reader.close();
            if (writer != null) writer.close();
        } catch (IOException e) {
            System.out.println("Error al cerrar la conexión");
        }
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensaje);
        alerta.showAndWait();
    }

    @Override
    public void stop() {
        cerrarConexion(); // Cierra conexión al cerrar la aplicación
    }

    public static void main(String[] args) {
        launch(args);
    }
}

