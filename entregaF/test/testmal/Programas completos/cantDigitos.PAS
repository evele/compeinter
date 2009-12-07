program CantidadDeDigitos;
var N,Cant:integer;
Procedure ContarDigitos(Numero:integer; var Cantidad:integer);
{Datos de Entrada:Numero
 Datos de Salida:Cantidad
 Funci¢n:Contar los digitos de N.
}
	begin
	{
	Cantidad:=0
	while Numero<>0 do
	begin
	Numero:=Numero div 10;
	Cantidad:=Cantidad+1;
	end;	{}
	
	if ((-10<Numero) and (Numero<10))
	then Cantidad:=1
	else begin
			ContarDigitos(Numero div 10,Cantidad);
			Cantidad:= Cantidad+1;
		end;
	end;

begin
{Lectura de N}
Write('I','n','g','r','e','s','e',' ','u','n',' ','n','u','m','e','r','o',' ','n','o',' ','n','e','g','a','t','i','v','o',':');writeln(' ');
readln(N);
{Conteo de Digitos}
ContarDigitos(N,Cant);
{Impresion del numero de digitos}
write('E','l',' ','n','u','m','e','r','o',' ',N,' ',',','t','i','e','n','e',' ',Cant,' ','d','i','g','i','t','o','s','.');writeln(' ');
end.

























































































