program componenteArregloXReferencia;
var
  a: array [1..2] of integer;
  procedure p1(var b: integer);
  begin
    b := 4;
  end;
begin
  a[1] := 3;
  p1(a[1]);
  write(a[1]);
end.
