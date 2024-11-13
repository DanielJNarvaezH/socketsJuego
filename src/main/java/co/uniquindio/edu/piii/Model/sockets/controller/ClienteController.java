package co.uniquindio.edu.piii.Model.sockets.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import co.uniquindio.edu.piii.Model.sockets.model.Tanqueo;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.time.LocalDateTime;

public class ClienteController {
    @FXML private TextField clienteIdField;
    @FXML private TextField tipoVehiculoField;
    @FXML private TextField cantidadGalonesField;
    @FXML private Label mensajeLabel;

    public void enviarRegistro() {
        try {
            Tanqueo tanqueo = new Tanqueo(clienteIdField.getText(), tipoVehiculoField.getText(),
                                          Double.parseDouble(cantidadGalonesField.getText()), LocalDateTime.now());

            try (Socket socket = new Socket("localhost", 12345);
                 ObjectOutputStream salida = new ObjectOutputStream(socket.getOutputStream());
                 ObjectInputStream entrada = new ObjectInputStream(socket.getInputStream())) {

                salida.writeObject(tanqueo);
                salida.flush();

                mensajeLabel.setText((String) entrada.readObject());

            } catch (Exception e) {
                mensajeLabel.setText("Error en la conexión.");
                e.printStackTrace();
            }
        } catch (NumberFormatException e) {
            mensajeLabel.setText("Entrada inválida en el campo de galones.");
        }
    }
}