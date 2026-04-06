
package models;

import java.util.Iterator;
import java.util.List;
// Iterador personalizado para recorrer la lista de vehículos
public class VehiculoIterator implements Iterator<Vehiculo> {
          private List<Vehiculo> lista;
          private int posicion = 0;
          // Inicializa el iterador con la lista
          public VehiculoIterator (List<Vehiculo> lista){
              this.lista = lista;
          }
          // Verifica si hay más elementos
          @Override
          public boolean hasNext(){
              return posicion < lista.size();
          }
           // Devuelve el siguiente vehículo
          @Override
          public Vehiculo next(){
              return lista.get(posicion++);
          }
    
}
