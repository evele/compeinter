program ImprimirPiramide;
var i,num:integer;{}
{Utilidad:}

PROCEDURE ImprimirRenglon(d:integer);
{Utilidad: }
var i:integer;{}
begin{-------------------ImprimirRenglon--------------------}
 i:=1;
 while i<=d do
  begin
	write(i);
	i:=i+1;
  end;
 i:=d-1;
 while i>=1 do
  begin
	write(i);
	i:=i-1;
  end;	
end; {-----------------ImprimirRenglon-----------------}

PROCEDURE ImprimirEspacios(m:integer);
{Utilidad: }
var i:integer;{}
begin{-------------------ImprimirEspacios--------------------}
 i:=1;
 while i<=m do
  begin  
   write(' ');
   i:=i+1;
  end; 
end; {-----------------ImprimirEspacios-----------------}

begin {-----------------ImprimirPiramide-----------}
 write('I');write('n');write('g');write('r');write('e');write('s');
 write('e');write(' ');write('u');write('n');write(' ');write('n');
 write('u');write('m');write(' ');write('1');write('-');write('9');
 write(':');writeln(' ');
 readln(num);
 i:=1;
 while i<=num do
  begin
   ImprimirEspacios(num-i);
   ImprimirRenglon(i);
   writeln(' ');
   i:=i+1;
  end;
end.{-----------ImprimirPiramide----------}

