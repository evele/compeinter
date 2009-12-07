{
 Llamada a un procedimento con definicion de procedimientos anidados.
 RECIBIDO POR REFERENCIA PASADO VALOR
}
program anidadosRefVal;

type arreglo = array[1..2] of integer;

var arr:arreglo;


	procedure p(var a:arreglo);
		{ anidado}
		procedure anidado(b: arreglo);
		begin
			writeln(b[1],b[2]);
		end;
	
	begin
		a[1] := 1;
		a[2] := 1;
		anidado(a); {recibe por ref pasa por val}
	end;

begin
    p(arr);
end.
