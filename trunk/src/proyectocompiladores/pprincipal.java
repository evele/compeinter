package proyectocompiladores;

import java.io.*;


public class pprincipal {
	      
	public static void main(String[] args) {
		 AnaLex analizadorLexico;
	     Token token;
	     String archivoDeEntrada;
	     String archivoDeSalida = "";
	     String resultado = "\n=============================================\n"+
         "=                                           =\n"+
         "=            Eveleens - Lozano              =\n"+
         "=     ANALIZADOR LEXICO para MINIPASCAL     =\n"+
         "=                                           =\n"+
         "=============================================\n\n"+
         "                                              \n";;
	     int cont = 1;
	     boolean terminar = false;
	      
        try {
            archivoDeEntrada = args[0];
            try {
                analizadorLexico = new AnaLex(archivoDeEntrada);
                while(!terminar) {
                    try {
                        token = analizadorLexico.getToken();
                        resultado = resultado.concat("Token:\t\t\t"+token.getNombre()+"\nLexema:\t\t\t"+token.getLexema()+"\nLinea:\t\t\t"+Integer.toString(token.getNumeroLinea())+"\nDescripcion:\t"+token.getDescripcion()+"\n");
                        resultado = resultado.concat("=============================================\n");
                        terminar = (token.EOF == token.getNumeroToken());
                    	}  	   catch (ErrorLexico ex) {
                    		ex.printStackTrace();
                    		terminar = true;
                    		}  catch (ErrorArchivo ex) {
                    		ex.printStackTrace();
                    		terminar = true;
                    		}       
                }
            } catch (ErrorArchivo ex) {
                	   ex.printStackTrace();
            }    	
            if(args.length>1){//mostrar la salida en el archivo de salida.
                	archivoDeSalida=args[1];
                	guardarDatos(archivoDeSalida,resultado);
            }
            else{//mostrar la salida en la pantalla.
                	System.out.println(resultado);
            }                        
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


