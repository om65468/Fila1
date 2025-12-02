package Controlador;

import Modelo.Entidad;
import Vista.App;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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

public class PrimaryController implements Initializable{

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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cursorHand();
        datosTablas();
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
    

    public void cursorHand(){
        button_abrir.setCursor(Cursor.HAND);
        button_empre.setCursor(Cursor.HAND);
        button_informacion.setCursor(Cursor.HAND);
        button_nuevo.setCursor(Cursor.HAND);
                
    }
    
    public void datosTablas() {
    TColumn_Nombre.setCellValueFactory(new PropertyValueFactory<>("nombre"));
    TColumn_NIF.setCellValueFactory(new PropertyValueFactory<>("nif"));
    TColumn_Direccion.setCellValueFactory(new PropertyValueFactory<>("calle"));
    TColumn_Telefono.setCellValueFactory(new PropertyValueFactory<>("telefono"));
    TColumn_email.setCellValueFactory(new PropertyValueFactory<>("email"));
    }
}
