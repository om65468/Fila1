package Controlador;

import Modelo.ConexionBBDD;
import Modelo.Entidad;
import Modelo.EntidadDAO;
import Vista.App;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class PrimaryController implements Initializable {

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

    private EntidadDAO entidadDAO;
    private ObservableList<Entidad> listaEmpresas;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cursorHand();
        entidadDAO = new EntidadDAO();
        listaEmpresas = FXCollections.observableArrayList(entidadDAO.obtenerEmpresas());
        datosTablas();
        TView_Empresa.setItems(listaEmpresas);
    }

    @FXML
    private void onCrearEmpresa(ActionEvent event) throws IOException {
        App.setRoot("secondary");
    }

    @FXML
    void onPaneAbrirEmpresa(ActionEvent event) {
        pane_info.setVisible(false);
        pane_nuevo.setVisible(false);
        pane_abrir.setVisible(true);
    }

    @FXML
    void onPaneInformacion(ActionEvent event) {
        pane_info.setVisible(true);
        pane_nuevo.setVisible(false);
        pane_abrir.setVisible(false);
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
}
