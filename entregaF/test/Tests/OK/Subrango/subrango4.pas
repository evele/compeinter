PROGRAM subrrango4;
VAR C:INTEGER;
    B:1..5;

BEGIN
C:=0;
B:=5;
 
 C:=succ(C);
 WRITEln(C);
 
 B:=pred(B);
 B:=pred(B);

 Writeln(B);
 
END.