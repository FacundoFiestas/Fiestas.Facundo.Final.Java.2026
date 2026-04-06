package models;

import Persistencia.VehiculoCsvDAO;
import Persistencia.VehiculoJsonDAO;
import Persistencia.VehiculoTxtExporter;
import comparators.VehiculoPorMarca;
import comparators.VehiculoPorAnio;
import interfaces.ICrud;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
// Clase encargada de gestionar la lista de vehículos (CRUD, ordenamientos y persistencia)
public class Gestion implements ICrud <Vehiculo> {
    
        public List<Vehiculo> vehiculos;
        private boolean ofertaAplicada = false;
        private VehiculoJsonDAO jsonDAO = new VehiculoJsonDAO();
        private VehiculoCsvDAO csvDAO = new VehiculoCsvDAO();
        private VehiculoTxtExporter txtExporter = new VehiculoTxtExporter();
        
        public Gestion(){
            vehiculos = new ArrayList<>();
            
        }
        
        @Override
        public void agregar(Vehiculo vehiculo){
            vehiculos.add(vehiculo);
        }
        
        @Override
        public void modificar(int index, Vehiculo vehiculo){
            vehiculos.set(index, vehiculo);
        }
        
        @Override
        public void eliminar(int index){
            vehiculos.remove(index);
        }
        
        @Override 
        public List<Vehiculo> listar(){
            return new ArrayList<>(vehiculos);
        }
        // Devuelve un iterador personalizado
        public VehiculoIterator obtenerIterator(){
            return new VehiculoIterator(vehiculos);
        }
        
        public void ordenarPorPrecio(){
            Collections.sort(vehiculos); //comparable
        }
        
        public void ordenarPorAnio(){
            Collections.sort(vehiculos, new VehiculoPorAnio());
        }
        
        public void ordenarPorMarca(){
            Collections.sort(vehiculos, new VehiculoPorMarca());
            
            
        }
    // Aplica un 10% de descuento a todos los vehículos (una sola vez    
   public boolean aplicarOferta() {

    if (ofertaAplicada) {
        return false;
    }

    // Uso de wildcard (? super Vehiculo)
    List<? super Vehiculo> lista = vehiculos;

     // Recorre y modifica precios
    lista.forEach(v -> ((Vehiculo) v).setPrecio(((Vehiculo) v).getPrecio() * 0.9));

    ofertaAplicada = true;

    return true;
}
//JSON
        public void guardarJson(){
            jsonDAO.guardar(vehiculos, "vehiculos.json");
        }
        
        public boolean recuperarJson(){

    java.io.File archivo = new java.io.File("vehiculos.json");

    if(!archivo.exists()){
        return false;
    }

    List<Vehiculo> recuperados = jsonDAO.recuperar("vehiculos.json");

    vehiculos.clear();
    vehiculos.addAll(recuperados);

    // resetear oferta
    ofertaAplicada = false;

    return true;
}
//CSV        
   public void guardarCsv(){

    csvDAO.guardar(vehiculos, "vehiculos.csv");

}     
        public boolean recuperarCsv(){

    java.io.File archivo = new java.io.File("vehiculos.csv");

    if(!archivo.exists()){
        return false;
    }

    List<Vehiculo> recuperados = csvDAO.recuperar("vehiculos.csv");

    vehiculos.clear();
    vehiculos.addAll(recuperados);

    // 🔥 importante
    ofertaAplicada = false;

    return true;
}
//TXT
    public void exportarAutos(){

    List<Vehiculo> autos = new ArrayList<>();

    for(Vehiculo v : vehiculos){
        if(v instanceof Auto){
            autos.add(v);
        }
    }

    txtExporter.exportar(autos, "autos.txt", "LISTADO DE AUTOS");
}

 public void exportarMotos(){

    List<Vehiculo> motos = new ArrayList<>();

    for(Vehiculo v : vehiculos){
        if(v instanceof Moto){
            motos.add(v);
        }
    }

    txtExporter.exportar(motos, "motos.txt", "LISTADO DE MOTOS");
}
 
 public void exportarCamionetas(){

    List<Vehiculo> camionetas = new ArrayList<>();

    for(Vehiculo v : vehiculos){
        if(v instanceof Camioneta){
            camionetas.add(v);
        }
    }

    txtExporter.exportar(camionetas, "camionetas.txt", "LISTADO DE CAMIONETAS");
}
}

