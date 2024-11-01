module co.uniquindio.edu.piii {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop; // Para usar AWT y Swing

    opens co.uniquindio.edu.piii to javafx.fxml;
    exports co.uniquindio.edu.piii;
}
