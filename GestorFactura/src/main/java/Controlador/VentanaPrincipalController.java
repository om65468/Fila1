package Controlador;

import Modelo.EmpresaEntidadRelacionDAO;
import Modelo.Entidad;
import Modelo.EntidadDAO;
import Vista.App;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class VentanaPrincipalController {

    private Entidad empresa;

    @FXML
    private TabPane tabPane;
    
    @FXML
    private Tab tabArchivo;

    @FXML
    private Tab tab_cliente;

    @FXML
    private Tab tab_proveedor;
    
    @FXML
    private Tab tabInformacion;

    @FXML
    private Button ButtonCliente;

    @FXML
    private AnchorPane paneCliente;

    //@FXML
    //private AnchorPane paneEmpresa;

    @FXML
    private AnchorPane paneProveedor;

    @FXML
    private AnchorPane paneProducto;

    //Proveedor
    @FXML
    private Button Boton_Guardar_prov;
    @FXML
    private Button Boton_comercial_prov;
    @FXML
    private Button Boton_duplicar_prov;
    @FXML
    private Button Boton_eliminar_prov;
    @FXML
    private Button Boton_nuevo_prov;
    @FXML
    private Button Boton_modificar_prov;
    @FXML
    private Button Boton_general_prov;
    @FXML
    private Button Boton_proveedor;
    @FXML
    private TextField CodProv;
    @FXML
    private TextField EmailProv;
    @FXML
    private TextField IDProv;
    @FXML
    private ComboBox<?> TipoProv;
    @FXML
    private TextField NIFProv;
    @FXML
    private TextField NomProv;

    //Cliente
    @FXML
    private Button Boton_comercial_cli;
    @FXML
    private Button Boton_duplicar_cli;
    @FXML
    private Button Boton_eliminar_cli;
    @FXML
    private Button Boton_general_cli;
    @FXML
    private Button Boton_guardar_cli;
    @FXML
    private Button Boton_modificar_cli;
    @FXML
    private Button Boton_nuevo_cli;
    @FXML
    private TextField CodCli;
    @FXML
    private TextField EmailCli;
    @FXML
    private TextField IDCli;
    @FXML
    private TextField NIFCli;
    @FXML
    private TextField NomCli;
    @FXML
    private ComboBox<?> TipoCli;

    /*    //Empresa
    @FXML
    private Button Button_Crear_empresa;
    @FXML
    private Button Button_cancelar_empresa;
    @FXML
    private TextField CPEmp;
    @FXML
    private TextField CiudEmp;
    @FXML
    private TextField DirEmp;
    @FXML
    private TextField EmailEmp;
    @FXML
    private TextField NIFEmp;
    @FXML
    private TextField NomEmp;
    @FXML
    private TextField TelEmp;*/

    //Extra
    @FXML
    private Button Boton_agentes;
    @FXML
    private Button Boton_cliente;

    @FXML
    public void initialize() {
        tabPane.getSelectionModel().select(tabInformacion);
        tabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
        if (newTab == tabArchivo) {
            try {
                // Se seleccionó la pestaña 2, llamar a tu método
                switchToPrimary();
            } catch (IOException ex) {
                System.out.println("Problema en la linea 143");
            }
        }
    });
    }

    //Metodos de Cliente
    @FXML
    private void onNuevoCliente() {
        ocultarPanes();
        paneCliente.setVisible(true);
        paneCliente.setManaged(true);
    }
    
    @FXML
    private void onModifiarCliente(ActionEvent event) {

    }
    
    @FXML
    private void onEliminarCliente(ActionEvent event) {

    }

    //Metodos de Proveedor
    @FXML
    private void onNuevoProveedor() {
        ocultarPanes();
        paneProveedor.setVisible(true);
        paneProveedor.setManaged(true);
    }
    
    @FXML
    private void onModificarProveedor(ActionEvent event) {

    }
    
    @FXML
    private void onEliminarProveedor(ActionEvent event) {

    }


    //Metodos de Articulo
    @FXML
    private void onNuevoArticulo() {
        ocultarPanes();
        paneProducto.setVisible(true);
        paneProducto.setManaged(true);
    }
    
    @FXML
    private void onEliminarArticulo(ActionEvent event) {

    }

    @FXML
    private void onModificarArticulo(ActionEvent event) {

    }
    
    
    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }
    
    @FXML
    private void switchToVentanaPrincipal() throws IOException {
    if (SecondaryController.getSc().crearEmpresa()) {
    App.setRoot("ventana_principal");
    }
    }
    
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
        if (empresa == null) {
            return;
        }

        // Aquí llenas tus labels, textfields, etc.
        System.out.println("Empresa cargada: " + empresa.getNombre());
    }
    /*OMAR MIRAME PORQUE NO HAY CAMPOS DE DIRECCIÓN EN EL PROVEEDOR NI EN EL CLIENTE*/
/*
    @FXML
    private void guardarCliente() {

        try {
            EntidadDAO entidadDAO = new EntidadDAO();
            TipoEntidadDAO tipoDAO = new TipoEntidadDAO();

            // 1. Datos del formulario
            String nombre = txtNombreCliente.getText().trim();
            String nif = txtNifCliente.getText().trim();
            String calle = txtCalleCliente.getText().trim();
            String cp = txtCpCliente.getText().trim();
            String ciudad = txtCiudadCliente.getText().trim();
            String email = txtEmailCliente.getText().trim();
            String telefono = txtTelefonoCliente.getText().trim();

            // 2. Crear la entidad
            Entidad nuevoCliente = new Entidad(0, nombre, nif, calle, cp, ciudad, email, telefono);

            // 3. Insertar la entidad base
            entidadDAO.insertar(nuevoCliente);

            // 4. Recuperar ID
            Entidad clienteInsertado = entidadDAO.buscarPorNif(nif);

            if (clienteInsertado == null) {
                mostrarAlerta("Error", "No se ha podido crear el cliente.");
                return;
            }

            // 5. Crear relación: empresa actual → cliente (tipo 2)
            tipoDAO.insertar(clienteInsertado.getId(), 2);
            tipoDAO.insertarRelacionEmpresa(empresa.getId(), clienteInsertado.getId());

            mostrarAlerta("Éxito", "Cliente guardado correctamente.");

        } catch (Exception ex) {
            ex.printStackTrace();
            mostrarAlerta("Error", "Ocurrió un error al guardar el cliente.");
        }
    }

    @FXML
    private void guardarProveedor() {

        try {
            EntidadDAO entidadDAO = new EntidadDAO();
            EmpresaEntidadRelacionDAO tipoDAO = new EmpresaEntidadRelacionDAO();

            String nombre = NomProv.getText().trim();
            String nif = NIFProv.getText().trim();
            String calle = .getText().trim();
            String cp = txtCpProveedor.getText().trim();
            String ciudad = txtCiudadProveedor.getText().trim();
            String email = EmailProv.getText().trim();
            String telefono = TelP.getText().trim();

            Entidad nuevoProveedor = new Entidad(0, nombre, nif, calle, cp, ciudad, email, telefono);

            entidadDAO.insertar(nuevoProveedor);

            Entidad proveedorInsertado = entidadDAO.buscarPorNif(nif);

            if (proveedorInsertado == null) {
                mostrarAlerta("Error", "No se ha podido crear el proveedor.");
                return;
            }

            // Crear relación: empresa actual → proveedor (tipo 3)
            tipoDAO.insertar(proveedorInsertado.getId(), 3);
            tipoDAO.insertarRelacionEmpresa(empresa.getId(), proveedorInsertado.getId());

            mostrarAlerta("Éxito", "Proveedor guardado correctamente.");

        } catch (Exception ex) {
            ex.printStackTrace();
            mostrarAlerta("Error", "Ocurrió un error al guardar el proveedor.");
        }
    }
*/
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
        /*        paneEmpresa.setVisible(false);
        paneEmpresa.setManaged(false);*/

        paneCliente.setVisible(false);
        paneCliente.setManaged(false);

        paneProveedor.setVisible(false);
        paneProveedor.setManaged(false);

        paneProducto.setVisible(false);
        paneProducto.setManaged(false);
    }

}
