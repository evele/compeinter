package proyectocompiladores;
import java.io.PrintStream;

/**
 * Clase para el manejo de errores lexicos.
 */
public class ErrorLexico extends Exception {
    
    
    /**
     * Error relacionado con un caracter invalido.
     */
    final static String CARACTER_INVALIDO = "Caracter invalido, no pertenece al alfabeto del lenguaje: ";    
    /**
     * Error relacionado con el formato invalido para un comentario.
     */
  
    public static final String COMENTARIO_ABIERTO = "Comentario Abierto pero no Cerrado: ";
    /**
     * Error relacionado con el formato invalido para un comentario.
     */
    final static String COMENTARIO_CERRADO = "Comentario Cerrado pero no Abierto: ";    
    /**
     * Error relacionado con el formato invalido para un numero.
     */
    final static String NUMERO_INVALIDO = "Formato de numero invalido: ";
    /**
     * Error relacionado con el formato invalido para un literal.
     */
    final static String LITERAL_INVALIDO = "Definicion de literal invalido: ";
	
        
    /**
     * Contruye el mensaje de error asociado a un error lexico.
     * @param nroFila numero de linea donde ocurrio el error.
     * @param nroCol numero del caracter dentro de nroFila donde ocurrio el error.
     * @param error indica el msj de error que sucedio.
     * @param o objeto que produjo el error.
     * @return mensaje de error asociado al mismo.
     */
    public static String mensajeError(int nroFila, int nroCol, String error, Object o) {
        String msj = "Error Lexico en: (" + nroFila +  ":"+ nroCol + ")\n\t" + error;
        if (o != null)
            msj= msj + o;
        return(msj);
    }
            
    /**
     * Constructor de un manejador de errores de archivo.
     * @param msj indica el msj de error encontrado.
     */
    public ErrorLexico(String msj) {
        super("\n ******************************************************************************* \n  " + msj 
              +"\n *******************************************************************************\n");
    }
    
}
