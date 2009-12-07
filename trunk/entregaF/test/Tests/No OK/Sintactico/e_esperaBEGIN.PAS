PROGRAM CaballoSaltarin;
USES
    crt;
CONST
    topetab = 8;
TYPE
   tIndice=1..topetab;
   tTablero=ARRAY [tIndice,tIndice]OF INTEGER;
VAR
   tablero:tTablero;
   fil,col,num:tIndice;
   mov:INTEGER;
   volver:BOOLEAN;
   rpta:CHAR;


     PROCEDURE CargarTablero(VAR tab:tTablero;N:INTEGER);
     VAR
       i,j:tIndice;
     BEGIN
        FOR i:= 1 TO N DO
          FOR j:= 1 TO N DO
            tab[i,j]:= 0;
     END;

     PROCEDURE MostrarTablero(var tab:tTablero;N:tIndice);
     VAR
       i,j:tIndice;
     BEGIN
         FOR i:= 1 TO N DO
          BEGIN
            FOR j:= 1 TO N DO
              BEGIN
                IF ((tab[i,j] DIV 10) = 0) THEN
                   BEGIN
                   write('[ ');
                   textcolor(white);
                   write(tab[i,j]);
                   textcolor(lastmode);
                   write(']');
                   END
                ELSE
                   BEGIN
                      write('[');
                      textcolor(white);
                      write(tab[i,j]);
                      textcolor(lastmode);
                      write(']');
                   END;
              END;
            writeln;
         END;
     END;


     FUNCTION EsValida(f,c,N:tIndice):BOOLEAN;
     BEGIN
         EsValida:= (f >= 1) AND (f <= N) AND (c >= 1) AND (c <= N);
     END;

     FUNCTION PuedoPisar(var tab:tTablero;f,c,N,movida:tIndice):BOOLEAN;
     BEGIN
         IF (EsValida(f,c,N)) AND (tab[f,c] = 0) THEN
          BEGIN
             tab[f,c]:= movida;
             PuedoPisar:= true;
          END
         ELSE
            PuedoPisar:= false;
     END;

     PROCEDURE  Desmarcar(var tab:tTablero;f,c:tIndice);
     BEGIN
         tab[f,c]:= 0;
     END;

     PROCEDURE Caballo(VAR tab:tTablero;N,f,c:tIndice;movida:INTEGER;
                       VAR volvi:BOOLEAN);
      BEGIN
       IF (movida <= (N*N)) THEN
         BEGIN
           volvi:= FALSE;

           IF (PuedoPisar(tab,f,c,N,movida)) THEN
             BEGIN
                Caballo(tab,N,f-1,c+2,movida + 1,volvi);

                IF (volvi) THEN Caballo(tab,N,f-2,c+1,movida + 1,volvi);

                IF (volvi) THEN Caballo(tab,N,f-2,c-1,movida + 1,volvi);

                IF (volvi) THEN Caballo(tab,N,f-1,c-2,movida + 1,volvi);

                IF (volvi) THEN Caballo(tab,N,f+1,c-2,movida + 1,volvi);

                IF (volvi) THEN Caballo(tab,N,f+2,c-1,movida + 1,volvi);

                IF (volvi) THEN Caballo(tab,N,f+2,c+1,movida + 1,volvi);

                IF (volvi) THEN Caballo(tab,N,f+1,c+2,movida + 1,volvi);

                IF (volvi) THEN
                   Desmarcar(tab,f,c);
             END
           ELSE
              volvi:= true;
         END;
    END;

BEGIN
  REPEAT
     CLRSCR;
     fil:=1;
     col:=1;
     mov:=1;

     WRITELN('Este programa muestra como debe recorrerse un tablero de NxN en NxN movimientos.');
     WRITE('Ingrese un numero: ');
     READLN(num);
     WRITELN;
     IF (num <= topetab) THEN
       BEGIN
          CargarTablero(tablero,num);
          WRITE('Espere un momento ....');
          Caballo(tablero,num,fil,col,mov,volver);
          clrscr;

          IF (volver) THEN
            BEGIN
               WRITELN('No es posible recorrer un tablero de ',num*num,' casilleros en ',num*num,' movimientos');
               WRITELN;
            END
          ELSE
            BEGIN
               WRITELN('El resultado es: ');
               MostrarTablero(tablero,num);
               WRITELN;
            END;

          WRITE('Desea probar con otro numero (S|N): ');
          READLN(rpta);
        END
       ELSE
         BEGIN
            WRITELN('Intente con un numero menor que ',topetab,'.');
            delay(2000);
         END;

  UNTIL (rpta = 'N') or (rpta = 'n');
END.
