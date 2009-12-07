{
 Llamada a un procedimiento con parametros POR VALOR
}
program porValor;

var 
	s, r :integer;
	
	procedure division(x:integer);	{ con un solo parametro por valor}
	begin
		s := x div 2;
	end;
	
	procedure suma(x, y, z:integer);	{ con mas de 1 parametro por valor}
	begin
		s := x + y + z;
	end;

begin
	suma(1, 2, 3);	
	division(4);
end.
