package xtras;

import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Buffer {

	static final public char EOF = (char)0;     
    
    final private int MAXLECTORBUF = 4096;    
    final private int MAXBUFFER = 2 * MAXLECTORBUF + 2;     /* Buffer: 2 * bloque + 2, para los EOFs adicionales */    
    final private int MITADBUFFER = (MAXBUFFER/2)-1; /* 1ra mitad va de 0 a 4096*/    
    final private int FINBUFFER = MAXBUFFER-1; /* 2da mitad va de 4097 a 8193*/    
    private char[] buffer = new char[MAXBUFFER];    
    private FileReader fr;
    private BufferedReader lectorBuffer;    
    boolean cargarbuffer = true;
    private int inicioLexema = 0;
    private int finLexema = -1; /* queda en 0 con avanzarfinLexema*/
	
    public void abrirArchivo(String f) throws ErrorArchivo {
		try {
            fr = new FileReader(f);
            lectorBuffer = new BufferedReader(fr, MAXLECTORBUF);
            buffer[MITADBUFFER] = EOF; /* cargo sentinela EOF */
            buffer[FINBUFFER] = EOF; /* cargo sentinela EOF */
        }
        catch (FileNotFoundException e) {
            throw new ErrorArchivo(ErrorArchivo.RUTA_INVALIDA);
        }
        catch (IOException e) {
            throw new ErrorArchivo(ErrorArchivo.ERROR_ENTRADA_SALIDA);
        }
	}

	public String getLexema() {
		String s = new String("");       
        while (inicioLexema != finLexema) {
            s = s + buffer[inicioLexema];
            inicioLexema = avanzarLexema(inicioLexema);
        }
        s = s + buffer[finLexema]; // ultimo caract. del token
        inicioLexema = avanzarLexema(inicioLexema); // queda igual a finLexema para el prox caract.
        
        return s;
	}

	private int avanzarLexema(int Lexema) {
		Lexema++;
        if (buffer[Lexema] == EOF)
        {
            if (Lexema == MITADBUFFER)     //nos falta ver la otra mitad del buffer.
                Lexema++;
            else if (Lexema == FINBUFFER)  //ya consumimos todo el buffer.
                Lexema = 0; 
        }
        return Lexema;
	}

	public void cerrarArchivo() throws ErrorArchivo {
		try {
            lectorBuffer.close();
            fr.close();
        }
        catch (IOException e) {
            throw new ErrorArchivo(ErrorArchivo.ERROR_ENTRADA_SALIDA);
        }
	}

	public char proximoCaracter() throws ErrorArchivo {
		int status;     //determina cuantos caracteres se leyeron del buffer, y si es -1 significa que  se llego a EndOfFile.
        finLexema = avanzarLexema(finLexema);
        try {
            if ((finLexema == 0) && (cargarbuffer)) /* cargo primera mitad del buffer */
            {
                status = lectorBuffer.read(buffer, 0, MAXLECTORBUF);
                controlEOF(1,status);
            }
            else if ((finLexema == MITADBUFFER+1) && (cargarbuffer)) /* cargo segunda mitad del buffer */
                 {
                     status = lectorBuffer.read(buffer, MITADBUFFER+1, MAXLECTORBUF);
                     controlEOF(2,status);
                 }
            cargarbuffer = true; // siempre actualizo cargarbuffer a true
        }
        
        catch (IOException e) {
            throw new ErrorArchivo(ErrorArchivo.ERROR_ENTRADA_SALIDA);
        }        
        return buffer[finLexema];
	}

	private void controlEOF(int mitad, int estado) {
		if (mitad == 1)
        {
            if (estado < MAXLECTORBUF) 
                if (estado == -1)   //llegamos a EOF
                    buffer[0] = EOF;
                else buffer[estado] = EOF;
        }
        else /* mitad == 2 */
        {
            if (estado < MAXLECTORBUF) 
                if (estado == -1)   //llegamos a EOF
                    buffer[MITADBUFFER + 1] = EOF;
                else buffer[MITADBUFFER + 1 + estado] = EOF;
        }
	}

	public void retrocederLexema() {
		finLexema--;
        if (finLexema == -1)
        {
            finLexema = FINBUFFER - 1;
            cargarbuffer = false;
        }
        else if (finLexema == MITADBUFFER)
             {
                finLexema--;
                cargarbuffer = false;
             }
	}

}
