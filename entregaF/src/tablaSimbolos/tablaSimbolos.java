/**
 * Clase responsable de representar la estructura de la tabla de simbolos
 * necesaria durante la compilacion del programa fuente.
 * La misma sigue un esquema pila, para lo cual usa un vector y una variable privada tope.
 * En la tabla va apilando las tablas (hashMap) para cada nuevo nivel lexico que va 
 * reconociendo durante la compilacion.
 * Al iniciar la compilacion siempre se encontrara la Tabla con los identificadores Predefinidos
 * en la base de la pila.
 */

package tablaSimbolos;

import compilador.ErrorSemantico;
import compilador.Compilador;
import java.awt.Color;
import java.util.Vector;
import java.util.HashMap;
import lexico.AnaLex;
import java.util.*;


public class tablaSimbolos {
       
    private Vector TS;		// Representa la pila que contendra la Tabla de cada nivel lexico
    private int nivelLex;	// Representa el nivel lexico que esta compilando en un dado intante de tiempo.
    private int tope;		// Representa el indice del vector en el cual se encuentra el tope de la pila.
    private Compilador comp;	// Referencia al compilador para obtener la linea y columa en caso de una excepcion.
	private static int MaxMepa = 31999;

    /** Creates a new instance of tablaSimbolos */
    public tablaSimbolos(Compilador c)
    {
	comp = c;
	setTS(new Vector());	
	setNivelLex(-1);
	setTope(-1);
    }

   /*****************************************************
    *		AUXILIARES    Privados
    ******************************************************/
    
    /**
     * @return el nivel lexico que utilizamos en la implementacion, que es el indice a usar en el Vector.
     * Estara entre los valores [0..size(Vector)].
     */
    private int getNivelLex() {
        return nivelLex;
    }

    private void setNivelLex(int nivelLex) {
        this.nivelLex = nivelLex;
    }

    private Vector getTS() {
        return TS;
    }

    private void setTS(Vector TS) {
        this.TS = TS;
    }

    /**
     * @return el valor del indice al tope de la TS
     */
    private int getTope() {
        return tope;
    }

    /**
     * 
     * @param Tope el valor del indice al tope de la TS
     */
    private void setTope(int Tope) {
        this.tope = Tope;
    }
    
    
    
    /*****************************************************
    *		Metodos Publicos
    ******************************************************/  
    
    /**
    * @return el nivel lexico que utilizamos en el disenio, que es el indice a usar en la Tabla de Simbolos.
    * Estara entre los valores [-1..tope(Tabla Simbolos)]; es una normalizacion de {@link getNivelLex()}.
    */
    public int getNivelActual(){ 
	return getNivelLex()-1;
    }

  
    /**
    * Apila en la TS la tabla que representa un nuevo nivel lexico
    * Actualiza el nivel lexico. (Incrementa su valor)
    */
    public void apilarNivel()
    {     
	HashMap nuevoNivel = new HashMap();	
	setTope(getTope() + 1);
	getTS().add(getTope(), nuevoNivel); 
	setNivelLex(getNivelLex() + 1);     
    }
    
    /**
     * Desapila de la TS la tabla que representa el nivel lexico actual
     * Actualiza el nivel lexico. (Decrementa su valor)
     */
    public void desapilarNivel()
    {        
         getTS().remove(getTope());//Elimina el elemento en el tope de la pila.
         setTope(getTope() - 1);
         setNivelLex(getNivelLex() - 1);    	 
    }

    /**
     * El metodo se encarga de insertar una nueva entrada en el nivel lexico que actualmente esta
     * siendo compilado verificando que no exista otra entrada con igual identificador
     * @param id lexema de la nueva entrada
     * @param nvaEntrada instancia de un objeto de la geraquia de entrada
     * @throws ErrorSemantico Identificador duplicado.
     */
    public void insertar(String id, entrada nvaEntrada) throws ErrorSemantico
    {
         HashMap nivel = (HashMap) getTS().get(getTope());  // Obtiene la tabla del nivel lexico actual
         
	 if ( ! nivel.containsKey(id.toUpperCase()))	// si NO EXISTE una entrada con id, inserta
             nivel.put(id.toUpperCase(), nvaEntrada);	// una nueva con llave id y valor nvaEntrada.
         else 
	     throw new ErrorSemantico(ErrorSemantico.ID_DUP, comp.getNumeroLinea(),comp.getNumeroColumna(),id);
    }

  /**
  * @return el objeto asociado al id dado buscando de forma global, es decir, 
  * desde el nivel lexico actual hacia afuera hasta encontrar.
  * Si no existe en la tabla levanta una excepcion .    
  */   
    public entrada getEntrada(String id)
    {
	HashMap nivel;    
	ListIterator I; // para poder recorrer la Tabla de Simbolos.
	boolean encontre = false;
	entrada ent = null;


	I = getTS().listIterator(getTope()+1);//Comienza en tope +1 asi cuando realiza previous da el elemento del tope.-

	//recorre la tabla de simbolos desde el elemento en el tope (ultimo indice)
	//hasta el principio del vector buscando el elemento dado.
	do{
	    nivel = (HashMap) I.previous();	//Da el valor en el tope.

	    if (nivel.containsKey(id.toUpperCase()))
	    {
		encontre = true;  
		ent = (entrada) nivel.get(id.toUpperCase());
	    }   
	}while ((!encontre) && (I.hasPrevious())); 

	if (!encontre)
        ent = null;

	return ent;      
  }
 
   
   
    /***********************************************
     *			CONSULTAS
     ***********************************************
    
    public boolean esArreglo(entrada ent)
    {
	return (ent instanceof Arreglo);
    }
    
    public boolean esBooleano(entrada ent)
    {
	return (ent instanceof Booleano);
    }    
    
    public boolean esCaracter(entrada ent)
    {
	return (ent instanceof caracter);
    } 
    
    public boolean esConstante(entrada ent)
    {
	return (ent instanceof Constante);
    }
    
    public boolean esEntero(entrada ent)
    {
	return (ent instanceof Entero);
    }
    
    public boolean esFuncion(entrada ent)
    {
	return (ent instanceof Funcion);
    }
    
    public boolean esParametro(entrada ent)
    {
	return (ent instanceof parametro);
    }
    
    public boolean esProcedimiento(entrada ent)
    {
	return (ent instanceof Procedimiento);
    }
    
    public boolean esPrograma(entrada ent)
    {
	return (ent instanceof programa);
    }
    
    public boolean esSubrango(entrada ent)
    {
	return (ent instanceof Subrango);
    }
    
    public boolean esTipo(entrada ent)
    {
	return (ent instanceof tipo);
    }
    
    public boolean esTipoSimple(entrada ent)
    {
	return (ent instanceof tipoSimple);
    }
    
    public boolean esVariable(entrada ent)
    {
	return (ent instanceof variable);
    }
   
    public boolean esConstanteEntera(entrada ent)
    {
	// true si es una Constante de tipo entera o Subrango
	return (ent instanceof Constante &&
		(((Constante)ent).getType() instanceof Entero ||
		((Constante)ent).getType() instanceof Subrango));	
    }*/
    
    public int getMaxMepa(){
		return MaxMepa;
    }
    
    public boolean esProcPredef(String id)
    {
	return (id.toUpperCase().compareTo("READ") == 0 || 
		id.toUpperCase().compareTo("READLN")== 0 || 
		id.toUpperCase().compareTo("WRITE")== 0 || 
		id.toUpperCase().compareTo("WRITELN")== 0);
    }

    public boolean esFuncPredef(String id)
    {
	return (id.toUpperCase().compareTo("SUCC")== 0 || 
		id.toUpperCase().compareTo("PRED")== 0);
    }

    public boolean esProcLectura(String id)
    {
	return (id.toUpperCase().compareTo("READ")==0 || 
		id.toUpperCase().compareTo("READLN")==0);
    }
    
   
    /**
    * @return true o false, si dados dos tipos son compatibles.
    */
    public boolean compatibles(tipo t1, tipo t2)
    { 
	if(t1 instanceof Arreglo && t2 instanceof Arreglo)
            {if(((Arreglo)t1).getSize()==((Arreglo)t2).getSize())
                { return compatibles(((Arreglo)t1).getTipoElem(),((Arreglo)t2).getTipoElem()); }
             else return false;
        }
        else{
                return ((t1 instanceof Entero && t2 instanceof Entero) ||
                        (t1 instanceof Entero && t2 instanceof Subrango) ||
                        (t1 instanceof Subrango && t2 instanceof Entero) ||
                        (t1 instanceof Subrango && t2 instanceof Subrango) ||		
                        (t1 instanceof Booleano && t2 instanceof Booleano));
            }
    }
    
    /**
     * Crea e inicializa el nivel -1 de la TS con los valores predefinidos:
     *    Constantes: true, false, maxint.
     *    tipos: integer, boolean
     *    fc: succ, pred
     *    procedimIentos: read, write, readln y writeln.
     */ 
   public void cargarPredefinidos()
   {
        HashMap nivel;
	
	apilarNivel();           
        Constante m = new Constante(new Entero(getNivelActual()),MaxMepa,getNivelActual());      
        Constante t = new Constante(new Booleano(getNivelActual()), 1,getNivelActual());   //TRUE=1
        Constante f = new Constante(new Booleano(getNivelActual()), 0,getNivelActual());   //FALSE=0    
        Entero i = new Entero(getNivelActual());
        Booleano b = new Booleano(getNivelActual());    
        
        nivel = (HashMap) TS.get(getTope());  // recupera la hash que esta en el tope
	
		nivel.put("MAXINT",m);
        nivel.put("TRUE",t);
        nivel.put("FALSE",f);
        nivel.put("INTEGER",i);
        nivel.put("BOOLEAN",b);
    	
        nivel.put("SUCC",new Funcion(null, 0, null, "", getNivelActual()));     
        nivel.put("PRED",new Funcion(null, 0, null, "", getNivelActual()));
	
        nivel.put("READ",new Procedimiento(null, "", getNivelLex()));
        nivel.put("READLN",new Procedimiento(null, "", getNivelLex()));
        nivel.put("WRITE",new Procedimiento(null, "", getNivelLex()));
        nivel.put("WRITELN",new Procedimiento(null, "", getNivelLex()));        
   }   
   
   public void insertarParametros(ArrayList listaParam, ArrayList listaIds) throws ErrorSemantico{ //pateado para más adelante by Eric
   ListIterator itNva, itIds;
   Parametro param;
   
		itNva = listaParam.listIterator();
		itIds = listaIds.listIterator();		   
		
		while (itNva.hasNext()) {
			param = (Parametro) itNva.next();
			insertar((String) itIds.next(), param);
		}
   }
   
   public entrada getEntradaNivelActual(String id){ //pateado para más adelante by Eric
	HashMap nivel;    
	ListIterator I; // para poder recorrer la Tabla de Simbolos.
	entrada entrada = null;
	   
	I = getTS().listIterator(getTope()+1);//Comienza en tope +1 asi cuando realiza previous da el elemento del tope.-

	nivel = (HashMap) I.previous();	//Da el valor en el tope.

	if (nivel.containsKey(id.toUpperCase())) {
		entrada = (entrada) nivel.get(id.toUpperCase());
	    }   
	else {
		entrada = null;
	}
	return entrada;  
   }
  
   
}
