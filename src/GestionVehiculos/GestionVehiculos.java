package GestionVehiculos;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class GestionVehiculos  extends Application{

       @Override
    public void start(Stage stage) throws Exception {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/views/MainView.fxml")
        );

        Scene scene = new Scene(loader.load());
        stage.setTitle("Sistema de Vehículos");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
    
    

