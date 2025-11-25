 /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Vista.App;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 *
 * @author stefano (seguro??)
 */
public class VentanaPrincipalController {
    
    @FXML
    private Button ButtonCliente;
    
    
    @FXML
    private void switchToSecondaryCli() throws IOException {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/davinci/gestorfactura/secondary.fxml"));
            Parent root = loader.load();

            SecondaryController secController = loader.getController();

            secController.irATab(secController.getTabCliente());

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
            stage.requestFocus();
            
            Stage stage2 = (Stage) ButtonCliente.getScene().getWindow();
            stage2.close();


        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
