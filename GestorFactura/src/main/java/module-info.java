module davinci.gestorfactura {
    requires javafx.controls;
    requires javafx.fxml;

    opens davinci.gestorfactura to javafx.fxml;
    exports davinci.gestorfactura;
}
