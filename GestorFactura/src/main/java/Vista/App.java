package Vista;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

    private static Scene scene;
    
    private static Object controller;

    @Override
    public void start(Stage stage) throws IOException {
        scene = new Scene(loadFXML("primary"), 992, 705);
        stage.setScene(scene);
        stage.sizeToScene();
        stage.setMinWidth(scene.getWidth()+16);
        stage.setMinHeight(scene.getHeight()+39);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        FXMLLoader loader = new FXMLLoader(App.class.getResource("/davinci/gestorfactura/ventana_principal.fxml"));
        Parent root = loader.load();
        scene.setRoot(root);
    }



    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/davinci/gestorfactura/" + fxml + ".fxml"));
        Parent root = fxmlLoader.load();
    controller = fxmlLoader.getController();
    return root;
    }
    
    public static Object getController() {
        return controller;
    }

    public static void main(String[] args) {
        launch(args);
    }

}