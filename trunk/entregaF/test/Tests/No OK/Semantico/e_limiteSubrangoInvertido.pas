program ejemplo;
type
  t = array [1..-2] of integer; {-->; Deber�a haber un error sem�ntico por
l�mites invertidos, pero al compilar el programa anterior se obtiene el
mensaje: "El PROGRAMA: ejemplo10.pas es SINTACTICA y SEMANTICAMENTE CORRECTOS".}
begin
end.