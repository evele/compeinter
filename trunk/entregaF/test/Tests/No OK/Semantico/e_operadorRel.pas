program opRel;
{en este caso falla xq el OR tiene mayor precedencia que los operadores relacionales, falta parentizarlo}
begin
	if(7<5 or 6<9)
	then writeln(1)
	else writeln(0);
end.