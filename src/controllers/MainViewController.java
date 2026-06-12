package controllers;

import exceptions.DatoInvalidoException;
import exceptions.VehiculoNoFinanciableException;
import interfaces.Financiable;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextInputDialog;
import javafx.stage.Stage;
import models.Gestion;
import models.Vehiculo;
import models.VehiculoIterator;
import utils.Validador;
// Controlador principal de la aplicación
// Maneja la lista de vehículos y las acciones del usuario
public class MainViewController implements Initializable {
    
      @FXML
    private ListView<Vehiculo> listaVehiculos;
    private boolean mostrandoFinanciables = false;
    @FXML
    private Button btnFinanciables;
    private Gestion gestion = new Gestion();
    
     // Carga inicial de la lista
    @Override
    public void initialize(URL url, ResourceBundle rb) {
         actualizarLista();
    }    
    
    private void actualizarLista() {

    listaVehiculos.getItems().clear();

    VehiculoIterator iterator = gestion.obtenerIterator();

    while (iterator.hasNext()) {
        listaVehiculos.getItems().add(iterator.next());
    }
}
    // Abre la ventana para agregar un vehículo
@FXML
private void abrirVentanaAgregar() {

    try {

        FXMLLoader loader = new FXMLLoader(
                getClass().getResource("/views/AgregarVehiculoView.fxml")
        );

        Parent root = loader.load();

        AgregarVehiculoViewController controller = loader.getController();

        Stage stage = new Stage();
        stage.setTitle("Agregar Vehiculo");
        stage.setScene(new Scene(root));

        stage.showAndWait();

        Vehiculo vehiculo = controller.getVehiculoCreado();
         // Si se creó correctamente, se agrega a la lista
        if (vehiculo != null) {
            gestion.agregar(vehiculo);
             actualizarLista();
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
}

 // Elimina el vehículo seleccionado
@FXML
private void eliminarVehiculo() {

    int index = listaVehiculos.getSelectionModel().getSelectedIndex();

    if (index >= 0) {

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmar eliminación");
        alert.setHeaderText("Eliminar vehículo");
        alert.setContentText("¿Seguro que querés eliminar este vehículo?");

        Optional<ButtonType> resultado = alert.showAndWait();

        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {

            gestion.eliminar(index);

             actualizarLista();

        }
    }

}
// Modifica el vehículo seleccionado
@FXML
private void modificarVehiculo() {

    int index = listaVehiculos.getSelectionModel().getSelectedIndex();

    if (index >= 0) {

        Vehiculo vehiculoSeleccionado = listaVehiculos.getSelectionModel().getSelectedItem();

        try {

            FXMLLoader loader = new FXMLLoader(
                    getClass().getResource("/views/AgregarVehiculoView.fxml")
            );

            Parent root = loader.load();

            AgregarVehiculoViewController controller = loader.getController();

            // cargar los datos del vehiculo en el formulario
            controller.cargarVehiculo(vehiculoSeleccionado);

            Stage stage = new Stage();
            stage.setTitle("Modificar Vehiculo");
            stage.setScene(new Scene(root));

            stage.showAndWait();

            Vehiculo vehiculoModificado = controller.getVehiculoCreado();
            
             // Si se modificó, se actualiza
            if (vehiculoModificado != null) {

                gestion.modificar(index, vehiculoModificado);

                 actualizarLista();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    

}

@FXML
private void ordenarPorPrecio(){

    gestion.ordenarPorPrecio();

     actualizarLista();

}

@FXML
private void ordenarPorAnio(){

    gestion.ordenarPorAnio();

     actualizarLista();

}

@FXML
private void ordenarPorMarca(){

    gestion.ordenarPorMarca();

     actualizarLista();

}
// Aplica descuento a todos los vehículos
@FXML
private void aplicarOferta(){

    boolean aplicada = gestion.aplicarOferta();

    if(!aplicada){

        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Oferta ya aplicada");
        alert.setHeaderText(null);
        alert.setContentText("Los vehículos ya se encuentran en oferta.");
        alert.showAndWait();

        return;
    }

     actualizarLista();

}

@FXML
private void guardarJson(){
    gestion.guardarJson();
}

@FXML
private void recuperarJson(){

    boolean ok = gestion.recuperarJson();

    if(!ok){

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("No se ha creado aun un archivo compatible para cargar.");
        alert.showAndWait();

        return;
    }

     actualizarLista();
}

@FXML
private void guardarCsv(){
    gestion.guardarCsv();
}

@FXML
private void recuperarCsv(){

    boolean ok = gestion.recuperarCsv();

    if(!ok){

        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText("No se ha creado aun un archivo compatible para cargar.");
        alert.showAndWait();

        return;
    }

     actualizarLista();
}

@FXML
private void exportarAutos(){
    gestion.exportarAutos();
}

@FXML
private void exportarMotos(){
    gestion.exportarMotos();
}

@FXML
private void exportarCamionetas(){
    gestion.exportarCamionetas();
}

 // Calcula el costo del seguro del vehículo seleccionado
@FXML
private void calcularSeguro(){

    Vehiculo seleccionado = listaVehiculos.getSelectionModel().getSelectedItem();

    if(seleccionado == null){
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Atención");
        alert.setHeaderText(null);
        alert.setContentText("Seleccione un vehículo.");
        alert.showAndWait();
        return;
    }

    double seguro = seleccionado.calcularCostoSeguro();

    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    alert.setTitle("Costo de seguro");
    alert.setHeaderText(null);
    alert.setContentText("El costo del seguro es: $" + seguro);
    alert.showAndWait();
}

// Calcula la cuota mensual si el vehículo es financiable
@FXML
private void calcularCuota(){

    try {
        Vehiculo seleccionado = listaVehiculos.getSelectionModel().getSelectedItem();

        if(seleccionado == null){
            mostrarError("Seleccione un vehículo.");
            return;
        }

        if(!(seleccionado instanceof Financiable)){
            throw new VehiculoNoFinanciableException("Este vehículo no es financiable");
        }

        Financiable financiable = (Financiable) seleccionado;

        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Financiación");
        dialog.setHeaderText("Ingrese la cantidad de meses");
        dialog.setContentText("Meses:");

        Optional<String> resultado = dialog.showAndWait();

        if(resultado.isEmpty()){
            return; 
        }

        int meses = Validador.validarEntero(resultado.get(), "cantidad de meses");

        double cuota = financiable.calcularCuotaMensual(meses);

        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Resultado");
        alert.setHeaderText(null);
        alert.setContentText("Cuota mensual: $" + cuota);
        alert.showAndWait();

    } catch (DatoInvalidoException | VehiculoNoFinanciableException e){

        mostrarError(e.getMessage());
    }
}
//Muestra mensaje de error
private void mostrarError(String mensaje){
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error");
    alert.setHeaderText(null);
    alert.setContentText(mensaje);
    alert.showAndWait();
}
@FXML
private void filtrarFinanciables() {

    if (!mostrandoFinanciables) {

        List<Vehiculo> financiables =
                gestion.obtenerFinanciables(gestion.listar());

        listaVehiculos.setItems(
                FXCollections.observableArrayList(financiables));

        btnFinanciables.setText("Mostrar todos");

        mostrandoFinanciables = true;

    } else {

         actualizarLista();

        btnFinanciables.setText("Mostrar financiables");

        mostrandoFinanciables = false;
    }
}
}