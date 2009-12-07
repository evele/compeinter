{
 Llamada a una funcion con declaracion de funcion
}
program fc8;

	function p: integer;	
		
		function q:integer;
		begin
			q:=10;
		end;		
	
	begin
		p := q;		
	end;
	
begin
   writeln(p);

end.


