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
import javafx.scene.control.Button;
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
        /* System.out.println("Empresa = " + tabEmpresa);
        System.out.println("Cliente = " + tabCliente);
        try {
        conn = ConexionBBDD.get();
        entidadDAO = new EntidadDAO(conn);
        } catch (SQLException e) {
        e.printStackTrace();
        } catch(Exception e){
        e.printStackTrace();
        }*/
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

            if (nombre.isEmpty() || nif.isEmpty()) {
                System.out.println("ERROR: Nombre y NIF son obligatorios.");
                return false;
            }

            Entidad entidad = new Entidad( 0, nombre, nif, calle, cp, ciudad, email, telefono);

            entidadDAO.insertar(entidad);

            Entidad entidadInsertada = entidadDAO.buscarPorNif(nif);

            if (entidadInsertada == null) {
                System.out.println("ERROR: No se pudo recuperar la entidad recién creada.");
                return false;
            }
            
            TipoEntidadDAO tipoDAO = new TipoEntidadDAO();
            tipoDAO.insertar(entidadInsertada.getId(), 1);

            System.out.println("Empresa creada correctamente.");
            return true;
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
    }

}
