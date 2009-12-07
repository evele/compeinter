{
 Llamada a un procedimento con parametros POR REFERENCIA
}
program porReferencia;

var ch1,ch2: integer;

	procedure p(var y,z: integer);
	begin
		y := 1;
		z := 1;
	end;

begin
    p(ch1,ch2);
	write(ch1,ch2);
end.
