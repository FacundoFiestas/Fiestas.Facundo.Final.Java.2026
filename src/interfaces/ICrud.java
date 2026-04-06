package interfaces;

import java.util.List;

public interface ICrud <T>{
    
    void agregar(T entidad);
    void modificar(int index, T entidad);
    void eliminar(int index);
    List<T> listar();
    
    
}
