package tablaSimbolos;

import java.util.ArrayList;

public class Procedimiento extends entrada {
    
    private ArrayList listaParam;

    private String etiq;

    public Procedimiento(ArrayList lp, String et, int nl) {
        super(nl);
        setEtiq(et);
        setListaParam(lp);
    }

    public ArrayList getListaParametrosFormales() {
        return listaParam;
    }

    public void setListaParam(ArrayList val) {
        this.listaParam = val;
    }

    public String getEtiq() {
        return etiq;
    }

    public void setEtiq(String val) {
        this.etiq = val;
    }
}
