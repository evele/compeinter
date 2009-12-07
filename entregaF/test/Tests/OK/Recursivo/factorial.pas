program Fact;
{Utilidad:}
VAR n,facto:integer;
FUNCTION Factorial(n:integer):integer;
         {Utilidad: }
         {var{}
         begin{-------------------Factorial--------------------}
         if n=0
           then factorial:=1
           else factorial:=n*factorial(n-1);
         end; {-----------------Factorial-----------------}

begin {-----------------Fact-----------}
readln(n);
facto:=factorial(n);
end.{-----------Fact----------}
