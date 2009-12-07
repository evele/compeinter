{
 El id de funcion no es asignado dentro del cuerpo de la funcion f
}

program errorfuncion;

var x:integer;

   function f(y:integer):integer;
   begin
     if(true) then y:=y+1; 
   end;

begin
 { variable 'x' sin inicializar (mepa la inicializa en 0) }
 
 x:=f(x);
 
 writeln(x);
end.