package models;
import enums.TipoCombustible; 
import java.io.Serializable;

public abstract class Vehiculo
        implements Comparable<Vehiculo>, Serializable{
    protected String marca;
    protected int anio;
    protected double precio;
    protected TipoCombustible tipoCombustible;

    public Vehiculo(String marca, int anio, double precio, TipoCombustible tipoCombustible) {
        this.marca = marca;
        this.anio = anio;
        this.precio = precio;
        this.tipoCombustible = tipoCombustible;
    }
    
    
    public Vehiculo(String marca, int anio,  double precio){
        this(marca, anio, precio, TipoCombustible.NAFTA);
    }
    
   public Vehiculo(String marca, double precio){
       this.marca = marca;
       this.precio = precio;
       this.anio = 0;
       this.tipoCombustible = TipoCombustible.NAFTA;
   }

    public String getMarca() {
        return marca;
    }

    public int getAnio() {
        return anio;
    }

    public double getPrecio() {
        return precio;
    }

    public TipoCombustible getTipoCombustible() {
        return tipoCombustible;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
    
    public abstract double calcularCostoSeguro();
    
     // Compara vehículos por precio(Comparable)
      @Override
    public int compareTo(Vehiculo otro) {
        return Double.compare(this.precio, otro.precio);
    }
    //Representacion en texto de vehiculo
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName() + " ");
        sb.append("marca=").append(marca);
        sb.append(", anio=").append(anio);
        sb.append("Precio: $" + String.format("%,.2f", precio));
        sb.append(", tipoCombustible=").append(tipoCombustible);
        sb.append('}');
        return sb.toString();
    }   
   
}
