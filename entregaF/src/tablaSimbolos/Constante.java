package tablaSimbolos;

public class Constante extends entrada {
    
    private tipo type;

    private int value;

    public Constante(tipo t, int valor, int nl) {
        super(nl);
        setTipo(t);
        setValor(valor);
    }

    public tipo getTipo() {
        return type;
    }

    public void setTipo(tipo val) {
        this.type = val;
    }

    public int getValor() {
        return value;
    }

    public void setValor(int val) {
        this.value = val;
    }
}
