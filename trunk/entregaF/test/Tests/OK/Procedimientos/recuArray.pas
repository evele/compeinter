{
 Llamada a un procedimiento RECURSIVO CON TIPO ARRAY
}
program recursivoArray;

const tope = 3;

type arreglo = array[1..tope] of integer;

var arr: arreglo;
	f:boolean;
	
	procedure asignar(var a:arreglo;n:integer);
	begin
		a[n]:=n;	
	end;
	
	
	procedure p(var a:arreglo; n:integer;var fin:boolean);	
	begin
		if (n <= tope) then		
			while (not fin) do
			begin
				asignar(a,n);				
	        	p(a,n+1,fin);				
			end
		else
			fin:=true;
	end;

begin	
	f:=false;
	p(arr,1,f);
	write(arr[1],arr[2],arr[3]);
end. 