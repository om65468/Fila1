package Controlador;

import Vista.App;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class SecondaryController {

    @FXML
    private TabPane tabPaneSecondary;

    @FXML
    private Tab tabEmpresa;

    @FXML
    private Tab tabCliente;

    @FXML
    private Tab tabProducto;
    
    @FXML
    private Tab tabProveedor;
    
    @FXML
    private Tab tabFactura;

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
    
    
    @FXML
    private void switchToVentanaPrincipal() throws IOException {
        App.setRoot("ventana_principal");
    }
    
    public void mostrarSoloCliente() {
        tabPaneSecondary.getSelectionModel().select(tabCliente); // Selecciona la pestaña Cliente
    }
    
    public void irATab(Tab tabDestino) {
        for (Tab t : tabPaneSecondary.getTabs()) {
            t.setDisable(t != tabDestino);
        }
        tabPaneSecondary.getSelectionModel().select(tabDestino);
    }
    
    @FXML
    public void initialize() {
        System.out.println("Empresa = " + tabEmpresa);
        System.out.println("Cliente = " + tabCliente);
    }
    
    
    public Tab getTabEmpresa() {
        return tabEmpresa;
    }


}