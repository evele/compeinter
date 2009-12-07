{
 Llamada a una funcion sin parametros
}
program simparam;

var x:integer;
s:1..12;

	function f:integer;
	begin
	
		f:= succ(12);
	end;

begin

	write(f);	
end. 