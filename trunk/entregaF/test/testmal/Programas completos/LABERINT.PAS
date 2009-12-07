PROGRAM JuegoDelLaberinto;
const tope=3;
{const tope=56;}
type Tfila=0..4;	
     Tcolumna=Tfila;
     Tnumero=1..7;	 
     Tlaberinto=array[Tfila] of array [Tcolumna]of Tnumero;
	 {Tlaberinto=array[Tfila] of array [Tcolumna]of integer;}
var Laberinto:Tlaberinto; fila:Tfila; columna:Tcolumna; encontre:boolean;{}
{Utilidad:Permite jugar al laberinto.}

FUNCTION EncontreSalida(var L:Tlaberinto; f:Tfila; c:Tcolumna):boolean;
{Utilidad:Chequea si se encontro la salida.}
{var{}
begin{------------------EncontreSalida--------------------}
 EncontreSalida:=(L[f][c]=3);
end; {------------------EncontreSalida--------------------}

PROCEDURE MarcarCamino(var L:Tlaberinto; f:Tfila; c:Tcolumna);
{Utilidad:Si corresponde a un casillero vacio, lo marca como camino.}
{var{}
begin{-------------------MarcarCamino--------------------}
 if((L[f][c]=6)or(L[f][c]=4)or(L[f][c]=1)or(L[f][c]=3))
   then L[f][c]:=2;
end; {-----------------MarcarCamino-----------------}

FUNCTION ExisteVacio(var L:Tlaberinto; f:Tfila; c:Tcolumna):boolean;
{Utilidad: Se fija si el casillero f,c esta vacio.}
{var{}
begin{------------------ExisteVacio--------------------}
 if((((((L[f][c]=6)or(L[f][c]=3))and(f>=1))and(f<=tope))and(c>=1))and(c<=tope))
   then ExisteVacio:=true
   else ExisteVacio:=false;
end; {------------------ExisteVacio--------------------}

PROCEDURE MarcarPasado(var L:Tlaberinto; f:Tfila; c:Tcolumna);
{Utilidad:Marca el casillero que fue usado pero resulto inutil para llegar a
          la salida.}
{var{}
begin{-------------------MarcarPasado--------------------}
 if(L[f][c]=6)
   then L[f][c]:=4;
end; {-----------------MarcarPasado-----------------}

PROCEDURE MostrarLaberinto(var L:Tlaberinto);
{Utilidad: Muestra en pantalla el laberinto.}
var i,j:integer;{}
begin{-------------------MostrarLaberinto--------------------}
 i:=1;
 while i<=tope do
  begin
   write('#');
   j:=1;
   while j<=tope do
    begin
    if(L[i][j]=1)
	then write('I')
	else if(L[i][j]=2)
			then write(' ')
			else if(L[i][j]=3)
					then write('O')
					else if(L[i][j]=4)
							then write(' ')
							else if(L[i][j]=5)     
									then write('#')
									else if(L[i][j]=6)    
											then write(' ')
											else if(L[i][j]=7)    
													then write(' ');
	 j:=j+1;   
	 end;
   writeln('#');
   i:=i+1;
  end;
end; {-----------------MostrarLaberinto-----------------}

PROCEDURE MarcarInutil(var L:Tlaberinto; f:Tfila; c:Tcolumna);
{Utilidad: Marca un casillero como inutil, es decir que ya se probo por alli,
           y no se ha llegado a ningun lado.}
{var{}
begin{-------------------MarcarInutil--------------------}
 if((L[f][c]<>1)or(L[f][c]<>3))
   then L[f][c]:=7;
end; {-----------------MarcarInutil-----------------}

PROCEDURE BuscarSalida(var L:Tlaberinto; f:Tfila; c:Tcolumna;
                       var encontre:boolean);
{Utilidad: Prueba Este,Sur,Oeste,Norte, buscando un casillero libre.}
{var{}
begin{-------------------BuscarSalida--------------------}
 if(not EncontreSalida(L,f,c))
   then begin
         MarcarPasado(L,f,c);
         if(ExisteVacio(L,f,c+1)) {ESTE}
           then begin
                 BuscarSalida(L,f,c+1,encontre);
                 if(encontre)
                   then MarcarCamino(L,f,c)
                   else BuscarSalida(L,f,c,encontre);
                end
           else begin
                 if(ExisteVacio(L,f+1,c))   {SUR}
                   then begin
                         BuscarSalida(L,f+1,c,encontre);
                         if(encontre)
                           then MarcarCamino(L,f,c)
                           else BuscarSalida(L,f,c,encontre);
                        end
                   else begin
                         if(ExisteVacio(L,f,c-1))  {OESTE}
                           then begin
                                 BuscarSalida(L,f,c-1,encontre);
                                 if(encontre)
                                   then MarcarCamino(L,f,c)
                                   else BuscarSalida(L,f,c,encontre);
                                end
                           else begin
                                 if(ExisteVacio(L,f-1,c))   {NORTE}
                                   then begin
                                         BuscarSalida(L,f-1,c,encontre);
                                         if(encontre)
                                           then MarcarCamino(L,f,c)
                                           else BuscarSalida(L,f,c,encontre);
                                        end
                                   else MarcarInutil(L,f,c);
                                end;
                        end;
                end;
        end
   else begin
         encontre:=true; MarcarCamino(L,f,c);
        end;
end; {-----------------BuscarSalida-----------------}

PROCEDURE ArmarLaberinto(var L:Tlaberinto);
{Utilidad:Permite dibujar el laberinto.}
var i,j:integer;{}
begin{-------------------ArmarLaberinto--------------------}
 write('P');write('u');write('s');write('e');writeln(':');
 write('1');write('-');write('I');writeln('N');
 write('3');write('-');write('O');write('U');writeln('t');
 write('5');write('-');write('W');write('A');write('L');writeln('L');
 write('6');write('-');write('E');write('M');write('P');write('T');writeln('Y');
 i:=1;
 while i<=tope do
 begin
  j:=1;
  while j<=tope do
  begin
   read(L[i][j]);
   j:=j+1;
  end;
  i:=i+1;
 end;
end; {-----------------ArmarLaberinto-----------------}

PROCEDURE ImprimirLinea;
{Utilidad: Imprime una linea en la pantalla que hace de borde.}
var i:integer;{}
begin{-------------------ImprimirLinea--------------------}
 i:=0;
 while i<=tope+1 do
  begin
   write('#');
   i:=i+1;
  end;
 writeln(' ');
end; {-----------------ImprimirLinea-----------------}

BEGIN {-----------------JuegoDelLaberinto-----------} 
 ArmarLaberinto(Laberinto);
 ImprimirLinea;
 MostrarLaberinto(Laberinto);
 ImprimirLinea;
 writeln(' ');
 encontre:=false;
{   EN fila Y columna COLOQUE LAS COORDENADAS DE LA ENTRADA.  }
 BuscarSalida(laberinto,1,1,encontre);{}
 write('S');write('o');write('l');write('u');write('c');write('i');write('o');
 write('n');write(' ');writeln(':');
 ImprimirLinea;
 MostrarLaberinto(Laberinto);
 ImprimirLinea;
END.{-----------JuegoDelLaberinto----------}
