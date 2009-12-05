package tablaSimbolos;

public class Parametro extends Variable {
    
    private boolean porValor;

    public Parametro(tipo t, int off, boolean porVal, int nl) {
        super(t,off,nl);
        setPorValor(porVal);
    }

    public boolean getPorValor() {
        return porValor;
    }

    public void setPorValor(boolean val) {
        this.porValor = val;
    }
}
