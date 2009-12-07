{
 Llamada a una funcion RECURSIVA
}
program recursivo;

var x:integer;

	function f(v:integer):integer;
	begin
		
		if (v > 0) then
			f := f(	f(v-1) - 1)
		else
			f := 1;
	end;

begin
	x:=f(5);
	write(x);
end. 