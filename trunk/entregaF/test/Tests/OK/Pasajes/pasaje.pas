Program pepe;
Type
s=array [1..2] of integer;
t = array [1..2] of s;
Var
z : s;
w: t;

procedure psub(Var b: s);
begin
writeln(2);
b[1]:=b[1]+1000;
writeln(2);
end;

procedure p(Var a: t);
begin
writeln(2);
psub(a[1]);
writeln(2)
end;

Begin
z[1]:=8;
w[1]:=z;
p(W);
End.

