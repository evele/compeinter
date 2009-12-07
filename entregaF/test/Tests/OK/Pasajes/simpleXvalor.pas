Program p;
var x,y:Integer;

    procedure p1(a:Integer);
    var b:Integer;

        procedure p2(c:Integer);
        var x:Integer;
        begin
             x:=1;
             c:=c-3;
             writeln(c);
             p1(c);
        end;
    begin
         b:=a+x;
         if(b>0)
             then p2(b);
    end;
    
    procedure q1(d:Integer);
    var x:Integer;
    begin
        x:=3;
        d:=d-1;
        p1(d);
    end;
begin
     x:=0;
	 write('n','u','m',':');writeln(' ');
     read(y);
     q1(y);
end.

