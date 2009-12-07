{
 Llamada a una funcion con parametros POR VALOR
}
program porValor;

var 
	n1, n2 :integer;
	
	function division(x:integer):integer;	{ con un solo parametro por valor}
	begin
		division := x div 2;
	end;
	
	function suma(x, y:integer):integer;	{ con mas de 1 parametro por valor}
	begin
		suma := x + y;
	end;
	
begin
	read(n1,n2);
	writeln(suma(n1, n2));	
	
	writeln(division(4));
end.
