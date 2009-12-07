program ejemplo;
type
  t = array [1..-2] of integer; {-->; Debería haber un error semántico por
límites invertidos, pero al compilar el programa anterior se obtiene el
mensaje: "El PROGRAMA: ejemplo10.pas es SINTACTICA y SEMANTICAMENTE CORRECTOS".}
begin
end.