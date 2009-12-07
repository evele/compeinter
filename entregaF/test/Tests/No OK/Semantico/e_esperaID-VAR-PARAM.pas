program ejemplo;
type
  t = array [1..2] of integer;
var x:t;

  procedure p1(a: t);
  begin
	t[1]:=99;
	write(t[1],t[2]);
  end;
  
begin
	x[1]:=44;
	x[2]:=55;
	p1(x);
	write(x[1],x[2]);
end.