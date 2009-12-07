{
  Este programa contiene varias declaraciones de tipo arreglo válidas
}

program arreglo;

type 
    lista	= array [1..10] of 0..1000;
	matriz	= array [1..10] of integer;
	rptas	= array [1..5] of boolean;
	tabla	= array [1..5] of integer;
	cubo	= array [1..10] of matriz;

var 
   v1 : lista;	
   v2 : matriz;   
   v4 : tabla;
   v5 : cubo;
   v6 : array [1..10] of integer;	   
   v7 : rptas;

begin
;;
end.