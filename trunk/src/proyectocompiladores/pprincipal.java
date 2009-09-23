package proyectocompiladores;

import java.io.FileOutputStream;
import java.io.ObjectOutputStream;


public class pprincipal {
	      
	public static void main(String[] args) {
		 AnaLex analizadorLexico;
	     Token token;
	     String archivoDeEntrada = "";
	     String archivoDeSalida = "";
	     String resultado = "";
	     int cont = 1;
	     boolean terminar = false;
	      
        try {
            archivoDeEntrada = args[0];
            try {
                analizadorLexico = new AnaLex(archivoDeEntrada);
                while(!terminar) {
                    try {
                        token = analizadorLexico.getToken();
                        resultado = resultado.concat("Token:\t\t"+token.getNombre()+"\nLexema:\t\t"+token.getLexema()+"\nLinea:\t\t\t"+Integer.toString(token.getNumeroLinea())+"\nDescripcion:\t"+token.getDescripcion()+"\n");
                        resultado = resultado.concat("*********************************************\n");
                        terminar = (token.EOF == token.getNumeroToken());
                    } catch (ErrorLexico ex) {
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
        } catch (ArrayIndexOutOfBoundsException a1){System.out.println("Debe ingresar el nombre de un archivo de entrada.");}
        }

	private static void guardarDatos(String archivoDeSalida, String resultado) {
		  ObjectOutputStream o;
	      
	      try {
	          o=new ObjectOutputStream(new FileOutputStream(archivoDeSalida));
	          o.flush();

	          
	          o.writeObject((String)resultado);
	          o.close();
	      } catch (Exception e) {
	          System.out.println(e.getMessage());
	      }
		
	}
	}


