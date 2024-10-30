module co.uniquindio.edu.piii {
    requires javafx.controls;
    requires javafx.fxml;

    opens co.uniquindio.edu.piii to javafx.fxml;
    exports co.uniquindio.edu.piii;
}
