{
 Llamada a un procedimento con definicion de procedimientos anidados.
 RECIBIDO POR VALOR PASADO VALOR
}
program anidadosValVal;

type arreglo = array[1..2] of integer;

var arr:arreglo;


	procedure p(a:arreglo);
		{ anidado}
		procedure anidado(b: arreglo);
		begin
			b[1] := 1;
			b[2] := 1;
		end;
	
	begin
		a[1] := 2;
		a[2] := 2;
		anidado(a); {recibe por val pasa por val}
		writeln(a[1],a[2]); { no cambia luego de la llamada a anidado}
	end;

begin
	arr[1] := 3;
	arr[2] := 3;
    p(arr);	
	writeln(arr[1],arr[2]); { no cambia luego de la llamada a P}
end.
