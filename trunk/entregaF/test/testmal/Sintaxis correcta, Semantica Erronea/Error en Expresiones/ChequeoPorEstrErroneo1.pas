program AsigMasivaEstrErroneo;

type ta=array[1..12]of integer;
     tb=array[1..20]of integer;

var a:ta ; b:tb ;


procedure asig(var v,b:ta;);
begin

 v:=b;

end;

begin
 
  asig(a,b);  

end.