package proyectocompiladores;

public class AnaLex {
	
	private int numeroLinea = 1;
	private int numeroColumna = 0;
	private Buffer buffer;
	
	public AnaLex(String archivoDeEntrada) {
		// TODO Auto-generated constructor stub
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
			estadoSiguiente = -5;			//caracter invalido para un numero.
		
		return
	}
	
	public Token getToken() {
		int estado = 0;				//estado del automata finito.
		char caracter = ' '; 		//ultimo caracter leido.
		String lexema;				//es el lexema leido del buffer.
		Token token = null;			//es el token encontrado.
		boolean terminar = false; 	//indica cuando no tiene que seguir con el ciclo while.
		
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
					estadoSiguiente = 21;
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
				lexema = buffer.lexema();
				token = new Token (Token.PARENTESISCIERRA,lexema,numeroLinea,numeroColumna);
				terminar = true;
				break;
			}
			case 9: {// leyó '[' recupera el token "CORCHETEABRE"
				lexema = buffer.lexema();
				token = new Token (Token.CORCHETEABRE,lexema,numeroLinea,numeroColumna);
				terminar = true;
				break;
			}
			case 10: {// leyó ']' recupera el token "CORCHETECIERRA"
				lexema = buffer.lexema();
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
			            lexema = buffer.lexema();
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
			 case 15: { // leyó "=" => recupera el token: "IGUAL"
                 lexema = buffer.lexema();
                 token = new Token (Token.IGUAL, lexema, numeroLinea, numeroColumna);                    
                 terminar = true;
                 break;
             }  
			 case 16: { //leyó "<" retornara el token "MENOR" o "MENOROIGUAL" o "DISTINTO"
				 caracter = buffer.proximoCaracter();
				 numeroColumna++;
				 if (caracter == '=') {  // se leyó un IGUAL luego de un MENOR token MENOROIGUAL		 
			            lexema = buffer.lexema();
			            token = new Token(Token.MENOROIGUAL, lexema, numeroLinea, numeroColumna);
			        }
			        else
			        {
			            buffer.retrocederLexema();
			            numeroColumna--;
			            lexema = buffer.getLexema();
			            token = new Token(Token.MENOR, lexema, numeroLinea, numeroColumna);
			        }
			        
			 }
		}
		
		return token;
	}

}
