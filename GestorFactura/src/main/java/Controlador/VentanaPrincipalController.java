package Controlador;

import Modelo.ConexionBBDD;
import Modelo.EmpresaEntidadRelacionDAO;
import Modelo.Entidad;
import Modelo.EntidadDAO;
import Modelo.Factura;
import Modelo.FacturaDAO;
import Modelo.Producto;
import Modelo.ProductoDAO;
import Modelo.TipoEntidadDAO;
import Vista.App;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
import Modelo.LineaFactura;
import java.sql.Connection;

public class VentanaPrincipalController {

    @FXML
    private Button Boton_emitir_fac;

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
    private EntidadDAO entidadDAO = new EntidadDAO();
    private ProductoDAO productoDAO = new ProductoDAO();
    private FacturaDAO facturaDAO = new FacturaDAO();

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
    private Tab TabFactura;

    @FXML
    private Tab tabInformacion;

    @FXML
    private Button Boton_guardar_info;

    @FXML
    private Button ButtonCliente;

    @FXML
    private AnchorPane paneCliente;

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
    private TextField NIFProv;
    @FXML
    private TextField NomProv;
    @FXML
    private TextField DirProv;
    @FXML
    private TextField TlfProv;

    //Cliente
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
    private TextField DirCli;

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
    private TextField txtInfoCalle;
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
    private TableView<Producto> TV_Articulos;

    @FXML
    private TableColumn<Producto, Double> TC_PvpArt;
    @FXML
    private TableColumn<Producto, Integer> TC_StockArt;
    @FXML
    private TableColumn<Producto, Integer> TC_IdProArt;
    @FXML
    private TableColumn<Producto, Double> TC_IvaArt;
    @FXML
    private TableColumn<Producto, Integer> TC_IdArt;
    @FXML
    private TableColumn<Producto, Double> TC_CostArt;
    @FXML
    private TableColumn<Producto, String> TC_DescArt;

    @FXML
    private TableView<Entidad> TV_Proveedores;

    @FXML
    private TableColumn<Entidad, String> TC_DirPro;
    @FXML
    private TableColumn<Entidad, Integer> TC_IdPro;
    @FXML
    private TableColumn<Entidad, String> TC_MailPro;
    @FXML
    private TableColumn<Entidad, String> TC_NifPro;
    @FXML
    private TableColumn<Entidad, String> TC_NomPro;
    @FXML
    private TableColumn<Entidad, String> TC_TelPro;

    @FXML
    private TableView<Entidad> TV_Clientes;

    @FXML
    private TableColumn<Entidad, String> TC_DirCli;
    @FXML
    private TableColumn<Entidad, Integer> TC_IdCli;
    @FXML
    private TableColumn<Entidad, String> TC_MailCli;
    @FXML
    private TableColumn<Entidad, String> TC_NifCli;
    @FXML
    private TableColumn<Entidad, String> TC_NomCli;
    @FXML
    private TableColumn<Entidad, String> TC_TelCli;

    // Buscador de Artículos para Factura
    @FXML
    private TextField txtBuscadorArticulo;
    @FXML
    private Button Boton_buscar_art;
    @FXML
    private TableView<Producto> TV_ResultadosArticulo;
    @FXML
    private TableColumn<Producto, Integer> TCR_IdArt;
    @FXML
    private TableColumn<Producto, String> TCR_DescArt;
    @FXML
    private TableColumn<Producto, Double> TCR_PvpArt;

    @FXML
    private TableView<LineaFactura> TV_FacturaLinea;
    @FXML
    private TableColumn<Factura, Integer> TC_ArtFacLi;
    @FXML
    private TableColumn<Factura, Integer> TC_CantFacLi;
    @FXML
    private TableColumn<Factura, Integer> TC_DTO1FacLi;
    @FXML
    private TableColumn<Factura, Integer> TC_DTO2FacLi;
    @FXML
    private TableColumn<Factura, String> TC_DescFacLi;
    @FXML
    private TableColumn<Factura, Integer> TC_IVAFacLi;
    @FXML
    private TableColumn<Factura, Integer> TC_LinFacLi;
    
    @FXML
    private TableView<Factura> TV_Factura;

    @FXML
    private TableColumn<Factura, String> TC_FEmisionFac;

    @FXML
    private TableColumn<Factura, Double> TC_IVAFac;

    @FXML
    private TableColumn<Factura, Integer> TC_IdClienteFac;
    
    @FXML
    private TableColumn<Factura, String> TC_ConFac;

    @FXML
    private TableColumn<Factura, Integer> TC_IdFac;

    @FXML
    private TableColumn<Factura, Integer> TC_NumFac;

    @FXML
    private TableColumn<Factura, String> TC_TipoFac;

    @FXML
    private TableColumn<Factura, Double> TC_TotalFac;

    @FXML
    private TableColumn<Factura, Integer> TC_DescFac;

    @FXML
    public void initialize() {
        tabPane.getSelectionModel().select(tabInformacion);
        paneInformacion.setVisible(true);
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
                paneFactura.setVisible(false);
            } else if (newTab == tab_cliente) {
                paneInfoClientes.setVisible(true);
                paneInfoProveedores.setVisible(false);
                paneInfoArticulos.setVisible(false);
                paneInformacion.setVisible(false);
                paneFactura.setVisible(false);
            } else if (newTab == tab_proveedor) {
                paneInfoClientes.setVisible(false);
                paneInfoProveedores.setVisible(true);
                paneInfoArticulos.setVisible(false);
                paneInformacion.setVisible(false);
                paneFactura.setVisible(false);
            } else if (newTab == tabArticulos) {
                paneInfoClientes.setVisible(false);
                paneInfoProveedores.setVisible(false);
                paneInfoArticulos.setVisible(true);
                paneInformacion.setVisible(false);
                paneFactura.setVisible(false);
                cargarProveedoresDeEmpresa();
            } else if (newTab == TabFactura) {
                paneInfoClientes.setVisible(false);
                paneInfoProveedores.setVisible(false);
                paneInfoArticulos.setVisible(false);
                paneInformacion.setVisible(false);
                paneFactura.setVisible(true);
            } else {
                paneInfoClientes.setVisible(false);
                paneInfoProveedores.setVisible(false);
                paneInfoArticulos.setVisible(false);
                paneInformacion.setVisible(false);
                paneFactura.setVisible(false);

            }
        });

        ColIDProv.setCellValueFactory(new PropertyValueFactory<>("id"));
        ColNomProv.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        escucharTablaProv();
        cargarTabla();
        escucharTablaResultadosArticulo();
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

    //Metodos de Proveedor
    @FXML
    private void onNuevoProveedor() {
        ocultarPanes();
        paneInfoProveedores.setVisible(false);
        paneInfoProveedores.setManaged(false);
        paneProveedor.setVisible(true);
        paneProveedor.setManaged(true);
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

    //Metodos de Factura
    @FXML
    private void onNuevoFactura() {
        ocultarPanes();
        paneFactura.setVisible(true);
        paneFactura.setManaged(true);
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
        cargarTablasEmpresa();
        InfoNombre.setText(empresa.getNombre());
        InfoNIF.setText(empresa.getNif());
        txtInfoCalle.setText(empresa.getDireccionCompleta());
        txtInfoEmail.setText(empresa.getEmail());
        txtInfoTlf.setText(empresa.getTelefono());
        System.out.println("Empresa cargada: " + empresa.getNombre());
        paneFactura.setVisible(false);
    }

    @FXML
    private void guardarCliente() {

        try {
            TipoEntidadDAO tipoEntidad = new TipoEntidadDAO();
            EmpresaEntidadRelacionDAO tipoDAO = new EmpresaEntidadRelacionDAO();

            // 1. Datos del formulario
            String nombre = NomCli.getText().trim();
            String nif = NIFCli.getText().trim();
            String calle = DirCli.getText().trim();
            String email = EmailCli.getText().trim();
            String telefono = TlfCli.getText().trim();

            if (entidadDAO.existe(nif)) {
                mostrarError("El cliente ya existe (NIF o Email duplicado).");
                return;
            }

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
            cargarTablasEmpresa();
        } catch (Exception ex) {
            ex.printStackTrace();
            mostrarAlerta("Error", "Ocurrió un error al guardar el cliente.");
        }
        CodCli.clear();
        EmailCli.clear();
        NIFCli.clear();
        NomCli.clear();
        TlfCli.clear();
        DirCli.clear();

    }

    @FXML
    private void borrarCliente() {
        Entidad clienteSeleccionado = TV_Clientes.getSelectionModel().getSelectedItem();
        if (clienteSeleccionado == null) {
            mostrarAlerta("Error", "No hay ningún cliente seleccionado.");
            return;
        }

        entidadDAO.eliminar(clienteSeleccionado.getId());
        mostrarAlerta("Éxito", "Cliente eliminado correctamente.");
        TV_Clientes.getItems().remove(clienteSeleccionado); // actualizar tabla
    }

    @FXML
    private void guardarProveedor() {

        try {
            entidadDAO = new EntidadDAO();
            TipoEntidadDAO tipoEntidad = new TipoEntidadDAO();
            EmpresaEntidadRelacionDAO tipoDAO = new EmpresaEntidadRelacionDAO();

            String nombre = NomProv.getText().trim();
            String nif = NIFProv.getText().trim();
            String calle = DirProv.getText().trim();
            String email = EmailProv.getText().trim();
            String telefono = TlfProv.getText().trim();

            if (entidadDAO.existe(nif)) {
                mostrarError("El Proveedor ya existe (NIF o Email duplicado).");
                return;
            }

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
            cargarTablasEmpresa();
        } catch (Exception ex) {
            ex.printStackTrace();
            mostrarAlerta("Error", "Ocurrió un error al guardar el proveedor.");
        }
            CodProv.clear();
            EmailProv.clear();
            NIFProv.clear();
            NomProv.clear();
            DirProv.clear();
            TlfProv.clear();
    }

    @FXML
    private void borrarProveedor() {
        Entidad proveedorSeleccionado = TV_Proveedores.getSelectionModel().getSelectedItem();
        if (proveedorSeleccionado == null) {
            mostrarAlerta("Error", "No hay ningún proveedor seleccionado.");
            return;
        }
        entidadDAO = new EntidadDAO();
        entidadDAO.eliminar(proveedorSeleccionado.getId());
        mostrarAlerta("Éxito", "Proveedor y todos sus productos eliminados correctamente.");
        TV_Proveedores.getItems().remove(proveedorSeleccionado); // actualizar tabla
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
            productoDAO.insertar(producto); // lanza SQLException si falla

            // si todo OK:
            IDProd.setText(String.valueOf(producto.getId())); // id autogenerado devuelto en DAO
            mostrarAlerta("Producto creado", "Producto creado correctamente con id " + producto.getId());
            cargarTablasEmpresa();
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

    @FXML
    private void borrarProducto() {
        Producto productoSeleccionado = TV_Articulos.getSelectionModel().getSelectedItem();
        if (productoSeleccionado == null) {
            mostrarAlerta("Error", "No hay ningún producto seleccionado.");
            return;
        }

        try {
            productoDAO = new ProductoDAO();
            productoDAO.eliminar(productoSeleccionado.getId());
            mostrarAlerta("Éxito", "Producto eliminado correctamente.");
            TV_Articulos.getItems().remove(productoSeleccionado); // actualizar tabla
        } catch (SQLException ex) {
            ex.printStackTrace();
            mostrarAlerta("Error", "No se pudo eliminar el producto: " + ex.getMessage());
        }
    }

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
        /*RefProd.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            proveedorSeleccionado = newSel;
            if (newSel != null) {
                ProvProd.setText(newSel.getNombre()); // referencia = nombre
                // si quieres mostrar el id en otro campo, por ejemplo IDProdProveedor:
                // IDProdProveedor.setText(String.valueOf(newSel.getId()));
            } else {
                ProvProd.clear();
            }
        });*/
    }

    public void cargarProveedoresDeEmpresa() {
        if (empresa == null) {
            return;
        }
        entidadDAO = new EntidadDAO();
        List<Entidad> proveedores = entidadDAO.listarProveedoresDeEmpresa(empresa.getId());
        listaProveedores.setAll(proveedores);
        RefProd.setItems(listaProveedores);
    }

    public void cargarTablasEmpresa() {
        int idEmpresa = empresa.getId();

        try {
            List<Entidad> clientes = entidadDAO.obtenerClientesEmpresa(idEmpresa);
            List<Entidad> proveedores = entidadDAO.obtenerProveedoresEmpresa(idEmpresa);
            List<Producto> productos = entidadDAO.obtenerProductosEmpresa(idEmpresa);

            TV_Clientes.setItems(FXCollections.observableArrayList(clientes));
            TV_Proveedores.setItems(FXCollections.observableArrayList(proveedores));
            TV_Articulos.setItems(FXCollections.observableArrayList(productos));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Mostrar mensaje de error (puedes adaptarlo a Alert de JavaFX)
    private void mostrarError(String mensaje) {
        //System.out.println("Error: " + mensaje);
        // Alternativamente, usar Alert:
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Validación");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    @FXML
    void onEmitirFactura(ActionEvent event) {
        Factura facturaSeleccionada = TV_Factura.getSelectionModel().getSelectedItem();

        if (facturaSeleccionada == null) {
            mostrarAlerta("Advertencia", "Debes seleccionar una factura de la tabla para poder emitirla.");
            return; // Sale del método si no hay selección
        }

        // Obtener el número (ID) de la factura seleccionada para el PDF
        int idFacturaAEmitir = facturaSeleccionada.getId();
        int numFacturaAEmitir = facturaSeleccionada.getNumFactura();

        Connection connection = null;
        try {
            // 2. Obtener la conexión a la BBDD
            connection = Modelo.ConexionBBDD.get();

            if (connection == null) {
                mostrarAlerta("Error", "No se pudo establecer la conexión a la base de datos.");
                return;
            }

            // 3. Generar el PDF usando el ID de la factura seleccionada
            GeneradorFactura generador = new GeneradorFactura();
            
            // NOTA: Tu GeneradorFactura.generarFacturaPDF usa NUMERO_FACTURA_A_EMITIR
            // Si el método realmente necesita el ID interno de la tabla (id), usa idFacturaAEmitir
            // Si el método realmente necesita el número de factura visible (numFactura), usa numFacturaAEmitir
            // Asumo que tu generador usa el número de factura para el PDF:
            String rutaPDF = generador.generarFacturaPDF(connection, numFacturaAEmitir); 

            if (rutaPDF != null) {
                mostrarAlerta("Éxito", "Factura N° " + numFacturaAEmitir + " generada.");
                abrirPDF(rutaPDF);
            }

        } catch (Exception e) {
            e.printStackTrace();
            mostrarAlerta("Error de Emisión", "Ocurrió un error al generar o guardar el PDF: " + e.getMessage());
        } finally {
            // 4. Cerrar la conexión
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

    private void cargarTabla() {
        TC_IdCli.setCellValueFactory(new PropertyValueFactory<>("id"));
        TC_NomCli.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        TC_NifCli.setCellValueFactory(new PropertyValueFactory<>("nif"));
        TC_DirCli.setCellValueFactory(new PropertyValueFactory<>("DireccionCompleta"));
        TC_MailCli.setCellValueFactory(new PropertyValueFactory<>("email"));
        TC_TelCli.setCellValueFactory(new PropertyValueFactory<>("telefono"));

        TC_IdPro.setCellValueFactory(new PropertyValueFactory<>("id"));
        TC_NomPro.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        TC_NifPro.setCellValueFactory(new PropertyValueFactory<>("nif"));
        TC_DirPro.setCellValueFactory(new PropertyValueFactory<>("DireccionCompleta"));
        TC_MailPro.setCellValueFactory(new PropertyValueFactory<>("email"));
        TC_TelPro.setCellValueFactory(new PropertyValueFactory<>("telefono"));

        TC_IdArt.setCellValueFactory(new PropertyValueFactory<>("id"));
        TC_DescArt.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        TC_CostArt.setCellValueFactory(new PropertyValueFactory<>("coste"));
        TC_PvpArt.setCellValueFactory(new PropertyValueFactory<>("pvp"));
        TC_IvaArt.setCellValueFactory(new PropertyValueFactory<>("iva"));
        TC_StockArt.setCellValueFactory(new PropertyValueFactory<>("stock"));
        TC_IdProArt.setCellValueFactory(new PropertyValueFactory<>("idProveedor"));

        TCR_IdArt.setCellValueFactory(new PropertyValueFactory<>("id"));
        TCR_DescArt.setCellValueFactory(new PropertyValueFactory<>("descripcion"));
        TCR_PvpArt.setCellValueFactory(new PropertyValueFactory<>("pvp"));
        
        TC_IdFac.setCellValueFactory(new PropertyValueFactory<>("id"));
        TC_TipoFac.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        TC_NumFac.setCellValueFactory(new PropertyValueFactory<>("numFactura"));
        TC_FEmisionFac.setCellValueFactory(new PropertyValueFactory<>("fechaEmision"));
        TC_IdClienteFac.setCellValueFactory(new PropertyValueFactory<>("idSecundario"));
        TC_ConFac.setCellValueFactory(new PropertyValueFactory<>("concepto"));

        // Mapeo de Double, ajustado desde tu definición inicial
        TC_IVAFac.setCellValueFactory(new PropertyValueFactory<>("iva"));
        TC_TotalFac.setCellValueFactory(new PropertyValueFactory<>("total"));

        try {
            List<Factura> facturas = facturaDAO.obtenerTodasLasFacturas(); 
            TV_Factura.setItems(FXCollections.observableArrayList(facturas));
        } catch (SQLException e) {
            e.printStackTrace();
            mostrarError("Error al cargar las facturas: " + e.getMessage());
        }
    }

    @FXML
    private void modificarInfo(ActionEvent event) {
        if (empresa == null) {
            mostrarAlerta("Error", "No hay ninguna empresa seleccionada para modificar.");
            return;
        }
        empresa.setNombre(InfoNombre.getText());
        empresa.setNif(InfoNIF.getText());
        empresa.setCalle(txtInfoCalle.getText());
        empresa.setTelefono(txtInfoTlf.getText());
        empresa.setEmail(txtInfoEmail.getText());

        EntidadDAO entDao = new EntidadDAO();
        try {
            boolean actualizado = entDao.actualizar(empresa);
            if (actualizado) {
                // recargar datos de la vista
                cargarTablasEmpresa();
                mostrarAlerta("Correcto", "Se han guardado los cambios");
            } else {
                mostrarAlerta("Aviso", "No se han detectado cambios o la actualización falló (0 filas afectadas).");
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            mostrarAlerta("Error BBDD", "Error al actualizar: " + ex.getMessage());
        }
    }
    
    @FXML
    private void cancelarCambioInfo(ActionEvent event){
        InfoNombre.setText(empresa.getNombre());
        InfoNIF.setText(empresa.getNif());
        txtInfoCalle.setText(empresa.getDireccionCompleta());
        txtInfoEmail.setText(empresa.getEmail());
        txtInfoTlf.setText(empresa.getTelefono());
        System.out.println("Empresa cargada: " + empresa.getNombre());
    }

    private void escucharTablaResultadosArticulo() {
        TV_ResultadosArticulo.getSelectionModel().selectedItemProperty().addListener((obs, oldSel, newSel) -> {
            if (newSel != null) {
                mostrarAlerta("Artículo Seleccionado", "El artículo '" + newSel.getDescripcion() + "' ha sido seleccionado para la línea de factura.");
                // Aquí podrías añadir la lógica para rellenar los campos de la nueva línea de factura
            }
        });
    }

    @FXML
    private void onBuscarArticulo(ActionEvent event) {
        String descripcionBusqueda = txtBuscadorArticulo.getText().trim();

        if (descripcionBusqueda.isEmpty()) {
            mostrarAlerta("Búsqueda vacía", "Introduce una descripción o parte de ella para buscar artículos.");
            TV_ResultadosArticulo.getItems().clear();
            return;
        }

        try {
            // Asume que ProductoDAO tiene un método para buscar por descripción, o lo usaremos desde EntidadDAO
            // Como tu productoDAO es un campo de la clase, lo usaremos.
            productoDAO = new ProductoDAO();

            // Suponemos que tienes un método en ProductoDAO para buscar por descripción.
            // Si no lo tienes, deberás añadirlo en la clase Modelo.ProductoDAO
            List<Producto> resultados = productoDAO.buscarPorDescripcion(descripcionBusqueda);

            if (resultados.isEmpty()) {
                mostrarAlerta("Sin resultados", "No se encontraron artículos con la descripción: " + descripcionBusqueda);
            }

            TV_ResultadosArticulo.setItems(FXCollections.observableArrayList(resultados));

        } catch (SQLException ex) {
            ex.printStackTrace();
            mostrarAlerta("Error BBDD", "Ocurrió un error al buscar los artículos: " + ex.getMessage());
        }
    }
}
