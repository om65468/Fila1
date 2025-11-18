package Controlador;

import Vista.App;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Button;

public class PrimaryController implements Initializable{

    @FXML
    private Button button_abrir;

    @FXML
    private Button button_empre;

    @FXML
    private Button button_informacion;

    @FXML
    private Button button_nuevo;
    

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cursorHand();
    }
    
    @FXML
    private void switchToSecondary() throws IOException {
        App.setRoot("secondary");
    }

    
    public void cursorHand(){
        button_abrir.setCursor(Cursor.HAND);
        button_empre.setCursor(Cursor.HAND);
        button_informacion.setCursor(Cursor.HAND);
        button_nuevo.setCursor(Cursor.HAND);
                
    }
}
