{
 Llamada a un procedimento con definicion de procedimientos anidados.
 RECIBIDO POR VALOR PASADO REFERENCIA
}
program anidadosValRef;

type arreglo = array[1..2] of integer;

var arr:arreglo;


	procedure p(a:arreglo);
		{ anidado}
		procedure anidado(var b: arreglo);
		begin
			b[1] := 1;
			b[2] := 1;
		end;
	
	begin
		anidado(a); {recibe por val pasa por ref}
	end;

begin
	arr[1] := 2;
	arr[2] := 2;
    p(arr);
	writeln(arr[1],arr[2]); { no cambia luego de la llamada a P}
end.
