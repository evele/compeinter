{
 Llamada a una funcion con mas de un parametro POR VALOR y de distintos tipos
}
program fc6;

type letras = array[1..3] of integer;

var n : integer;
	let: letras;
	
	function varios(x:integer; c1,c2:integer; L: letras):integer;	{ con mas de 1 parametro por valor de distinto tipo}
	begin
		L[1]:=c1;
		L[2]:=c2;
		
		if ((x >= 1) and (x <= 2)) then
			varios:= L[x]
		else
		begin
			varios:= L[3];
		end			
	end;
	
begin
   let[3] := 4;
   read(n);
   write(varios(n, 2,3, let));

end.


