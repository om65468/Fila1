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
import java.time.LocalDate;
import javafx.application.Platform;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;

public class VentanaPrincipalController {

    private ObservableList<Producto> masterDataProductos = FXCollections.observableArrayList();
    private FilteredList<Producto> filteredDataProductos;

    @FXML
    private Button Boton_emitir_fac;

    @FXML
    private Button Boton_ver_fac;

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
    private Entidad clienteSeleccionado;
    private Producto productoSeleccionado;
    private ObservableList<Entidad> listaProveedores = FXCollections.observableArrayList();
    private EntidadDAO entidadDAO = new EntidadDAO();
    private ProductoDAO productoDAO = new ProductoDAO();
    private FacturaDAO facturaDAO = new FacturaDAO();
    
    private boolean editandoCliente = false;
    private boolean editandoProveedor = false;
    private boolean editandoArticulo = false;
    private boolean editandoFactura = false;


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
    @FXML
    private AnchorPane paneFacturaLinea;

    //Proveedor
    @FXML
    private Button Boton_guardar_prov;
    @FXML
    private Button Boton_duplicar_prov;
    @FXML
    private Button Boton_modificar_prov_form;
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
    private Button Boton_modificar_cli_form;
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
    private Button Boton_modificar_articulo;
    @FXML
    private Button Boton_guardar_art;
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
    private AnchorPane paneFacturaNueva; // <-- Necesitas añadir este AnchorPane
    @FXML
    private Button Boton_guardar_fac_nueva;
    @FXML
    private Button Boton_cancelar_fac_nueva;
    @FXML
    private TextField txtFacId;
    @FXML
    private TextField txtFacTipo;
    @FXML
    private TextField txtFacNumFactura;
    @FXML
    private DatePicker txtFacFechaEmision;
    @FXML
    private TextField txtFacIdSecundario;
    @FXML
    private TextArea txtFacConcepto;
    @FXML
    private TextField txtFacBase;
    @FXML
    private TextField txtFacIva;
    @FXML
    private TextField txtFacTotal;
    @FXML
    private TextField txtFacEstado;
    @FXML
    private TextArea txtFacObservaciones;
    
    private boolean ignorarCambioTab = false;


    @FXML
    private Button Boton_nuevo_fac;

    @FXML
    public void initialize() {
        System.out.println(empresa);
        tabPane.getSelectionModel().select(tabInformacion);
        paneInformacion.setVisible(true);

        // Configuración de columnas (solo una vez)
        cargarTabla();

        // Inicialización de escuchadores
        escucharTablaProv();
        escucharTablaResultadosArticulo();

        // Inicializar la lista de facturas (si ya está cargada en cargarTabla, dejar)
        // Inicializar la carga de productos maestros
        try {
            // En lugar de cargar facturas aquí, lo haremos al abrir la pestaña TabFactura
            // Vamos a cargar todos los productos disponibles en el master
            productoDAO = new ProductoDAO();
            masterDataProductos.addAll(productoDAO.listarTodos()); // Asegúrate de que ProductoDAO tiene listarTodos()

            // 1. Inicializar la lista filtrada con todos los datos
            filteredDataProductos = new FilteredList<>(masterDataProductos, p -> true);

            // 2. Crear una lista ordenada (opcional, pero mejora la UI)
            SortedList<Producto> sortedData = new SortedList<>(filteredDataProductos);

            // 3. Vincular el comparador de SortedList a la TableView
            sortedData.comparatorProperty().bind(TV_ResultadosArticulo.comparatorProperty());

            // 4. Establecer la lista ordenada/filtrada como contenido de la tabla
            TV_ResultadosArticulo.setItems(sortedData);

            setupFiltroArticulos();
        } catch (Exception e) {
            mostrarError("Error BBDD");
            e.printStackTrace();
        }

        tabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {

            // 0. Evitar re-ejecución cuando cambiamos la pestaña manualmente
            if (ignorarCambioTab) {
                ignorarCambioTab = false; // reseteamos
                return;
            }

            // 1. Si estamos editando, mostrar mensaje
            boolean bloqueado =
                    editandoCliente ||
                    editandoProveedor ||
                    editandoArticulo ||
                    editandoFactura;

            if (bloqueado) {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmar salida");
                alert.setHeaderText("Tienes cambios sin guardar");
                alert.setContentText("¿Deseas salir sin guardar los cambios?");

                ButtonType salir = new ButtonType("Salir");
                ButtonType cancelar = new ButtonType("Cancelar", ButtonBar.ButtonData.CANCEL_CLOSE);
                alert.getButtonTypes().setAll(salir, cancelar);

                var result = alert.showAndWait();

                if (result.isPresent() && result.get() == cancelar) {

                    // Evitar nueva ejecución del listener
                    ignorarCambioTab = true;

                    // Regresar a la pestaña anterior
                    Platform.runLater(() -> tabPane.getSelectionModel().select(oldTab));
                    return; 
                }

                // Si acepta salir, reseteamos banderas
                editandoCliente = false;
                editandoProveedor = false;
                editandoArticulo = false;
                editandoFactura = false;
            }

            // 2. Control de visibilidad de panes según tab
            if (newTab == tabArchivo) {
                try { switchToPrimary(); } catch (IOException e) {}
            } else if (newTab == tabInformacion) {
                paneInfoClientes.setVisible(false);
                paneInfoProveedores.setVisible(false);
                paneInfoArticulos.setVisible(false);
                paneInformacion.setVisible(true);
                paneFactura.setVisible(false);
                paneFacturaLinea.setVisible(false);
                paneFacturaNueva.setVisible(false);
            } else if (newTab == tab_cliente) {
                paneInfoClientes.setVisible(true);
                paneInfoProveedores.setVisible(false);
                paneInfoArticulos.setVisible(false);
                paneInformacion.setVisible(false);
                paneFactura.setVisible(false);
                paneFacturaLinea.setVisible(false);
                paneFacturaNueva.setVisible(false);
            } else if (newTab == tab_proveedor) {
                paneInfoClientes.setVisible(false);
                paneInfoProveedores.setVisible(true);
                paneInfoArticulos.setVisible(false);
                paneInformacion.setVisible(false);
                paneFactura.setVisible(false);
                paneFacturaLinea.setVisible(false);
                paneFacturaNueva.setVisible(false);
            } else if (newTab == tabArticulos) {
                paneInfoClientes.setVisible(false);
                paneInfoProveedores.setVisible(false);
                paneInfoArticulos.setVisible(true);
                paneInformacion.setVisible(false);
                paneFactura.setVisible(false);
                paneFacturaLinea.setVisible(false);
                paneFacturaNueva.setVisible(false);
                cargarProveedoresDeEmpresa();
            } else if (newTab == TabFactura) {
                paneInfoClientes.setVisible(false);
                paneInfoProveedores.setVisible(false);
                paneInfoArticulos.setVisible(false);
                paneInformacion.setVisible(false);
                paneFactura.setVisible(false);
                paneCliente.setVisible(false);
                paneFactura.setVisible(true);
                paneFacturaLinea.setVisible(false);
                paneFacturaNueva.setVisible(false);
                cargarFacturas();
            }
        });

    }

    //Metodos de Cliente
    @FXML
    private void onNuevoCliente() {
        editandoCliente = true;
        ocultarPanes();
        paneInfoClientes.setVisible(false);
        paneInfoClientes.setManaged(false);
        paneCliente.setVisible(true);
        paneCliente.setManaged(true);
        cargarTablasEmpresa();
        Boton_modificar_cli_form.setDisable(true);
        Boton_guardar_cli.setDisable(false);
        CodCli.setDisable(false);
        NomCli.setDisable(false);
        NIFCli.setDisable(false);
    }

    @FXML
    private void onModificarCliente() {
        editandoCliente = true;
        clienteSeleccionado = TV_Clientes.getSelectionModel().getSelectedItem();
        if (clienteSeleccionado == null) {
            mostrarAlerta("Error", "No hay ningún cliente seleccionado.");
            return;
        }
        ocultarPanes();
        paneInfoClientes.setVisible(false);
        paneInfoClientes.setManaged(false);
        paneCliente.setVisible(true);
        paneCliente.setManaged(true);
        cargarTablasEmpresa();
        CodCli.setText(String.valueOf(clienteSeleccionado.getId()));
        EmailCli.setText(clienteSeleccionado.getEmail());
        NIFCli.setText(clienteSeleccionado.getNif());
        NomCli.setText(clienteSeleccionado.getNombre());
        TlfCli.setText(clienteSeleccionado.getTelefono());
        DirCli.setText(clienteSeleccionado.getDireccionCompleta());
        Boton_modificar_cli_form.setDisable(false);
        Boton_guardar_cli.setDisable(true);
        CodCli.setDisable(true);
        NomCli.setDisable(true);
        NIFCli.setDisable(true);
    }

    //Metodos de Proveedor
    @FXML
    private void onNuevoProveedor() {
        editandoProveedor = true;
        ocultarPanes();
        paneInfoProveedores.setVisible(false);
        paneInfoProveedores.setManaged(false);
        paneProveedor.setVisible(true);
        paneProveedor.setManaged(true);
        cargarTablasEmpresa();
        Boton_guardar_prov.setDisable(false);
        Boton_modificar_prov_form.setDisable(true);
        CodProv.setDisable(false);
        NomProv.setDisable(false);
        NIFProv.setDisable(false);
    }

    @FXML
    private void onModificarProveedor() {
        editandoProveedor = true;
        proveedorSeleccionado = TV_Proveedores.getSelectionModel().getSelectedItem();
        if (proveedorSeleccionado == null) {
            mostrarAlerta("Error", "No hay ningún proveedor seleccionado.");
            return;
        }
        ocultarPanes();
        paneInfoProveedores.setVisible(false);
        paneInfoProveedores.setManaged(false);
        paneProveedor.setVisible(true);
        paneProveedor.setManaged(true);
        cargarTablasEmpresa();
        CodProv.setText(String.valueOf(proveedorSeleccionado.getId()));
        EmailProv.setText(proveedorSeleccionado.getEmail());
        NIFProv.setText(proveedorSeleccionado.getNif());
        NomProv.setText(proveedorSeleccionado.getNombre());
        TlfProv.setText(proveedorSeleccionado.getTelefono());
        DirProv.setText(proveedorSeleccionado.getDireccionCompleta());
        Boton_guardar_prov.setDisable(true);
        Boton_modificar_prov_form.setDisable(false);
        CodProv.setDisable(true);
        NomProv.setDisable(true);
        NIFProv.setDisable(true);
    }
    
    @FXML
    private void cancelarNuevo(){
        editandoCliente = false;
        editandoProveedor = false;
        editandoArticulo = false;
        editandoFactura = false;

    }

    //Metodos de Articulo
    @FXML
    private void onNuevoArticulo() {
        editandoArticulo = true;
        ocultarPanes();
        paneInfoArticulos.setVisible(false);
        paneInfoArticulos.setManaged(false);
        paneProducto.setVisible(true);
        paneProducto.setManaged(true);
        escucharTablaProv();
        cargarProveedoresDeEmpresa();
        cargarTablasEmpresa();
        Boton_modificar_articulo.setDisable(true);
        Boton_guardar_art.setDisable(false);
        IDProd.setDisable(false);
        
    }

    @FXML
    private void onModificarArticulo() {
        editandoArticulo = true;
        productoSeleccionado = TV_Articulos.getSelectionModel().getSelectedItem();
        if (productoSeleccionado == null) {
            mostrarAlerta("Error", "No hay ningún artículo seleccionado.");
            return;
        }
        ocultarPanes();
        paneInfoArticulos.setVisible(false);
        paneInfoArticulos.setManaged(false);
        paneProducto.setVisible(true);
        paneProducto.setManaged(true);
        escucharTablaProv();
        cargarProveedoresDeEmpresa();
        cargarTablasEmpresa();
        
        IDProd.setText(productoSeleccionado.getId()+"");
        DescProd.setText(productoSeleccionado.getDescripcion());
        //ProvProd.setText(productoSeleccionado.getRefProveedor());
        CosProd.setText(productoSeleccionado.getCoste() + "");
        PVPProd.setText(productoSeleccionado.getPvp() + "");
        IVAProd.setText(productoSeleccionado.getIva() + "");
        StokProd.setText(productoSeleccionado.getStock() + "");
        Boton_modificar_articulo.setDisable(false);
        Boton_guardar_art.setDisable(true);
        IDProd.setDisable(true);
    }

    //Metodos de Factura
    @FXML
    private void onNuevoFactura() {
        ocultarPanes();
        paneFacturaNueva.setVisible(true); // Muestra el formulario
        paneFacturaNueva.setManaged(true);

        // Limpiar campos para un nuevo registro
        limpiarCamposFactura();
    }

    private void limpiarCamposFactura() {
        txtFacId.clear(); // El ID es automático, debería estar vacío
        txtFacTipo.clear();
        txtFacNumFactura.clear();
        txtFacFechaEmision.setValue(null);
        txtFacIdSecundario.clear();
        txtFacConcepto.clear();
        txtFacBase.clear();
        txtFacIva.clear();
        txtFacTotal.clear();
        txtFacEstado.clear();
        txtFacObservaciones.clear();
    }

    @FXML
    private void guardarFactura() {
        System.out.println(empresa.getId());
        if (empresa == null) {
            mostrarError("Debe haber una empresa cargada para guardar una factura.");
            return;
        }

        // 1. Validar y parsear datos de entrada
        try {
            char tipo = txtFacTipo.getText().trim().isEmpty() ? ' ' : txtFacTipo.getText().trim().toUpperCase().charAt(0);
            int numFactura = Integer.parseInt(txtFacNumFactura.getText().trim());
            LocalDate localDate = txtFacFechaEmision.getValue();
            if (localDate == null) {
                mostrarError("Debes seleccionar una fecha de emisión.");
                return;
            }

            // 1. Validar y parsear datos de entrada
            try {
                tipo = txtFacTipo.getText().trim().isEmpty() ? ' ' : txtFacTipo.getText().trim().toUpperCase().charAt(0);
                numFactura = Integer.parseInt(txtFacNumFactura.getText().trim());
                localDate = txtFacFechaEmision.getValue();
                if (localDate == null) {
                    mostrarError("Debes seleccionar una fecha de emisión.");
                    return;
                }
                String fechaEmision = localDate.toString();
                int idSecundario = Integer.parseInt(txtFacIdSecundario.getText().trim());
                int idEmpresa = empresa.getId(); // ** <--- NUEVO: Obtener ID de la empresa **

                String concepto = txtFacConcepto.getText().trim();
                double base = Double.parseDouble(txtFacBase.getText().trim());
                double iva = Double.parseDouble(txtFacIva.getText().trim());
                double total = base * (1 + iva / 100.0); // Recalculamos para mayor seguridad
                String estado = txtFacEstado.getText().trim();
                String observaciones = txtFacObservaciones.getText().trim();

                // 2. Validación básica
                if (tipo == ' ' || fechaEmision.isEmpty() || concepto.isEmpty() || idSecundario <= 0) {
                    mostrarError("Faltan campos obligatorios (Tipo, Fecha, Concepto o ID Entidad).");
                    return;
                }

                // Re-validar tipo de factura (C=Compra, V=Venta)
                if (tipo != 'C' && tipo != 'V') {
                    mostrarError("El tipo de factura debe ser 'C' (Compra) o 'V' (Venta).");
                    return;
                }

                Factura nuevaFactura = new Factura(
                        0,
                        tipo,
                        numFactura,
                        fechaEmision,
                        idSecundario,
                        idEmpresa,
                        concepto,
                        base,
                        iva,
                        total,
                        estado,
                        observaciones
                );

                // 4. Insertar en la BBDD
                facturaDAO.insertar(nuevaFactura);

                // 5. Notificación y limpieza
                mostrarAlerta("Éxito", "Factura N° " + nuevaFactura.getNumFactura() + " guardada correctamente con ID " + nuevaFactura.getId());

                // Vuelve a la vista de lista de facturas
                cancelarNuevaFactura();

            } catch (NumberFormatException e) {
                mostrarError("Error en formato numérico: Asegúrate de que Número de Factura, ID Entidad, Base e IVA son números válidos.");
                e.printStackTrace();
            }
        } catch (SQLException e) {
            mostrarError("Error BBDD al guardar la factura: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    private void cancelarNuevaFactura() {
        limpiarCamposFactura();

        // Vuelve a la vista de lista de facturas
        paneFacturaNueva.setVisible(false);
        paneFacturaNueva.setManaged(false);
        paneFactura.setVisible(true);
        paneFactura.setManaged(true);

        // Recarga la tabla de facturas para ver los posibles cambios
        cargarFacturas();

        // Cambia la selección de pestaña al TabFactura para asegurar el foco visual
        tabPane.getSelectionModel().select(TabFactura);
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
        cargarTabla();
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
    private void guardarModificacionArticulo() {
        try {
            // Recoger datos modificables
            String descripcion = DescProd.getText().trim();
            double coste;
            double pvp;
            double iva;
            int stock;

            // Validar campos numéricos
            try {
                coste = Double.parseDouble(CosProd.getText().trim());
                pvp = Double.parseDouble(PVPProd.getText().trim());
                iva = Double.parseDouble(IVAProd.getText().trim());
                stock = Integer.parseInt(StokProd.getText().trim());
            } catch (NumberFormatException nfe) {
                mostrarAlerta("Datos numéricos inválidos", "Comprueba coste, pvp, iva y stock (deben ser números).");
                return;
            }

            // Validación básica
            if (descripcion.isEmpty()) {
                mostrarAlerta("Descripción vacía", "Introduce una descripción para el artículo.");
                return;
            }

            // Actualizar objeto
            productoSeleccionado.setDescripcion(descripcion);
            productoSeleccionado.setCoste(coste);
            productoSeleccionado.setPvp(pvp);
            productoSeleccionado.setIva(iva);
            productoSeleccionado.setStock(stock);

            // Guardar en base de datos
            productoDAO.actualizar(productoSeleccionado);

            // Refrescar tabla
            cargarTablasEmpresa();

            mostrarAlerta("Éxito", "Artículo modificado correctamente.");

            // Limpiar selección y campos
            productoSeleccionado = null;
            DescProd.clear();
            CosProd.clear();
            PVPProd.clear();
            IVAProd.clear();
            StokProd.clear();
            IDProd.clear();
//            ProvProd.clear();

        } catch (SQLException ex) {
            ex.printStackTrace();
            mostrarAlerta("Error BBDD", "No se pudo modificar el artículo: " + ex.getMessage());
        }
    }

    @FXML
    private void guardarModificacionCliente() {

        // ID, NIF y Nombre NO se cambian → NO tocar esos campos
        String nuevoEmail = EmailCli.getText();
        String nuevoTelefono = TlfCli.getText();
        String nuevaDireccion = DirCli.getText();

        // Actualizar solo datos modificables
        clienteSeleccionado.setEmail(nuevoEmail);
        clienteSeleccionado.setTelefono(nuevoTelefono);
        clienteSeleccionado.setCalle(nuevaDireccion);

        try {
            entidadDAO.actualizar(clienteSeleccionado);
            mostrarAlerta("Éxito", "Cliente modificado correctamente.");
        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudo modificar el cliente: " + e.getMessage());
        }
    }

    @FXML
    private void borrarCliente() {
        clienteSeleccionado = TV_Clientes.getSelectionModel().getSelectedItem();
        if (clienteSeleccionado == null) {
            mostrarAlerta("Error", "No hay ningún cliente seleccionado.");
            return;
        }

        entidadDAO.eliminar(clienteSeleccionado.getId());
        mostrarAlerta("Éxito", "Cliente eliminado correctamente.");
        TV_Clientes.getItems().remove(clienteSeleccionado); // actualizar tabla
    }

    @FXML
    private void borrarFactura() {
        Factura facturaSeleccionada = TV_Factura.getSelectionModel().getSelectedItem();
        if (facturaSeleccionada == null) {
            mostrarAlerta("Error", "No hay ninguna factura seleccionada.");
            return;
        }
        FacturaDAO facturadao = new FacturaDAO();
        mostrarAlerta("Éxito", "Factura eliminada correctamente.");
        try {
            facturadao.eliminar(facturaSeleccionada.getId());
        } catch (SQLException e) {
        }
        TV_Factura.getItems().remove(facturaSeleccionada);
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
    private void guardarModificacionProveedor() {

        // ID, NIF y Nombre NO se cambian → NO tocar esos campos
        String nuevoEmail = EmailProv.getText();
        String nuevoTelefono = TlfProv.getText();
        String nuevaDireccion = DirProv.getText();

        // Actualizar solo datos modificables
        proveedorSeleccionado.setEmail(nuevoEmail);
        proveedorSeleccionado.setTelefono(nuevoTelefono);
        proveedorSeleccionado.setCalle(nuevaDireccion);

        try {
            entidadDAO.actualizar(proveedorSeleccionado);
            mostrarAlerta("Éxito", "Proveedor modificado correctamente.");
        } catch (Exception e) {
            mostrarAlerta("Error", "No se pudo modificar el proveedor: " + e.getMessage());
        }
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
//            ProvProd.clear();
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

        paneFacturaNueva.setVisible(false);
        paneFacturaNueva.setManaged(false);

        paneFactura.setVisible(false);
        paneFactura.setManaged(false);

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
        });
    }

    public void cargarProveedoresDeEmpresa() {
        if (empresa == null) {
            return;
        }
        List<Entidad> proveedores = entidadDAO.listarProveedoresDeEmpresa(empresa.getId());
        listaProveedores.setAll(proveedores);

        if (ColIDProv.getCellValueFactory() == null) {
            ColIDProv.setCellValueFactory(new PropertyValueFactory<>("id"));
        }
        if (ColNomProv.getCellValueFactory() == null) {
            ColNomProv.setCellValueFactory(new PropertyValueFactory<>("nombre"));
        }
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

    private void mostrarError(String mensaje) {
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
            connection = ConexionBBDD.get();

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

    }

    private void cargarFacturas() {
        // Mapeo de Double, ajustado desde tu definición inicial
        TC_IVAFac.setCellValueFactory(new PropertyValueFactory<>("iva"));
        TC_TotalFac.setCellValueFactory(new PropertyValueFactory<>("total"));

        try {
            if (empresa == null) {
                TV_Factura.setItems(FXCollections.observableArrayList()); // Muestra vacío si no hay empresa
                mostrarError("No se ha cargado la empresa para filtrar facturas.");
                return;
            }

            // ** CAMBIO CLAVE: Llama a un método en DAO para filtrar por id_empresa **
            // DEBES implementar FacturaDAO.obtenerFacturasPorEmpresa(int idEmpresa)
            List<Factura> facturas = facturaDAO.obtenerFacturasPorEmpresa(empresa.getId());
            TV_Factura.setItems(FXCollections.observableArrayList(facturas));

        } catch (SQLException e) {
            e.printStackTrace();
            mostrarError("Error al cargar las facturas de la empresa: " + e.getMessage());
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
    private void cancelarCambioInfo(ActionEvent event) {
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

    // Nuevo método en VentanaPrincipalController
    private void setupFiltroArticulos() {
        // Añadir un listener al campo de texto de búsqueda de artículos
        txtBuscadorArticulo.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredDataProductos.setPredicate(producto -> {
                // Si el campo de búsqueda está vacío, muestra todos los artículos.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                // Convertir el texto de búsqueda a minúsculas para una comparación sin distinción entre mayúsculas y minúsculas
                String lowerCaseFilter = newValue.toLowerCase();

                // Filtrar por la descripción
                if (producto.getDescripcion().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Coincidencia encontrada
                }
                // Opcional: podrías filtrar por ID o PVP también, si quisieras

                return false; // No hay coincidencia
            });
        });
    }

    @FXML
    void onVerLineasFacturas(ActionEvent event) {
        // 1. Verificar si hay una factura seleccionada en la tabla TV_Factura
        Factura facturaSeleccionada = TV_Factura.getSelectionModel().getSelectedItem();

        if (facturaSeleccionada == null) {
            mostrarAlerta("Advertencia", "Por favor, selecciona una factura de la lista para ver sus líneas.");
            return; // Sale del método si no hay selección
        }

        // Obtener el ID de la factura para cargar sus líneas
        int idFactura = facturaSeleccionada.getId();

        // 2. Cargar las líneas de la factura seleccionada (Lógica Pendiente en DAO)
        try {
            // ** [PENDIENTE] ** Debes crear un método en FacturaDAO o LineaFacturaDAO
            // para obtener las líneas asociadas a este idFactura.
            // Ejemplo de llamada (asumiendo que FacturaDAO puede hacerlo por ahora):
            // List<LineaFactura> lineas = facturaDAO.obtenerLineasPorFactura(idFactura);

            // Suponiendo que tienes un método similar a cargarFacturas() para las líneas:
            cargarLineasFactura(idFactura); // Llamamos al nuevo método de carga

            // 3. Cambiar la vista
            paneFactura.setVisible(false);
            paneFacturaLinea.setVisible(true);
            paneFacturaLinea.setManaged(true);

        } catch (SQLException e) {
            e.printStackTrace();
            mostrarError("No se pudieron cargar las líneas de la factura: " + e.getMessage());
        }
    }

    private void cargarLineasFactura(int idFactura) throws SQLException {

        // ** [ATENCIÓN] **
        // Es CRUCIAL que las TableColumn de TV_FacturaLinea (`TC_ArtFacLi`, `TC_CantFacLi`, etc.)
        // estén mapeadas correctamente a las propiedades de la clase Modelo.LineaFactura.
        // Actualmente, tus TableColumns están mapeadas a la clase Factura (Error de tipo).
        // Configuración de columnas (solo si no se hizo en cargarTabla o si necesitas re-configurar)
        // Ejemplo de mapeo correcto (Asumiendo que LineaFactura tiene getCantidad(), getIdProducto(), etc.):
        /*
        TC_LinFacLi.setCellValueFactory(new PropertyValueFactory<>("id_linea"));
        TC_ArtFacLi.setCellValueFactory(new PropertyValueFactory<>("id_producto"));
        TC_CantFacLi.setCellValueFactory(new PropertyValueFactory<>("cantidad"));
        // ... otros campos de línea
         */
        // ** PENDIENTE: Crear FacturaDAO.obtenerLineasPorFactura(idFactura) **
        // List<LineaFactura> lineas = facturaDAO.obtenerLineasPorFactura(idFactura);
        // TV_FacturaLinea.setItems(FXCollections.observableArrayList(lineas));
        // Por ahora, para no tener errores de compilación, dejaremos esta parte comentada
        // hasta que el DAO y los mapeos de columnas se definan correctamente.
        // Ejemplo de alerta para confirmar qué factura estamos viendo:
        System.out.println("Cargando líneas para Factura ID: " + idFactura);
    }

}
