program masivaSimple;
var
  a: array [1..2] of integer;
  b: array [3..4] of integer;
begin
  a[1] := 5;
  a[2] := 6;
  b := a;
  write(b[3]);
  write(b[4]);
end.