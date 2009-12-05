package xtras;

import java.io.PrintStream;

// Clase para el manejo de Errores de Archivo.
public class ErrorArchivo extends Exception {
    
 
    // Error producido al no encontrar el archivo
   
    final static String RUTA_INVALIDA = "La ruta o el arhivo especificado son invalidos.";
        
    // Error producido al no poder acceder al archivo.
    
    final static String ERROR_ENTRADA_SALIDA = "Error de E/S al leer del archivo fuente.";
    
     
    
    // Constructor de un manejador de errores de archivo
    
    public ErrorArchivo(String tipo) {
        super("\n ================================================= \n\n  " + tipo
              + "\n\n ================================================= \n");
    }

    
}
