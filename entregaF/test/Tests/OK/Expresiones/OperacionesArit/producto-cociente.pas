program Producto_Cociente;
const c=3;
var a,b,producto,cociente:integer;
{Datos de entrada:num1,num2.
 Datos de salida: Producto,Cociente.
 Datos aux: c.
 Funcion:Dados num1 y num2, compara su diferencia con c y si esta es mayor
         que c entonces realiza el producto num1*num2; si la diferencia entre
         num1 y num2 es menor que c entonces realiza el cociente num1/num2.}

 begin 
 readln(a,b);
 if (a-b)>c
    then
        begin
        producto:=a*b;        
        end
    else
        begin
        cociente:=a div b;        
        end; 
 end.