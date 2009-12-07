{
 Llamada a un procedimiento RECURSIVO SIMPLE
}
program recursivoSimple;

var x,y,z:integer;

	procedure p(var a,b,c:integer);

	begin		
		while ((a > 0) and (b > 0) and (c > 0)) do
		begin
			a:= pred(a);
			b:= pred(b);
			c:= pred(c);			
			write(a,b,c);
        	p(a,b,c);			
		end;
	end;

begin
	x:= 5;
	y:= 5;
	z:= 5;
	p(x,y,z);
	write(x,y,z);
end. 
