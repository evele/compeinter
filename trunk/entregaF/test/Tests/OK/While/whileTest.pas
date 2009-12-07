program whileTest;
const tope=4;
var i,j,k:integer;
begin
	i:=1;
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
end.