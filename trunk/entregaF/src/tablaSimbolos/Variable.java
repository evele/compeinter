package tablaSimbolos;

public class Variable extends entrada {
    
    private tipo type;

    private int offset;

    public Variable(tipo t, int off, int nl) {
        super(nl);
        setOffSet(off);
        setTipo(t);
    }

    public tipo getTipo() {
        return type;
    }

    public void setTipo(tipo val) {
        this.type = val;
    }

    public int getOffSet() {
        return offset;
    }

    public void setOffSet(int val) {
        this.offset = val;
    }
}
