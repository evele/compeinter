{
  Llamada una funcion definida sin parametros, con parametros actuales.
}
program sinFormales;

var x,y:integer;

	function f:integer;
	begin
	   f := 22;
	end;

begin
   y := f(x);
end.
