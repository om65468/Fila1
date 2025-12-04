package Controlador;

import Modelo.Entidad;
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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class VentanaPrincipalController {
    
    private Entidad empresa;
    
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
    private AnchorPane paneCliente;

    @FXML
    private AnchorPane paneEmpresa;
    
    @FXML
    private AnchorPane paneProveedor;
    
    @FXML 
    private AnchorPane paneProducto;
    
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
    private void mostrarEmpresa() {
        ocultarPanes();
        paneEmpresa.setVisible(true);
        paneEmpresa.setManaged(true);
    }

   
    @FXML
    private void onMostrarCliente() {
        ocultarPanes();
        paneCliente.setVisible(true);
        paneCliente.setManaged(true);
    }


    @FXML
    private void onMostrarProveedor() {
        ocultarPanes();
        paneProveedor.setVisible(true);
        paneProveedor.setManaged(true);
    }

    @FXML
    private void onMostrarProducto() {
        ocultarPanes();
        paneProducto.setVisible(true);
        paneProducto.setManaged(true);
    }

    
    /*
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
    }*/

    private void cargarOtraVentana() {
        try {
            App.setRoot("primary");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    
        public void setEmpresa(Entidad empresa) {
        this.empresa = empresa;
        cargarDatos();
    }

    private void cargarDatos() {
        if (empresa == null) return;

        // Aquí llenas tus labels, textfields, etc.
        System.out.println("Empresa cargada: " + empresa.getNombre());
    }
    
    
    /*
    private void abrirSecondary(String tipoTab) {
        try {
            FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/davinci/gestorfactura/secondary.fxml")
            );

            Parent root = loader.load();
            SecondaryController controller = loader.getController();

            switch (tipoTab) {
                case "EMPRESA":
                    controller.mostrarTab(controller.getTabEmpresa());
                    break;
                case "CLIENTE":
                    controller.mostrarTab(controller.getTabCliente());
                    break;
                case "PRODUCTO":
                    controller.mostrarTab(controller.getTabProducto());
                    break;
                case "PROVEEDOR":
                    controller.mostrarTab(controller.getTabProveedor());
                    break;
                case "FACTURA":
                    controller.mostrarTab(controller.getTabFactura());
                    break;
            }

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/



    /*
    private void cargarEnPane(AnchorPane destino, String fxml, String tipoTab) {
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource(fxml)
            );

            Parent vista = loader.load();

            // Ajustar la vista al tamaño del pane
            AnchorPane.setTopAnchor(vista, 0.0);
            AnchorPane.setBottomAnchor(vista, 0.0);
            AnchorPane.setLeftAnchor(vista, 0.0);
            AnchorPane.setRightAnchor(vista, 0.0);

            destino.getChildren().setAll(vista);

            // Configurar el controller secundario
            SecondaryController controller = loader.getController();
            if (tipoTab != null) {
                controller.mostrarTabSegunTipo(tipoTab);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }*/
    
    private void ocultarPanes() {
        paneEmpresa.setVisible(false);
        paneEmpresa.setManaged(false);

        paneCliente.setVisible(false);
        paneCliente.setManaged(false);

        paneProveedor.setVisible(false);
        paneProveedor.setManaged(false);

        paneProducto.setVisible(false);
        paneProducto.setManaged(false);
    }

    
    


}
