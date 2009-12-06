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
       
    public tipo getTipoS()
    {
	return (tipo) getParams().get(0);
    }
       
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
   
    public ArrayList getListaParametrosFormalesS()
    {
	return (ArrayList) getParams().get(1);
    }
    
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
    
    public int getOffsetS()
    {
	return ((Integer) getParams().get(2)).intValue();
    }
   
    public void setListaIdsS(ArrayList L)
    {
        try{
	getParams().set(3, L);}
        catch(ArrayIndexOutOfBoundsException a){
            getParams().setSize(3);
            getParams().add(L);
        }
    }
  
    public ArrayList getListaIdsS()
    {
	return (ArrayList) getParams().get(3);
    }
    
    
    public void setRetornoS(tipo t)
    {
        try{
	getParams().add(4, t);}
        catch(ArrayIndexOutOfBoundsException a){
            getParams().setSize(4);
            getParams().add(t);
        }
    }
    
    
    public TipoSimple getRetornoS()
    {
	return (TipoSimple) getParams().get(4);
    }

   
    public void setEspacioS(int e)
    {
        try{
	getParams().add(5, new Integer(e));}
        catch(ArrayIndexOutOfBoundsException a){
            getParams().setSize(5);
            getParams().add(new Integer(e));
        }
    }
    
   
    public int getEspacioS()
    {
	return ((Integer) getParams().get(5)).intValue();
    }
    
    
    public void setValorS(int value)
    {   try{
            getParams().add(6,new Integer(value));   }
        catch(ArrayIndexOutOfBoundsException a){
            getParams().setSize(6);
            getParams().add(new Integer(value));
        }
    }
    
   
    public int getValorS()
    {   return ((Integer) getParams().get(6)).intValue();  }
	
 
    public void setSignoS(int value)
    {   try{getParams().add(7,new Integer(value));   }
        catch(ArrayIndexOutOfBoundsException a){
            getParams().setSize(7);
            getParams().add(new Integer(value));
        }
    }
    
    
    public int getSignoS()
    {   return ((Integer) getParams().get(7)).intValue();  }
    
    
    public boolean esSignoMenos()
    {   return getSignoS()==0;  }
    
   
    public void setLimiteInfS(int value)
    {   try{getParams().add(8,new Integer(value));   }
        catch(ArrayIndexOutOfBoundsException a){
            getParams().setSize(8);
            getParams().add(new Integer(value));
        }
    }
    
   
    public int getLimiteInfS()
    {   return ((Integer) getParams().get(8)).intValue();  }   
    
   
    public void setLimiteSupS(int value)
    {   try{getParams().add(9,new Integer(value));   }
        catch(ArrayIndexOutOfBoundsException a){
            getParams().setSize(9);
            getParams().add(new Integer(value));
        }
    }
    
    
    public int getLimiteSupS()
    {   return ((Integer) getParams().get(9)).intValue();  }   
    
  
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
    
    
    public String getIdS()
    {
	return (String) getParams().get(10);
    }
    
    
    public boolean typeSNulo()
    {   return getTipoS()==null;    }
    
  
    
    public Vector getParams() {
        return params;
    }

    
    public void setParams(Vector params) {
        this.params = params;
    }
    
    
    public void agregarID(String id)
    {
        getListaIdsS().add(0, id);	
    }

}
