module davinci.gestorfactura {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    
    opens Controlador to javafx.fxml;
    exports Controlador;
    exports Vista to javafx.graphics;
}
