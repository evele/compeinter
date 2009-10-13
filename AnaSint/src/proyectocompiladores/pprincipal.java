package proyectocompiladores;

import java.io.File;
import java.io.FileWriter;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

public class pprincipal {
	
	public static void main(String[] args) {
		 AnaLex analizadorLexico;
		 Anasint analizadorSintactico;
	     String archivoDeEntrada;
	     String resultado = "\n\n=============================================\n"+
        "=                                           =\n"+
        "=            Eveleens - Lozano              =\n"+
        "=   ANALIZADOR SINTACTICO para MINIPASCAL   =\n"+
        "=                                           =\n"+
        "=============================================\n";
      
		      
       try {
           archivoDeEntrada = args[0];
           try {
        	   System.out.println(resultado);
        	   analizadorLexico = new AnaLex(archivoDeEntrada);
        	   analizadorSintactico = new Anasint(analizadorLexico);
        	   analizadorSintactico.analizarSintaxis();
        	   System.out.println("\n =============================================================================== \n" + 
        			   "  El programa " + archivoDeEntrada + " \n  es SINTACTICAMENTE CORRECTO.\n" + 
        			   "\n =============================================================================== \n  ");
               
           } catch (ErrorArchivo ex) {System.out.println(ex.getMessage());}
           	 catch (ErrorLexico ex) {System.out.println(ex.getMessage());}
           	 catch (ErrorSintactico ex) {System.out.println(ex.getMessage());}                   
       } catch (ArrayIndexOutOfBoundsException a1){
       	System.out.println("\n =================================================== \n\n" 
       			+ "  Debe ingresar el nombre de un archivo de entrada."
                + "\n\n =================================================== \n");
       	}
       }
}
