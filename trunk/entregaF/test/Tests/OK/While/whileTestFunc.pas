program whileTestFunc;
const tope=4;
var m,j,k:integer;

{1}{procedure proc(var i:integer);
{2}procedure proc(i:integer);{}
begin
	while i<=tope do
	begin
	 j:=1;
	 while j<=tope do
	 begin
	    k:=1;
		while k<=tope do
		begin
			write(k);
			k:=k+1;
		end;
		j:=j+1;
	 end;	 
	 i:=i+1;
	end;
end;

begin
	m:=1;
	proc(m);	
	write(m);	
end.