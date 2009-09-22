package proyectocompiladores;

import java.io.PrintStream;

/**
 * Clase para el manejo de Errores de Archivo.
 */
public class ErrorArchivo extends Exception {
    
    /**
     * Error relacionado a una ruta invalida.
     */
    final static String RUTA_INV = "La ruta del arhivo especificado es invalida.";
    /**
     * Error relacionado al acceder al archivo.
     */
    final static String ERROR_IO = "Error de E/S al leer del archivo fuente.";
    
     
    /**
     * Constructor de un manejador de errores de archivo.
     * @param tipo indica el tipo de error encontrado.
     */
    public ErrorArchivo(String tipo) {
        super("\n *************************************************** \n  " + tipo
              + "\n *************************************************** \n");
    }
    
}
