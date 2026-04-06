package models;
import enums.TipoCombustible;
import interfaces.Financiable;
public class Camioneta extends Vehiculo implements Financiable {
    
    private int capacidadCarga;
    private boolean es4x4;
    
      public Camioneta(int capacidadCarga, boolean es4x4, String marca, int anio, double precio, TipoCombustible tipoCombustible) {
        super(marca, anio, precio, tipoCombustible);
        this.capacidadCarga = capacidadCarga;
        this.es4x4 = es4x4;
    }
      
      public Camioneta(String marca, int anio, double precio,
                int capacidadCarga, boolean es4x4) {
        super(marca, anio, precio);
        this.capacidadCarga = capacidadCarga;
        this.es4x4 = es4x4;
    }
      

      public Camioneta(String marca, double precio) {
        super(marca, precio);
        this.capacidadCarga = 500;
        this.es4x4= false;
    }

    public int getCapacidadCarga() {
        return capacidadCarga;
    }

    public boolean isEs4x4() {
        return es4x4;
    }
      //calcula el costo del seguro (2% del precio)
   @Override
    public double calcularCostoSeguro(){
        return precio * 0.03;
    }
      //representacion en texto de Camioneta
    @Override
    public String toString() {
    return super.toString() +
           ", capacidad de carga =" + capacidadCarga +
           ", es 4x4? =" + es4x4;
}
      //calcula cuota mensual con interes  
    @Override
    public double calcularCuotaMensual(int cantidadMeses) {
    double interes = 0.15; // más interés que un auto
    double precioFinal = getPrecio() * (1 + interes);
    return precioFinal / cantidadMeses;
}  
    
}
