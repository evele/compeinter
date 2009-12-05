package tablaSimbolos;

import java.util.ArrayList;

public class Funcion extends Procedimiento {
    
    private TipoSimple retorno;

    private int offset;

    public Funcion(TipoSimple r, int off, ArrayList lp, String et, int nl) {
        super(lp,et,nl);
        setRetorno(r);
        setOffset(off);
    }

    public TipoSimple getRetorno() {
        return retorno;
    }

    public void setRetorno(TipoSimple val) {
        this.retorno = val;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int val) {
        this.offset = val;
    }
}
