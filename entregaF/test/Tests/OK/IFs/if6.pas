{
  SENTENCIA CONDICIONAL VÁLIDA.
}
program if6;

var x: integer;

begin
  
  if (x > 2) then		{ IF-THEN-ELSE CON BLOQUES}
     begin
      x := x + 12;
	   x := x * 2;
	   x := x div 2;
	   x := x - 1;
     end
  else
     begin
       x := x - 12;
     end;  
	
end. 