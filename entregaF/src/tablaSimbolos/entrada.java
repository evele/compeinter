package tablaSimbolos;

public abstract class entrada {
    
    private int nivelLexico;

    public entrada(int nl) {
	nivelLexico = nl;
    }

    public int getNivelLexico() {
        return nivelLexico;
    }

    public void setNivelLexico(int val) {
        this.nivelLexico = val;
    }
}
