package proyectocompiladores;
import java.io.PrintStream;

public class ErrorSintactico extends Exception {
	
	// Constructor de un manejador de errores sintacticos. 
	public ErrorSintactico(String msj) {
        super("\n =============================================================================== \n  " + msj 
              +"\n\n ===============================================================================\n");
        //setMsjerror(msj);
        
    }
	
	// Contruye el mensaje de error asociado a un error sintactico. 
	public static String mensajeError(int nroFila, int nroCol, String esperado, String encontrado) {
        String msj = "Error Sintactico en (fila: " + nroFila +  " columna: "+ nroCol + ")\n\t" + 
		" se esperaba: " + esperado + " y se encontro: " + encontrado;
        
        return(msj);
    }   
}
