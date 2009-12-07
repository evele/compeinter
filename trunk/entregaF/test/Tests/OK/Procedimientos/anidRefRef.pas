{
 Llamada a un procedimento con definicion de procedimientos anidados.
 RECIBIDO POR REFERENCIA PASADO REFERENCIA
}
program anidadosRefRef;

type arreglo = array[1..2] of integer;

var arr:arreglo;


	procedure p(var a:arreglo);
		{ anidado}
		procedure anidado(var b: arreglo);
		begin
			b[1] := 1;
			b[2] := 1;
		end;
	
	begin
		a[1] := 2;
		a[2] := 2;
		anidado(a);{recibe por ref pasa por ref}
	end;

begin
    p(arr);
	writeln(arr[1],arr[2]);
end.
