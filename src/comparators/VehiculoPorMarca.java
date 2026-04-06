package comparators;

import java.util.Comparator;
import models.Vehiculo;

public class VehiculoPorMarca implements Comparator<Vehiculo> {
    // Comparator que permite ordenar vehículos según su marca (alfabéticamente)
    @Override
    public int compare(Vehiculo v1, Vehiculo v2){
        return v1.getMarca().compareToIgnoreCase(v2.getMarca());
    }
    
}
