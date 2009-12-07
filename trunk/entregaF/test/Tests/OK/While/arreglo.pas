{
 Estructura repetitiva While para recorrer un arreglo
}
 
program arreglo;
 
var i: integer;
	a: array[1..10] of integer;	
	
begin
	i:=1;
	
	while (i<=10) do
	begin
		a[i]:= 1;
		i:=succ(i);
	end;

	i:=1;
	
	while (i<=10) do
	begin	
		write(a[i]);
		i:=succ(i);
	end;
	
end.
