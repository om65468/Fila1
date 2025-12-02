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
    ///private Connection conn;
    
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
    private Button ButtonCrear;

    @FXML
    private TextField txtCP;
    @FXML
    private TextField txtCiud;
    @FXML
    private TextField txtCon;
    @FXML
    private TextField txtDir;
    @FXML
    private TextField txtEmail;
    @FXML
    private TextField txtID;
    @FXML
    private TextField txtNIF;
    @FXML
    private TextField txtNom;
    @FXML
    private TextField txtTel;
    
    //cliente
    
    @FXML
    private TextField txtNombre;
    
    @FXML
    private TextField txtID2;
    
    @FXML
    private TextField txtCod;
    @FXML
    private TextField txtNIF2;
    
    @FXML
    private TextField txtEmail2;
    
    @FXML
    private TextField txtTlf;
    
    @FXML
    private ComboBox combTipo;
    
    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("primary");
        Stage stage = (Stage) ButtonCrear.getScene().getWindow();
        stage.close();
    }

    @FXML
    private void switchToVentanaPrincipal() throws IOException {
        if(crearEmpresa()){
            App.setRoot("ventana_principal");
            Stage stage = (Stage) ButtonCrear.getScene().getWindow();
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
            String nombre = txtNom.getText().trim();
            String nif = txtNIF.getText().trim();
            String calle = txtDir.getText().trim();
            String cp = txtCP.getText().trim();
            String ciudad = txtCiud.getText().trim();
            String telefono = txtTel.getText().trim();
            String email = txtEmail.getText().trim();

            if(comprobarCli()==true){
                Entidad entidad = new Entidad( 0, nombre, nif, calle, cp, ciudad, email, telefono);
                entidadDAO.insertar(entidad);

                Entidad entidadInsertada = entidadDAO.buscarPorNif(nif);

                if (entidadInsertada == null) {
                    mostrarAlerta("Incompleto", "No se pudo recuperar la entidad recién creada.");
                    return false;
                }

                TipoEntidadDAO tipoDAO = new TipoEntidadDAO();
                tipoDAO.insertar(entidadInsertada.getId(), 1);

                System.out.println("Empresa creada correctamente.");
                return true;
            }
            else{
                return false;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }
    
    public boolean comprobarCli(){
        if (txtNombre.getText().isEmpty()) {
            mostrarAlerta("Incompleto", "Debe indicar el nombre de persona.");
            return false;
        }
        else if(txtID2.getText().isEmpty()){
            mostrarAlerta("Incompleto", "Debe indicar el número de personas.");
            return false;
        }
        else if(txtCod.getText().isEmpty()){
            mostrarAlerta("Incompleto", "Debe indicar el codigo de personas.");
            return false;
        }
        else if(txtNIF2.getText().isEmpty()){
            mostrarAlerta("Incompleto", "Debe indicar el NIF de personas.");
            return false;
        }else if(txtEmail2.getText().isEmpty()){
            mostrarAlerta("Incompleto", "Debe indicar el email de personas.");
            return false;
        }
        else if(txtTlf.getText().isEmpty()){
            mostrarAlerta("Incompleto", "Debe indicar el número de telefono.");
            return false;
        }else{
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
