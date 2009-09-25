package proyectocompiladores;

import java.util.HashMap;

public class AnaLex {
	public static final char ENTER = (char) 10;
	
	private int numeroLinea = 1;
	private int numeroColumna = 0;
	private Buffer buffer;
	private HashMap tablaPalabrasReservadas; 
	private String mensajeError;
	
	public AnaLex(String archivoDeEntrada) throws ErrorArchivo {
		buffer = new Buffer();
		inicializarTablaPalabrasReservadas();
		buffer.abrirArchivo(archivoDeEntrada);
	}

	public void inicializarTablaPalabrasReservadas () {
        tablaPalabrasReservadas = new HashMap(19);
        tablaPalabrasReservadas.put ("PROGRAM", Token.PROGRAM);  
        tablaPalabrasReservadas.put ("BEGIN", Token.BEGIN);   
        tablaPalabrasReservadas.put ("END", Token.END);   
        tablaPalabrasReservadas.put ("AND", Token.AND);   
        tablaPalabrasReservadas.put ("OR", Token.OR);    
        tablaPalabrasReservadas.put ("DIV", Token.DIV);   
        tablaPalabrasReservadas.put ("NOT", Token.NOT);  
        tablaPalabrasReservadas.put ("VAR", Token.VAR);  
        tablaPalabrasReservadas.put ("CONST", Token.CONST);  
        tablaPalabrasReservadas.put ("TYPE", Token.TYPE);  
        tablaPalabrasReservadas.put ("FUNCTION", Token.FUNCTION);  
        tablaPalabrasReservadas.put ("PROCEDURE", Token.PROCEDURE);  
        tablaPalabrasReservadas.put ("WHILE", Token.WHILE);  
        tablaPalabrasReservadas.put ("DO", Token.DO);   
        tablaPalabrasReservadas.put ("ARRAY", Token.ARRAY);   
        tablaPalabrasReservadas.put ("OF", Token.OF);   
        tablaPalabrasReservadas.put ("IF", Token.IF);   
        tablaPalabrasReservadas.put ("THEN", Token.THEN);   
        tablaPalabrasReservadas.put ("ELSE", Token.ELSE);         
     }    
	
	private boolean esSeparador (char caracter) {
        if (caracter == ENTER) 
        {
            numeroLinea++;
            numeroColumna = 0;
            return(true);             
        }
        else
            return Character.isWhitespace(caracter);
    }
	
	private boolean caracterValido(char caracter)
    {
       boolean resultado = false;
       
       switch (caracter)
       {
           case '.': resultado = true; break;
           case ',': resultado = true; break;
           case ';': resultado = true; break;
           case ':': resultado = true; break;
           case '+': resultado = true; break;
           case '-': resultado = true; break;
           case '*': resultado = true; break;
           case '(': resultado = true; break;
           case ')': resultado = true; break;
           case '[': resultado = true; break;
           case ']': resultado = true; break;
           case '{': resultado = true; break;
           case '}': resultado = true; break;
           case '>': resultado = true; break;
           case '<': resultado = true; break;
           case '=': resultado = true; break;
           case '\'': resultado = true; break;           
       }
       return resultado;
    }
	
	public int getCantidadLineas() {
        return numeroLinea;
    }
         
    public int getCantidadColumnas() {
        return numeroColumna;
    }      
	
	private int procesarEstado0 (char caracter) {
		int estadoSiguiente;
		
		if (esSeparador(caracter))			//enter o espacio en blanco.
			estadoSiguiente = 1;
		else if (Character.isLetter(caracter) || caracter == '_')	//letra o guión bajo.
			estadoSiguiente = 2;
		else if (Character.isDigit(caracter))	//digito.
			estadoSiguiente = 3;
		else if (caracter == '+')			//signo +.
			estadoSiguiente = 4;
		else if (caracter == '-')			//signo -.
			estadoSiguiente = 5;
		else if (caracter == '*')			//signo *.
			estadoSiguiente = 6;
		else if (caracter == '(')			//parentesis abierto.
			estadoSiguiente = 7;
		else if (caracter == ')')			//parentesis cerrado.
			estadoSiguiente = 8;
		else if (caracter == '[')			//corchete abierto.
			estadoSiguiente = 9;
		else if (caracter == ']')			//corchete cerrado.
			estadoSiguiente = 10;
		else if (caracter == '.')			//punto.
			estadoSiguiente = 11;
		else if (caracter == ',')			//coma.
			estadoSiguiente = 12;
		else if (caracter == ';')			//punto y coma.
			estadoSiguiente = 13;
		else if (caracter == ':')			//dos puntos.
			estadoSiguiente = 14;
		else if (caracter == '=')			//operador igual.
			estadoSiguiente = 15;
		else if (caracter == '<')			//operador menor.
			estadoSiguiente = 16;
		else if (caracter == '>')			//operador mayor.
			estadoSiguiente = 17;
		else if (caracter == '{')			//llave abierta.
			estadoSiguiente = 18;
		else if (caracter == '}')			//llave cerrada.
			estadoSiguiente = -3;
		else if (caracter == buffer.EOF)	//finalizo el archivo de entrada.
			estadoSiguiente = -1;
		else								//caracter inválido.
			estadoSiguiente = -2;
	
		return estadoSiguiente;
	}
	
	private int procesarEstado1 (char caracter){
		int estadoSiguiente;
		String lexema;
		
		if (esSeparador (caracter))		//enter o espacio en blanco.
			estadoSiguiente = 1;
		else {							//otro caracter o EOF.
			buffer.retrocederLexema();	//coloca el buffer en la primera posicion del siguiente token.
			numeroColumna--;
			lexema = buffer.getLexema();//saca los separadores del buffer.
			estadoSiguiente = 0;
		}
			
		return estadoSiguiente;
	}
	
	private int procesarEstado2 (char caracter) {
		int estadoSiguiente;
		
		if (Character.isLetterOrDigit(caracter) || caracter == '_') //lee un identificador (letra, guion bajo o digito).
			estadoSiguiente = 2;
		else if (esSeparador(caracter) || caracterValido(caracter) || caracter == buffer.EOF) //caracteres que pueden ir pegados a un identificador.
			estadoSiguiente = 19;
		else				//caracter invalido.
			estadoSiguiente = -2;
		
		return estadoSiguiente;
	}
	
	private int procesarEstado3 (char caracter) {
		int estadoSiguiente;
		
		if (Character.isDigit(caracter))	//lee un identificador.
			estadoSiguiente = 3;
		else if (esSeparador(caracter) || caracterValido(caracter) || caracter == buffer.EOF) //caracteres que pueden ir pegados a un numero.
			estadoSiguiente = 20;
		else
			estadoSiguiente = -4;			//caracter invalido para un numero.
		
		return estadoSiguiente;
	}
	
	private int procesarEstado18 (char caracter) throws ErrorLexico, ErrorArchivo { 
		int estadoSiguiente;
		String lexema;
		
		if (caracter == '}') {	//cierra el comentario.
			lexema = buffer.getLexema();		//saca la cadena de comentarios del buffer.
			estadoSiguiente = 0;				//vuelve al estado inicial.
		}
		else if (caracter == buffer.EOF) {		//finalizo el archivo de entrada antes de cerrar el comentario.
			lexema = buffer.getLexema();
			buffer.cerrarArchivo();
			mensajeError = ErrorLexico.mensajeError(numeroLinea, numeroColumna, ErrorLexico.COMENTARIO_ABIERTO, lexema);
			throw new ErrorLexico(mensajeError);
		}
		else {									//otro caracter dentro del comentario.
			esSeparador(caracter);
			estadoSiguiente = 18;
		}
		return estadoSiguiente;
	}
	
	private int procesarEstado21(char caracter) throws ErrorLexico, ErrorArchivo {
		int estadoSiguiente;
		char caracterAuxiliar;
		String lexema;
		
		if (caracter == '*') {
			caracterAuxiliar = buffer.proximoCaracter();
			if (caracterAuxiliar == ')') {
				lexema = buffer.getLexema();	//saca la cadena de comentarios del buffer.
				estadoSiguiente = 0;
			}
			else {
				estadoSiguiente = 21;
			}
		}
		else if (caracter == buffer.EOF) {		//finalizo el archivo de entrada que cierra el comentario.
			lexema = buffer.getLexema();
			buffer.cerrarArchivo();
			mensajeError = ErrorLexico.mensajeError (numeroLinea, numeroColumna, ErrorLexico.COMENTARIO_ABIERTO, lexema);
			throw new ErrorLexico(mensajeError);
		}
		else {
			esSeparador(caracter);
			estadoSiguiente = 21;
		}
		
		return estadoSiguiente;
	}
	
	public Token getToken() throws ErrorLexico, ErrorArchivo {
		int estado = 0;				//estado del automata finito.
		char caracter = ' '; 		//ultimo caracter leido.
		String lexema;				//es el lexema leido del buffer.
		Token token = null;			//es el token encontrado.
		boolean terminar = false; 	//indica cuando no tiene que seguir con el ciclo while.
		int valor;
		
		while (!terminar) {
			switch (estado) {
				
			case 0: {//estado inicial, o leyó enter o blanco. 
				caracter = buffer.proximoCaracter();
				numeroColumna++;
				estado = procesarEstado0(caracter);
				break;
			}
			case 1: {//salta los separadores.
				caracter = buffer.proximoCaracter();
				numeroColumna++;
				estado = procesarEstado1(caracter);
				break;
			}
			case 2: {//leyó una letra: posible identificador.
				caracter = buffer.proximoCaracter();
				numeroColumna++;
				estado = procesarEstado2(caracter);
				break;
			}
			case 3: {//leyó un digito: posible numero.
				caracter = buffer.proximoCaracter();
				numeroColumna++;
				estado = procesarEstado3(caracter);
				break;
			}
			case 4: {//leyó "+" y recupera el token "SUMA".
				lexema = buffer.getLexema();
				token = new Token (Token.SUMA, lexema, numeroLinea, numeroColumna);
				terminar = true;
				break;
			}
			case 5: {//leyo "-" y recupera el token "RESTA".
				lexema = buffer.getLexema();
				token = new Token (Token.RESTA, lexema, numeroLinea, numeroColumna);
				terminar = true;
				break;
			}
			case 6: {//leyo "*" y recupera el token "MULTIPLICACION".
				lexema = buffer.getLexema();
				token = new Token (Token.MULTIPLICACION, lexema, numeroLinea, numeroColumna);
				terminar = true;
				break;
			}
			case 7: {//leyo un "(" y recupera el token "PARENTESISABRE" o saltea un comentario.
				caracter = buffer.proximoCaracter();
				numeroColumna++;
				if (caracter == '*')		//se leyó un * luego de un "(" entonces es un comentario.
					estado = 21;
				else {
					buffer.retrocederLexema();
					numeroColumna--;
					lexema = buffer.getLexema();
					token = new Token(Token.PARENTESISABRE, lexema, numeroLinea, numeroColumna);
					terminar = true;
				}
				break;
			}
			case 8: { //leyó un ")" y recupera el token "PARENTESICIERRA"
				lexema = buffer.getLexema();
				token = new Token (Token.PARENTESISCIERRA,lexema,numeroLinea,numeroColumna);
				terminar = true;
				break;
			}
			case 9: {// leyó '[' recupera el token "CORCHETEABRE"
				lexema = buffer.getLexema();
				token = new Token (Token.CORCHETEABRE,lexema,numeroLinea,numeroColumna);
				terminar = true;
				break;
			}
			case 10: {// leyó ']' recupera el token "CORCHETECIERRA"
				lexema = buffer.getLexema();
				token = new Token (Token.CORCHETECIERRA,lexema,numeroLinea,numeroColumna);
				terminar = true;
				break;
			}
			case 11: { //leyó "." recupera el token PUNTO o PUNTOPUNTO
				caracter = buffer.proximoCaracter();
				numeroColumna++;
				if (caracter == '.'){  // se leyó otro punto retorna el token PUNTOPUNTO de subrango
			     
			            lexema = buffer.getLexema();
			            token = new Token(Token.PUNTOPUNTO, lexema, numeroLinea, numeroColumna);
			    }
			    else {
			            buffer.retrocederLexema();
			            numeroColumna--;
			            lexema = buffer.getLexema();
			            token = new Token(Token.PUNTO, lexema, numeroLinea, numeroColumna);
			    }
				terminar = true;
				break;
			}
			case 12: { // leyó "," recupera el token: "COMA"
                lexema = buffer.getLexema();
                token = new Token (Token.COMA, lexema, numeroLinea, numeroColumna);                    
                terminar = true;
                break;
            }
			 case 13: { // leyó ";" recupera el token: "PUNTOCOMA"
                 lexema = buffer.getLexema();
                 token = new Token (Token.PUNTOCOMA, lexema, numeroLinea, numeroColumna);                    
                 terminar = true;
                 break;
             }
			 case 14: { //leyó ":" recupera el token "DOSPUNTOS" o "ASIGNACION"
				 caracter = buffer.proximoCaracter();
				 numeroColumna++;
				 if (caracter == '='){  // se leyó un IGUAL luego de un DOSPUNTOS retorna el token ASIGNACION
			            lexema = buffer.getLexema();
			            token = new Token(Token.ASIGNACION, lexema, numeroLinea, numeroColumna);
			     }
			     else {
			           buffer.retrocederLexema();
			           numeroColumna--;
			           lexema = buffer.getLexema();
			           token = new Token(Token.DOSPUNTOS, lexema, numeroLinea, numeroColumna);
			     }
				 terminar = true;
				 break;
			 }
			 case 15: { // leyó "=" y recupera el token "IGUAL"
                 lexema = buffer.getLexema();
                 token = new Token (Token.IGUAL, lexema, numeroLinea, numeroColumna);                    
                 terminar = true;
                 break;
             }  
			 case 16: { //leyó "<" retornara el token "MENOR" o "MENOROIGUAL" o "DISTINTO"
				 caracter = buffer.proximoCaracter();
				 numeroColumna++;
				 if (caracter == '=') {  // se leyó un IGUAL luego de un MENOR token MENOROIGUAL		 
			            lexema = buffer.getLexema();
			            token = new Token(Token.MENOROIGUAL, lexema, numeroLinea, numeroColumna);
			        }
				 else if (caracter == '>') {  //se leyó un MAYOR luego de un menor token DISTINTO
					 	lexema = buffer.getLexema();
					 	token = new Token(Token.DISTINTO, lexema, numeroLinea, numeroColumna);
				 }
				 else		//se leyó un caracter cualquiera token MENOR
			        {
			            buffer.retrocederLexema();
			            numeroColumna--;
			            lexema = buffer.getLexema();
			            token = new Token(Token.MENOR, lexema, numeroLinea, numeroColumna);
			        }
				 break;
			        
			 }
			 case 17: { //leyó ">" y recupera el token "MAYOR".
				 caracter = buffer.proximoCaracter();
				 numeroColumna++;
				 if (caracter == '=') {  // se leyó un IGUAL luego de un MAYOR token MAYOROIGUAL		 
			            lexema = buffer.getLexema();
			            token = new Token(Token.MAYOROIGUAL, lexema, numeroLinea, numeroColumna);
			        }
				 else		//se leyó un caracter cualquiera token MAYOR
			        {
			            buffer.retrocederLexema();
			            numeroColumna--;
			            lexema = buffer.getLexema();
			            token = new Token(Token.MAYOR, lexema, numeroLinea, numeroColumna);
			        }
				 break;
			 }
			 case 18: { //leyó un "{" entonces saltea el comentario.
				 caracter = buffer.proximoCaracter();
				 numeroColumna++;
				 estado = procesarEstado18(caracter);
				 break;
			 }
			 case 19: { //recupera el token de un IDENTIFICADOR o una PALABRARESERVADA.
				 buffer.retrocederLexema();
				 numeroColumna--;
				 lexema = buffer.getLexema();
				 
				 if (tablaPalabrasReservadas.containsKey(lexema.toUpperCase())) {
					 valor = tablaPalabrasReservadas.get(lexema.toUpperCase()).hashCode();	//recupera el valor del token con clave lexema (en mayuscula).
					 token = new Token (valor, lexema, numeroLinea, numeroColumna);
				 }
				 else {
					 token = new Token(Token.IDENTIFICADOR, lexema, numeroLinea, numeroColumna);
				 }
				 terminar = true;
                 break;
			 }
			 case 20: { // recupera el token NUMERO
				 buffer.retrocederLexema();
				 numeroColumna--;
				 lexema = buffer.getLexema();
				 token = new Token(Token.NUMERO, lexema, numeroLinea, numeroColumna);
				 terminar = true;
				 break;
			 }
			 case 21: { // leyó un (* salteamos los comentarios
				 caracter = buffer.proximoCaracter();
				 estado = procesarEstado21(caracter);
				 break;
			 }
			 case -1: { //finalizo el archivo de entrada.
				 buffer.cerrarArchivo();
				 token = new Token (Token.EOF, "FIN DE ARCHIVO", numeroLinea, numeroColumna);
				 terminar = true;
				 break;
			 }
			 case -2: { //error lexico: CARACTER INVALIDO.
				 buffer.cerrarArchivo();
				 mensajeError = ErrorLexico.mensajeError(numeroLinea, numeroColumna, ErrorLexico.CARACTER_INVALIDO, caracter);
				 throw new ErrorLexico(mensajeError);
			 }
			 case -3: { //error lexico: leyó un "}" antes que un "{".
				 buffer.cerrarArchivo();
				 mensajeError = ErrorLexico.mensajeError(numeroLinea, numeroColumna, ErrorLexico.COMENTARIO_CERRADO, caracter);
				 throw new ErrorLexico(mensajeError);
			 }
			 case -4: { //error lexico: NUMERO INVALIDO.
				 lexema = buffer.getLexema();
				 buffer.cerrarArchivo();
				 mensajeError = ErrorLexico.mensajeError(numeroLinea, numeroColumna, ErrorLexico.NUMERO_INVALIDO, caracter);
				 throw new ErrorLexico(mensajeError);
			 }
			}
		}
		return token;
	}

}
