package utils;

import exceptions.DatoInvalidoException;
// Clase utilitaria para validar datos ingresados por el usuario
public class Validador {
    // Valida que un texto no esté vacío
    public static void validarTextoNoVacio(String valor, String nombreCampo) 
            throws DatoInvalidoException {

        if (valor == null || valor.trim().isEmpty()) {
            throw new DatoInvalidoException("El campo " + nombreCampo + " no puede estar vacío");
        }
    }

   // Valida que el valor sea un entero positivo
    public static int validarEntero(String valor, String nombreCampo) 
        throws DatoInvalidoException {

    validarTextoNoVacio(valor, nombreCampo);

    try {
        int numero = Integer.parseInt(valor);

        if (numero <= 0) {
            throw new DatoInvalidoException(
                "El campo " + nombreCampo + " debe ser un número positivo"
            );
        }

        return numero;

    } catch (NumberFormatException e) {
        throw new DatoInvalidoException(
            "El campo " + nombreCampo + " debe ser un número entero"
        );
    }
}
}

