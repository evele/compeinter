Program ExpresionesError11;{ "Tipo de expresión no válida como índice de arreglo "}
type tarray=array [1..12]of integer;
var a:tarray;x,y:integer;

begin

a[x<>y]:=12; 

end.