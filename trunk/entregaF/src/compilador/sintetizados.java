/*
 * Clase utilizada para representar los atributos sintetizados en el EDT.
 * Posee como atributo una lista la cual es retornada por los procedimientos,
 * que representan las reglas del EDT, con los atributos que sintetiza cada uno.
 */

package compilador;
import java.util.Vector;
import tablaSimbolos.tipo;
import java.util.ArrayList;
import tablaSimbolos.TipoSimple;


/**
 * Representa todos los atributos que pueden ser sintetizados por una entrada.
 */
public class sintetizados {
    
    // Atributos
private Vector params;
    
    
    /**
     * Constructor de la clase Sintetizados
     */
    public sintetizados()
    {
	setParams(new Vector());
    }        
    
    /**
     * 
     * @param t 
     */
    public void setTipoS(tipo t)
    {        
        try
        {  
            getParams().set(0, t);
        }
        catch (ArrayIndexOutOfBoundsException e1)
        {
                getParams().setSize(0);    
                getParams().add(t);
        }
    }
    
    /**
     * 
     * @return 
     */
    public tipo getTipoS()
    {
	return (tipo) getParams().get(0);
    }
    
    /**
     * 
     * @param L 
     */
    public void setListaFormalesS(ArrayList L)
    {
        try {
        
            getParams().set(1, L);
        } 
        catch (ArrayIndexOutOfBoundsException a){
            getParams().setSize(1);
            getParams().add(L);
        }
    }
    
    /**
     * 
     * @return 
     */
    public ArrayList getListaParametrosFormalesS()
    {
	return (ArrayList) getParams().get(1);
    }
    
    /**
     * 
     * @param off 
     */
    public void setOffsetS(int off)
    {
        try {
            getParams().set(2, new Integer(off));
        } 
        catch(ArrayIndexOutOfBoundsException a) {
            getParams().setSize(2);
            getParams().add(new Integer(off));
        }
    }
    
    /**
     * 
     * @return 
     */
    public int getOffsetS()
    {
	return ((Integer) getParams().get(2)).intValue();
    }
    
    /**
     * 
     * @param L 
     */
    public void setListaIdsS(ArrayList L)
    {
        try{
	getParams().set(3, L);}
        catch(ArrayIndexOutOfBoundsException a){
            getParams().setSize(3);
            getParams().add(L);
        }
    }
    
    /**
     * 
     * @return 
     */
    public ArrayList getListaIdsS()
    {
	return (ArrayList) getParams().get(3);
    }
    
    /**
     * 
     * @param t 
     */
    public void setRetornoS(tipo t)
    {
        try{
	getParams().add(4, t);}
        catch(ArrayIndexOutOfBoundsException a){
            getParams().setSize(4);
            getParams().add(t);
        }
    }
    
    /**
     * 
     * @return 
     */
    public TipoSimple getRetornoS()
    {
	return (TipoSimple) getParams().get(4);
    }

    /**
     * Setter del atributo sintetizado 'espacio'
     * @param e valor para el atributo sintetizado 'espacio' 
     */
    public void setEspacioS(int e)
    {
        try{
	getParams().add(5, new Integer(e));}
        catch(ArrayIndexOutOfBoundsException a){
            getParams().setSize(5);
            getParams().add(new Integer(e));
        }
    }
    
    /**
     * Getter del atributo sintetizado 'espacio'
     * @return atributo sintetizado 'espacio'
     */
    public int getEspacioS()
    {
	return ((Integer) getParams().get(5)).intValue();
    }
    
    /**
     * Setter del atributo sintetizado 'value'
     * @param value valor para el atributo sintetizado 'value'
     */
    public void setValorS(int value)
    {   try{
            getParams().add(6,new Integer(value));   }
        catch(ArrayIndexOutOfBoundsException a){
            getParams().setSize(6);
            getParams().add(new Integer(value));
        }
    }
    
    /**
     * Getter del atributo sintetizado 'value'
     * @return el atributo sintetizado 'value' 
     */
    public int getValorS()
    {   return ((Integer) getParams().get(6)).intValue();  }
	
    /**
     * Setter del atributo sintetizado 'signo'
     * 1 representa el signo +
     * 0 representa el singo -
     * @param value valor para el atributo sintetizado 'signo'
     */
    public void setSignoS(int value)
    {   try{getParams().add(7,new Integer(value));   }
        catch(ArrayIndexOutOfBoundsException a){
            getParams().setSize(7);
            getParams().add(new Integer(value));
        }
    }
    
    /**
     * Getter del atributo sintetizado 'signo'
     * @return el atributo sintetizado 'signo'
     */
    public int getSignoS()
    {   return ((Integer) getParams().get(7)).intValue();  }
    
    /**
     * Determina si el signo sintetizado es el signo menos
     * @return si el signo sintetizado es el signo menos
     */
    public boolean esSignoMenos()
    {   return getSignoS()==0;  }
    
    /**
     * Setter del limite inferior de un subrango
     * @param value valor para el limite inferior de un subrango
     */
    public void setLimiteInfS(int value)
    {   try{getParams().add(8,new Integer(value));   }
        catch(ArrayIndexOutOfBoundsException a){
            getParams().setSize(8);
            getParams().add(new Integer(value));
        }
    }
    
    /**
     * Getter del limite inferior de un subrango
     * @return el limite inferior de un subrango
     */
    public int getLimiteInfS()
    {   return ((Integer) getParams().get(8)).intValue();  }   
    
    /**
     * Setter del limite superior de un subrango
     * @param value valor para el limite superior de un subrango
     */
    public void setLimiteSupS(int value)
    {   try{getParams().add(9,new Integer(value));   }
        catch(ArrayIndexOutOfBoundsException a){
            getParams().setSize(9);
            getParams().add(new Integer(value));
        }
    }
    
    /**
     * Getter del limite superior en el caso de un Subrango
     * @return el limite superior de un subrango
     */
    public int getLimiteSupS()
    {   return ((Integer) getParams().get(9)).intValue();  }   
    
    /**
     * 
     * @param id 
     */
    public void setIdS(String id)
    {
        try{
            getParams().set(10, id);
        }
        catch (ArrayIndexOutOfBoundsException e1)
        {
                getParams().setSize(10);    
                getParams().add(id);
        }

    }
    
    /**
     * 
     * @return 
     */
    public String getIdS()
    {
	return (String) getParams().get(10);
    }
    
    /**
     * Determina si el tipo sintetizado es nulo.
     * @return verdadero o falso si el tipo siintetizado es nulo
     */
    public boolean typeSNulo()
    {   return getTipoS()==null;    }
    
    //public void setT

    /**
     * Getter de los parametros sintetizados
     * @return lista de parametros sintetizados
     */
    public Vector getParams() {
        return params;
    }

    /**
     * Setter de la lista de parametros sintetizados.
     * @param params lista de parametros sintetizados
     */
    public void setParams(Vector params) {
        this.params = params;
    }
    
    /**
     * 
     * @param id 
     */
    public void agregarID(String id)
    {
//        getListaIdsS().add(id);
	
	// agrega en la posicion 0 pq los id se insertan en orden inverzo
        getListaIdsS().add(0, id);	
    }

}
