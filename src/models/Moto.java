 package models;
import enums.TipoCombustible;
public class Moto extends Vehiculo  {
    
    private int cilindrada;
    private boolean tieneFrenosABS;
    
     public Moto(int cilindrada, boolean tieneFrenosABS, String marca, int anio, double precio, TipoCombustible tipoCombustible) {
        super(marca, anio, precio, tipoCombustible);
        this.cilindrada = cilindrada;
        this.tieneFrenosABS = tieneFrenosABS;
    }
    
     public Moto(String marca, int anio, double precio,
                int cilindrada, boolean tieneFrenosABS) {
        super(marca, anio, precio);
        this.cilindrada = cilindrada;
        this.tieneFrenosABS = tieneFrenosABS;
    }
     
     public Moto(String marca, double precio) {
        super(marca, precio);
        this.cilindrada = 150;
        this.tieneFrenosABS = false;
    }

    public int getCilindrada() {
        return cilindrada;
    }

    public boolean isTieneFrenosABS() {
        return tieneFrenosABS;
    }
    // Calcula el costo del seguro (1% del precio)
    @Override
    public double calcularCostoSeguro(){
        return precio * 0.01;
    }
    //Representacion en texto de Moto
    @Override
    public String toString() {
    return super.toString() +
           ", cilindrada=" + cilindrada +
           ", tiene frenos abs? =" + tieneFrenosABS;
}
     
}
