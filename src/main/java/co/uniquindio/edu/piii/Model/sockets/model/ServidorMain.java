package co.uniquindio.edu.piii.Model.sockets.model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import co.uniquindio.edu.piii.Model.sockets.controller.ServidorController;

public class ServidorMain extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Servidor - Registro de Tanqueo");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ServidorView.fxml"));
        primaryStage.setScene(new Scene(loader.load()));
        primaryStage.show();

        ServidorController controlador = loader.getController();
        controlador.iniciarServidor(12345);
    }

    public static void main(String[] args) {
        launch(args);
    }
}