package co.uniquindio.edu.piii.Model.sockets.model;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class ClienteMain extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Cliente - Registro de Tanqueo");
        primaryStage.setScene(new Scene(FXMLLoader.load(getClass().getResource("/view/ClienteView.fxml"))));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}