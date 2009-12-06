//version factorF
package compilador;
import java.awt.Color;
import java.io.PrintStream;

/**
 * Clase que implementa el manejo de los errores que pueden surgir durante el Analisi Semantico
 */
public class ErrorSemantico extends Exception {    
        
    public static final String ID_INV="Identificador invalido";
    public static final String TIPO_INV_MAS="Tipo invalido para el operador +";
    public static final String TIPO_INV_MENOS="Tipo invalido para el operador -";
    public static final String TIPO_INCOMP="Tipos incompatibles";
    public static final String IND_SUBRANGO_INV="Indice de subrango invalido";
    public static final String LIM_ENT="Los limites de un subrango deben ser enteros";
    public static final String TIPO_IND_SUBRANGO="El tipo indice debe ser un subrango";
    public static final String ID_VAR_PARAM="Se esperaba id de variable o parametro";
    public static final String TIPO_ARRAY="Se esperaba tipo arreglo";
    public static final String EXP_POR_VALOR="La expresion debe ser por Valor";
    public static final String INT_SUBRANGO="Tipos Incompatibles: Se esperaba entero o subrango";    
    public static final String TIPO_INV_OR="Tipo invalido para el operador OR";
    public static final String TIPO_INV_AND="Tipo invalido para el operador AND";      
    public static final String TIPO_INV_NOT="Tipo invalido para el operador NOT";    
    public static final String TIPO_IND_INCOMP="Tipos indice incompatible";
    public static final String FUNC="Se esperaba una funcion";    
    public static final String VAR_CTE_FUNC="Se esperaba una variable,constante,funcion sin parametros o un parametro";    
    public static final String CTE_SIMPLE="Las constantes son de Tipo simple";
    public static final String TIPO_SIMPLE_ARRAY="Se esperaba un Tipo Simple o un arreglo";
    public static final String TIPO_INCOMP_ENTERO="Tipos incompatibles";
    public static final String LIM_SUBRANGO_INV="Limites de subrango invertidos";
    public static final String CTE_REF="Se esperaba un identificador de variable";
    public static final String DEMAS_PARAM="Demasiados parametros";
    public static final String FALTAN_PARAM="Faltan parametros";
    public static final String ID_DUP="Identificador duplicado";
    public static final String ID_NO_DEC="Identificador no declarado";
    public static final String PARAM_REF="Se esperaba un parametro por referencia";
    public static final String CTE_ENT="Se esperaba una constante de tipo entero";
    
    
    /**
     * Constructor de un manejador de errores semanticos.
     * @param error tipo de error 
     * @param nroFila Indica el nro de la fila del codigo en donde se produjo el error.
     * @param nroCol Indica el nro de la columna del codigo en donde se produjo el error.
     * @param encontrado lexema encontrado
     */    
    public ErrorSemantico(String error, int nroFila, int nroCol,String encontrado) {
        super("\n\n" +
                 "\n ******************************************************************************* \n " +
		   "Error Semantico en: (Fila: " + nroFila +  " Columna: "+ nroCol + ")\n\t" + error +	 ". (*    "  + encontrado+"    *)"+
		"\n ******************************************************************************* \n");
    }
    
    /**
     * Constructor de un manejador de errores semanticos.
     * @param error tipo de error
     * @param nroFila Indica el nro de la fila del codigo en donde se produjo el error.
     * @param nroCol Indica el nro de la columna del codigo en donde se produjo el error.
     */    
    public ErrorSemantico(String error, int nroFila, int nroCol) {
        super("\n\n" +
                 "\n ******************************************************************************* \n " +
		   "Error Semantico en: (Fila: " + nroFila +  " Columna: "+ nroCol + ")\n\t" + error +	
		"\n ******************************************************************************* \n");
    }    
    
    /**
     * Constructor de un manejador de errores semanticos asociados a errores de tipos
     * @param error tipo de error
     * @param nroFila Indica el nro de la fila del codigo en donde se produjo el error.
     * @param nroCol Indica el nro de la columna del codigo en donde se produjo el error.
     * @param tipo1 tipo que no conforma con tipo2
     * @param tipo2 tipo que no conforma con tipo1
     */    
    public ErrorSemantico(String error, int nroFila, int nroCol,String tipo1, String tipo2) {                       
        super("\n\n" +
                 "\n ******************************************************************************* \n " +
		   "Error Semantico en: (Fila: " + nroFila +  " Columna: "+ nroCol + ")\n\t" + error +	 ". ( "+tipo1+" - "+tipo2+" )"+
		"\n ******************************************************************************* \n");
    }    
   
    
    /**
     * Crea el formato asociado al error semantico.
     * @param tipo jerarquia de tipos a formatear
     * @return mensaje asociado el error.
     */
    public static String construirMsj(String tipo) {                       
        
        String jerarq = tipo.split("@")[0];//.split(".")[1];                
        String type = jerarq.substring(14); //le sacamos el substring "tablaSimbolos"
        //String[] ar=jerarq.split(".");
        
        
        return(type);
    }        
}