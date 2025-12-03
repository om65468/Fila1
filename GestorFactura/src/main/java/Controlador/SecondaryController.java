package Controlador;

import Modelo.ConexionBBDD;
import Modelo.Entidad;
import Modelo.EntidadDAO;
import Modelo.TipoEntidadDAO;
import Vista.App;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SecondaryController {

    private EntidadDAO entidadDAO;

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
    private Button ButtonCancelar;

    @FXML
    private Button ButtonCrear;

    @FXML
    private Button CancelProd;

    //Empresa    
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
    
    //Proveedor
    @FXML
    private Button ElimProd;
    
    @FXML
    private TextField ProovProd;

    @FXML
    private TextField RefProd;
    
    @FXML
    private TextField StockProd;

    @FXML
    private TextField VentProd;
    
    @FXML
    private TextField IDProd;

    @FXML
    private ComboBox<?> IVAProd;
    
    @FXML
    private TextField CodProd;

    @FXML
    private TextField CosteProv;

    @FXML
    private TextField DescProd;    
    
    //Cliente
    @FXML
    private TextField CodCli;
    
    @FXML
    private TextField IDCli;
    
    @FXML
    private TextField NIFCli;

    @FXML
    private TextField NomCli;

    @FXML
    private ComboBox<?> TipoCli;

    @FXML
    private TextField TlfCli;

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
    }

    @FXML
    private void switchToVentanaPrincipal() throws IOException {
        if (crearEmpresa()) {
            App.setRoot("ventana_principal");
        }
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

    }

    public Tab getTabEmpresa() {
        return tabEmpresa;
    }

    public Tab getTabCliente() {
        return tabCliente;
    }

    @FXML
    private boolean crearEmpresa() {
        try {
            entidadDAO = new EntidadDAO();
            String nombre = NomEmp.getText().trim();
            String nif = NIFEmp.getText().trim();
            String calle = DirEmp.getText().trim();
            String cp = CPEmp.getText().trim();
            String ciudad = CiudEmp.getText().trim();
            String telefono = TelEmp.getText().trim();
            String email = EmailEmp.getText().trim();

            if (validarEmpresa()) {
                Entidad entidad = new Entidad(0, nombre, nif, calle, cp, ciudad, email, telefono);
                entidadDAO.insertar(entidad);
                System.out.println(entidad.toString());
                Entidad entidadInsertada = entidadDAO.buscarPorNif(nif);

                /*                if (entidadInsertada == null) {
                mostrarAlerta("Incompleto", "No se pudo recuperar la entidad recién creada.");
                return false;
                }*/

                TipoEntidadDAO tipoDAO = new TipoEntidadDAO();
                tipoDAO.insertar(entidadInsertada.getId(), 1);

                System.out.println("Empresa creada correctamente.");
                return true;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return false;
    }

    public boolean comprobarCli() {
        if (NomCli.getText().isEmpty()) {
            mostrarAlerta("Incompleto", "Debe indicar el nombre de persona.");
            return false;
        } else if (DirEmp.getText().isEmpty()) {
            mostrarAlerta("Incompleto", "Debe indicar el número de personas.");
            return false;
        } else if (CPEmp.getText().isEmpty()) {
            mostrarAlerta("Incompleto", "Debe indicar el codigo de personas.");
            return false;
        } else if (NIFEmp.getText().isEmpty()) {
            mostrarAlerta("Incompleto", "Debe indicar el NIF de personas.");
            return false;
        } else if (EmailEmp.getText().isEmpty()) {
            mostrarAlerta("Incompleto", "Debe indicar el email de personas.");
            return false;
        } else if (TelEmp.getText().isEmpty()) {
            mostrarAlerta("Incompleto", "Debe indicar el número de telefono.");
            return false;
        } else {
            return true;
        }
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
    if (!validarNoVacio(NomEmp, "Nombre")) return false;
    if (!validarNoVacio(NIFEmp, "NIF")) return false;
    if (!validarNoVacio(CPEmp, "Código Postal")) return false;
    if (!validarNoVacio(CiudEmp, "Ciudad")) return false;
    if (!validarNoVacio(DirEmp, "Calle")) return false;
    if (!validarNoVacio(EmailEmp, "E-mail")) return false;
    if (!validarNoVacio(TelEmp, "Teléfono")) return false;

    // Validaciones específicas
    if (!validarNIF(NIFEmp.getText())) return false;
    if (!validarCodigoPostal(CPEmp.getText())) return false;
    if (!validarEmail(EmailEmp.getText())) return false;
    if (!validarTelefono(TelEmp.getText())) return false;

    return true; // Todo válido
}

// Validación de campo no vacío
private boolean validarNoVacio(TextField campo, String nombreCampo) {
    if (campo.getText().trim().isEmpty()) {
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

// Validar email (muy simple)
private boolean validarEmail(String email) {
    if (!email.matches("^\\S+@\\S+\\.\\S+$")) {
        mostrarError("Email inválido");
        return false;
    }
    return true;
}

// Validar teléfono (9 dígitos)
private boolean validarTelefono(String tel) {
    if (!tel.matches("\\d{9}")) {
        mostrarError("Teléfono inválido");
        return false;
    }
    return true;
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
}
