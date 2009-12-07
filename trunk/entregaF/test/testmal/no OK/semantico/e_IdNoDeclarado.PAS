program CantidadDeVecesDeCadaVocal;
type  Tvocales=a..u;
     TArreglo=array[Tvocales]of integer;
var arreglo:TArreglo;{}
{Utilidad:}

PROCEDURE CantVecesVocal(var A:Tarreglo);
         {Utilidad: }
         var ch:char;{}
         begin{-------------------CantVecesVocal--------------------}
         read(ch);
         if ch='.'
            then begin
                  A[a]:=0;
                  A[e]:=0;
                  A[i]:=0;
                  A[o]:=0;
                  A[u]:=0;
                 end
            else begin
                 CantVecesVocal(A);
                 case ch of
                  'A','a':A[a]:=A[a]+1;
                  'E','e':A[e]:=A[e]+1;
                  'I','i':A[i]:=A[i]+1;
                  'O','o':A[o]:=A[o]+1;
                  'U','u':A[u]:=A[u]+1;
                 end;
                 end;
         end; {-----------------CantVecesVocal-----------------}

begin {-----------------CantidadDeVecesDeCadaVocal-----------}
writeln('Ingrese una frase terminada en punto: ');
CantVecesVocal(arreglo);
writeln;
readln;
end.{-----------CantidadDeVecesDeCadaVocal----------}

