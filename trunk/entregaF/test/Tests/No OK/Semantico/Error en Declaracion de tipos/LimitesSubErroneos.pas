program subrango;{ El l�mite superior del tipo subrango es menor que el limite inferior. }
const min=-20;max=10;li=40;ls=20;

type S1=min..max;
    s2=1..12;
    s3=-20..100;	 
    s4=-20..max;
    s5=	li..80;
    s6=-li..min;
    s7=-10..-ls;

var x,y:S1;


begin
x:=succ(5);
write(x);

y:=pred(7);
write(y);

end.
