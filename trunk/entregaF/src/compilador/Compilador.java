package compilador;

import generadorDeCodigo.generador;
import lexico.*;
import tablaSimbolos.*;
import xtras.*;
import java.util.*;

public class Compilador {
	
	AnaLex analizadorLexico;
    private Token tokenActual;
    private int numeroTokenActual;
    private tablaSimbolos TS;
    public generador generador;  //parche el public
	public Compilador(String fileIn, String fileOut) throws ErrorLexico, Exception    
    {	
	TS = new tablaSimbolos(this);
	analizadorLexico = new AnaLex(fileIn);
    numeroTokenActual = analizadorLexico.getToken().getNumeroToken();	//pide al Analizador Lexico, el primer TOKEN. // tambien arreglado
	generador = new generador(fileOut);
    }
	
	public void analizar()throws ErrorLexico, ErrorArchivo, ErrorSintactico, ErrorSemantico, Exception
    {
        Programa();
        generador.finalizarGen();
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
	
	public void analizarSintaxis () throws ErrorLexico, ErrorArchivo, ErrorSintactico, ErrorSemantico, Exception {
			Programa();
			match(Token.EOF);			
	}
	
	private void Programa () throws ErrorLexico, ErrorArchivo, ErrorSintactico, ErrorSemantico, Exception {
	sintetizados sintEnc, sintB;
		
		TS.cargarPredefinidos();
		generador.genInstSinArg("", generador.INPP);
		sintEnc = encabezadoPrograma();
	
		sintB = bloque();
		match(Token.PUNTO);
		
		if (sintB.getEspacioS() != 0)
			generador.genInst1ArgCte("", generador.LMEM, sintB.getEspacioS());
		
		generador.genInstSinArg("", generador.PARA);
		TS.desapilarNivel();
	}
	
	private sintetizados encabezadoPrograma () throws ErrorLexico, ErrorArchivo, ErrorSintactico {
	Programa p;
		
		sintetizados sint = new sintetizados();
		
		TS.apilarNivel();
		p = new Programa(TS.getNivelActual());
		
		match(Token.PROGRAM);
		
		sint.setIdS(tokenActual.getLexema());
		
		match(Token.IDENTIFICADOR);
		match(Token.PUNTOCOMA);
		
		return sint;
	}
	
	private sintetizados bloque () throws ErrorLexico, ErrorArchivo, ErrorSintactico, Exception {
	String label;
	int offsetH = 0;
	sintetizados sint = new sintetizados();	
		
		bloqueDefinicionConstante();
		bloqueDefinicionTipo();
		
		sint = bloqueDeclaracionVariable(offsetH);
		
		if (sint.getEspacioS() != 0)
			generador.genInst1ArgCte("", generador.RMEM, sint.getEspacioS());
		
		label = generador.genEtiqueta();
		generador.genInst1ArgEtiq("", generador.DSVS, label);
		
		bloqueDeclaracionProcYFun();
		
		generador.genInstSinArg(label, generador.NADA);
		bloqueSentencia();
		
		return sint;
	} 
	
	private void bloqueDefinicionConstante () throws ErrorLexico, ErrorArchivo, ErrorSintactico, ErrorSemantico {
		if (numeroTokenActual == Token.CONST) {
			match(Token.CONST);
			definicionConstante();
			match(Token.PUNTOCOMA);
			restoDefinicionConstante();
		}
	}
	
	private void definicionConstante() throws ErrorLexico, ErrorArchivo, ErrorSintactico, ErrorSemantico {
	String lex = tokenActual.getLexema();	//ojo retoques mios también..
		entrada entrada = TS.getEntradaNivelActual(lex);
		if (entrada == null){
			sintetizados s = new sintetizados();
			lex = tokenActual.getLexema();
			
			match(Token.IDENTIFICADOR);
			match(Token.IGUAL);
			
			s = Constante();
			Constante c = new Constante(s.getTipoS(),s.getValorS(),TS.getNivelActual());
			TS.insertar(lex,c);						
		}
		else
			throw new ErrorSemantico(ErrorSemantico.ID_DUP,getNumeroLinea(),getNumeroColumna(),tokenActual.getLexema());		
	}
	
	private sintetizados Constante () throws ErrorLexico, ErrorArchivo, ErrorSintactico, ErrorSemantico {
		sintetizados saux = new sintetizados(),s = new sintetizados();	
		String lex = tokenActual.getLexema();
		entrada entrada;
		
		if (numeroTokenActual == Token.NUMERO) {
			s.setTipoS(new Entero(TS.getNivelActual()));
			s.setValorS(Integer.parseInt(lex));
			match(Token.NUMERO);
		}
		else if (numeroTokenActual == Token.IDENTIFICADOR) {
			entrada = TS.getEntrada(lex);	
			if (entrada != null) {
				if (entrada instanceof Constante) {
					 s.setTipoS(((Constante)entrada).getTipo());
					s.setValorS(((Constante)entrada).getValor());
				}
				else {
					String t1=ErrorSemantico.construirMsj(saux.getTipoS().toString());
                    throw new ErrorSemantico(ErrorSemantico.ID_INV,getNumeroLinea(),getNumeroColumna(),t1);
				}					
			}
			else {
					String t1=ErrorSemantico.construirMsj(saux.getTipoS().toString());
                    throw new ErrorSemantico(ErrorSemantico.ID_INV,getNumeroLinea(),getNumeroColumna(),t1);
			}		

			match(Token.IDENTIFICADOR);
			
		}
		else if (numeroTokenActual == Token.SUMA) {
			match(Token.SUMA); 
			saux = ConstanteFac(); 
			if (saux.getTipoS() instanceof Entero){
				s = saux;
			}
			else {
						String t1=ErrorSemantico.construirMsj(saux.getTipoS().toString());
                        throw new ErrorSemantico(ErrorSemantico.TIPO_INV_MAS,getNumeroLinea(),getNumeroColumna(),t1);
            }
		}
		else {
			match(Token.RESTA); 
			saux = ConstanteFac();
			saux.setValorS(-saux.getValorS());
			if (saux.getTipoS() instanceof Entero){
				s = saux;
			}
			else {
						String t1=ErrorSemantico.construirMsj(saux.getTipoS().toString());
                        throw new ErrorSemantico(ErrorSemantico.TIPO_INV_MENOS,getNumeroLinea(),getNumeroColumna(),t1);
            }
			}
		return s;
			
	}
	
	private sintetizados ConstanteFac () throws ErrorLexico, ErrorArchivo, ErrorSintactico, ErrorSemantico {
		sintetizados s = new sintetizados();	
		String lex = tokenActual.getLexema();
		entrada entrada;
		
		if (numeroTokenActual == Token.NUMERO) {
			s.setTipoS(new Entero(TS.getNivelActual()));
			s.setValorS(Integer.parseInt(lex));
			match(Token.NUMERO);
		}
		else {	
			entrada = TS.getEntrada(lex);
			if (entrada != null) {
				if (entrada instanceof Constante) {
					s.setTipoS(((Constante)entrada).getTipo());
					s.setValorS(((Constante)entrada).getValor());
				}
				else 
					throw new ErrorSemantico(ErrorSemantico.ID_INV,getNumeroLinea(),getNumeroColumna(),lex);
			}
			else				
				throw new ErrorSemantico(ErrorSemantico.ID_NO_DEC,getNumeroLinea(),getNumeroColumna(),lex);
			match(Token.IDENTIFICADOR);
		}
		return s;
	}
	
	private void restoDefinicionConstante () throws ErrorLexico, ErrorArchivo, ErrorSintactico, ErrorSemantico {
		if (numeroTokenActual == Token.IDENTIFICADOR) {
			definicionConstante();
			match(Token.PUNTOCOMA);
			restoDefinicionConstante();
		}
	}
	
	private void bloqueDefinicionTipo () throws ErrorLexico, ErrorArchivo, ErrorSintactico, ErrorSemantico {
		if (numeroTokenActual == Token.TYPE) {
			match(Token.TYPE);
			definicionTipo();
			match(Token.PUNTOCOMA);
			restoDefinicionTipo();
		}
	}
	
	private void definicionTipo () throws ErrorLexico, ErrorArchivo, ErrorSintactico, ErrorSemantico  {
		sintetizados s;
		String lex = tokenActual.getLexema();
		match(Token.IDENTIFICADOR);
		match(Token.IGUAL);
		s = tipo();
		entrada entrada;
		
		entrada = TS.getEntradaNivelActual(lex);
		if (entrada == null){
			if (s.getTipoS() instanceof Entero){
				TS.insertar(lex,new Entero(TS.getNivelActual())); }
			else if (s.getTipoS() instanceof Booleano) {
				TS.insertar(lex,new Booleano(TS.getNivelActual())); }
			else if (s.getTipoS() instanceof Subrango) {
				TS.insertar(lex,new Subrango(((Subrango)s.getTipoS()).getLimiteInf(),((Subrango)s.getTipoS()).getLimiteSup(),TS.getNivelActual())); }
			else {
				TS.insertar(lex, new Arreglo(((Arreglo)s.getTipoS()).getTipoElem(),((Arreglo)s.getTipoS()).getTipoIndice().getLimiteInf(),((Arreglo)s.getTipoS()).getTipoIndice().getLimiteSup(),TS.getNivelActual())); 
			}			
		}
		else {
				String t1=ErrorSemantico.construirMsj(s.getTipoS().toString());
                throw new ErrorSemantico(ErrorSemantico.ID_DUP,getNumeroLinea(),getNumeroColumna(),t1);
		}
	}
	
	
	private sintetizados tipoSimple () throws ErrorLexico, ErrorArchivo, ErrorSintactico, ErrorSemantico {
	
	sintetizados s=new sintetizados();
    sintetizados saux,saux2;
    String lex;	
    entrada en;
		
		if (numeroTokenActual == Token.NUMERO) {
			lex = tokenActual.getLexema();			
			match(Token.NUMERO);
			match(Token.PUNTOPUNTO);
			saux = Constante();
			if (saux.getTipoS() instanceof Entero) {
				if (Integer.parseInt(lex) <= saux.getValorS()) {
						s.setTipoS(new Subrango(Integer.parseInt(lex), saux.getValorS(), TS.getNivelActual()));
				}
				else {
						throw new ErrorSemantico(ErrorSemantico.LIM_SUBRANGO_INV, getNumeroLinea(), getNumeroColumna());
				}
			}
			else {
				throw new ErrorSemantico(ErrorSemantico.LIM_ENT, getNumeroLinea(), getNumeroColumna());
			}
		}
		
		else if (numeroTokenActual == Token.IDENTIFICADOR) {
			lex = tokenActual.getLexema();			
			match(Token.IDENTIFICADOR);
			en = TS.getEntrada(lex);
			saux = tipoSimpleFac();
			
			if (en != null) {
				if (saux.getTipoS() == null) {
						if (en instanceof Entero)
							s.setTipoS((Entero)en);
						else if (en instanceof Booleano)
							s.setTipoS((Booleano)en);
						else if (en instanceof Subrango)
							s.setTipoS((Subrango)en);
						else if (en instanceof Arreglo)
							s.setTipoS((Arreglo)en);
						else
							throw new ErrorSemantico(ErrorSemantico.ID_INV,getNumeroLinea(),getNumeroColumna(),lex);
				}
				else {
						if (((Constante)en).getTipo() instanceof Entero) {
								if (((Constante)en).getValor() <= saux.getValorS()) {
									s.setTipoS(new Subrango(((Constante)en).getValor(),saux.getLimiteSupS(),TS.getNivelActual()));
								}
								else {
									throw new ErrorSemantico(ErrorSemantico.LIM_SUBRANGO_INV,getNumeroLinea(),getNumeroColumna());
								}								
						}
						else {
								throw new ErrorSemantico(ErrorSemantico.LIM_ENT,getNumeroLinea(),getNumeroColumna());
						}
				}
			}
			else {
				throw new ErrorSemantico(ErrorSemantico.ID_NO_DEC,getNumeroLinea(),getNumeroColumna());
			}
			
		}
		else if (numeroTokenActual == Token.SUMA) {
			match(Token.SUMA);
			saux = ConstanteFac();
			match(Token.PUNTOPUNTO);
			saux2 = Constante();
			
			if ((saux.getTipoS() instanceof Entero) && (saux2.getTipoS() instanceof Entero)) {
					if (saux.getValorS() <= saux2.getValorS()) {
							s.setTipoS(new Subrango(saux.getValorS(),saux2.getValorS(),TS.getNivelActual()));
					}
					else
							throw new ErrorSemantico(ErrorSemantico.LIM_SUBRANGO_INV,getNumeroLinea(),getNumeroColumna(),Integer.toString(saux.getValorS()),Integer.toString(saux2.getValorS()));
			}
			else {
					String t1,t2;
                    t1=ErrorSemantico.construirMsj(saux.getTipoS().toString());
                    t2=ErrorSemantico.construirMsj(saux2.getTipoS().toString());
                    throw new ErrorSemantico(ErrorSemantico.TIPO_INCOMP,getNumeroLinea(),getNumeroColumna(),t1,t2);
			}
		}
		else {
			match(Token.RESTA);
			saux = ConstanteFac();
			match(Token.PUNTOPUNTO);
			saux2 = Constante();	
			
			if ((saux.getTipoS() instanceof Entero) && (saux2.getTipoS() instanceof Entero)) {
					if ((saux.getValorS()*-1) <= saux2.getValorS()) {
							s.setTipoS(new Subrango(saux.getValorS()*-1,saux2.getValorS(),TS.getNivelActual()));
					}
					else
							throw new ErrorSemantico(ErrorSemantico.LIM_SUBRANGO_INV,getNumeroLinea(),getNumeroColumna(),Integer.toString(saux.getValorS()*-1),Integer.toString(saux2.getValorS()));
			}
			else {
					String t2;
                    t2=ErrorSemantico.construirMsj(saux.getTipoS().toString());
                    throw new ErrorSemantico(ErrorSemantico.TIPO_INCOMP,getNumeroLinea(),getNumeroColumna(),"Entero",t2);
			}
		}
		return s;
	}
	
	private sintetizados tipoSimpleFac () throws ErrorLexico, ErrorArchivo, ErrorSintactico, ErrorSemantico {
	sintetizados s = new sintetizados();
	
		if (numeroTokenActual == Token.PUNTOPUNTO) {
			match(Token.PUNTOPUNTO);
			s = Constante();
			if (s.getTipoS() instanceof Entero) {
					s.setLimiteSupS(s.getValorS());
			}
			else {
					String t = ErrorSemantico.construirMsj(s.getTipoS().toString());
					throw new ErrorSemantico(ErrorSemantico.LIM_ENT,getNumeroLinea(),getNumeroColumna(),t);
			}
		}
		else {
			s.setTipoS(null);
		}
		return s;
	}
	
	private sintetizados tipo () throws ErrorLexico, ErrorArchivo, ErrorSintactico, ErrorSemantico {
	sintetizados s = new sintetizados();
	sintetizados sInd, sComp;
	
		if (numeroTokenActual == Token.ARRAY) {
			match(Token.ARRAY);
			match(Token.CORCHETEABRE);
			sInd = tipoSimple();
			match(Token.CORCHETECIERRA);
			match(Token.OF);
			sComp = tipoSimple();
			if (sInd.getTipoS() instanceof Subrango) {
					s.setTipoS(new Arreglo(sComp.getTipoS(),((Subrango)sInd.getTipoS()).getLimiteInf(),((Subrango)sInd.getTipoS()).getLimiteSup(),TS.getNivelActual()));			
			}
			else {					
					String t = ErrorSemantico.construirMsj(sInd.getTipoS().toString());
					throw new ErrorSemantico(ErrorSemantico.IND_SUBRANGO_INV,getNumeroLinea(),getNumeroColumna(),t);
			}
		}
		else 
			s = tipoSimple();
		
		return s;
	}
	
	private void restoDefinicionTipo () throws ErrorLexico, ErrorArchivo, ErrorSintactico, ErrorSemantico {
		if (numeroTokenActual == Token.IDENTIFICADOR) {
			definicionTipo();
			match(Token.PUNTOCOMA);
			restoDefinicionTipo();
		}
	}
	
	
	private sintetizados bloqueDeclaracionVariable (int offsetH) throws ErrorLexico, ErrorArchivo, ErrorSintactico, ErrorSemantico, Exception {
	sintetizados sint1 = new sintetizados();
	sintetizados sint2 = new sintetizados();
	
		if (numeroTokenActual == Token.VAR) {
			match(Token.VAR);
			sint1 = declaracionVariable(offsetH);
			match(Token.PUNTOCOMA);
			sint2 = restoDeclaracionVariable(sint1.getOffsetS());
			sint1.setEspacioS(sint1.getEspacioS() + sint2.getEspacioS());
		}
		else {
			sint1.setEspacioS(0);
			sint1.setOffsetS(0);
		}
		
		return sint1;
	}
	
	private sintetizados declaracionVariable (int offsetH) throws ErrorLexico, ErrorArchivo, ErrorSintactico, ErrorSemantico {
	sintetizados s = new sintetizados();
	sintetizados sId,sTipo;
    ArrayList lista;
    int offset, espacio = 0;
    entrada en;
	
		String lex = tokenActual.getLexema();
		match(Token.IDENTIFICADOR);
		sId = declaracionVariableFac();
		sId.agregarID(lex);
		match(Token.DOSPUNTOS);
		
		sTipo = tipo();
		offset = offsetH;
		lista = sId.getListaIdsS();
		ListIterator it = lista.listIterator();
		while(it.hasNext()) {
			String id = (String)it.next();
			en = TS.getEntradaNivelActual(id);
			if (en == null) {
				int nivel = TS.getNivelActual();
				TS.insertar(id, new Variable(sTipo.getTipoS(), offset, nivel));
				offset = offset + (int)sTipo.getTipoS().getSize();
				espacio = espacio + (int)sTipo.getTipoS().getSize();
			}
			else {				
				throw new ErrorSemantico(ErrorSemantico.ID_DUP,getNumeroLinea(),getNumeroColumna(),id);
			}
		}
		
		s.setOffsetS(offset);
		s.setEspacioS(espacio);
		
		return s;
	}
	
	private sintetizados declaracionVariableFac () throws ErrorLexico, ErrorArchivo, ErrorSintactico, ErrorSemantico {
	sintetizados s;
	
		if (numeroTokenActual == Token.COMA) {
			match(Token.COMA);
			String lex = tokenActual.getLexema();
			match(Token.IDENTIFICADOR);
			s = declaracionVariableFac();
			s.agregarID(lex);					
		}
		else {
			s=new sintetizados();
            s.setListaIdsS(new ArrayList());
		}
		
		return s;
	}
	
		
	private sintetizados restoDeclaracionVariable (int offsetH) throws ErrorLexico, ErrorArchivo, ErrorSintactico, ErrorSemantico, Exception {
	sintetizados sint1 = new sintetizados();
	sintetizados sint2 = new sintetizados();
	
		if (numeroTokenActual == Token.IDENTIFICADOR) {
			sint1 = declaracionVariable(offsetH);
			match(Token.PUNTOCOMA);
			sint2 = restoDeclaracionVariable (sint1.getOffsetS());
			sint1.setEspacioS(sint1.getEspacioS() + sint2.getEspacioS());
			sint1.setOffsetS(sint2.getOffsetS());
		}
		else {
			sint1.setEspacioS(0);
			sint1.setOffsetS(0);
		}
		
		return sint1;
	}
	
	private void bloqueDeclaracionProcYFun () throws ErrorLexico, ErrorArchivo, ErrorSintactico, ErrorSemantico, Exception {
	String lex,label;	
	sintetizados sintEncab, sintBloque;
	Procedimiento p;
	int longitud;
	entrada entrada;
	Funcion f;
	
		if (numeroTokenActual == Token.PROCEDURE) {
			match(Token.PROCEDURE);
			lex = tokenActual.getLexema();
			match(Token.IDENTIFICADOR);
			System.out.println("llegue 1");
			sintEncab = encabezadoProcedimiento();
			System.out.println("llegue 2");
			longitud = sintEncab.getEspacioS();
			
			entrada = TS.getEntradaNivelActual(lex);
			if ((entrada == null) || (!(entrada instanceof Procedimiento))){
				label = generador.genEtiqueta();
				// suma uno al NIVEL porque el bloque del proc corresponde a un NIVEL más		    
				generador.genInst1ArgCte(label,generador.ENPR, TS.getNivelActual()+1);
				p = new Procedimiento(sintEncab.getListaParametrosFormalesS(),label,TS.getNivelActual());
				TS.insertar(lex,p);	 
				TS.apilarNivel();
				TS.insertarParametros(p.getListaParametrosFormales(),sintEncab.getListaIdsS());		
			 }
			else throw new ErrorSemantico(ErrorSemantico.ID_DUP,getNumeroLinea(),getNumeroColumna(),lex);

			System.out.println("llegue 3");
			sintBloque = bloque();
			match(Token.PUNTOCOMA);
			 
			generador.genInst1ArgCte("",generador.LMEM, sintBloque.getEspacioS());
			
			generador.genInst2ArgCte("",generador.RTPR, TS.getNivelActual(), longitud);
			TS.desapilarNivel();

			bloqueDeclaracionProcYFun();
		}
		else if (numeroTokenActual == Token.FUNCTION) {
			match(Token.FUNCTION);
			lex = tokenActual.getLexema();
			match(Token.IDENTIFICADOR);
			sintEncab = encabezadoFuncion();
			longitud = sintEncab.getOffsetS();
			
			entrada = TS.getEntradaNivelActual(lex);
			if ((entrada == null) || (!(entrada instanceof Funcion))) {
				label = generador.genEtiqueta();
				generador.genInst1ArgCte(label, generador.ENPR, TS.getNivelActual());
				f = new Funcion(sintEncab.getRetornoS(),-(longitud + 3),sintEncab.getListaParametrosFormalesS(),label,TS.getNivelActual());
				TS.insertar(lex,f);	 
				TS.apilarNivel();
				TS.insertarParametros(f.getListaParametrosFormales(),sintEncab.getListaIdsS());
			 }
			else throw new ErrorSemantico(ErrorSemantico.ID_DUP,getNumeroLinea(),getNumeroColumna(),lex);
			
			sintBloque = bloque();
			match(Token.PUNTOCOMA);
			 
			generador.genInst1ArgCte("",generador.LMEM, sintBloque.getEspacioS());
			generador.genInst2ArgCte("",generador.RTPR, TS.getNivelActual(), longitud);
			TS.desapilarNivel();

			bloqueDeclaracionProcYFun();
		}
	}
		
	
	private sintetizados encabezadoProcedimiento () throws ErrorLexico, ErrorArchivo, ErrorSintactico, ErrorSemantico {
	sintetizados s = new sintetizados(); 
		
		if (numeroTokenActual == Token.PUNTOCOMA) {
			match(Token.PUNTOCOMA);	
			s.setOffsetS(0);
			s.setListaFormalesS(new ArrayList());
			s.setListaIdsS(new ArrayList());                    
			s.setEspacioS(0);      
		}
		else {
			match(Token.PARENTESISABRE);
			s = seccionParametrosFormales(1); // <seccion Parametros formales>.offsetH = 1;
			match(Token.PARENTESISCIERRA);
			match(Token.PUNTOCOMA);
		}
		return s;
	}
	
	private sintetizados seccionParametrosFormales(int offsetH) throws ErrorLexico, ErrorArchivo, ErrorSintactico, ErrorSemantico {
	sintetizados sSecPF,sResPF;
		sSecPF = seccionParametroFormal(offsetH);
		sResPF = restoParametrosFormales(sSecPF.getOffsetS());
		sSecPF.setOffsetS(sResPF.getOffsetS());	
		sSecPF.setEspacioS(sSecPF.getEspacioS() + sResPF.getEspacioS());
		//sResPF.getListaParametrosFormalesS().insertar(sSecPF.getListaIdsS(), sSecPF.getTipoS(),true/*,sSecPF.porValor()*/);
		sSecPF.setListaFormalesS(sResPF.getListaParametrosFormalesS());	
		return sSecPF;
	}
	
	private sintetizados seccionParametroFormal (int offsetH) throws ErrorLexico, ErrorArchivo, ErrorSintactico, ErrorSemantico {
	sintetizados sGrupoP;
	ListIterator listIds;
	Parametro param;
	String identificador;
	ArrayList listaF = new ArrayList();
	int espacio = 0;
		
		boolean porValor;
		if (numeroTokenActual == Token.VAR) {
			match(Token.VAR);
			sGrupoP = grupoParametros();
			porValor = false;
		}
		else {
			sGrupoP = grupoParametros();
			porValor = true;
		}
		
		listIds = sGrupoP.getListaIdsS().listIterator();
		
		while (listIds.hasNext()){
			identificador = (String) listIds.next();
			param = new Parametro(sGrupoP.getTipoS(),offsetH,porValor,TS.getNivelActual()+1); 
			listaF.add(param);
			if (porValor) {
				offsetH = offsetH + (int)sGrupoP.getTipoS().getSize();
				espacio = espacio + (int)sGrupoP.getTipoS().getSize();
			} 
			else{
				offsetH++;  
				espacio++;
			}
		}
		sGrupoP.setListaFormalesS(listaF);
		sGrupoP.setOffsetS(offsetH);
		sGrupoP.setEspacioS(espacio);
		return sGrupoP;
	}
	
	private sintetizados grupoParametros () throws ErrorLexico, ErrorArchivo, ErrorSintactico, ErrorSemantico {
		String lex;
		sintetizados sintIds,sintGrupParam =  new sintetizados(); 
		sintIds = identificadores();
		match(Token.DOSPUNTOS);
		lex = tokenActual.getLexema();
		match(Token.IDENTIFICADOR);
		entrada entrada = TS.getEntrada(lex);
		 if (entrada != null){  
			if (entrada instanceof Arreglo){
				sintGrupParam.setListaIdsS(sintIds.getListaIdsS());
				sintGrupParam.setTipoS((Arreglo)entrada);
			} 
			if (entrada instanceof Subrango){
				sintGrupParam.setListaIdsS(sintIds.getListaIdsS());
				sintGrupParam.setTipoS((Subrango)entrada);
			} 
			if (entrada instanceof Entero){
				sintGrupParam.setListaIdsS(sintIds.getListaIdsS());
				sintGrupParam.setTipoS((Entero)entrada);
			} 
			if (entrada instanceof Booleano){
				sintGrupParam.setListaIdsS(sintIds.getListaIdsS());
				sintGrupParam.setTipoS((Booleano)entrada);
			}
		} 
		else throw new ErrorSemantico(ErrorSemantico.ID_INV,getNumeroLinea(),getNumeroColumna(),lex);
		
		return sintGrupParam;

	}
	
	private sintetizados identificadores () throws ErrorLexico, ErrorArchivo, ErrorSintactico, ErrorSemantico {
		sintetizados sintIdFac,sintId = new sintetizados();
		String lex;
		
		lex = tokenActual.getLexema();
		match(Token.IDENTIFICADOR);
		sintIdFac = identificadoresFac();
		
		sintIdFac.agregarID(lex);
		sintId.setListaIdsS(sintIdFac.getListaIdsS());
				
		 return sintId;
	}
	
	private sintetizados identificadoresFac () throws ErrorLexico, ErrorArchivo, ErrorSintactico, ErrorSemantico {
	sintetizados sintId;
		if (numeroTokenActual == Token.COMA) {
			match(Token.COMA);
			sintId = identificadores();
		} 
		else {
			sintId = new sintetizados();
			sintId.setListaIdsS(new ArrayList());
		}
		return sintId;
	}
	
	private sintetizados restoParametrosFormales (int offsetH) throws ErrorLexico, ErrorArchivo, ErrorSintactico, ErrorSemantico {
	sintetizados sintRPF;
		if (numeroTokenActual == Token.PUNTOCOMA) {
			match(Token.PUNTOCOMA);
			sintRPF = seccionParametrosFormales(offsetH);
		}
		else {
			sintRPF = new sintetizados ();
			sintRPF.setOffsetS(0);
			sintRPF.setEspacioS(0); //supongo que esto es lamda y que va a andar :)
			sintRPF.setListaFormalesS(new ArrayList());
		}
		return sintRPF;
	}
	
	
	private sintetizados encabezadoFuncion () throws ErrorLexico, ErrorArchivo, ErrorSintactico, ErrorSemantico {
		sintetizados sintSPF,sintTipo,sintEF = new sintetizados();
		
		if (numeroTokenActual == Token.DOSPUNTOS) {
			match(Token.DOSPUNTOS);
			sintTipo = tipo();
			match(Token.PUNTOCOMA);
			sintEF.setOffsetS(0);
			sintEF.setRetornoS(sintTipo.getRetornoS());
			sintEF.setListaFormalesS(new ArrayList());
			sintEF.setListaIdsS(new ArrayList());
		}
		else {
			match(Token.PARENTESISABRE);
			sintSPF = seccionParametrosFormales(1);
			match(Token.PARENTESISCIERRA);
			match(Token.DOSPUNTOS);
			sintTipo = tipo();
			match(Token.PUNTOCOMA);
			sintEF.setOffsetS(sintSPF.getOffsetS());
			//sintEF.setRetornoS(sintTipo.getRetornoS());
			sintEF.setRetornoS(sintTipo.getTipoS());
			sintEF.setListaFormalesS(sintSPF.getListaParametrosFormalesS());
			sintEF.setListaIdsS(sintSPF.getListaIdsS());
		}
		return sintEF;
	}
	
	
	private void bloqueSentencia () throws ErrorLexico, ErrorArchivo, ErrorSintactico, ErrorSemantico, Exception {
		match(Token.BEGIN);
		sentencia();
		restoSentencias();
		match(Token.END);
	}
	
	private void sentencia () throws ErrorLexico, ErrorArchivo, ErrorSintactico, ErrorSemantico, Exception {
	String idH, labelH, labelH2;
	boolean porValorH;
	sintetizados sint;
	
		if (numeroTokenActual == Token.IDENTIFICADOR) {		
			idH = tokenActual.getLexema();			
			match(Token.IDENTIFICADOR);
			sentenciaSimple(idH);
		}
		else if (numeroTokenActual == Token.BEGIN) {
			match(Token.BEGIN);
			sentencia();
			restoSentencias();
			match(Token.END);
		}
		else if (numeroTokenActual == Token.IF) {
			match(Token.IF);
			porValorH = true;
			sint = expresion(porValorH, "");  //idPredefH = ""
			match(Token.THEN);
			
			if (sint.getTipoS() instanceof Booleano) {
				labelH = generador.genEtiqueta();
				generador.genInst1ArgEtiq("", generador.DSVF, labelH);
			}
			else {
				String t=ErrorSemantico.construirMsj(sint.getTipoS().toString());
                throw new ErrorSemantico(ErrorSemantico.TIPO_INCOMP, getNumeroLinea(), getNumeroColumna(),"Booleano",t);
			}
			
			sentencia();			
			restoSentenciaIf(labelH);
		}
		else if (numeroTokenActual == Token.WHILE) {
			match(Token.WHILE);
			labelH = generador.genEtiqueta();
			generador.genInstSinArg(labelH, generador.NADA);
			porValorH = true;
			sint = expresion(porValorH, "");	//idPredefH = ""
			
			if (sint.getTipoS() instanceof Booleano) {
				labelH2 = generador.genEtiqueta();
				generador.genInst1ArgEtiq("",generador.DSVF,labelH2);
			}
			else {
				String t=ErrorSemantico.construirMsj(sint.getTipoS().toString());
                throw new ErrorSemantico(ErrorSemantico.TIPO_INCOMP, getNumeroLinea(), getNumeroColumna(),"Booleano",t);
			}
			
			match(Token.DO);
			sentencia();
			generador.genInst1ArgEtiq("",generador.DSVS, labelH);
			generador.genInstSinArg(labelH2, generador.NADA);
		}
	}
	
	private void restoSentencias () throws ErrorLexico, ErrorArchivo, ErrorSintactico, ErrorSemantico, Exception {
		if (numeroTokenActual == Token.PUNTOCOMA) {
			match(Token.PUNTOCOMA);
			sentencia();
			restoSentencias();
		}
	}
	
	private void restoSentenciaIf (String labelH) throws ErrorLexico, ErrorArchivo, ErrorSintactico, Exception {
	String label2;
	
		if (numeroTokenActual == Token.ELSE) {
			match(Token.ELSE);
			label2 = generador.genEtiqueta();
			generador.genInst1ArgEtiq("", generador.DSVS, label2);
			generador.genInstSinArg(labelH, generador.NADA);
			sentencia();
			generador.genInstSinArg(label2, generador.NADA);
		}
		else
			generador.genInstSinArg(labelH, generador.NADA);
	}
	
	private void sentenciaSimple (String idH) throws ErrorLexico, ErrorArchivo, ErrorSintactico, ErrorSemantico, Exception {
	
	boolean porValorH, predefH = true;
	sintetizados sint, sint2, sint3 = null;
	entrada ent;
	int li, ls, nivel, off, longitud;
	ArrayList listaFormalesH = null;
	Arreglo arr;
	tipo tipoH;
	
		if (numeroTokenActual == Token.ASIGNACION) {
			ent = TS.getEntrada(idH);
			match(Token.ASIGNACION);
			porValorH = true;
			
			if ((ent instanceof Variable) || (ent instanceof Parametro)) {								
						if ((((Variable)ent).getTipo() instanceof Entero) || (((Variable)ent).getTipo() instanceof Booleano) || (((Variable)ent).getTipo() instanceof Subrango)) {
							sint = expresion(porValorH, "");
								if (compatibles(((Variable)ent).getTipo(), sint.getTipoS())) {
										if (((Variable)ent).getTipo() instanceof Subrango) {
												li = ((Subrango)((Variable)ent).getTipo()).getLimiteInf();
												ls = ((Subrango)((Variable)ent).getTipo()).getLimiteSup();
												generador.genInst2ArgCte("", generador.CONT, li, ls);
										}
										if ((ent instanceof Parametro) && (((Parametro)ent).getPorValor() == false)) {
												generador.genInst2ArgCte("", generador.ALVI,((Parametro)ent).getNivelLexico(),((Parametro)ent).getOffSet());
										}
										else {
												generador.genInst2ArgCte("", generador.ALVL,((Variable)ent).getNivelLexico(),((Variable)ent).getOffSet());
										}
								}
								else {
										String t1,t2;
                                        t1=ErrorSemantico.construirMsj(((Variable)ent).getTipo().toString());
                                        t2=ErrorSemantico.construirMsj(sint.getTipoS().toString());                                        
                                        throw new ErrorSemantico(ErrorSemantico.TIPO_INCOMP, getNumeroLinea(), getNumeroColumna(),t1,t2);
								}
						}
						else if (((Variable)ent).getTipo() instanceof Arreglo) {
								generador.genInst1ArgCte("",generador.APCT, 0);
								sint = expresion(porValorH, "");
								if (compatibles(((Variable)ent).getTipo(), sint.getTipoS())) {
										nivel = ((Variable)ent).getNivelLexico();
										off = ((Variable)ent).getOffSet();
										longitud = sint.getTipoS().getSize();
										
										if ((ent instanceof Parametro) && (((Parametro)ent).getPorValor() == false)) {
												generador.genInst3ArgCte("", generador.POAI, nivel, off, longitud);
										}
										else {
												generador.genInst3ArgCte("", generador.POAR, nivel, off, longitud);
										}
								}
								else {
										String t1,t2;
                                        t1=ErrorSemantico.construirMsj(((Variable)ent).getTipo().toString());
                                        t2=ErrorSemantico.construirMsj(sint.getTipoS().toString());
                                        throw new ErrorSemantico(ErrorSemantico.TIPO_INCOMP, getNumeroLinea(), getNumeroColumna(),t1,t2);
								}								
						}
			}
			else if ((ent instanceof Funcion) && ((TS.getNivelActual()-1) == (((Funcion)ent).getNivelLexico()))) {
						sint = expresion(porValorH, "");
						
						if (compatibles(((Funcion)ent).getRetorno(),sint.getTipoS())) {
								generador.genInst2ArgCte("", generador.ALVL, ((Funcion)ent).getNivelLexico()+1, ((Funcion)ent).getOffset());
						}
						else {
								String t1,t2;
								t1=ErrorSemantico.construirMsj(((Funcion)ent).getRetorno().toString());
								t2=ErrorSemantico.construirMsj(sint.getTipoS().toString());
								throw new ErrorSemantico(ErrorSemantico.TIPO_INCOMP, getNumeroLinea(), getNumeroColumna(),t1,t2);
						}
			}
			else {
						throw new ErrorSemantico(ErrorSemantico.ID_NO_DEC, getNumeroLinea(), getNumeroColumna(),idH);
			}			
		}
		else if (numeroTokenActual == Token.PARENTESISABRE) {
		
			ent = TS.getEntrada(idH);
			if (ent instanceof Procedimiento) {
					if (esProcPredef(idH)) {
							predefH = true;
							listaFormalesH = new ArrayList();
					}
					else {
							predefH = false;
							listaFormalesH = ((Procedimiento)ent).getListaParametrosFormales();
					}
			}
			
			match(Token.PARENTESISABRE);
			ParametrosActuales(idH, predefH, listaFormalesH);
			match(Token.PARENTESISCIERRA);
			
			if (! predefH) {
					generador.genInst1ArgEtiq("", generador.LLPR, ((Procedimiento)ent).getEtiq());
			}
			else {
					throw new  ErrorSemantico(ErrorSemantico.ID_INV, getNumeroLinea(), getNumeroColumna(),idH);
			}
		}
		else if (numeroTokenActual == Token.CORCHETEABRE) {
			
			ent = TS.getEntrada(idH);
			
			if ((ent instanceof Variable) || (ent instanceof Parametro)) {
					if (((Variable)ent).getTipo() instanceof Arreglo) {
							generador.genInst1ArgCte("", generador.APCT, 0);
							match(Token.CORCHETEABRE);
							porValorH = true;	
							sint = expresion(porValorH, "");
							match(Token.CORCHETECIERRA);
							
							arr = (Arreglo) ((Variable)ent).getTipo();
							
							if (compatibles(arr.getTipoIndice(), sint.getTipoS())) {
									generador.genInst2ArgCte("", generador.CONT, arr.getLimiteInf(), arr.getLimiteSup());
									generador.genInst1ArgCte("", generador.APCT, arr.getLimiteInf());
									generador.genInstSinArg("", generador.SUST);
									generador.genInst1ArgCte("",generador.APCT, arr.getTipoElem().getSize());
									generador.genInstSinArg("", generador.MULT);
									generador.genInstSinArg("", generador.SUMA);
									tipoH = arr.getTipoElem();
							}
							else {
									String t1,t2;
                                    t1=ErrorSemantico.construirMsj(arr.getTipoIndice().toString());
                                    t2=ErrorSemantico.construirMsj(sint.getTipoS().toString());
                                    throw new  ErrorSemantico(ErrorSemantico.TIPO_INCOMP, getNumeroLinea(), getNumeroColumna(),t1,t2);
							}
					}
					else {
							String t=ErrorSemantico.construirMsj(((Variable)ent).getTipo().toString());
                            throw new  ErrorSemantico(ErrorSemantico.TIPO_INCOMP, getNumeroLinea(), getNumeroColumna(),"Arreglo",t);
					}
			}
			else {
					throw new  ErrorSemantico("Se esperaba id de Variable o Parametro", getNumeroLinea(), getNumeroColumna(),idH);
			}
			
			match(Token.ASIGNACION);
			sint2 = expresion(porValorH, "");
			
			if (compatibles(((Arreglo)ent).getTipoElem(), sint2.getTipoS())) {			
					nivel = ((Variable)ent).getNivelLexico();
					off = ((Variable)ent).getOffSet();
					longitud = sint3.getTipoS().getSize();
					
					if ((ent instanceof Parametro) && (((Parametro)ent).getPorValor() == false)) {
							generador.genInst3ArgCte("", generador.POAI, nivel, off, longitud);
					}
					else {
							generador.genInst3ArgCte("", generador.POAR, nivel, off, longitud);
					}
			}
			else {
					String t1,t2;
                    t1=ErrorSemantico.construirMsj(sint2.getTipoS().toString());
                    t2=ErrorSemantico.construirMsj(sint3.getTipoS().toString());
                    throw new  ErrorSemantico(ErrorSemantico.TIPO_INCOMP, getNumeroLinea(), getNumeroColumna(),t1,t2);
			}
		}
		else {
			ent = TS.getEntrada(idH);
			
			if (ent instanceof Procedimiento) {
					if (! TS.esProcPredef(idH)) {
							if(((Procedimiento)ent).getListaParametrosFormales().size() == 0) {
									listaFormalesH = ((Procedimiento)ent).getListaParametrosFormales();
									generador.genInst1ArgEtiq("", generador.LLPR, ((Procedimiento)ent).getEtiq());
							}
							else {
									throw new  ErrorSemantico("Funcion o procedimiento sin Parametros", getNumeroLinea(), getNumeroColumna(),idH);		
							}
					}
					else {
							throw new  ErrorSemantico("Funcion o procedimiento predefinido sin Parametros", getNumeroLinea(), getNumeroColumna(),idH);
					}
			}
			else {
					throw new  ErrorSemantico(ErrorSemantico.ID_INV, getNumeroLinea(), getNumeroColumna(),idH);
			}
		}
	}
	
	private sintetizados expresion (boolean porValorH, String idPredefH) throws ErrorLexico, ErrorArchivo, ErrorSintactico, ErrorSemantico, Exception {
	
	sintetizados s = new sintetizados();
	sintetizados sExpF, sExpS;
	
		sExpS = expresionSimple(porValorH, idPredefH);		
		sExpF = expresionFac(sExpS.getTipoS(), porValorH, idPredefH);
		s.setTipoS(sExpF.getTipoS());
		
		return s;
	}
	
	private sintetizados expresionSimple (boolean porValorH, String idPredefH) throws ErrorLexico, ErrorArchivo, ErrorSintactico, ErrorSemantico, Exception{
		sintetizados sintTer,sintESF,sintES = new sintetizados();
		String e1,e2;
		if (numeroTokenActual == Token.SUMA) {
			match(Token.SUMA);
			if(!porValorH){
				throw new ErrorSemantico(ErrorSemantico.PARAM_REF,getNumeroLinea(),getNumeroColumna());
			}
			sintTer = termino(porValorH, idPredefH);
			sintESF = expresionSimpleFac(sintTer.getTipoS(),porValorH, idPredefH);
		        if(compatibles(sintTer.getTipoS(),sintESF.getTipoS())){  //si el segundo es NULL también deberían ser compatibles, ojo al piojo
				sintES.setTipoS(sintTer.getTipoS());
			}
		        else {
				e1=ErrorSemantico.construirMsj(sintTer.getTipoS().toString());
				e2=ErrorSemantico.construirMsj(sintESF.getTipoS().toString());
				throw new ErrorSemantico(ErrorSemantico.TIPO_INCOMP,getNumeroLinea(),getNumeroColumna(),e1,e2);
			}
		}
		else if (numeroTokenActual == Token.RESTA) {
			match(Token.RESTA);
			if(!porValorH){
				throw new ErrorSemantico(ErrorSemantico.PARAM_REF,getNumeroLinea(),getNumeroColumna());
			}
			sintTer = termino(porValorH, idPredefH);
			generador.genInstSinArg("",generador.UMEN);
			sintESF = expresionSimpleFac(sintTer.getTipoS(),porValorH, idPredefH);
		        if(compatibles(sintTer.getTipoS(),sintESF.getTipoS())){ //si el segundo es NULL también deberían ser compatibles, ojo al piojo
				sintES.setTipoS(sintTer.getTipoS());
			}
		        else {
				e1=ErrorSemantico.construirMsj(sintTer.getTipoS().toString());
				e2=ErrorSemantico.construirMsj(sintESF.getTipoS().toString());
				throw new ErrorSemantico(ErrorSemantico.TIPO_INCOMP,getNumeroLinea(),getNumeroColumna(),e1,e2);
			}
		}
		else {
	
			sintTer = termino(porValorH,idPredefH);			
			sintESF = expresionSimpleFac(sintTer.getTipoS(),porValorH, idPredefH);	
			if (compatibles(sintTer.getTipoS(),sintESF.getTipoS())){ //si el segundo es NULL también deberían ser compatibles, ojo al piojo				
				sintES.setTipoS(sintTer.getTipoS());
			}
			else {
				 e1=ErrorSemantico.construirMsj(sintTer.getTipoS().toString());
				 e2=ErrorSemantico.construirMsj(sintESF.getTipoS().toString());
				throw new ErrorSemantico(ErrorSemantico.TIPO_INCOMP,getNumeroLinea(),getNumeroColumna(),e1,e2);
			}
		}		
		return sintES;
	}
	
	private sintetizados expresionFac (tipo tipoH, boolean porValorH, String idPredefH) throws ErrorLexico, ErrorArchivo, ErrorSintactico, ErrorSemantico, Exception {
	
	int tokenValue;
	sintetizados s = new sintetizados();
	sintetizados sES;
	
		if (esOperadorRelacional(numeroTokenActual)) {
		
			if (!porValorH) {
				throw new ErrorSemantico(ErrorSemantico.EXP_POR_VALOR,getNumeroLinea(),getNumeroColumna());
			}
			else{
				tokenValue = tokenActual.getNumeroToken();
				match(numeroTokenActual);
				sES = expresionSimple(porValorH,idPredefH);
				
				if(TS.compatibles(tipoH,sES.getTipoS())) {
					s.setTipoS(new Booleano(TS.getNivelActual()));
				
					if (!(tipoH instanceof Booleano)) {
						if (tokenValue == Token.IGUAL) {						
							generador.genInstSinArg("",generador.CMIG);
						}
						else if (tokenValue == Token.MAYOR) {						
							generador.genInstSinArg("",generador.CMMA);
						}
						else if (tokenValue == Token.MENOR) {						
							generador.genInstSinArg("",generador.CMME);
						}
						else if (tokenValue == Token.MAYOROIGUAL) {						
							generador.genInstSinArg("",generador.CMYI);
						}
						else if (tokenValue == Token.MENOROIGUAL) {						
							generador.genInstSinArg("",generador.CMNI);
						}
						else if (tokenValue == Token.DISTINTO) {						
							generador.genInstSinArg("",generador.CMDG);
						}				
					}
					else {
						if (tokenValue == Token.IGUAL) {						
							generador.genInstSinArg("",generador.CMIG);
						}
						else if (tokenValue == Token.DISTINTO) {						
							generador.genInstSinArg("",generador.CMDG);
						}		
						else{
							String t1,t2;
							t1=ErrorSemantico.construirMsj(tipoH.toString());
							t2=ErrorSemantico.construirMsj(sES.getTipoS().toString());
							throw new ErrorSemantico(ErrorSemantico.TIPO_INCOMP,getNumeroLinea(),getNumeroColumna(),t1,t2);
						}
					}
				}
				else {
					String t1,t2;
					t1=ErrorSemantico.construirMsj(tipoH.toString());
					t2=ErrorSemantico.construirMsj(sES.getTipoS().toString());
					throw new ErrorSemantico(ErrorSemantico.TIPO_INCOMP,getNumeroLinea(),getNumeroColumna(),t1,t2);
				}
			}
		}
		else { //lambda
					s.setTipoS(tipoH);
			}
		return s;
	}
	
	private sintetizados expresionSimpleFac (tipo tipoH,boolean porValorH, String idPredH) throws ErrorLexico, ErrorArchivo, ErrorSintactico, ErrorSemantico, Exception {
		sintetizados sintTer,sintESF;
		sintetizados sint = new sintetizados();
		String e;
		
		if (numeroTokenActual == Token.OR) {
			match(Token.OR);
					
			sintTer = termino(porValorH, idPredH);
			if(compatibles(sintTer.getTipoS(),tipoH)){
				if(sintTer.getTipoS() instanceof Booleano) { // ojo al piojo again, qué onda?? en suma y resta hay que controlar que el tipo término sea Entero o Subrango
					generador.genInstSinArg("",generador.DISJ);   
					sintESF = expresionSimpleFac(sintTer.getTipoS(),porValorH, idPredH);
					if (sintESF.getTipoS() instanceof Booleano){
						sint.setTipoS(new Booleano(TS.getNivelActual()));
					}
					 else {
						 	String e1, e2;
							e1 = ErrorSemantico.construirMsj(sintTer.getTipoS().toString());
							e2 = ErrorSemantico.construirMsj(sintESF.getTipoS().toString());
							throw new ErrorSemantico(ErrorSemantico.TIPO_INCOMP,getNumeroLinea(),getNumeroColumna(),e1,e2);
					 }
				}
				else {
						String e1, e2;
						e1 = ErrorSemantico.construirMsj(tipoH.toString());
						e2 = ErrorSemantico.construirMsj(sintTer.getTipoS().toString());
						throw new ErrorSemantico(ErrorSemantico.TIPO_INCOMP,getNumeroLinea(),getNumeroColumna(),e1,e2);
				}
			}
			else {
					String e1, e2;
					e1 = ErrorSemantico.construirMsj(tipoH.toString());
					e2 = ErrorSemantico.construirMsj(sintTer.getTipoS().toString());
					throw new ErrorSemantico(ErrorSemantico.TIPO_INCOMP,getNumeroLinea(),getNumeroColumna(),e1,e2);
			}
			
			
		}
		else if (numeroTokenActual == Token.SUMA) {
			match(Token.SUMA);
			 if(!porValorH) {
			    throw new ErrorSemantico(ErrorSemantico.PARAM_REF,getNumeroLinea(),getNumeroColumna());
			 }
			 sintTer = termino(porValorH, idPredH);
			if(compatibles(sintTer.getTipoS(),tipoH)){
				generador.genInstSinArg("", generador.SUMA);
				generador.genInst2ArgCte("",generador.CONT, -TS.getMaxMepa(),TS.getMaxMepa()-1);
				sintESF = expresionSimpleFac(sintTer.getTipoS(),porValorH, idPredH);
				
				if((sintESF.getTipoS() instanceof Entero) || (sintESF.getTipoS() instanceof Subrango)){
					sint.setTipoS(new Entero(TS.getNivelActual()));
				}
				 else {String e1, e2;
					e1 = ErrorSemantico.construirMsj(sintTer.getTipoS().toString());
					e2 = ErrorSemantico.construirMsj(sintESF.getTipoS().toString());
					throw new ErrorSemantico(ErrorSemantico.TIPO_INCOMP,getNumeroLinea(),getNumeroColumna(),e1,e2);
				 }
			}
			else {			
				String e1, e2;
				e1 = ErrorSemantico.construirMsj(tipoH.toString());
				e2 = ErrorSemantico.construirMsj(sintTer.getTipoS().toString());
				throw new ErrorSemantico(ErrorSemantico.TIPO_INCOMP,getNumeroLinea(),getNumeroColumna(),e1,e2);
			}
		}
		else if (numeroTokenActual == Token.RESTA) {
			match(Token.RESTA);
			 if(!porValorH) {
			    throw new ErrorSemantico(ErrorSemantico.PARAM_REF,getNumeroLinea(),getNumeroColumna());
			 }
			 sintTer = termino(porValorH, idPredH);
			if(compatibles(sintTer.getTipoS(),tipoH)){
				generador.genInstSinArg("", generador.SUST);
				generador.genInst2ArgCte("",generador.CONT, -TS.getMaxMepa(),TS.getMaxMepa()-1);
				sintESF = expresionSimpleFac(sintTer.getTipoS(),porValorH, idPredH);
				
				if((sintESF.getTipoS() instanceof Entero) || (sintESF.getTipoS() instanceof Subrango)){
					sint.setTipoS(new Entero(TS.getNivelActual()));
				}
				 else {	String e1, e2;
						e1 = ErrorSemantico.construirMsj(sintTer.getTipoS().toString());
						e2 = ErrorSemantico.construirMsj(sintESF.getTipoS().toString());
						throw new ErrorSemantico(ErrorSemantico.TIPO_INCOMP,getNumeroLinea(),getNumeroColumna(),e1,e2);
				 }
			}
			else {
				String e1, e2;
				e1 = ErrorSemantico.construirMsj(tipoH.toString());
				e2 = ErrorSemantico.construirMsj(sintTer.getTipoS().toString());
				throw new ErrorSemantico(ErrorSemantico.TIPO_INCOMP,getNumeroLinea(),getNumeroColumna(),e1,e2);
			}
		}
		else { //lambda
			sint.setTipoS(tipoH);
		}
		return sint;
	}
	
	private sintetizados termino (boolean porValorH, String idPredefH) throws ErrorLexico, ErrorArchivo, ErrorSintactico, ErrorSemantico, Exception {
	
	sintetizados s = new sintetizados();
	sintetizados sFac, sTerF;
	
		sFac = factor(porValorH, idPredefH);
		sTerF = terminoFac(sFac.getTipoS(), porValorH, idPredefH);
		s.setTipoS(sTerF.getTipoS());
		
		return s;
	}
	
	private sintetizados terminoFac (tipo tipoH, boolean porValorH, String idPredefH) throws ErrorLexico, ErrorArchivo, ErrorSintactico, ErrorSemantico, Exception {
	
	sintetizados s = new sintetizados();
	sintetizados sFac, sTerF;
	boolean esMult = true;
	
		if ((numeroTokenActual == Token.MULTIPLICACION) || (numeroTokenActual == Token.DIV) || (numeroTokenActual == Token.AND)) {
		
			if (!porValorH) {
				throw new ErrorSemantico(ErrorSemantico.PARAM_REF,getNumeroLinea(),getNumeroColumna());
			}
			else {				
					if ((numeroTokenActual == Token.MULTIPLICACION) || (numeroTokenActual == Token.DIV)){
						
						if (numeroTokenActual == Token.MULTIPLICACION) {
									match(Token.MULTIPLICACION);									
						}
						else { // es DIV
									match(Token.DIV);
									esMult = false;
						}
						sFac = factor(porValorH, idPredefH);					
						
						if (compatibles(tipoH, sFac.getTipoS())) {
								
								if (esMult) {
									generador.genInstSinArg("", generador.MULT);
								}
								else {
									generador.genInstSinArg("", generador.DIVC);
									generador.genInstSinArg("", generador.DIVI);
								}
								generador.genInst2ArgCte("", generador.CONT, -TS.getMaxMepa(), TS.getMaxMepa()-1);
								
								sTerF = terminoFac(sFac.getTipoS(), porValorH, idPredefH);
								
								if ((sTerF.getTipoS() instanceof Entero) || (sTerF.getTipoS() instanceof Subrango)) {
										s.setTipoS(new Entero(TS.getNivelActual()));
								}
								else {String e1, e2;
								e1 = ErrorSemantico.construirMsj(sFac.getTipoS().toString());
								e2 = ErrorSemantico.construirMsj(sTerF.getTipoS().toString());
								throw new ErrorSemantico(ErrorSemantico.TIPO_INCOMP,getNumeroLinea(),getNumeroColumna(),e1,e2);
								}						
						}
						else {	
								String e1, e2;
								e1 = ErrorSemantico.construirMsj(tipoH.toString());
								e2 = ErrorSemantico.construirMsj(sFac.getTipoS().toString());
								throw new ErrorSemantico(ErrorSemantico.TIPO_INCOMP,getNumeroLinea(),getNumeroColumna(),e1,e2);								
                        }
					}
					else { // es AND
						match(Token.AND);
						sFac = factor(porValorH, idPredefH);
						if (compatibles(tipoH, sFac.getTipoS())) {
								if (sFac.getTipoS() instanceof Booleano) {
										
										generador.genInstSinArg("", generador.CONJ);
										sTerF = terminoFac(sFac.getTipoS(), porValorH, idPredefH);
										if (sTerF.getTipoS() instanceof Booleano) {
												s.setTipoS(new Booleano(TS.getNivelActual()));
										}
										else {String e1, e2;
												e1 = ErrorSemantico.construirMsj(tipoH.toString());
												e2 = ErrorSemantico.construirMsj(sTerF.getTipoS().toString());
												throw new ErrorSemantico(ErrorSemantico.TIPO_INCOMP,getNumeroLinea(),getNumeroColumna(),e1,e2);
										}
								}
								else {	String e1, e2;
										e1 = ErrorSemantico.construirMsj(tipoH.toString());
										e2 = ErrorSemantico.construirMsj(sFac.getTipoS().toString());
										throw new ErrorSemantico(ErrorSemantico.TIPO_INCOMP,getNumeroLinea(),getNumeroColumna(),e1,e2);
								}
						}
						else {	String e1, e2;
								e1 = ErrorSemantico.construirMsj(tipoH.toString());
								e2 = ErrorSemantico.construirMsj(sFac.getTipoS().toString());
								throw new ErrorSemantico(ErrorSemantico.TIPO_INCOMP,getNumeroLinea(),getNumeroColumna(),e1,e2);
                        }
					}			
			}						
		}		
		else {
				s.setTipoS(tipoH);
		}	
		
		return s;
	}
	
	private sintetizados factor (boolean porValorH, String idPredefH) throws ErrorLexico, ErrorArchivo, ErrorSintactico, ErrorSemantico, Exception {
	String idH;
	entrada en;
	sintetizados s = new sintetizados();
	sintetizados sE, sFac;
	
		if (numeroTokenActual == Token.IDENTIFICADOR) {
			idH = tokenActual.getLexema();
			en = TS.getEntrada(idH);
			if (en != null){
				match(Token.IDENTIFICADOR);
				if ((en instanceof Variable) && (((Variable)en).getTipo() instanceof Arreglo)) {
						generador.genInst1ArgCte("", generador.APCT, 0);
				}
			
				s = factorFac(idH, porValorH, idPredefH);
			}
			else {
				throw new ErrorSemantico(ErrorSemantico.ID_NO_DEC,getNumeroLinea(),getNumeroColumna(),idH);
			}
		}
		else if (numeroTokenActual == Token.NUMERO) {
			if (! porValorH) {
					throw new ErrorSemantico(ErrorSemantico.PARAM_REF,getNumeroLinea(),getNumeroColumna());
			}
			else {
					s.setTipoS(new Entero(TS.getNivelActual()));
					generador.genInst1ArgCte("",generador.APCT,Integer.parseInt(tokenActual.getLexema()));
					match(Token.NUMERO);
			}
						
		}
		else if (numeroTokenActual == Token.PARENTESISABRE) {
			match(Token.PARENTESISABRE);
			
			if (!porValorH) {
					throw new ErrorSemantico(ErrorSemantico.EXP_POR_VALOR,getNumeroLinea(),getNumeroColumna());
			}
			else {
					sE = expresion(porValorH, idPredefH);
					match(Token.PARENTESISCIERRA);
					s.setTipoS(sE.getTipoS());
			}
		}
		else {
			
			if (!porValorH) {
					throw new ErrorSemantico(ErrorSemantico.EXP_POR_VALOR,getNumeroLinea(),getNumeroColumna());
			}
			else {
					match(Token.NOT);
					sFac = factor(porValorH, idPredefH);
					if (sFac.getTipoS() instanceof Booleano) {
							s.setTipoS(sFac.getTipoS());
							generador.genInstSinArg("", generador.NEGA);
					}
					else {
							String t=ErrorSemantico.construirMsj(sFac.getTipoS().toString());
                            throw new ErrorSemantico(ErrorSemantico.TIPO_INV_NOT,getNumeroLinea(),getNumeroColumna(),t);
                    }
			}
		}
		
		return s;
	}
	
	private sintetizados factorFac (String identificadorH,boolean porValorH, String idPredH) throws ErrorLexico, ErrorArchivo, ErrorSintactico, ErrorSemantico, Exception {
		sintetizados sint = new sintetizados(),sintE,sintP;
		entrada entrada;
		Arreglo Arreglo;
		int nivel,li,ls,offSet,longitud;
		String e1,e2;
		
		if (numeroTokenActual == Token.CORCHETEABRE) {
			match(Token.CORCHETEABRE);
			sintE = expresion(true,"");
			match(Token.CORCHETECIERRA);
			entrada = TS.getEntrada(identificadorH);
			if ((entrada instanceof Variable) ||(entrada instanceof Parametro)){
				if ((((Variable)entrada).getTipo()) instanceof Arreglo){
					Arreglo = (Arreglo)((Variable)entrada).getTipo();
					if (compatibles(Arreglo.getTipoIndice(),sintE.getTipoS())) {
						generador.genInst2ArgCte("",generador.CONT,Arreglo.getLimiteInf(),Arreglo.getLimiteSup());
						generador.genInst1ArgCte("",generador.APCT,Arreglo.getLimiteInf());
						generador.genInstSinArg("",generador.SUST);
						generador.genInst1ArgCte("",generador.APCT,Arreglo.getTipoElem().getSize());
						generador.genInstSinArg("",generador.MULT);
						generador.genInstSinArg("",generador.SUMA);
						
						sint = new sintetizados();					//con mucho cuidado, porque tal vez no funca
						sint.setTipoS(Arreglo.getTipoElem());
						nivel = entrada.getNivelLexico();
						offSet = ((Variable)entrada).getOffSet();
						longitud =  sint.getTipoS().getSize();
						
						if (porValorH){
							//pasado por VALOR    (el que obtnemos de los Parametros FORMALES)
							// Parametro recibido por referencia
							if ((entrada instanceof Parametro) &&  !((Parametro)entrada).getPorValor()){
								if (esProcLectura(idPredH)) {  // si se debe leer la inst. es la inversa
									generarProcLectura(idPredH, sint.getTipoS());
									generador.genInst3ArgCte("",generador.POAI, nivel, offSet, longitud);
								}
								else {
									generador.genInst3ArgCte("",generador.PUAI, nivel, offSet, longitud);
								}
							}
							else { // Parametro recibido por valor	
								if (TS.esProcLectura(idPredH)) {   // si se debe leer la inst. es la inversa
									generarProcLectura(idPredH, sint.getTipoS());
									generador.genInst3ArgCte("",generador.POAR, nivel, offSet, longitud);
								}
								else {
									generador.genInst3ArgCte("",generador.PUAR, nivel, offSet, longitud);
								}
							}
						}
						else { //pasado por REFERENCIA
							  // Parametro recibido por referencia
							if ((entrada instanceof Parametro) &&  !((Parametro)entrada).getPorValor()){
								if (esProcLectura(idPredH)) {  // si se debe leer la inst. es la inversa
									    generarProcLectura(idPredH, sint.getTipoS());
									    generador.genInst3ArgCte("",generador.POAI, nivel, offSet, longitud);
								}
								else {
									generador.genInst2ArgCte("",generador.APVL, nivel, offSet); 
									generador.genInstSinArg("",generador.SUMA);			
								}
							}	 
							else { // Parametro recibido por valor	
								if (esProcLectura(idPredH)) {  // si se debe leer la inst. es la inversa
									generarProcLectura(idPredH, sint.getTipoS());
									generador.genInst3ArgCte("",generador.POAR, nivel, offSet, longitud);
								}
								else{
									generador.genInst2ArgCte("",generador.APDC, nivel, offSet);
								}
							}
						}
						
					}
					else {
						e1 = ErrorSemantico.construirMsj(Arreglo.getTipoIndice().toString());
						e2 = ErrorSemantico.construirMsj(sintE.getTipoS().toString());
						throw new ErrorSemantico(ErrorSemantico.TIPO_IND_INCOMP,getNumeroLinea(),getNumeroColumna(),e1,e2);
					}
					
				}
				else {
					e1 = ErrorSemantico.construirMsj(((Variable)entrada).getTipo().toString());
					throw new ErrorSemantico(ErrorSemantico.TIPO_ARRAY,getNumeroLinea(),getNumeroColumna(),e1);
				}
			}
			else {
				throw new ErrorSemantico(ErrorSemantico.ID_VAR_PARAM,getNumeroLinea(),getNumeroColumna(),identificadorH);
			}
				
		}
		else if (numeroTokenActual == Token.PARENTESISABRE) {
			
			entrada=TS.getEntrada(identificadorH);	    
			if (entrada instanceof Funcion){  // solo puede ser una funcion NO un proc
				if ((((Funcion)entrada).getListaParametrosFormales() == null) || (((Funcion)entrada).getListaParametrosFormales() != null && ((Funcion)entrada).getListaParametrosFormales().size() > 0)){
					if ( ! esFuncPredef(identificadorH)) {
						generador.genInst1ArgCte("", generador.RMEM, ((Funcion)entrada).getRetorno().getSize());	
					}
					match(Token.PARENTESISABRE);
					sintP = ParametrosActuales(identificadorH,esFuncPredef(identificadorH),((Funcion)entrada).getListaParametrosFormales());
					match(Token.PARENTESISCIERRA);
					if ( ! esFuncPredef(identificadorH)) {  //si no es func predefinida genera inst de llamada y sintetiza el tipo de retorno	
					    generador.genInst1ArgEtiq("", generador.LLPR, ((Funcion)entrada).getEtiq());		    
					    sint.setTipoS(((Funcion)entrada).getRetorno()); 
					}
					else {// sintetiza el tipo de retorno que es el tipo del Parametro
					    sint.setTipoS(sintP.getRetornoS());	
					}
				}
				else {
					throw new ErrorSemantico(ErrorSemantico.DEMAS_PARAM,getNumeroLinea(),getNumeroColumna());
				}
			}
			else {
				throw new ErrorSemantico(ErrorSemantico.FUNC,getNumeroLinea(),getNumeroColumna(),identificadorH);
			}
		}
		else {
			entrada = TS.getEntrada(identificadorH);
			nivel = entrada.getNivelLexico();
               
			if ((entrada instanceof Variable)||(entrada instanceof Parametro)||(entrada instanceof Constante)){
				if(entrada instanceof Constante) {
					if(porValorH) {       //CAMBIADO
						if(((Constante)entrada).getTipo() instanceof TipoSimple) {
							generador.genInst1ArgCte("",generador.APCT, ((Constante)entrada).getValor());
							sint.setTipoS(((Constante)entrada).getTipo());
						}
						else {
							throw new ErrorSemantico(ErrorSemantico.CTE_SIMPLE,getNumeroLinea(),getNumeroColumna(),identificadorH);
						}
					}
					else {
						throw new ErrorSemantico(ErrorSemantico.CTE_REF,getNumeroLinea(),getNumeroColumna(),identificadorH);
					}
				} //es Variable o Parametro
				else if (((Variable)entrada).getTipo() instanceof TipoSimple) {
					offSet=((Variable)entrada).getOffSet();
					if (porValorH) {//pasado por valor
						if ((entrada instanceof Parametro) && (!((Parametro)entrada).getPorValor())) {//Parametro recibido por referencia
							if (esProcLectura(idPredH)) {  // si se debe leer la inst. es la inversa
								generarProcLectura(idPredH, (((Variable)entrada).getTipo()));
								generador.genInst2ArgCte("",generador.ALVI, nivel, offSet);			       
							}
							else {
								generador.genInst2ArgCte("",generador.APVI, nivel, offSet);	
							}
						}
						else {//Parametro recibido por valor o es una Variable				   
						       if (esProcLectura(idPredH)) {  // si se debe leer la inst. es la inversa
							    generarProcLectura(idPredH, (((Variable)entrada).getTipo()));				   
							    generador.genInst2ArgCte("",generador.ALVL, nivel, offSet);			       
						       }
						       else {
							    generador.genInst2ArgCte("",generador.APVL, nivel, offSet);	
						       }
					       }
					}
					else {//pasado por referencia
						if ((entrada instanceof Parametro)&& (!((Parametro)entrada).getPorValor())) {//Parametro recibido por referencia
							if (TS.esProcLectura(idPredH)) {   // si se debe leer la inst. es la inversa
								generarProcLectura(idPredH, (((Variable)entrada).getTipo()));				    
								generador.genInst2ArgCte("",generador.ALVL, nivel, offSet);
							}
							else {
								generador.genInst2ArgCte("",generador.APVL, nivel, offSet);
							}
						}
						else { //Parametro recibido por valor o Variable
							if (esProcLectura(idPredH)) {  // si se debe leer la inst. es la inversa
								generarProcLectura(idPredH, (((Variable)entrada).getTipo()));				    
							    generador.genInst2ArgCte("",generador.ALVL, nivel, offSet);
							}
							else {
								 generador.genInst2ArgCte("",generador.APDR, nivel, offSet);		
							}	
						}
					}
					sint.setTipoS(((Variable)entrada).getTipo());	// sintetiza el tipo de la var o param		
				       }
				       else if ((((Variable)entrada).getTipo()) instanceof Arreglo) {
						li=((Arreglo)((Variable)entrada).getTipo()).getLimiteInf();
						ls=((Arreglo)((Variable)entrada).getTipo()).getLimiteSup();
						offSet=((Variable)entrada).getOffSet();
					  	longitud = ((Arreglo)((Variable)entrada).getTipo()).getSize();
						if (porValorH) {//pasado por valor
							if ((entrada instanceof Parametro) && (!((Parametro)entrada).getPorValor())) {//Parametro recibido por referencia o es una Variable
								generador.genInst3ArgCte("",generador.PUAI,nivel,offSet,longitud);
							}
							else { //Parametro recibido por valor
								generador.genInst3ArgCte("",generador.PUAR,nivel,offSet,longitud);
							}
						}
						else {//pasado por referencia
							if ((entrada instanceof Parametro) && (!((Parametro)entrada).getPorValor())) {//Parametro recibido por referencia
								generador.genInst2ArgCte("",generador.APVL, nivel, offSet); 
								//este es el de la TABLA!!!!!!!!!!!!                                                     
								//para que anda chiche....hay que sacarlo!
								generador.genInstSinArg("",generador.SUMA);				      
							}
							else { //Parametro recibido por valor o Variable
								generador.genInst2ArgCte("",generador.APDC, nivel, offSet);
							}
						}
						sint.setTipoS(((Variable) entrada).getTipo());	// sintetiza el tipo elemento del Arreglo			  
					}
					else {
						e1 = ErrorSemantico.construirMsj(((Variable)entrada).getTipo().toString());
						throw new ErrorSemantico(ErrorSemantico.TIPO_SIMPLE_ARRAY,getNumeroLinea(),getNumeroColumna(),e1);
					 }
			       }
			       else if (entrada instanceof Funcion) { // se leyó el token PARABRE entonces la fc debe tener Parametros
					if (((Funcion)entrada).getListaParametrosFormales().size() == 0) {
						if (porValorH) {      //una fc solo puede pasarse por valor
							generador.genInst1ArgCte("", generador.RMEM, ((Funcion)entrada).getRetorno().getSize());
							generador.genInst1ArgEtiq("", generador.LLPR, ((Funcion)entrada).getEtiq());			    
							offSet = ((Funcion)entrada).getOffset();
							sint.setTipoS(((Funcion)entrada).getRetorno()); // sintetiza el tipo de retorno
						}
						else { 
							throw new ErrorSemantico(ErrorSemantico.CTE_REF,getNumeroLinea(),getNumeroColumna(),identificadorH);
						}
					}
					else {
						throw new ErrorSemantico(ErrorSemantico.DEMAS_PARAM,getNumeroLinea(),getNumeroColumna());
					}
				}
				else { 
					throw new ErrorSemantico(ErrorSemantico.VAR_CTE_FUNC,getNumeroLinea(),getNumeroColumna(),identificadorH);
				}
			}		
		return sint;
	}
	
	private sintetizados ParametrosActuales (String idH, boolean predefH, ArrayList listaFormalesH) throws ErrorLexico, ErrorArchivo, ErrorSintactico, ErrorSemantico, Exception {
	boolean porValorH;
	sintetizados s = new sintetizados(), sintActual;
	ArrayList restoLFormalesH = new ArrayList();
		
		if (predefH) {
				if (esProcLectura(idH)) 
						porValorH = false;
				else
						porValorH = true;
						
				restoLFormalesH = null;
		}
		else {
				porValorH = ((Parametro) listaFormalesH.get(0)).getPorValor();
				
				if (listaFormalesH.size() > 1)
						restoLFormalesH.addAll(listaFormalesH.subList(1, listaFormalesH.size()));
				else
						restoLFormalesH = new ArrayList();
		}
		
		sintActual = ParametroActual(idH, porValorH, predefH);
		
		if (esFuncPredef(idH))
				s.setRetornoS(sintActual.getTipoS());
		
		restoParametrosActuales(idH, predefH, restoLFormalesH);
		
		if ( ! predefH && ! TS.compatibles(sintActual.getTipoS(), ((Parametro) listaFormalesH.get(0)).getTipo()))
        {   String t1,t2;
            t1=ErrorSemantico.construirMsj(sintActual.getTipoS().toString());
            t2=ErrorSemantico.construirMsj(((Parametro) listaFormalesH.get(0)).getTipo().toString());
            throw new ErrorSemantico(ErrorSemantico.TIPO_INCOMP ,getNumeroLinea(),getNumeroColumna(),t1,t2);
        } 
        
        return s;
	}
	
	private sintetizados ParametroActual (String idH, boolean porValorH, boolean predefH) throws ErrorLexico, ErrorArchivo, ErrorSintactico, ErrorSemantico, Exception {
	tipo tipoS;
	sintetizados sintExp, sint = new sintetizados();
	
		sintExp = expresion(porValorH, idH);
		tipoS = sintExp.getTipoS();
		
		if (predefH && !esProcLectura(idH))
				tipoS = generarPredef(idH, tipoS);
				
		sint.setTipoS(tipoS);
		
		return sint;
	}
	
	private void restoParametrosActuales (String idH, boolean predefH, ArrayList listaFormalesH) throws ErrorLexico, ErrorArchivo, ErrorSintactico, ErrorSemantico, Exception {
		if (numeroTokenActual == Token.COMA) {
				
				if ((listaFormalesH == null) || (listaFormalesH != null && listaFormalesH.size() > 0)) {
						match(Token.COMA);
						ParametrosActuales(idH, predefH, listaFormalesH);
				}
				else {
						throw new ErrorSemantico(ErrorSemantico.DEMAS_PARAM, getNumeroLinea(),getNumeroColumna());
				}
		}
		else {// lambda
		
				if (listaFormalesH != null && listaFormalesH.size() > 0)
						throw new ErrorSemantico(ErrorSemantico.FALTAN_PARAM, getNumeroLinea(),getNumeroColumna());	
		}
	}
	
	public boolean esProcLectura(String id) {
	
		return (id.toUpperCase().compareTo("READ")==0 || id.toUpperCase().compareTo("READLN")==0);
    }
    
    
    public boolean compatibles(tipo t1, tipo t2) { 
    
	if(t1 instanceof Arreglo && t2 instanceof Arreglo)
            {if(((Arreglo)t1).getSize()==((Arreglo)t2).getSize())
                { return compatibles(((Arreglo)t1).getTipoElem(),((Arreglo)t2).getTipoElem()); }
             else return false;
        }
        else{
                return ((t1 instanceof Entero && t2 instanceof Entero) ||
                        (t1 instanceof Entero && t2 instanceof Subrango) ||
                        (t1 instanceof Subrango && t2 instanceof Entero) ||
                        (t1 instanceof Subrango && t2 instanceof Subrango) ||		
                        (t1 instanceof Booleano && t2 instanceof Booleano));
            }
    }
    
    public boolean esOperadorRelacional(int t)
      {
	  switch (t)
	  {
	      case Token.MAYOR:	    	return true;
	      case Token.MAYOROIGUAL: 	return true;
	      case Token.MENOR:	    	return true;
	      case Token.MENOROIGUAL:	return true;
	      case Token.DISTINTO:	    return true;
	      case Token.IGUAL:	    	return true;	      
	      default:		    		return false;
	  }	  
	}
    
    public boolean esFuncPredef(String id) {
    
	return (id.toUpperCase().compareTo("SUCC")== 0 || 
		id.toUpperCase().compareTo("PRED")== 0);
    }
    
    public boolean esProcPredef(String id) {
    
	return (id.toUpperCase().compareTo("READ") == 0 || 
		id.toUpperCase().compareTo("READLN")== 0 || 
		id.toUpperCase().compareTo("WRITE")== 0 || 
		id.toUpperCase().compareTo("WRITELN")== 0);
    }
    
    private tipo generarPredef(String idH, tipo typeS) throws Exception
    {
	if (idH.toUpperCase().compareTo("WRITE")==0)
        {
	    if ((typeS instanceof Entero) || (typeS instanceof Subrango) || (typeS instanceof Booleano))		
                generador.genInstSinArg("", generador.IMPR);            
            else {String t=ErrorSemantico.construirMsj(typeS.toString());
                    throw new ErrorSemantico("Tipo de expresion invalido para impresion", getNumeroLinea(),getNumeroColumna(),t);
            }
         }
         else if (idH.toUpperCase().compareTo("WRITELN")==0)
         {
            if ((typeS instanceof Entero) || (typeS instanceof Subrango) || (typeS instanceof Booleano))		
                generador.genInstSinArg("", generador.IMLN);            
            else
            {   String t=ErrorSemantico.construirMsj(typeS.toString());
                throw new ErrorSemantico("Tipo de expresion invalido para impresion", getNumeroLinea(),getNumeroColumna(),t);
            }
         }
	 else 
         {
	    if (idH.toUpperCase().compareTo("SUCC")==0)
	    {
		generador.genInst1ArgCte("", generador.APCT, 1);  // Apila la cte 1 para luego sumarla a lo anterior en el tope
		generador.genInstSinArg("", generador.SUMA);
	    }
	    else if (idH.toUpperCase().compareTo("PRED")==0)
	    {
		generador.genInst1ArgCte("", generador.APCT, 1);  // Apila la cte 1 para luego restarla a lo anterior en el tope
		generador.genInstSinArg("", generador.SUST);
	    }	
	    	    
	    
	    if ((typeS instanceof Booleano))	// valores booleanos válidos
		generador.genInst2ArgCte("", generador.CONT, 0, 1);	    
	    
	    else if ((typeS instanceof Entero))    // enteros válidos
		generador.genInst2ArgCte("", generador.CONT, -TS.getMaxMepa(), TS.getMaxMepa()-1);    
	   
	 }	 
	
	return typeS;
    }
    
    private void generarProcLectura(String id, tipo typeS) throws Exception
    {
	if (id.toUpperCase().compareTo("READ")==0) 
	{
	    if ((typeS instanceof Entero) || (typeS instanceof Subrango) || (typeS instanceof Subrango))		
                generador.genInstSinArg("", generador.LEER);           
            else
            {   String t=ErrorSemantico.construirMsj(typeS.toString());
                throw new ErrorSemantico("Tipo de expresion invalido para lectura", getNumeroLinea(),getNumeroColumna(),t);
            }
	}	
	else // entonce es READLN
	{
	 if ((typeS instanceof Entero) || (typeS instanceof Subrango) || (typeS instanceof Subrango))		
                generador.genInstSinArg("", generador.LELN);            
            else
            {String t=ErrorSemantico.construirMsj(typeS.toString());
             throw new ErrorSemantico("Tipo de expresion invalido para lectura", getNumeroLinea(),getNumeroColumna(),t);
            }
	}
    }
    
    public int getNumeroLinea() {
	return tokenActual.getNumeroLinea();
    }
    
    
    public int getNumeroColumna() {
	return tokenActual.getNumeroColumna();
	}
}
