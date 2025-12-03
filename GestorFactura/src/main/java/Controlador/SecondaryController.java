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
        Stage stage = (Stage) ButtonCrear.getScene().getWindow();
        App.setRoot("primary");
        stage.close();
    }

    @FXML
    private void switchToVentanaPrincipal() throws IOException {
        if (crearEmpresa()) {
            Stage stage = (Stage) ButtonCrear.getScene().getWindow();
            App.setRoot("ventana_principal");
            stage.close();  
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

            //if (comprobarCli() == true) {
                Entidad entidad = new Entidad(0, nombre, nif, calle, cp, ciudad, email, telefono);
                entidadDAO.insertar(entidad);
                System.out.println(entidad.toString());
                Entidad entidadInsertada = entidadDAO.buscarPorNif(nif);

                if (entidadInsertada == null) {
                    mostrarAlerta("Incompleto", "No se pudo recuperar la entidad recién creada.");
                    return false;
                }

                TipoEntidadDAO tipoDAO = new TipoEntidadDAO();
                tipoDAO.insertar(entidadInsertada.getId(), 1);

                System.out.println("Empresa creada correctamente.");
                return true;
            //} else {
                //return false;
           // }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
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
    
}
