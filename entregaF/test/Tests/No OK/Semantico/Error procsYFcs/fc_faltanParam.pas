{
 Llamada a una funcion con MENOS parametros actuales que los definidos en su declaracion
}
program menosActualesQFormales;

var x,y:integer;

	function f(x,y,z:integer):integer;
	begin
	   f := 22;
	end;

begin
   y := f(x);
end.
