package Controlador;

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
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cursorHand();
    }
    
    @FXML
    private void onCrearEmpresa(ActionEvent event) throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/davinci/gestorfactura/secondary.fxml"));
            Parent root = loader.load();


            SecondaryController secController = loader.getController();

            secController.irATab(secController.getTabEmpresa());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            stage.requestFocus();


        } catch (Exception e) {
            e.printStackTrace();
        }
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
}
