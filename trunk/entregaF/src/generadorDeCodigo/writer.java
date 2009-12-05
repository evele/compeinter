/**
* Clase encargada de la escritura de las instrucciones MEPa en el archivo de salida
*/

package generadorDeCodigo;
import java.io.*;

public class writer {

   private String NombreArchivo;
   private FileWriter Archivo;
   private BufferedWriter Buffer;
    
   public void escribir(String Instruccion) throws IOException {
       this.Buffer.write(Instruccion);
       this.Buffer.newLine();
   }
    
   public void cerrarArchivo() throws IOException {
     try{ 
       this.Buffer.close();
       this.Archivo.close();      
     } catch (IOException e){
         throw new IOException("Se produjo un error al cerrar el archivo de salida (MEPA).");
     }      
   }        

    /**
     * Creates a new instance of writer
     */
    public writer(String NombreArchivo) throws IOException {
      try{  
        this.NombreArchivo=NombreArchivo;
        this.Archivo=new FileWriter(NombreArchivo);
        this.Buffer=new BufferedWriter(Archivo);
      } catch (IOException e){
         throw new IOException("Se produjo un error al intentar crear el archivo de salida: "+NombreArchivo);
     }
    }
    
}
