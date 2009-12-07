program CribaDeEratostenes;
{uses crt;}
type Tindice=1..20;
     Tarreglo=array[Tindice]of boolean;
var n:integer;{}
{Utilidad:}
PROCEDURE Criba(n:integer);
	 {Utilidad: }
	 var A:Tarreglo; i,j:Tindice;{}
	 begin{-------------------Criba--------------------}
	 i:=1;
	 while i<=n do
	 begin
		 A[i]:=true;
		 A[j]:=true;	 
		 i:=i+1;
	 end;
	 i:=2;
	 write(i);
	 write(',');
	 while (i<=n)and A[i] do
		 begin
		  j:=i+1;
		  while (j<=n)do
		   begin
			 if A[j]
			 then if(j * i)=0
					then A[j]:=false
					else begin
						 write(j);
						 write(',');
						 end;
			 j:=j+1;
		   end;
		  i:=i+1;
		  end;
	  end; {-----------------Criba-----------------}

begin {-----------------CribaDeEratostenes-----------}
{clrscr;{}
write('I','n','g','r','e','s','e',' ','u','n',' ','n',',u','m','e','r','o',':');writeln(' ');
readln(n);
writeln(' ');
writeln('Los primos hasta',n, 'son:');
criba(n);
{writeln;
readln;}
end.{-----------CribaDeEratostenes----------}
