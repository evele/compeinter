program ejemplo;
var
  a: array [1..2] of integer;
  b: array [1..3] of integer;
begin
  a := b; {-->; Debería haber un error semántico por incompatibilidad de tipos,
pero al compilar el programa anterior se obtiene el mensaje: "El PROGRAMA:
ejemplo08.pas es SINTACTICA y SEMANTICAMENTE CORRECTOS".'}
end.