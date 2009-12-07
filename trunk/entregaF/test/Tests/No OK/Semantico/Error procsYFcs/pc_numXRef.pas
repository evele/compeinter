{
 Parametro actual incorrecto para el tipo de pasaje.
}
program numXRef;

var a:integer;

	procedure p(var V,c,b:integer);

	begin
		writeln(V,c,b); 
	end;

begin
	p(a,56,88);
end.
