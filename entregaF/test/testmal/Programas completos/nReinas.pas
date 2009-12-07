program nReinas;

const
    topetab=25;
	reina = 1;
	intentada = 2;
	
	

type
   tIndice= 0..26;	{uno mas de cada lado para q no falle le MEPa cuando se pasa de rango}

   tCelda = array[1..2] of boolean;

   tTablero=array[tIndice] of array [tIndice] of tCelda;

var
  fil,col,numreinas:tIndice;
  tabla:tTablero;
  fin:boolean;
  rpta:char;

     procedure crearTabla(var tab:tTablero; num:tIndice);
     var
       i,j:tindice;
     begin
		i:= 1;
        while (i <= num) do
		begin
			j:= 1;
			while (j <= num) do
			begin
				tab[i][j][reina] := false;
				tab[i][j][intentada] := false;
				j:= succ(j);
			end;
			i:= succ(i);
		end;		
     end;

    procedure mostrarTabla(var tab:ttablero;num:tindice);
     var
       i,j:tindice;
     begin
		i:= 1;
        while (i <= num) do
		begin
			j:= 1;
			while (j <= num) do
			begin
                 if (tab[i][j][reina]) then
                   begin
                      write('[');
                      write('R');
                      write(']');
                   end
                 else
                    write('[',' ',']');
					
				j:= succ(j);				
			end;
			i:= succ(i);
			writeln(' ');			
		end;
		writeln(' ');
	end;

     function esCorrecta(f,c,num:tindice):boolean;
     begin
       escorrecta:= (f >= 1) and (f <= num) and (c >= 1) and (c <= num);
     end;

     procedure verDiagonalder(var tab:ttablero;f,c:tindice;var limpio:boolean;num:tindice);
     begin
         limpio:=true;
         while (escorrecta(f,c,num)) and (limpio) do
           begin
               if (tab[f][c][reina]) then
                 limpio:=false;

               c:=c+1;
               f:=f-1;
           end;
     end;

     procedure verVertical(var tab:ttablero;f,c:tindice;var limpio:boolean);
     begin
       limpio:=true;
       while (f >= 1) and (limpio) do
         begin
            if (tab[f][c][reina]) then
               limpio:=false;
            f:=f-1;
         end;
     end;

     procedure verDiagonalizq(var tab:ttablero;f,c:tindice;var limpio:boolean);
     begin
       limpio:=true;
       while (f >= 1) and (c >= 1) and (limpio) do
         begin
           if (tab[f][c][reina]) then
              limpio:=false;
           f:=f-1;
           c:=c-1;
         end;
     end;

     procedure limpiarFila(var tab:ttablero;f,num:tindice);
     var
       i:tindice;
     begin
		i:= 1;
         while (i <= num) do
           begin
              tab[f][i][reina]:=false;
              tab[f][i][intentada]:=false;
			  i:= succ(i);
           end;
     end;

     function ubiqueReina (var tab:ttablero;var f,c:tindice;num:tindice):boolean;
     var
       ubique:boolean;
       caux:tindice;
     begin
         ubique:= false;
         caux:= c;

         while (not ubique) and (caux <= num) do
           begin
              if (tab[f][caux][reina]) then
                 tab[f][caux][reina]:=false;

              if (not tab[f][caux][intentada]) then
                begin
                  verDiagonalder(tab,f,caux,ubique,num);
                  if (ubique) then
                     verVertical(tab,f,caux,ubique);
                  if (ubique) then
                     verDiagonalizq(tab,f,caux,ubique);
                  if (ubique) then
                    begin
                      tab[f][caux][reina]:=true;
                      tab[f][caux][intentada]:=true;
                    end;
                end;

             if (not ubique) then  
				caux:=caux + 1;
           end;

           if (ubique) then
             begin
                c:= caux;
                limpiarFila(tab,f+1,num)
             end

           ubiqueReina:= ubique;
     end;


     procedure n_reinas(var tab:ttablero;f,c,num:tindice;var termine:boolean);
	 var r:char;
	 ubic:boolean;
     begin
        if (f <= num) then
          begin
			ubic:=ubiqueReina(tab,f,c,num);
             while (not termine) and (ubic) do
			 begin				
				n_reinas(tab,f+1,1,num,termine);				
				
				if (not termine) then 
					ubic:=ubiqueReina(tab,f,c,num);				
			 end;
          end
        else
          termine:=true;
     end;

begin
  rpta:='s';
  
   while ((rpta='s') or (rpta='S')) do
   begin
		fil:= 1;
		col:= 1;
		fin:= false;

		write('E','l','i','j','a',' ','e','l',' ','n','u','m','e','r','o',' ','d','e',' ','r','e','i','n','a','s',' ','q','u','e',' ','d','e','s','e','a',' ','o','r','d','e','n','a','r',':');
		writeln(' ');
		readln(numreinas);


		if (numreinas > 3) then
		   begin
			  creartabla(tabla,numreinas);
			  n_reinas(tabla,fil,col,numreinas,fin);
			  writeln(' ');
			  mostrartabla(tabla,numreinas);
			  writeln(' ');
		   end
		else
		   begin
			  write('N','o',' ','e','s',' ','p','o','s','i','b','l','e',' ','o','r','d','e','n','a','r',' ','n','u','m','r','e','i','n','a','s',' ','r','e','i','n','a','s',' ','e','n',' ','e','l',' ','t','a','b','l','e','r','o');
			  writeln(' ');
		   end;

		write('D','e','s','e','a',' ','p','r','o','b','a','r',' ','c','o','n',' ','o','t','r','o',' ','n','u','m','e','r','o',' ','(','s','|','n',')',':',' ');
		writeln(' ');
		readln(rpta);
	end;
end.