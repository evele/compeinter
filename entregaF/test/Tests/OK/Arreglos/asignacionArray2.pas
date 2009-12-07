program arreglo;

type 
    lista	= array [1..10] of 0..1000;
	matriz1	= array [1..10] of integer;
	matriz2	= array [1..10] of integer;
	matriz3	= array [1..10] of integer;
	rptas	= array [1..5] of boolean;
	cubo	= array [1..10] of matriz1;

var 
   v1 : lista;	
   m1 : matriz1;      
   v5 : cubo;
   v6 : array [1..10] of integer;	   
   v7 : rptas;
   m2 : matriz2;
   m4,m3 : matriz3;
   nro : integer;
begin
	m1[1] := 99;
	nro:=m1[1];
	write(nro);
	m4:=m3;	
	nro:=v6[10];
	writeln(nro);	
	v6[2]:=55;
	nro:=v6[2];
	write(nro);
end. 