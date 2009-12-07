program monio;

var i,j,k,l,n:integer;{}
{Utilidad:}

begin {-----------------ImprimirPiramide-----------}
 write('I');write('n');write('g');write('r');write('e');write('s');
 write('e');write(' ');write('u');write('n');write(' ');write('n');
 write('u');write('m');write(' ');write('1');write('-');write('9');
 write(':');
 readln(n);
 writeln(' ');
 writeln(' ');
 i:=1;
 while i<=n do
  begin
   j:=1;
   while j<=i do
   begin
    write(j);
	j:=j+1;
   end;
   
   k:=j;
   while k<=(2*n-1)-i do
   begin
    write(' ');
	k:=k+1;
   end;
   
   l:=k;
   while l<=(2*n-1) do
   begin
    write(l);
	l:=l+1;
   end;	
   writeln(' ');
   i:=i+1;
  end;

 i:=n+1; 
 while i<=(2*n-1) do
  begin
   j:=1;
   while j<=(2*n)-i do
    begin
	write(j);
	j:=j+1;
	end;
	
	k:=j;
   while k<=i-1 do
   begin
    write(' ');
	k:=k+1;
	end;
	
	l:=i;
	while l<=(2*n-1) do
	begin
    write(l);
	l:=l+1;
	end;
   writeln(' ');
   i:=i+1;
  end;
end.{-----------ImprimirPiramide----------}

