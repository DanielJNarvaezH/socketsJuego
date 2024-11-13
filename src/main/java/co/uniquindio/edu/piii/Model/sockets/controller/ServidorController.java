package co.uniquindio.edu.piii.Model.sockets.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import co.uniquindio.edu.piii.Model.sockets.model.TanqueoDatabase;
import co.uniquindio.edu.piii.Model.sockets.model.Tanqueo;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class ServidorController {
    @FXML private TextArea registroArea;

    public void iniciarServidor(int puerto) {
        new Thread(() -> {
            try (ServerSocket servidorSocket = new ServerSocket(puerto)) {
                registroArea.appendText("Servidor iniciado en el puerto " + puerto + "\n");

                while (true) {
                    try (Socket clienteSocket = servidorSocket.accept();
                         ObjectInputStream entrada = new ObjectInputStream(clienteSocket.getInputStream());
                         ObjectOutputStream salida = new ObjectOutputStream(clienteSocket.getOutputStream())) {

                        Tanqueo tanqueo = (Tanqueo) entrada.readObject();
                        TanqueoDatabase.guardarTanqueo(tanqueo);

                        salida.writeObject("Registro exitoso del tanqueo: " + tanqueo);
                        registroArea.appendText("Datos recibidos y almacenados: " + tanqueo + "\n");

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }
}