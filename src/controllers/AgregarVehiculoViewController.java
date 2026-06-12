package controllers;

import enums.TipoCombustible;
import exceptions.DatoInvalidoException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.*;
import utils.Validador;

// Controlador de la vista para agregar o editar vehículos
// Maneja la interacción con la interfaz y la creación de objetos de tipo Vehiculo

public class AgregarVehiculoViewController implements Initializable {

    @FXML private ComboBox<String> comboTipo;
    @FXML private ComboBox<TipoCombustible> comboCombustible;

    @FXML private TextField txtMarca;
    @FXML private TextField txtAnio;
    @FXML private TextField txtPrecio;

    @FXML private VBox boxAuto;
    @FXML private VBox boxMoto;
    @FXML private VBox boxCamioneta;

    @FXML private TextField txtPuertas;
    @FXML private CheckBox checkAutomatico;

    @FXML private TextField txtCilindrada;
    @FXML private CheckBox checkABS;

    @FXML private TextField txtCarga;
    @FXML private CheckBox check4x4;
    
    private Vehiculo vehiculoCreado;
    
    // Inicializa combos y eventos de la vista
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        comboTipo.getItems().addAll(
                "Auto",
                "Moto",
                "Camioneta"
        );

        comboCombustible.getItems().addAll(TipoCombustible.values());

        ocultarTodos();

        comboTipo.setOnAction(e -> cambiarFormulario());
    }
    
     // Oculta todos los formularios específicos
    private void ocultarTodos(){
        boxAuto.setVisible(false);
        boxMoto.setVisible(false);
        boxCamioneta.setVisible(false);
    }
    
     // Muestra el formulario según el tipo seleccionado
    private void cambiarFormulario(){

        ocultarTodos();

        String tipo = comboTipo.getValue();

        if(tipo.equals("Auto")){
            boxAuto.setVisible(true);
        }

        if(tipo.equals("Moto")){
            boxMoto.setVisible(true);
        }

        if(tipo.equals("Camioneta")){
            boxCamioneta.setVisible(true);
        }
    }

    // Valida datos y crea el vehículo
    @FXML
private void crearVehiculo(){

    try {
        // 🔹 MARCA
        String marca = txtMarca.getText();
        Validador.validarTextoNoVacio(marca, "marca");

        // 🔹 NUMÉRICOS
        int anio = Validador.validarEntero(txtAnio.getText(), "año");
        int precio = Validador.validarEntero(txtPrecio.getText(), "precio");

        // 🔹 COMBOS
        if (comboTipo.getValue() == null) {
            throw new DatoInvalidoException("Debe seleccionar un tipo de vehículo");
        }

        if (comboCombustible.getValue() == null) {
            throw new DatoInvalidoException("Debe seleccionar un combustible");
        }

        String tipo = comboTipo.getValue();
        TipoCombustible combustible = comboCombustible.getValue();
        
        // Creación según tipo
        switch(tipo){

            case "Auto":

                int puertas = Validador.validarEntero(txtPuertas.getText(), "puertas");
                boolean automatico = checkAutomatico.isSelected();

                vehiculoCreado = new Auto(
                        puertas, automatico, marca, anio, precio, combustible
                );
                break;

            case "Moto":

                int cilindrada = Validador.validarEntero(txtCilindrada.getText(), "cilindrada");
                boolean abs = checkABS.isSelected();

                vehiculoCreado = new Moto(
                        cilindrada, abs, marca, anio, precio, combustible
                );
                break;

            case "Camioneta":

                int carga = Validador.validarEntero(txtCarga.getText(), "capacidad de carga");
                boolean es4x4 = check4x4.isSelected();

                vehiculoCreado = new Camioneta(
                        carga, es4x4, marca, anio, precio, combustible
                );
                break;
        }

        // cerrar ventana si todo salió bien
        Stage stage = (Stage) comboTipo.getScene().getWindow();
        stage.close();

    } catch (DatoInvalidoException e) {

        mostrarError(e.getMessage());
    }
}
    
//devuelve el vehiculo creado 
    public Vehiculo getVehiculoCreado(){
        return vehiculoCreado;
    }
    
//carga el vehiculo seleccionado para su edicion.
    public void cargarVehiculo(Vehiculo v){

    comboTipo.setDisable(true);
    //datos en comun
    txtMarca.setText(v.getMarca());
    txtAnio.setText(String.valueOf(v.getAnio()));
    txtPrecio.setText(String.format("%.0f", v.getPrecio()));
    comboCombustible.setValue(v.getTipoCombustible());
    
    //datos especificos
    if(v instanceof Auto){

        comboTipo.setValue("Auto");
        cambiarFormulario();

        Auto a = (Auto) v;

        txtPuertas.setText(String.valueOf(a.getCantidadPuertas()));
        checkAutomatico.setSelected(a.isEsAutomatico());

    }

    if(v instanceof Moto){

        comboTipo.setValue("Moto");
        cambiarFormulario();

        Moto m = (Moto) v;

        txtCilindrada.setText(String.valueOf(m.getCilindrada()));
        checkABS.setSelected(m.isTieneFrenosABS());

    }

    if(v instanceof Camioneta){

        comboTipo.setValue("Camioneta");
        cambiarFormulario();

        Camioneta c = (Camioneta) v;

        txtCarga.setText(String.valueOf(c.getCapacidadCarga()));
        check4x4.setSelected(c.isEs4x4());

    }

}
//Muestra mensaje de error.
    private void mostrarError(String mensaje){
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Error de validación");
    alert.setHeaderText(null);
    alert.setContentText(mensaje);
    alert.showAndWait();
}

}