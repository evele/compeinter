{
  Llamada a procedimiento sin parámetros
}

program proc1;
var x:integer;
	procedure sinParam;
	var x: integer;
	begin
		read(x);
		if x=1 then
			write(1)
		else
			write(0);
	end;

begin

   sinParam;
   
end.