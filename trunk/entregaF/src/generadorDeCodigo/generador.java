/* 
 * Esta clase provee las operaciones para generar cada una de las instrucciones
 * MEPa. Utiliza la clase writer que escribira en el archivo de salida cada 
 * instruccion generada durante la compilacion.
 */
 
package generadorDeCodigo;

import java.io.IOException;

public class generador {
   
    //ATRIBUTOS 
    writer ArchivoFteMepa;
    int etiqueta=0;
            

    public void genInstSinArg(String etiqueta, String nombreInst) throws Exception
    {
        ArchivoFteMepa.escribir(etiqueta+"\t"+nombreInst);
    }
    
    public void genInst1ArgCte(String etiqueta, String nombreInst, int ArgCte) throws Exception
    {
        ArchivoFteMepa.escribir(etiqueta+"\t"+nombreInst+" "+ArgCte);
    }
    
    public void genInst1ArgEtiq(String etiqueta, String nombreInst, String ArgEtiq) throws Exception
    {
        ArchivoFteMepa.escribir(etiqueta+"\t"+nombreInst+" "+ArgEtiq);
    } 
   
    public void genInst2ArgCte(String etiqueta, String nombreInst, int ArgCte1,int ArgCte2) throws Exception
    {
        ArchivoFteMepa.escribir(etiqueta+"\t"+nombreInst+" "+ArgCte1+", "+ArgCte2);
    }
    
   public void genInst3ArgCte(String etiqueta, String nombreInst, int ArgCte1,int ArgCte2, int ArgCte3) throws Exception
    {
        ArchivoFteMepa.escribir(etiqueta+"\t"+nombreInst+" "+ArgCte1+", "+ArgCte2+", "+ArgCte3);
    } 
   
   public String genEtiqueta()
   {
       return "L"+ etiqueta++;
   }
   
   public void finalizarGen() throws IOException {
       ArchivoFteMepa.cerrarArchivo();
   }
   

    public generador(String FileOut) throws Exception{
        ArchivoFteMepa = new writer(FileOut);
    }
    
    //Instrucciones de 0 argumentos
    // El primer operando de estas instrucciones esta en M[tope-1] y el segundo M[tope]
    public static final String INPP = "INPP";	// inicia el programa principal
    public static final String PARA = "PARA";	// parada de la maquina MEPa
    public static final String MULT = "MULT";	// multiplicación
    public static final String DIVI = "DIVI";	// división entera
    public static final String NADA = "NADA";   // no tiene efecto de ejecución
    public static final String MODU = "MODU";	// modulo
    public static final String UMEN = "UMEN";	// menos unario
    public static final String CONJ = "CONJ";	// AND lógico
    public static final String DISJ = "DISJ"; 	// OR lógico
    public static final String NEGA = "NEGA";	// NOT lógico
    public static final String CMME = "CMME";	// menor estricto <
    public static final String CMMA = "CMMA";	// mayor estricto >
    public static final String CMIG = "CMIG";	// igual =
    public static final String CMDG = "CMDG";	// distinto <>
    public static final String CMNI = "CMNI";  // menor o igual <=
    public static final String CMYI = "CMYI";  // mayor o igual >=
    public static final String LEER = "LEER";  // lee un valor entero
    public static final String IMPR = "IMPR";  // imprime un valor entero
    public static final String LELN = "LELN";  // lee un valor entero hasta fin de linea
    public static final String IMLN = "IMLN";  // imprime un valor entero y baja de linea
    public static final String SUMA = "SUMA";	// suma 
    public static final String SUST = "SUST";  // resta 
    public static final String DIVC = "DIVC";  // controla si el divisor es cero
    public static final String LECH = "LECH";  // lee un caracter de la pila
    public static final String IMCH = "IMCH";  // imprimir un caracter de la pila

    public static final String LECN = "LECN";  // readln para el caso de operandos de tipo char
    public static final String IMCN = "IMCN";  // writeln para el caso de operandos de tipo char
    
    //Instrucciones de 1 argumento: constante 
    public static final String APCT = "APCT";	// apila una constante k
    public static final String ENPR = "ENPR";  // entrar al proc. de nivel k
    public static final String RMEM = "RMEM";	// reservar memoria para variables
    public static final String LMEM = "LMEM";	// libera memoria de variables locales
   
    //Instrucciones de 1 argumento: etiqueta
    public static final String DSVS = "DSVS";  // salta siempre a la etiqueta l
    public static final String DSVF = "DSVF";  // salta si el tope es falso
    public static final String LLPR = "LLPR";  // llamar a un proc. de etiqueta l

    //Instrucciones de 2 argumentos 
    public static final String APVL = "APVL";  // apila el valor de una variable:
    public static final String ALVL = "ALVL";  // almacena el tope de la pila en una variable
    public static final String RTPR = "RTPR";  // retorna de un proc. de nivel k
    public static final String APDR = "APDR";  // apila la dirección de una variable
    public static final String APVI = "APVI";  // apila un valor indirecto
    public static final String ALVI = "ALVI";  // almacena el tope de la pila en un valor indirecto o referencia
    public static final String APAR = "APAR";	// apila el elemento de un arreglo
    public static final String ALAR = "ALAR";	// almacena un valor en el elemento de un arreglo
    public static final String APAI = "APAI";	// apila un elemento de un arreglo indirecto
    public static final String ALAI = "ALAI";	// modifica un elemento de un arreglo indirecto
    public static final String APDC = "APDC";	// apila la dirección de una componente de un arreglo como par´ametro por referencia
    public static final String CONT = "CONT";	// controla del rango de valores
    
    //Instrucciones de 3 argumentos 
    public static final String POAI = "POAI";  // modifica todo un sub-arreglo indirecto de tamañoo l y desplazamiento en el arreglo i
    public static final String PUAI = "PUAI";  // apilar todo un sub-arreglo indirecto de tamaño l y desplazamiento en el arreglo i
    public static final String PUAR = "PUAR";  // carga en la pila un sub-arreglo
    public static final String POAR = "POAR";  // copia de la pila a la memoria un sub-arreglo de un arreglo
    
    
}
