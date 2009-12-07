program limiteInvertido;
const
  a = 6;
type
  t1 = a..2; {--> Debería haber un error semántico por límites invertidos.}
begin
end.