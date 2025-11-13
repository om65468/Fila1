module com.mycompany.almacen {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.mycompany.almacen to javafx.fxml;
    exports com.mycompany.almacen;
}
