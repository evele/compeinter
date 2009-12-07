program CribaDeEratostenes;
{uses crt;}
type Tindice=1..20;
     Tarreglo=array[Tindice]of boolean;
var n:integer;{}
{Utilidad:}

function modulo(j,i:integer):integer;
begin

{write('A',' ',j);writeln(' ');
write('B',' ',i);writeln(' ');{}

while(j>=i)do
	begin
	j:=j-i;
	end;
	
{write('M','O','D',j);writeln(' ');	{}
modulo:=j;
end;


PROCEDURE Criba(n:integer);
	 {Utilidad: }
	 var A:Tarreglo; i,j:Tindice;{}
	 begin{-------------------Criba--------------------}
	 i:=1;
	 while i<=n do
	 begin
		 A[i]:=true;
		 i:=i+1;
	 end;
	 i:=2;
	 write(i);
	 write(',');
	 while((i<=n)and A[i]) do
		 begin
		  j:=i+1;
		  while (j<=n)do
		   begin{}
			 if A[j]
			 then if(modulo(j,i)=0)
					then A[j]:=false
					else begin
						 write(j);
						 write(',');
						 end;
			 j:=j+1;
		   end;{}
		  i:=i+1;
		  end;
	  end; {-----------------Criba-----------------}

begin {-----------------CribaDeEratostenes-----------}
{clrscr;{}
write('I','n','g','r','e','s','e',' ','u','n',' ','n','u','m','e','r','o',':');writeln(' ');
readln(n);
writeln(' ');
write('L','o','s',' ','p','r','i','m','o','s',' ','h','a','s','t','a',' ',n,' ','s','o','n',':');writeln(' ');
criba(n);
{writeln;
readln;}
end.{-----------CribaDeEratostenes----------}
