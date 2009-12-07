{ 
 Error: Intento de asignacion a un id de función fuera de su bloque de declaracion.
}

Program fueraDelBloque;

	procedure pp(var f:integer);
	begin
	end;

	function fc:integer;
	begin
		fc:=3;
	end;

begin
	pp(fc);

end.