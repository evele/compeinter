program constantes;

const 
	{<constante> ::= NUM | ID | CARACTER | + NUM | - NUM | +ID | -ID}
	pi = 3;			{NUM}
	error = -1;		{ -NUM}
	ok = +1;		{ +NUM}
	error2 = error;	{ ID}
	menosPI = -pi;	{ -ID}
	masPI = +pi;	{ +ID}
	
var
   sumaConst: integer;

begin
	sumaConst := pi + error + ok + error2 + menosPI + masPI;	
end.

