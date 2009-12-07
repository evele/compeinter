program Vuelto;

var c,p,v,cant50,cant25,cant10,cant5,cant1,resto:integer;
    o:char;
{Funcion:Dado el costo de un producto y el efectivo para pagar (costo<efectivo),
         calcula el vuelto y muestra el vuelto con la menor cantidad de billetes de
         50,25,10,5 y 1.}
		 
	 
function modulo(j,i:integer):integer;
begin

{write('A',' ',j);writeln(' ');
write('B',' ',i);writeln(' ');{}

while(j>=i)do
	begin
	j:=j-i;
	end;
	
{write('M','O','D',j);writeln(' ');	{}
modulo:=j;
end;
		 
begin

write('I','n','g','r','e','s','e',' ','e','l',' ','c','o','s','t','o',':');writeln(' ');
readln(c);
write('I','n','g','r','e','s','e',' ','e','l',' ','v','a','l','o','r',' ','d','e','l',' ','d','i','n','e','r','o',' ','e','n',' ','e','f','e','c','t','i','v','o',':');writeln(' ');
readln(p);
v:=p-c;
cant50:=v div 50;
resto:=modulo(v,50);
cant25:=resto div 25;
resto:=modulo(resto,25);
cant10:=resto div 10;
resto:=modulo(resto,10);
cant5:=resto div 5;
resto:=modulo(resto,5);
cant1:=resto;
write('E','l',' ','v','a','l','o','r',' ','d','e','l',' ','v','u','e','l','t','o',' ','e','s',':',' ',v);writeln(' ');
write('L','a',' ','c','a','n','t','i','d','a','d',' ','d','e',' ','b','i','l','l','e','t','e','s',' ','d','e',' ','5','0',' ','e','s',':',' ',cant50);writeln(' ');
write('L','a',' ','c','a','n','t','i','d','a','d',' ','d','e',' ','b','i','l','l','e','t','e','s',' ','d','e',' ','2','5',' ','e','s',':',' ',cant25);writeln(' ');
write('L','a',' ','c','a','n','t','i','d','a','d',' ','d','e',' ','b','i','l','l','e','t','e','s',' ','d','e',' ','1','0',' ','e','s',':',' ',cant10);writeln(' ');
write('L','a',' ','c','a','n','t','i','d','a','d',' ','d','e',' ','b','i','l','l','e','t','e','s',' ','d','e',' ','5',' ','e','s',':',' ',cant5);writeln(' ');
write('L','a',' ','c','a','n','t','i','d','a','d',' ','d','e',' ','b','i','l','l','e','t','e','s',' ','d','e',' ','1',' ','e','s',':',' ',cant1);writeln(' ');
end.