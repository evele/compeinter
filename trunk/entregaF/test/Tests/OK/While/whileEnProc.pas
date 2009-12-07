{
 Estructura repetitiva While para recorrer un arreglo pasado por VALOR en un procedimiento
}
 
program whileEnProc;
 
const tope = 10;

type arreglo	= array [1..tope] of integer;
		
var v1: arreglo;
i,j:integer;

   procedure iniciar(var A:arreglo);
   var i,j:integer;
   begin
		i:=1;		
		while(i <= tope)do
		begin
			j:=1;
			while(j <= tope)do
			begin
				A[i] := i+j;
				j:=j+1;
			end;
			i:=i+1;
		end;
   end;
   
begin
	iniciar(v1);
	i:=1;
	while(i <= tope)do
	begin
		j:=1;
		while(j <= tope) do
		begin
			write(v1[i]);
			j:=succ(j);
		end;				
		i:=succ(i);
	end;
end.
