 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Vista.App;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

/**
 *
 * @author GON 
 */
public class VentanaPrincipalController {
    
    @FXML
    private Tab tabArchivo;
    
    @FXML
    private TabPane tabPane;
    
    @FXML
    private Tab tab_cliente;

    @FXML
    private Tab tab_proveedor;
    
    @FXML
    private Button ButtonCliente;
    
    
    @FXML
    public void initialize() {
        tabPane.getTabs().remove(tab_cliente);
        tabPane.getTabs().remove(tab_proveedor);
        
        tabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
            if (newTab != tab_cliente && newTab != tab_proveedor) {
                eliminarTabsDinamicos();
            }
        });
        
        tabArchivo.setOnSelectionChanged(event -> {
            if (tabArchivo.isSelected()) {
                cargarOtraVentana();

                // Regresar al tab anterior
                tabPane.getSelectionModel().selectFirst(); 
            }
        });
    }
    
    private void eliminarTabsDinamicos(){
        if (tabPane.getTabs().contains(tab_cliente)) {
            tabPane.getTabs().remove(tab_cliente);
        }
        if (tabPane.getTabs().contains(tab_proveedor)) {
            tabPane.getTabs().remove(tab_proveedor);
        }
    }
    
    @FXML
    void onMostrarTabCliente(ActionEvent event) {
        if (!tabPane.getTabs().contains(tab_cliente)) {
            tabPane.getTabs().add(tab_cliente);
            tabPane.getSelectionModel().select(tab_cliente);
        }
    }

    @FXML
    void onMostrarTabProveedor(ActionEvent event) {
        if (!tabPane.getTabs().contains(tab_proveedor)) {
            tabPane.getTabs().add(tab_proveedor);
            tabPane.getSelectionModel().select(tab_proveedor);
        }
    }
    
    @FXML
    private void switchToSecondaryCli() throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/davinci/gestorfactura/secondary.fxml"));
            Parent root = loader.load();

            SecondaryController secController = loader.getController();

            secController.irATab(secController.getTabCliente());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            stage.requestFocus();
            
            Stage stage2 = (Stage) ButtonCliente.getScene().getWindow();
            stage2.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cargarOtraVentana() {
        try {
            App.setRoot("primary");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
