package proyectocompiladores;

public class Anasint {
	
	AnaLex analizadorLexico;
    private Token tokenActual;
    private int numeroTokenActual;
	
    public Anasint(AnaLex AL) throws ErrorLexico, ErrorArchivo
    {
        analizadorLexico = AL;
        numeroTokenActual = analizadorLexico.getToken().getNumeroToken();
    }
    
	private void match (int numeroToken) throws ErrorLexico, ErrorArchivo, ErrorSintactico {
		Token tokenEsperado;
		int lineaError, columnaError;
		String lexemaEncontrado, lexemaEsperado, mensajeError;
		
		if (numeroTokenActual == numeroToken) {
			tokenActual = analizadorLexico.getToken();
			numeroTokenActual = tokenActual.getNumeroToken();
			
		}
		else {
			tokenEsperado = new Token(numeroToken, "", analizadorLexico.getCantidadLineas(), analizadorLexico.getCantidadColumnas());
			columnaError = tokenActual.getNumeroColumna();
			lineaError = tokenActual.getNumeroLinea();						
			lexemaEncontrado = tokenActual.getLexema();
			lexemaEsperado = tokenEsperado.getDescripcion();
			mensajeError = ErrorSintactico.mensajeError(lineaError,columnaError,lexemaEsperado,lexemaEncontrado);
			throw new ErrorSintactico(mensajeError);
		}
	}
	
	public void analizarSintaxis () throws ErrorLexico, ErrorArchivo, ErrorSintactico {
		programa();
		match(Token.EOF);
	}
	
	private void programa () throws ErrorLexico, ErrorArchivo, ErrorSintactico {
		encabezadoPrograma();
		bloque();
		match(Token.PUNTO);
	}
	
	private void encabezadoPrograma () throws ErrorLexico, ErrorArchivo, ErrorSintactico {
		match(Token.PROGRAM);
		match(Token.IDENTIFICADOR);
		match(Token.PUNTOCOMA);
	}
	
	private void bloque () throws ErrorLexico, ErrorArchivo, ErrorSintactico {
		bloqueDefinicionConstante();
		bloqueDefinicionTipo();
		bloqueDeclaracionVariable();
		bloqueDeclaracionProcYFun();
		bloqueSentencia();
	} 
	
	private void bloqueDefinicionConstante () throws ErrorLexico, ErrorArchivo, ErrorSintactico {
		if (numeroTokenActual == Token.CONST) {
			match(Token.CONST);
			definicionConstante();
			match(Token.PUNTOCOMA);
			restoDefinicionConstante();
		}
	}
	
	private void definicionConstante() throws ErrorLexico, ErrorArchivo, ErrorSintactico {
		match(Token.IDENTIFICADOR);
		match(Token.IGUAL);
		constante();
	}
	
	private void constante () throws ErrorLexico, ErrorArchivo, ErrorSintactico {
		if (numeroTokenActual == Token.NUMERO)
			match(Token.NUMERO);
		else if (numeroTokenActual == Token.IDENTIFICADOR)
			match(Token.IDENTIFICADOR);
		else if (numeroTokenActual == Token.SUMA) {
			match(Token.SUMA); 
			constanteFac(); 
			}
		else {
			match(Token.RESTA); 
			constanteFac();
			}
			
	}
	
	private void constanteFac () throws ErrorLexico, ErrorArchivo, ErrorSintactico {
		if (numeroTokenActual == Token.NUMERO) {
			match(Token.NUMERO);
		}
		else {
			match(Token.IDENTIFICADOR);
		}
	}
	
	private void restoDefinicionConstante () throws ErrorLexico, ErrorArchivo, ErrorSintactico {
		if (numeroTokenActual == Token.IDENTIFICADOR) {
			definicionConstante();
			match(Token.PUNTOCOMA);
			restoDefinicionConstante();
		}
	}
	
	private void bloqueDefinicionTipo () throws ErrorLexico, ErrorArchivo, ErrorSintactico {
		if (numeroTokenActual == Token.TYPE) {
			match(Token.TYPE);
			definicionTipo();
			match(Token.PUNTOCOMA);
			restoDefinicionTipo();
		}
	}
	
	private void definicionTipo () throws ErrorLexico, ErrorArchivo, ErrorSintactico {
		match(Token.IDENTIFICADOR);
		match(Token.IGUAL);
		tipo();
	}
	
	private void tipoSimple () throws ErrorLexico, ErrorArchivo, ErrorSintactico {
		if (numeroTokenActual == Token.NUMERO) {
			match(Token.NUMERO);
			match(Token.PUNTOPUNTO);
			constante();
		}
		else if (numeroTokenActual == Token.IDENTIFICADOR) {
			match(Token.IDENTIFICADOR);
			tipoSimpleFac();
		}
		else if (numeroTokenActual == Token.SUMA) {
			match(Token.SUMA);
			constanteFac();
			match(Token.PUNTOPUNTO);
			constante();
		}
		else {
			match(Token.RESTA);
			constanteFac();
			match(Token.PUNTOPUNTO);
			constante();	
		}
	}
	
	private void tipoSimpleFac () throws ErrorLexico, ErrorArchivo, ErrorSintactico {
		if (numeroTokenActual == Token.PUNTOPUNTO) {
			match(Token.PUNTOPUNTO);
			constante();
		}
	}
	
	private void tipo () throws ErrorLexico, ErrorArchivo, ErrorSintactico {
		if (numeroTokenActual == Token.ARRAY) {
			match(Token.ARRAY);
			match(Token.CORCHETEABRE);
			tipoSimple();
			match(Token.CORCHETECIERRA);
			match(Token.OF);
			tipoSimple();			
		}
		else 
			tipoSimple();
	}
	
	private void restoDefinicionTipo () throws ErrorLexico, ErrorArchivo, ErrorSintactico {
		if (numeroTokenActual == Token.IDENTIFICADOR) {
			definicionTipo();
			match(Token.PUNTOCOMA);
			restoDefinicionTipo();
		}
	}
	
	private void bloqueDeclaracionVariable () throws ErrorLexico, ErrorArchivo, ErrorSintactico {
		if (numeroTokenActual == Token.VAR) {
			match(Token.VAR);
			declaracionVariable();
			match(Token.PUNTOCOMA);
			restoDeclaracionVariable();
		}
	}
	
	private void declaracionVariable () throws ErrorLexico, ErrorArchivo, ErrorSintactico {
		match(Token.IDENTIFICADOR);
		declaracionVariableFac();
		match(Token.DOSPUNTOS);
		tipo();
	}
	
	private void declaracionVariableFac () throws ErrorLexico, ErrorArchivo, ErrorSintactico {
		if (numeroTokenActual == Token.COMA) {
			match(Token.COMA);
			match(Token.IDENTIFICADOR);
			declaracionVariableFac();
		}
	}
	
		
	private void restoDeclaracionVariable () throws ErrorLexico, ErrorArchivo, ErrorSintactico {
		if (numeroTokenActual == Token.IDENTIFICADOR) {
			declaracionVariable();
			match(Token.PUNTOCOMA);
			restoDeclaracionVariable ();
		}
	}
	
	private void bloqueDeclaracionProcYFun () throws ErrorLexico, ErrorArchivo, ErrorSintactico {
		if (numeroTokenActual == Token.PROCEDURE) {
			match(Token.PROCEDURE);
			match(Token.IDENTIFICADOR);
			encabezadoProcedimiento();
			bloque();
			match(Token.PUNTOCOMA);
			bloqueDeclaracionProcYFun();
		}
		else if (numeroTokenActual == Token.FUNCTION) {
			match(Token.FUNCTION);
			match(Token.IDENTIFICADOR);
			encabezadoFuncion();
			bloque();
			match(Token.PUNTOCOMA);
			bloqueDeclaracionProcYFun();
		}
	}
		
	
	private void encabezadoProcedimiento () throws ErrorLexico, ErrorArchivo, ErrorSintactico {
		if (numeroTokenActual == Token.PUNTOCOMA) {
			match(Token.PUNTOCOMA);			
		}
		else {
			match(Token.PARENTESISABRE);
			seccionParametrosFormales();
			match(Token.PARENTESISCIERRA);
			match(Token.PUNTOCOMA);
		}
	}
	
	private void seccionParametrosFormales() throws ErrorLexico, ErrorArchivo, ErrorSintactico {
		seccionParametroFormal();
		restoParametrosFormales();
	}
	
	private void seccionParametroFormal () throws ErrorLexico, ErrorArchivo, ErrorSintactico {
		if (numeroTokenActual == Token.VAR) {
			match(Token.VAR);
			grupoParametros();
		}
		else {
			grupoParametros();
		}
	}
	
	private void grupoParametros () throws ErrorLexico, ErrorArchivo, ErrorSintactico {
		identificadores();
		match(Token.DOSPUNTOS);
		match(Token.IDENTIFICADOR);
	}
	
	private void identificadores () throws ErrorLexico, ErrorArchivo, ErrorSintactico {
		match(Token.IDENTIFICADOR);
		identificadoresFac();
	}
	
	private void identificadoresFac () throws ErrorLexico, ErrorArchivo, ErrorSintactico {
		if (numeroTokenActual == Token.COMA) {
			match(Token.COMA);
			identificadores();
		}
	}
	
	private void restoParametrosFormales () throws ErrorLexico, ErrorArchivo, ErrorSintactico {
		if (numeroTokenActual == Token.PUNTOCOMA) {
			match(Token.PUNTOCOMA);
			seccionParametrosFormales();
		}
	}
	
	
	private void encabezadoFuncion () throws ErrorLexico, ErrorArchivo, ErrorSintactico {
		if (numeroTokenActual == Token.DOSPUNTOS) {
			match(Token.DOSPUNTOS);
			tipo();
			match(Token.PUNTOCOMA);
		}
		else {
			match(Token.PARENTESISABRE);
			seccionParametrosFormales();
			match(Token.PARENTESISCIERRA);
			match(Token.DOSPUNTOS);
			tipo();
			match(Token.PUNTOCOMA);
		}
	}
	
	private void bloqueSentencia () throws ErrorLexico, ErrorArchivo, ErrorSintactico {
		match(Token.BEGIN);
		sentencia();
		restoSentencias();
		match(Token.END);
	}
	
	private void sentencia () throws ErrorLexico, ErrorArchivo, ErrorSintactico {
		if (numeroTokenActual == Token.IDENTIFICADOR) {
			match(Token.IDENTIFICADOR);
			sentenciaSimple();
		}
		else if (numeroTokenActual == Token.BEGIN) {
			match(Token.BEGIN);
			sentencia();
			restoSentencias();
			match(Token.END);
		}
		else if (numeroTokenActual == Token.IF) {
			match(Token.IF);
			expresion();
			match(Token.THEN);
			sentencia();
			restoSentenciaIf();
		}
		else if (numeroTokenActual == Token.WHILE) {
			match(Token.WHILE);
			expresion();
			match(Token.DO);
			sentencia();
		}
	}
	
	private void restoSentencias () throws ErrorLexico, ErrorArchivo, ErrorSintactico {
		if (numeroTokenActual == Token.PUNTOCOMA) {
			match(Token.PUNTOCOMA);
			sentencia();
			restoSentencias();
		}
	}
	
	private void restoSentenciaIf () throws ErrorLexico, ErrorArchivo, ErrorSintactico {
		if (numeroTokenActual == Token.ELSE) {
			match(Token.ELSE);
			sentencia();
		}
	}
	
	private void sentenciaSimple () throws ErrorLexico, ErrorArchivo, ErrorSintactico {
		if (numeroTokenActual == Token.ASIGNACION) {
			match(Token.ASIGNACION);
			expresion();
		}
		else if (numeroTokenActual == Token.PARENTESISABRE) {
			match(Token.PARENTESISABRE);
			parametrosActuales();
			match(Token.PARENTESISCIERRA);
		}
		else if (numeroTokenActual == Token.CORCHETEABRE) {
			match(Token.CORCHETEABRE);
			expresion();
			match(Token.CORCHETECIERRA);
			match(Token.ASIGNACION);
			expresion();
		}
	}
	
	private void expresion () throws ErrorLexico, ErrorArchivo, ErrorSintactico {
		expresionSimple();
		expresionFac();
	}
	
	private void expresionSimple () throws ErrorLexico, ErrorArchivo, ErrorSintactico {
		if (numeroTokenActual == Token.SUMA) {
			match(Token.SUMA);
			termino();
			expresionSimpleFac();
		}
		else if (numeroTokenActual == Token.RESTA) {
			match(Token.RESTA);
			termino();
			expresionSimpleFac();
		}
		else {
			termino();
			expresionSimpleFac();
		}
	}
	
	private void expresionFac () throws ErrorLexico, ErrorArchivo, ErrorSintactico {
		if (numeroTokenActual == Token.IGUAL) {
			match(Token.IGUAL);
			expresionSimple();
		}
		else if (numeroTokenActual == Token.MAYOR) {
			match(Token.MAYOR);
			expresionSimple();
		}
		else if (numeroTokenActual == Token.MENOR) {
			match(Token.MENOR);
			expresionSimple();
		}
		else if (numeroTokenActual == Token.MAYOROIGUAL) {
			match(Token.MAYOROIGUAL);
			expresionSimple();
		}
		else if (numeroTokenActual == Token.MENOROIGUAL) {
			match(Token.MENOROIGUAL);
			expresionSimple();
		}
		else if (numeroTokenActual == Token.DISTINTO) {
			match(Token.DISTINTO);
			expresionSimple();
		}
	}
	
	private void expresionSimpleFac () throws ErrorLexico, ErrorArchivo, ErrorSintactico {
		if (numeroTokenActual == Token.OR) {
			match(Token.OR);
			termino();
			expresionSimpleFac();
		}
		else if (numeroTokenActual == Token.SUMA) {
			match(Token.SUMA);
			termino();
			expresionSimpleFac();
		}
		else if (numeroTokenActual == Token.RESTA) {
			match(Token.RESTA);
			termino();
			expresionSimpleFac();
		}
	}
	
	private void termino () throws ErrorLexico, ErrorArchivo, ErrorSintactico {
		factor();
		terminoFac();
	}
	
	private void terminoFac () throws ErrorLexico, ErrorArchivo, ErrorSintactico {
		if (numeroTokenActual == Token.MULTIPLICACION) {
			match(Token.MULTIPLICACION);
			factor();
			terminoFac();
		}
		else if (numeroTokenActual == Token.DIV) {
			match(Token.DIV);
			factor();
			terminoFac();
		}
		else if (numeroTokenActual == Token.AND) {
			match(Token.AND);
			factor();
			terminoFac();
		}
	}
	
	private void factor () throws ErrorLexico, ErrorArchivo, ErrorSintactico {
		if (numeroTokenActual == Token.IDENTIFICADOR) {
			match(Token.IDENTIFICADOR);
			factorFac();
		}
		else if (numeroTokenActual == Token.NUMERO) {
			match(Token.NUMERO);
		}
		else if (numeroTokenActual == Token.PARENTESISABRE) {
			match(Token.PARENTESISABRE);
			expresion();
			match(Token.PARENTESISCIERRA);
		}
		else {
			match(Token.NOT);
			factor();
		}
	}
	
	private void factorFac () throws ErrorLexico, ErrorArchivo, ErrorSintactico {
		if (numeroTokenActual == Token.CORCHETEABRE) {
			match(Token.CORCHETEABRE);
			expresion();
			match(Token.CORCHETECIERRA);
		}
		else if (numeroTokenActual == Token.PARENTESISABRE) {
			match(Token.PARENTESISABRE);
			parametrosActuales();
			match(Token.PARENTESISCIERRA);
		}
	}
	
	private void parametrosActuales () throws ErrorLexico, ErrorArchivo, ErrorSintactico {
		parametroActual();
		restoParametrosActuales();
	}
	
	private void parametroActual () throws ErrorLexico, ErrorArchivo, ErrorSintactico {
		expresion();
	}
	
	private void restoParametrosActuales () throws ErrorLexico, ErrorArchivo, ErrorSintactico {
		if (numeroTokenActual == Token.COMA) {
			match(Token.COMA);
			parametrosActuales();
		}
	}
}
