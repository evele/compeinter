Program ExpresionesError1;{ "Asignacion de valor a componente de array:Tipos incompatibles "}
type tarray=array [1..12]of integer;
var a:tarray;

begin

a[1]:=TRUE; 

end.