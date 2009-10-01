package proyectocompiladores;

public class Token {
	
	private int numeroToken;
    private String lexema;   
    private int numeroLinea;	// nro de linea donde es encuentra el lexema
    private int numeroColumna;		// nro de caracter en la linea en donde comienza en lexema
	
	/* Constantes para representar tokens */   
    public static final int EOF = -1; 
    public static final int IDENTIFICADOR=1;
    public static final int IGUAL=2;
    public static final int DISTINTO=3;
    public static final int MENOR=4;
    public static final int MAYOR=5;
    public static final int MAYOROIGUAL=6;
    public static final int MENOROIGUAL=7;
    public static final int SUMA=8;
    public static final int RESTA=9;
    public static final int MULTIPLICACION=10;
    public static final int ASIGNACION=11;
    public static final int PARENTESISABRE=12;
    public static final int PARENTESISCIERRA=13;
    public static final int PUNTO=14;
    public static final int PUNTOPUNTO=15;
    public static final int COMA=16;
    public static final int PUNTOCOMA=17;
    public static final int DOSPUNTOS=18;
    public static final int CORCHETEABRE=19;
    public static final int CORCHETECIERRA=20;
    public static final int NUMERO=21;

    
    // Palabras Reservadas
    public static final int PROGRAM = 23;
    public static final int BEGIN = 24;   
    public static final int END = 25;   
    public static final int AND = 26;   
    public static final int OR = 27;    
    public static final int DIV = 28;   
    public static final int NOT = 29;  
    public static final int VAR = 30;  
    public static final int CONST = 31;  
    public static final int TYPE = 32;  
    public static final int FUNCTION = 33;  
    public static final int PROCEDURE = 34;  
    public static final int WHILE = 35;  
    public static final int DO = 36;   
    public static final int ARRAY = 37;   
    public static final int OF = 38;   
    public static final int IF = 39;   
    public static final int THEN = 40;   
    public static final int ELSE = 41;      
	
    public Token(int numeroToken, String lexema, int linea, int columna) {
    	this.numeroToken = numeroToken;
    	this.lexema = lexema;
    	this.numeroLinea = linea;
    	this.numeroColumna = columna;    	
    }  
    
    public String getNombre()
    {
        int valor = getNumeroToken();
        
        switch (valor)
        {
            case -1: return ("EOF");
            case 1: return ("IDENTIFICADOR");
            case 2: return ("IGUAL"); 
            case 3: return ("DISTINTO");
            case 4: return ("MENOR"); 
            case 5: return ("MAYOR"); 
            case 6: return ("MAYOROIGUAL");
            case 7: return ("MENOROIGUAL"); 
            case 8: return ("SUMA"); 
            case 9: return ("RESTA"); 
            case 10:return ("MULTIPLICACION"); 
            case 11:return ("ASIGNACION"); 
            case 12:return ("PARENTESISABRE"); 
            case 13:return ("PARENTESISCIERRA"); 
            case 14:return ("PUNTO"); 
            case 15:return ("PUNTOPUNTO"); 
            case 16:return ("COMA"); 
            case 17:return ("PUNTOCOMA"); 
            case 18:return ("DOSPUNTOS"); 
            case 19:return ("CORCHETEABRE"); 
            case 20:return ("CORCHETECIERRA"); 
            case 21:return ("NUMERO"); 
        
            
            // Palabras Reservadas
            case 23:return ("PROGRAM");
            case 24:return ("BEGIN");   
            case 25:return ("END");
            case 26:return ("AND");
            case 27:return ("OR");
            case 28:return ("DIV");
            case 29:return ("NOT");
            case 30:return ("VAR");
            case 31:return ("CONST");
            case 32:return ("TYPE");
            case 33:return ("FUNCTION");
            case 34:return ("PROCEDURE");
            case 35:return ("WHILE");
            case 36:return ("DO");
            case 37:return ("ARRAY");
            case 38:return ("OF");
            case 39:return ("IF");
            case 40:return ("THEN");
            case 41:return ("ELSE");
        }
        return "";
    }
    
	public int getNumeroLinea() {
		return numeroLinea;
	}

	public String getDescripcion() {
		int valor = getNumeroToken();
        
        switch (valor)
        {
            case -1: return ("Fin del archivo");
            case 1: return ("Identificador");
            case 2: return ("Operador binario IGUAL"); 
            case 3: return ("Operador binario DISTINTO");
            case 4: return ("Operador binario MENOR"); 
            case 5: return ("Operador binario MAYOR"); 
            case 6: return ("Operador binario MAYOR O IGUAL");
            case 7: return ("Operador binario MENOR O IGUAL"); 
            case 8: return ("Operador SUMA"); 
            case 9: return ("Operador RESTA"); 
            case 10:return ("Operador MULTIPLICACION"); 
            case 11:return ("Operador ASIGNACION"); 
            case 12:return ("Parentesis que abre"); 
            case 13:return ("Parentesis que cierra"); 
            case 14:return ("Punto"); 
            case 15:return ("Dos puntos seguidos (rango)"); 
            case 16:return ("Coma"); 
            case 17:return ("Punto y coma"); 
            case 18:return ("Dos puntos"); 
            case 19:return ("Corchete que abre"); 
            case 20:return ("Corchete que cierra"); 
            case 21:return ("Número"); 
               
            
            // Palabras Reservadas
            case 23:return ("Palabra Reservada PROGRAM");
            case 24:return ("Palabra Reservada BEGIN");   
            case 25:return ("Palabra Reservada END");
            case 26:return ("Palabra Reservada AND");
            case 27:return ("Palabra Reservada OR");
            case 28:return ("Palabra Reservada DIV");
            case 29:return ("Palabra Reservada NOT");
            case 30:return ("Palabra Reservada VAR");
            case 31:return ("Palabra Reservada CONST");
            case 32:return ("Palabra Reservada TYPE");
            case 33:return ("Palabra Reservada FUNCTION");
            case 34:return ("Palabra Reservada PROCEDURE");
            case 35:return ("Palabra Reservada WHILE");
            case 36:return ("Palabra Reservada DO");
            case 37:return ("Palabra Reservada ARRAY");
            case 38:return ("Palabra Reservada OF");
            case 39:return ("Palabra Reservada IF");
            case 40:return ("Palabra Reservada THEN");
            case 41:return ("Palabra Reservada ELSE");
        }
        return "";
	}

	public String getLexema() {
		return lexema;
	}
	
	public int getNumeroToken() {
		return numeroToken;
	}
	

}
