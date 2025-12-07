module davinci.gestorfactura {
    // REQUIRES DEL JDK
    requires java.sql;
    requires java.desktop;
    requires java.logging; // Es fundamental para que Jasper funcione
    
    // REQUIRES DE JAVAFX
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics; 

    // REQUIRES DE JASPERREPORTS 7.0.3
    // Usar el nombre canónico. Si esto falla, la versión es incompatible.
    requires net.sf.jasperreports.core;
    requires net.sf.jasperreports.functions;  
    requires net.sf.jasperreports.metadata;
    requires net.sf.jasperreports.pdf; 

    // APERTURA DE PAQUETES
    opens Controlador to javafx.fxml;
    opens Modelo to javafx.base; 

    // EXPORTACIÓN
    exports Controlador;
    exports Vista to javafx.graphics;
    exports Modelo;
}