package Controlador;

import Modelo.Entidad;
import Modelo.EntidadDAO;
import Modelo.TipoEntidadDAO;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class PrimaryController implements Initializable {

    @FXML
    private Button btn_eliminar;

    @FXML
    private Button button_abrir;

    @FXML
    private Button button_empre;

    @FXML
    private Button button_informacion;

    @FXML
    private Button button_nuevo;

    @FXML
    private AnchorPane pane_abrir;

    @FXML
    private AnchorPane pane_info;

    @FXML
    private AnchorPane pane_nuevo;

    @FXML
    private TableColumn<Entidad, String> TColumn_Direccion;

    @FXML
    private TableColumn<Entidad, String> TColumn_NIF;

    @FXML
    private TableColumn<Entidad, String> TColumn_Nombre;

    @FXML
    private TableColumn<Entidad, String> TColumn_Telefono;

    @FXML
    private TableColumn<Entidad, String> TColumn_email;

    @FXML
    private TableView<Entidad> TView_Empresa;

    //Empresa
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
    private TextField TelEmp;

    @FXML
    private AnchorPane paneEmpresa;

    private EntidadDAO entidadDAO;
    private ObservableList<Entidad> listaEmpresas;

    private Entidad empresaCreada;

    @FXML
    private TextField txtBuscarEmpresa;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        empresaCreada = null;
        cursorHand();

        entidadDAO = new EntidadDAO();
        listaEmpresas = FXCollections.observableArrayList(entidadDAO.obtenerEmpresas());

        datosTablas();

        // esta parte lo filtra por nombre
        FilteredList<Entidad> filteredData = new FilteredList<>(listaEmpresas, p -> true);

        txtBuscarEmpresa.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(empresa -> {

                // Si no hay nada muestra todas
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String filtro = newValue.toLowerCase();

                // Comparación por nombre o NIF
                if (empresa.getNombre().toLowerCase().contains(filtro)) {
                    return true;
                }
                if (empresa.getNif().toLowerCase().contains(filtro)) {
                    return true;
                }

                return false;
            });
        });

        // Ordenación automática del TableView
        SortedList<Entidad> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(TView_Empresa.comparatorProperty());

        // Y ahora asignamos esta lista filtrada y ordenada a la tabla
        TView_Empresa.setItems(sortedData);
    }

    @FXML
    private void onCrearEmpresa(ActionEvent event) throws IOException {
        paneEmpresa.setVisible(true);
    }

    @FXML
    void switchToVentanaPrincipal(ActionEvent event) throws IOException {
        if (crearEmpresa()) {
            try {
                // 2. Cargar la nueva vista
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/davinci/gestorfactura/ventana_principal.fxml"));
                Parent root = loader.load();

                // 3. Obtener el controlador de la nueva vista
                VentanaPrincipalController controller = loader.getController();

                // 4. Pasar la empresa al nuevo controlador
                controller.setEmpresa(empresaCreada);

                // 5. Cambiar la escena pero en la MISMA ventana
                Scene escenaActual = ((Node) event.getSource()).getScene();
                escenaActual.setRoot(root);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    void onCancelarEmpresa(ActionEvent event) {
        paneEmpresa.setVisible(false);
    }

    @FXML
    void onPaneAbrirEmpresa(ActionEvent event) {
        pane_info.setVisible(false);
        pane_nuevo.setVisible(false);
        paneEmpresa.setVisible(false);
        pane_abrir.setVisible(true);
    }

    @FXML
    void onPaneInformacion(ActionEvent event) {
        pane_info.setVisible(true);
        pane_nuevo.setVisible(false);
        pane_abrir.setVisible(false);
        paneEmpresa.setVisible(false);
    }

    @FXML
    void onPaneNuevaEmpresa(ActionEvent event) {
        pane_info.setVisible(false);
        pane_nuevo.setVisible(true);
        pane_abrir.setVisible(false);
    }

    public void cursorHand() {
        button_abrir.setCursor(Cursor.HAND);
        button_empre.setCursor(Cursor.HAND);
        button_informacion.setCursor(Cursor.HAND);
        button_nuevo.setCursor(Cursor.HAND);

    }

    public void datosTablas() {
        TColumn_Nombre.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));

        TColumn_NIF.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getNif()));

        TColumn_Telefono.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getTelefono()));

        TColumn_email.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));

        TColumn_Direccion.setCellValueFactory(
                cellData -> new SimpleStringProperty(cellData.getValue().getDireccionCompleta()));
    }

    @FXML
    void onElegirEmpresa(ActionEvent event) {
        // 1. Obtener la empresa seleccionada
        Entidad seleccionada = TView_Empresa.getSelectionModel().getSelectedItem();

        if (seleccionada == null) {
            mostrarError("Selecciona una empresa.");
            return;
        }

        try {
            // 2. Cargar la nueva vista
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/davinci/gestorfactura/ventana_principal.fxml"));
            Parent root = loader.load();

            // 3. Obtener el controlador de la nueva vista
            VentanaPrincipalController controller = loader.getController();

            // 4. Pasar la empresa al nuevo controlador
            controller.setEmpresa(seleccionada);

            // 5. Cambiar la escena pero en la MISMA ventana
            Scene escenaActual = ((Node) event.getSource()).getScene();
            escenaActual.setRoot(root);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @FXML
    private void borrarEmpresa() {
        Entidad empresa = TView_Empresa.getSelectionModel().getSelectedItem();
        if (empresa == null) {
            mostrarAlerta("Error", "No hay ninguna empresa seleccionada.");
            return;
        }
        entidadDAO = new EntidadDAO();
        entidadDAO.eliminar(empresa.getId());
        recargarTablaEmpresas();
        mostrarAlerta("Éxito", "Empresa y todos sus datos relacionados eliminados.");
        listaEmpresas = FXCollections.observableArrayList(entidadDAO.obtenerEmpresas());
        datosTablas();
        TView_Empresa.setItems(listaEmpresas);
    }

    @FXML
    public boolean crearEmpresa() {
        try {
            entidadDAO = new EntidadDAO();
            String nombre = NomEmp.getText().trim();
            String nif = NIFEmp.getText().trim();
            String calle = DirEmp.getText().trim();
            String cp = CPEmp.getText().trim();
            String ciudad = CiudEmp.getText().trim();
            String telefono = TelEmp.getText().trim();
            String email = EmailEmp.getText().trim();

            if (entidadDAO.existe(nif)) {
                mostrarError("El cliente ya existe (NIF o Email duplicado).");

            } else {
                if (validarEmpresa()) {
                    Entidad entidad = new Entidad(0, nombre, nif, calle, cp, ciudad, email, telefono);
                    entidadDAO.insertar(entidad);
                    System.out.println(entidad.toString());
                    Entidad entidadInsertada = entidadDAO.buscarPorNif(nif);

                    TipoEntidadDAO tipoDAO = new TipoEntidadDAO();
                    tipoDAO.insertar(entidadInsertada.getId(), 1);

                    System.out.println("Empresa creada correctamente.");
                    empresaCreada = entidad;
                    return true;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return false;
    }

    private void mostrarAlerta(String titulo, String mensaje) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(titulo);
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    // Método principal para validar todos los campos
    private boolean validarEmpresa() {
        if (!validarNoVacio(NomEmp, "Nombre")) {
            return false;
        }
        if (!validarNoVacio(NIFEmp, "NIF")) {
            return false;
        }
        if (!validarNoVacio(CPEmp, "Código Postal")) {
            return false;
        }
        if (!validarNoVacio(CiudEmp, "Ciudad")) {
            return false;
        }
        if (!validarNoVacio(DirEmp, "Calle")) {
            return false;
        }
        if (!validarNoVacio(EmailEmp, "E-mail")) {
            return false;
        }
        if (!validarNoVacio(TelEmp, "Teléfono")) {
            return false;
        }

        // Validaciones específicas
        if (!validarNIF(NIFEmp.getText())) {
            return false;
        }
        if (!validarEmail(EmailEmp.getText().trim())){
            return false;
        }
        if (!validarCodigoPostal(CPEmp.getText())) {
            return false;
        }
       
        if (!validarTelefono(TelEmp.getText())) {
            return false;
        }

        return true; // Todo válido
    }

    public boolean validarEmail(String email) {
        // Comprobar vacío
        if (email.isEmpty()) {
            mostrarError("El email no puede estar vacío");
            return false;
        }

        // Expresión regular simple para validar email
        String patronEmail = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";

        if (!email.matches(patronEmail)) {
            mostrarError("No es un email válido");
            return false;
        }

        return true;
    }

    // Validación de campo no vacío
    private boolean validarNoVacio(TextField campo, String nombreCampo) {
        if (campo.getText().isEmpty()) {
            mostrarError(nombreCampo + " no puede estar vacío");
            return false;
        }
        return true;
    }

    // Validar NIF español (simplificado)
    private boolean validarNIF(String nif) {
        if (!nif.matches("\\d{8}[A-Z]")) { // 8 dígitos + letra
            mostrarError("NIF inválido");
            return false;
        }
        return true;
    }

    // Validar código postal español
    private boolean validarCodigoPostal(String cp) {
        if (!cp.matches("\\d{5}")) {
            mostrarError("Código postal inválido");
            return false;
        }
        return true;
    }

    // Validar teléfono (9 dígitos)
    public boolean validarTelefono(String tel) {
        if (!tel.matches("\\d{9}")) {
            mostrarError("Teléfono inválido");
            return false;
        }
        return true;
    }

    // Mostrar mensaje de error (puedes adaptarlo a Alert de JavaFX)
    private void mostrarError(String mensaje) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Validación");
        alert.setHeaderText(null);
        alert.setContentText(mensaje);
        alert.showAndWait();
    }

    private void recargarTablaEmpresas() {
        listaEmpresas.setAll(entidadDAO.obtenerEmpresas());
        System.out.println("Tabla recargada con " + listaEmpresas.size() + " empresas.");
    }

}
