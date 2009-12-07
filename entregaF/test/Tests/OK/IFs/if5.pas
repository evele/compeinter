{
  SENTENCIA CONDICIONAL VÁLIDA.
}
program if5;

var x: integer;

begin
  
  if (x > 2) or (x = 2) then		{ IF-THEN SI BLOQUE, ELSE CON BLOQUE}
	x := 2*4
  else
   begin
	 x := x + 4;	 
	end;
	
end. 