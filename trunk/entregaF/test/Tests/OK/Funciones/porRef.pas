{
 Llamada a una funcion con parametros POR REFERENCIA
}
program porReferencia;

var x:integer;

	function anterior(var z:integer):integer;

	begin
		read(z);
		anterior := pred(z);
	end;

begin
	x:=1;
	writeln(x);
    writeln(anterior(x));
	writeln(x);
	
end.