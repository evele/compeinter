package lexico;
import java.io.PrintStream;

// Clase para el manejo de errores lexicos.
public class ErrorLexico extends Exception {
    
    
    
     // Error relacionado con un caracter invalido.
     
    final static String CARACTER_INVALIDO = "Caracter invalido, no pertenece al alfabeto del lenguaje: ";    
    
     //  Error relacionado con el formato invalido para un comentario.
     
    public static final String COMENTARIO_ABIERTO = "Comentario Abierto pero no Cerrado: ";
    
     // Error relacionado con el formato invalido para un comentario con una "}".
     
    final static String COMENTARIO_CERRADO1 = "Comentario Cerrado pero no Abierto: ";    
    
     // Error relacionado con el formato invalido para un comentario con un "*)".
    
    final static String COMENTARIO_CERRADO2 = "Comentario Cerrado pero no Abierto: *";
    
     // Error relacionado con el formato invalido para un numero.
     
    final static String NUMERO_INVALIDO = "Formato de numero invalido: ";
 	
        
    // Contruye el mensaje de error asociado a un error lexico. 
    public static String mensajeError(int nroFila, int nroCol, String error, Object o) {
        String msj;
    	
        if (!(o.getClass().isInstance(String.class))) {
        	o = String.valueOf(o);        	
        }
    	if (((String) o).startsWith("{")) {
    		msj = "Error Lexico en: (fila: " + nroFila +  " columna: "+ nroCol + ")\n\t" + error + " {";
    	}
    	else if (((String) o).startsWith("(*")) {
    		msj = "Error Lexico en: (fila: " + nroFila +  " columna: "+ nroCol + ")\n\t" + error + " (*";
    	}    		
    	else {
    		msj = "Error Lexico en: (fila: " + nroFila +  " columna: "+ nroCol + ")\n\t" + error;
    		if (o != null)
            msj= msj + o;
    	}
        return(msj);
    }
            
    // Constructor de un manejador de errores de archivo. 
    public ErrorLexico(String msj) {
        super("\n =============================================================================== \n  " + msj 
              +"\n\n ===============================================================================\n");
    }
    
}
