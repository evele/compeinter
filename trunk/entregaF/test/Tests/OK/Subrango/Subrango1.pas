program subrango;
type sub=1..12;
    sub2=-5..0;

var x,y:sub2;
	z:sub;

begin
	x:=succ(-3);
	write(x);
	y:=pred(0);	
	write(y);
	z:= succ(succ(y));
	write(z);
end.
