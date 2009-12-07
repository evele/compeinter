program constParam;
const
  a = 1;
  procedure p1(var b: integer);
  begin
	write(b);
  end;
  
begin
  p1(a); {--> Debería haber un error semántico de la forma: "se esperaba un identificador de variable".}
end.