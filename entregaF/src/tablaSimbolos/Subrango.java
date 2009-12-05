package tablaSimbolos;

public class Subrango extends TipoSimple {
    
    private int limiteInf;

    private int limiteSup;

    public Subrango(int li, int ls, int nl) {
        super(nl);
        setLimiteInf(li);
        setLimiteSup(ls);
    }

    public int getLimiteInf() {
        return limiteInf;
    }

    public void setLimiteInf(int val) {
        this.limiteInf = val;
    }

    public int getLimiteSup() {
        return limiteSup;
    }

    public void setLimiteSup(int val) {
        this.limiteSup = val;
    }
}
