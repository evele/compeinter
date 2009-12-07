{
 Llamada a una funcion con MAS parametros actuales que los definidos en su declaracion
}
program masActualesQFormales;

var x,y:integer;

	function f(x:integer):integer;
	begin
	   f := 22;
	end;

begin
   y := f(x,x,x);
end.
