{
	Asignacion de componentes de arreglos
}

program componenteArray;

const tope = 10;

type arreglo = array [1..tope] of integer;

var a1,a2: arreglo;
i,j:integer;
	   
begin
  i:= 3;
  j:= 8;
  read(a1[i],a1[j]);
  write(a1[i],a1[j]);
  a2[tope] := a1[i];  
  write(a2[tope]);
end.