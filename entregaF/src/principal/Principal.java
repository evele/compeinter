package principal;

import compilador.ErrorSintactico;
import compilador.Compilador;
import java.io.IOException;
import lexico.ErrorLexico;
import xtras.ErrorArchivo;


public class Principal {
    

    public Principal() {
    }
    
    public static void main(String[] args){
    
		Compilador comp=null;
        String fileIn, fileOut = "default.mepa";
        String resultado="\n\n===============================================================================\n"+
        "=                                                                             =\n"+
        "=                              Eveleens - Lozano                              =\n"+
        "=                          COMPILADOR para MINIPASCAL                         =\n"+
        "=                                                                             =\n"+
        "===============================================================================\n";
        													  
		try {
			if (args.length == 2) // controla que hayan ingresado dos parametros
			{
				fileIn = args[0];
				fileOut= args[1];
				comp = new Compilador(fileIn, fileOut);            
				comp.analizar(); //Analiza sintactica y semanticamente
				System.out.println(resultado + "\nEl programa: " + fileIn + " es Sintactica y Semanticamente Correcto." +
								"\n\nEl codigo MEPa generado se encuentra en: "+ fileOut + ".\n\n");			
			}
			else if (args.length == 1) {
				fileIn = args[0];				
				comp = new Compilador(fileIn, fileOut);            
				comp.analizar(); //Analiza sintactica y semanticamente
				System.out.println(resultado + "\nEl programa: " + fileIn + " es Sintactica y Semanticamente Correcto." +
								"\n\nEl codigo MEPa generado se encuentra en: "+ fileOut + ".\n\n");	
			}
			else System.out.println("\n\n" + "\n =============================================================================== \n " +
	        		"Error: Cantidad de argumentos invalida" +	
    		"\n\n =============================================================================== \n\n");
		}	
		catch (Exception ex) {System.out.println(ex.getMessage());    }               
		finally{if(comp!=null)	// escribe el archivo hasta el punto anterior a la excepcion
			try {
				comp.generador.finalizarGen();
				} 
			catch (IOException ex) {    System.out.println(ex.getMessage());   }
		}
   } 
    
}
