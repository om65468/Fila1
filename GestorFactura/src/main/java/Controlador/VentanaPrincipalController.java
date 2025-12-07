package Controlador;

import Modelo.ConexionBBDD;
import Modelo.EmpresaEntidadRelacionDAO;
import Modelo.Entidad;
import Modelo.EntidadDAO;
import Modelo.Producto;
import Modelo.ProductoDAO;
import Modelo.TipoEntidadDAO;
import Vista.App;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import Modelo.GeneradorFactura;
import java.sql.Connection;

public class VentanaPrincipalController {
    @FXML
    private Button Boton_emitir_fac;
    private static final int NUMERO_FACTURA_A_EMITIR = 1001;

    @FXML
    private AnchorPane paneInfoArticulos;
    @FXML
    private AnchorPane paneInfoClientes;
    @FXML
    private AnchorPane paneInfoProveedores;
    @FXML
    private AnchorPane paneInformacion;

    private Entidad empresa;
    private Entidad proveedorSeleccionado;
    private ObservableList<Entidad> listaProveedores = FXCollections.observableArrayList();

    @FXML
    private TabPane tabPane;
    @FXML
    private Tab tabArchivo;

    @FXML
    private Tab tab_cliente;

    @FXML
    private Tab tab_proveedor;

    @FXML
    private Tab tabArticulos;

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
    @FXML
    private AnchorPane paneFactura;

    //Proveedor
    @FXML
    private Button Boton_Guardar_prov;
    @FXML
    private Button Boton_duplicar_prov;
    @FXML
    private Button Boton_eliminar_prov;
    @FXML
    private Button Boton_nuevo_prov;
    @FXML
    private Button Boton_modificar_prov;
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
    @FXML
    private TextField DirProv;
    @FXML
    private TextField TlfProv;

    //Cliente
    @FXML
    private TextField DirCli;
    @FXML
    private Button Boton_duplicar_cli;
    @FXML
    private Button Boton_eliminar_cli;
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
    private TextField TlfCli;
    @FXML
    private ComboBox<?> TipoCli;

    //Extra
    @FXML
    private Button Boton_agentes;
    @FXML
    private Button Boton_cliente;

    //Empresa
    @FXML
    private Button Boton_info_empresa;
    @FXML
    private Label InfoNIF;
    @FXML
    private Label InfoNombre;
    @FXML
    private TextField txtInfoCP;
    @FXML
    private TextField txtInfoCalle;
    @FXML
    private TextField txtInfoCiudad;
    @FXML
    private TextField txtInfoEmail;
    @FXML
    private TextField txtInfoTlf;

    //Productos
    @FXML
    private Button GuardarProd;
    @FXML
    private Button EliminarProd;
    @FXML
    private TextField PVPProd;
    @FXML
    private TextField ProvProd;
    @FXML
    private TextField StokProd;
    @FXML
    private TextField IVAProd;
    @FXML
    private TextField IDProd;
    @FXML
    private TextField CosProd;
    @FXML
    private TextArea DescProd;

    //Tabla
    @FXML
    private TableView<Entidad> RefProd;
    @FXML
    private TableColumn<Entidad, Integer> ColIDProv;
    @FXML
    private TableColumn<Entidad, String> ColNomProv;

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
            } else if (newTab == tabInformacion) {
                paneInfoClientes.setVisible(false);
                paneInfoProveedores.setVisible(false);
                paneInfoArticulos.setVisible(false);
                paneInformacion.setVisible(true);
            } else if (newTab == tab_cliente) {
                paneInfoClientes.setVisible(true);
                paneInfoProveedores.setVisible(false);
                paneInfoArticulos.setVisible(false);
                paneInformacion.setVisible(false);
            } else if (newTab == tab_proveedor) {
                paneInfoClientes.setVisible(false);
                paneInfoProveedores.setVisible(true);
                paneInfoArticulos.setVisible(false);
                paneInformacion.setVisible(false);
            } else if (newTab == tabArticulos) {
                paneInfoClientes.setVisible(false);
                paneInfoProveedores.setVisible(false);
                paneInfoArticulos.setVisible(true);
                paneInformacion.setVisible(false);
            } else {
                paneInfoClientes.setVisible(false);
                paneInfoProveedores.setVisible(false);
                paneInfoArticulos.setVisible(false);
                paneInformacion.setVisible(false);
            }
        });

        ColIDProv.setCellValueFactory(new PropertyValueFactory<>("id"));
        ColNomProv.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        escucharTablaProv();
    }

    //Metodos de Cliente
    @FXML
    private void onNuevoCliente() {
        ocultarPanes();
        paneInfoClientes.setVisible(false);
        paneInfoClientes.setManaged(false);
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
        paneInfoProveedores.setVisible(false);
        paneInfoProveedores.setManaged(false);
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
        paneInfoArticulos.setVisible(false);
        paneInfoArticulos.setManaged(false);
        paneProducto.setVisible(true);
        paneProducto.setManaged(true);
    }

    @FXML
    private void onEliminarArticulo(ActionEvent event) {

    }

    @FXML
    private void onModificarArticulo(ActionEvent event) {

    }

    //Metodos de Factura
    @FXML
    private void onNuevoFactura() {
        ocultarPanes();
        paneFactura.setVisible(true);
        paneFactura.setManaged(true);
    }

    @FXML
    private void onEliminarFactura(ActionEvent event) {

    }

    @FXML
    private void onModificarFactura(ActionEvent event) {

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
        cargarProveedoresDeEmpresa();
        // Aquí llenas tus labels, textfields, etc.
        System.out.println("Empresa cargada: " + empresa.getNombre());
    }

    /*OMAR MIRAME PORQUE NO HAY CAMPOS DE DIRECCIÓN EN EL PROVEEDOR NI EN EL CLIENTE*/
    @FXML
    private void guardarCliente() {

        try {
            EntidadDAO entidadDAO = new EntidadDAO();
            TipoEntidadDAO tipoEntidad = new TipoEntidadDAO();
            EmpresaEntidadRelacionDAO tipoDAO = new EmpresaEntidadRelacionDAO();

            // 1. Datos del formulario
            String nombre = NomCli.getText().trim();
            String nif = NIFCli.getText().trim();
            String calle = DirCli.getText().trim();
            //String cp = txtCpCliente.getText().trim();
            //String ciudad = txtCiudadCliente.getText().trim();
            String email = EmailCli.getText().trim();
            String telefono = TlfCli.getText().trim();

            // 2. Crear la entidad
            Entidad nuevoCliente = new Entidad(0, nombre, nif, calle, "", "", email, telefono);

            // 3. Insertar la entidad base
            entidadDAO.insertar(nuevoCliente);

            // 4. Recuperar ID
            Entidad clienteInsertado = entidadDAO.buscarPorNif(nif);

            if (clienteInsertado == null) {
                mostrarAlerta("Error", "No se ha podido crear el cliente.");
                return;
            }

            // 5. Crear relación: empresa actual → cliente (tipo 2)
            tipoEntidad.insertar(clienteInsertado.getId(), 2);
            tipoDAO.insertarRelacion(empresa.getId(), clienteInsertado.getId());

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
            TipoEntidadDAO tipoEntidad = new TipoEntidadDAO();
            EmpresaEntidadRelacionDAO tipoDAO = new EmpresaEntidadRelacionDAO();

            String nombre = NomProv.getText().trim();
            String nif = NIFProv.getText().trim();
            String calle = DirProv.getText().trim();
            //String cp = txtCpProveedor.getText().trim();
            //String ciudad = txtCiudadProveedor.getText().trim();
            String email = EmailProv.getText().trim();
            String telefono = TlfProv.getText().trim();

            Entidad nuevoProveedor = new Entidad(0, nombre, nif, calle, "", "", email, telefono);

            entidadDAO.insertar(nuevoProveedor);

            Entidad proveedorInsertado = entidadDAO.buscarPorNif(nif);

            if (proveedorInsertado == null) {
                mostrarAlerta("Error", "No se ha podido crear el proveedor.");
                return;
            }

            // Crear relación: empresa actual → proveedor (tipo 3)
            tipoEntidad.insertar(proveedorInsertado.getId(), 3);
            tipoDAO.insertarRelacion(empresa.getId(), proveedorInsertado.getId());

            mostrarAlerta("Éxito", "Proveedor guardado correctamente.");

        } catch (Exception ex) {
            ex.printStackTrace();
            mostrarAlerta("Error", "Ocurrió un error al guardar el proveedor.");
        }
    }

    @FXML
    private void agregarProducto(ActionEvent event) {
        try {
            // validar proveedor seleccionado
            if (proveedorSeleccionado == null) {
                mostrarAlerta("Proveedor no seleccionado", "Selecciona un proveedor de la lista antes de crear el producto.");
                return;
            }

            String descripcion = DescProd.getText().trim();
            int idProveedor = proveedorSeleccionado.getId();
            String refProveedor = proveedorSeleccionado.getNombre();

            // parsear valores numéricos con control de errores
            double coste;
            double pvp;
            double iva;
            int stock;
            try {
                coste = Double.parseDouble(CosProd.getText().trim());
                pvp = Double.parseDouble(PVPProd.getText().trim());
                iva = Double.parseDouble(IVAProd.getText().trim());
                stock = Integer.parseInt(StokProd.getText().trim());
            } catch (NumberFormatException nfe) {
                mostrarAlerta("Datos numéricos inválidos", "Comprueba coste, pvp, iva y stock (deben ser números).");
                return;
            }

            // validaciones básicas
            if (descripcion.isEmpty()) {
                mostrarAlerta("Descripción vacía", "Introduce una descripción para el producto.");
                return;
            }

            Producto producto = new Producto(0, descripcion, refProveedor, idProveedor, coste, pvp, iva, stock);
            ProductoDAO productoDAO = new ProductoDAO();
            productoDAO.insertar(producto); // lanza SQLException si falla

            // si todo OK:
            IDProd.setText(String.valueOf(producto.getId())); // id autogenerado devuelto en DAO
            mostrarAlerta("Producto creado", "Producto creado correctamente con id " + producto.getId());

            // limpiar campos si quieres
            DescProd.clear();
            ProvProd.clear();
            CosProd.clear();
            PVPProd.clear();
            IVAProd.clear();
            StokProd.clear();
            proveedorSeleccionado = null;

            // refrescar listas, tablas, etc. si procede
        } catch (SQLException ex) {
            ex.printStackTrace();
            mostrarAlerta("Error BBDD", "No se pudo insertar el producto: " + ex.getMessage());
        }
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

        paneCliente.setVisible(false);
        paneCliente.setManaged(false);

        paneProveedor.setVisible(false);
        paneProveedor.setManaged(false);

        paneProducto.setVisible(false);
        paneProducto.setManaged(false);
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void escucharTablaProv() {
        RefProd.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            proveedorSeleccionado = newSel;
            if (newSel != null) {
                ProvProd.setText(newSel.getNombre()); // referencia = nombre
                // si quieres mostrar el id en otro campo, por ejemplo IDProdProveedor:
                // IDProdProveedor.setText(String.valueOf(newSel.getId()));
            } else {
                ProvProd.clear();
            }
        });
    }

    public void cargarProveedoresDeEmpresa() {
        if (empresa == null) {
            return;
        }
        EntidadDAO entidadDAO = new EntidadDAO();
        List<Entidad> proveedores = entidadDAO.listarProveedoresDeEmpresa(empresa.getId());
        listaProveedores.setAll(proveedores);
        RefProd.setItems(listaProveedores);
    }
    
    
    @FXML
    void onEmitirFactura(ActionEvent event) {
            Connection connection = null;
        try {
        // 1. Obtener la conexión a la BBDD usando tu clase de conexión
        connection = Modelo.ConexionBBDD.get();
        
        if (connection == null) {
        mostrarAlerta("Error", "No se pudo establecer la conexión a la base de datos.");
        return;
        }
        
        // 2. Generar el PDF
        GeneradorFactura generador = new GeneradorFactura();
        String rutaPDF = generador.generarFacturaPDF(connection, NUMERO_FACTURA_A_EMITIR);
        
        if (rutaPDF != null) {
        mostrarAlerta("Éxito", "Factura N° " + NUMERO_FACTURA_A_EMITIR + " generada.");
        abrirPDF(rutaPDF);
        }
        
        } catch (Exception e) {
        e.printStackTrace();
        mostrarAlerta("Error de Emisión", "Ocurrió un error al generar o guardar el PDF: " + e.getMessage());
        } finally {
        // 3. Cerrar la conexión
        if (connection != null) {
        try {
        connection.close();
        } catch (SQLException e) {
        e.printStackTrace();
        }
        }
        }
        }
        
        
        private void abrirPDF(String rutaPDF) {
            try {
                java.io.File pdfFile = new java.io.File(rutaPDF);
                // Usamos java.awt.Desktop para interactuar con el SO (Requiere 'requires java.desktop;')
                if (pdfFile.exists() && java.awt.Desktop.isDesktopSupported()) {
                    java.awt.Desktop.getDesktop().open(pdfFile);
                }
            } catch (java.io.IOException e) {
                System.err.println("No se pudo abrir el archivo PDF: " + e.getMessage());
            }
        }
}
