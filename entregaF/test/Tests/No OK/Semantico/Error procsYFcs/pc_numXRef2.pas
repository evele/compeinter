{
 Parametro actual no valido en pasaje por referencia. 
}
program numXRef2;

	procedure p(VAR V:integer);
	begin
		write(V); 
	end;

begin

	p(-5);

end.
