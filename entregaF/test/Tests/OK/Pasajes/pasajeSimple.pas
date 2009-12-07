program simpleRef;
var a:integer;
	i:integer;

{1}	{procedure printInt(var another:integer);	{anda ok cdo se llama desde ppal, anda MAL si se llama de incArray}
{2}	procedure printInt(another:integer);	
		begin
			write('P');
			writeln(another);			
		end;		
	
	procedure incInt(var other:integer);		
		begin
			other:=succ(other);
			write('I');
			writeln(other);			
{3}		printInt(other);
		end;

begin

	a:=1;

{4}	incInt(a);	

	write('M');
	writeln(a);	
{5}	{printInt(a);}
end.

{2,3,4	ok}
{2,4,5	ok}
{1,4,5		ok}
{1,3,4		ok}
