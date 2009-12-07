{
 Llamada a una funcion con mas de un parametro POR REFERENCIA
}

program fc5;

var a,b,c:integer;

	function porRefMas(var z,y,w:integer):integer;
	var a:integer;
	begin
		w := z + y;
		porRefMas := w;
	end;

begin
	a:=5;
	b:=1;
	write(porRefMas(a,b,c));	
end. 