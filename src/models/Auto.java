package models;

import enums.TipoCombustible;
import interfaces.Financiable;

public class Auto extends Vehiculo implements Financiable {
    
    private int cantidadPuertas;
    private boolean esAutomatico;

    public Auto(int cantidadPuertas, boolean esAutomatico, String marca, int anio, double precio, TipoCombustible tipoCombustible) {
        super(marca, anio, precio, tipoCombustible);
        this.cantidadPuertas = cantidadPuertas;
        this.esAutomatico = esAutomatico;
    }
    
    public Auto(String marca, int anio, double precio,
                int cantidadPuertas, boolean esAutomatico) {
        super(marca, anio, precio);
        this.cantidadPuertas = cantidadPuertas;
        this.esAutomatico = esAutomatico;
    }
    
    public Auto(String marca, double precio) {
        super(marca, precio);
        this.cantidadPuertas = 4;
        this.esAutomatico = false;
    }

    public int getCantidadPuertas() {
        return cantidadPuertas;
    }

    public boolean isEsAutomatico() {
        return esAutomatico;
    }
     // Calcula el costo del seguro (2% del precio)
    @Override
    public double calcularCostoSeguro(){
        return precio * 0.02;
    }
     // Representación en texto del auto
    @Override
    public String toString() {
    return super.toString() +
           ", puertas=" + cantidadPuertas +
           ", automatico=" + esAutomatico;
}
    // Calcula la cuota mensual con interés
  @Override
    public double calcularCuotaMensual(int cantidadMeses) {
    double interes = 0.10; // 10% interés
    double precioFinal = getPrecio() * (1 + interes);
    return precioFinal / cantidadMeses;
}
    
}
