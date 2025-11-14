module davinci.gestorfactura {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    
    opens davinci.gestorfactura to javafx.fxml;
    exports davinci.gestorfactura;
}
