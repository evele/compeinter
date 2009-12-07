program CantidadDeVecesDeCadaVocal;
type TArreglo=array[1..5]of integer;
var arreglo:TArreglo;{}
{Utilidad:}

procedure CantVecesVocal(var A:Tarreglo);
         {Utilidad: }
         var ch:char;{}
         begin{-------------------CantVecesVocal--------------------}
         read(ch);
         if ch='.'
            then begin
                  A[1]:=0;
                  A[2]:=0;
                  A[3]:=0;
                  A[4]:=0;
                  A[5]:=0;
                 end
            else begin
                 CantVecesVocal(A);
				 if((ch='A') or (ch='a'))
					then A[1]:=A[1]+1
					else if((ch='E') or (ch='e'))
							then A[2]:=A[2]+1
							else if((ch='I') or (ch='i'))
									then A[3]:=A[3]+1
									else if((ch='O') or (ch='o'))
											then A[4]:=A[4]+1
											else if((ch='U') or (ch='u'))
													then A[5]:=A[5]+1;
                 end;
         end; {-----------------CantVecesVocal-----------------}

procedure verCantVocales;
	begin
	write('A');write('s');write(':');writeln(arreglo[1]);
	write('E');write('s');write(':');writeln(arreglo[2]);
	write('I');write('s');write(':');writeln(arreglo[3]);
	write('O');write('s');write(':');writeln(arreglo[4]);
	write('U');write('s');write(':');writeln(arreglo[5]);
	end;
			
begin {-----------------CantidadDeVecesDeCadaVocal-----------}
write('I','n','g','r','e','s','e',' ','u','n','a',' ','f','r','a','s','e',' ','t','e','r','m','i','n','a','d','a',' ','e','n',' ','p','u','n','t','o');writeln(':');
CantVecesVocal(arreglo);
writeln(' ');
verCantVocales;
end.{-----------CantidadDeVecesDeCadaVocal----------}

