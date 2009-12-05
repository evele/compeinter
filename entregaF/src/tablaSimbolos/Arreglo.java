package tablaSimbolos;

public class Arreglo extends tipo {
    
    private tipo tipoElem;
    private Subrango tipoIndice;    
    private int size;

    public Arreglo(tipo te, int li, int ls, int nl) {
        super(nl);
        int tam;
        setTipoElem(te);            
        tipoIndice = new Subrango(li,ls,nl);
        tam = ((getTipoIndice().getLimiteSup()-getTipoIndice().getLimiteInf())+1)*getTipoElem().getSize();
        setSize(tam);
    }

    public tipo getTipoElem() {
        return tipoElem;
    }

    public void setTipoElem(tipo val) {
        this.tipoElem = val;
    }

    public Subrango getTipoIndice() {
        return tipoIndice;
    }

    public void setTipoIndice(Subrango val) {
        this.tipoIndice = val;
    }

    public int getSize() {
        return size;
    }

    private void setSize(int size) {
        this.size = size;
    }
    
    public int getLimiteInf() {
        return getTipoIndice().getLimiteInf();
    }

    public void setLimiteInf(int val) {
        getTipoIndice().setLimiteInf(val);
    }

    public int getLimiteSup() {
	return getTipoIndice().getLimiteSup();
    }

    public void setLimiteSup(int val) {
        getTipoIndice().setLimiteSup(val);
    }
    
}
