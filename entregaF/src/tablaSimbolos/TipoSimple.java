package tablaSimbolos;

public abstract class TipoSimple extends tipo {

    private int size;
    
    public TipoSimple(int nl) {
        super(nl);
        setSize(1);
    }

    public int getSize() {
        return size;
    }

    private void setSize(int size) {
        this.size = size;
    }
}
