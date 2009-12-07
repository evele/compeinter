{ 
 Error: y:x donde x es id de variable NO de tipo
}

program paramFormales;

type A = array[1..3] of integer;
     bool = boolean;
	 
var x:A;

   procedure malTipo(b:bool; y:x);    
   begin
		if(not b) then 
			writeln(y[2])
		else 
			writeln(0);
   end;

begin
 x[2]:=4;
 malTipo(false,x);
end.

