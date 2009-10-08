package proyectocompiladores;

import java.io.File;
import java.io.FileWriter;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;

public class pprincipal {
	
	public static void main(String[] args) {
		 AnaLex analizadorLexico;
		 Anasint analizadorSintactico;
	     Token token;
	     String archivoDeEntrada;
	     String archivoDeSalida = "";
	     String resultado = "\n=============================================\n"+
        "=                                           =\n"+
        "=            Eveleens - Lozano              =\n"+
        "=   ANALIZADOR SINTACTICO para MINIPASCAL   =\n"+
        "=                                           =\n"+
        "=============================================\n\n"+
        "                                              \n";;
	     int cont = 1;
	     boolean terminar = false;
	      
       try {
           archivoDeEntrada = args[0];
           try {
        	   System.out.println(resultado);
        	   resultado = "";
        	   analizadorLexico = new AnaLex(archivoDeEntrada);
        	   analizadorSintactico = new Anasint(analizadorLexico);
        	   analizadorSintactico.analizarSintaxis();
        	   System.out.println(resultado + "\nEl programa: " + archivoDeEntrada + " es SINTACTICAMENTE CORRECTO.");
       	       //analizadorSintactico.warning();
               
           } catch (ErrorArchivo ex) {System.out.println(ex.getMessage());}
           	 catch (ErrorLexico ex) {System.out.println(ex.getMessage());}
           	 catch (ErrorSintactico ex) {System.out.println(ex.getMessage());}
           //catch (Exception e){System.out.println("mira vos...");}
           /* comentamos esto porque no lo usamos en esta entrega pero puede servir pa la siguiente segun dijeron
           if(args.length>1){//mostrar la salida en el archivo de salida.
               	archivoDeSalida=args[1];
               	guardarDatos(archivoDeSalida,resultado);
           }
           else{//mostrar la salida en la pantalla.
               	System.out.println(resultado);
           } 
           */                       
       } catch (ArrayIndexOutOfBoundsException a1){
       	System.out.println("Debe ingresar el nombre de un archivo de entrada.");
       	}
       }

	private static void guardarDatos(String archivoDeSalida, String resultado) {
		ObjectOutputStream o;

		java.io.PrintStream ps;
		  File f;
	      try {	    	 
	    	  	String fileName = archivoDeSalida;
	            FileWriter fw;
	            f = new File(archivoDeSalida);
	            f.delete();
	            fw = new FileWriter(fileName, true);
	            fw.flush();
	           
	            PrintWriter pw = new PrintWriter(fw);
	            pw.flush();
	            pw.println(resultado);
	            pw.close();
	            fw.close();
	      } catch (Exception e) {
	          System.out.println(e.getMessage());
	      }
		
	}
}
