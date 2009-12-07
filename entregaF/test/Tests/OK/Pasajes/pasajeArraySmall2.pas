program arrayRef;
const tope=1;
type Tarreglo=array[1..tope]of integer;
var
    a:Tarreglo;	
		
	procedure succArray(var suc:Tarreglo);	
		begin
		 suc[1]:=succ(suc[1]);
		 write('S');write(':');
		 writeln(suc[1]);
		end;	
		
	procedure incArray(var other:Tarreglo);		
		begin
			other[1]:=8;
			{other[2]:=succ(other[2]);}
			write('I');write(':');			
			writeln(other[1]);		
			succArray(other);
		end;
		
begin
{	a[1]:=5;
{	a[2]:=5;}
{	write('P');write(' ');
	writeln(a[1]);
{	writeln(a[2]);
	
{4}	incArray(a);

	{succArray(a);}
	
{	write('M');write(' ');
	writeln(a[1]);
{	writeln(a[2]);}
end.