package comparators;

import java.util.Comparator;
import models.Vehiculo;


public class VehiculoPorAnio implements Comparator<Vehiculo> {
    // Comparator que permite ordenar vehículos según su año de fabricación
    @Override
    public int compare(Vehiculo v1, Vehiculo v2){
        return Integer.compare(v1.getAnio(), v2.getAnio());
    }
    
}
