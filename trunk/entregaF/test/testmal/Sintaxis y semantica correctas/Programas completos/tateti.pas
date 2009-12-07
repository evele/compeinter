program tateti;

const
    hombre = 1;
    pc     = 0;
	topeTab = 3;

type
   tindice = 0..4; {tienen uno mas de cada lado para que no halla error de ejecucion cuando le asignan un valor fuera del rango}
   tjugador = -1..hombre;

   tablero = array[tindice] of array[tindice] of tjugador;

var
  tab:tablero;
  ganohom,ganopc:boolean;
  rpta:char;


   procedure mostrartablero(var t:tablero);
   var
       f,c:tindice;
   begin
		f:= 1;
       while (f <= topeTab) do
         begin
            c:= 1;
			while(c <= topeTab) do
              begin
                 write('[');

                 if (t[f][c] = -1) then
                    write(' ')
                 else
                    if (t[f][c] = 1) then
                       write('x')
                    else
                       write(t[f][c]);

                 write(']');
				 c:= succ(c);
              end;
            writeln(' ');
			f:= succ(f);
         end;
   end;

   function modulo(coord, b:integer):integer;
   var modu:integer;
   begin
		modu:= coord;
		while(modu >= b) do
			modu:= modu- b;
			
		modulo:= modu;
   end;
   
   function coordValida(n:integer):boolean;
   begin
       coordValida:= (n div 10 >= 1) and (n div 10 <= topeTab) and
                  (modulo(n,10) >= 1) and (modulo(n,10) <= topeTab);
   end;

   function esvalida(fil,col:tindice):boolean;
   begin
       esvalida:= (fil <= topeTab) and (fil >= 1) and (col <= topeTab) and (col >= 1)
   end;

   function estalibre(var t:tablero;coord:integer):boolean;
   begin
       estalibre:= (t[coord div 10][modulo(coord,10)] = -1);
   end;


   function llenofila(var t:tablero;jugador:tjugador;fil:tindice):boolean;
   var
       c:tindice;
       lleno:boolean;
   begin
       c:= 1;
       lleno:= true;

       while (lleno) and (c <= topeTab) do
          begin
             lleno:= (t[fil][c] = jugador);
             c:= c + 1;
          end;

       llenofila:= lleno;
   end;

   function llenocolumna(var t:tablero;jugador:tjugador;col:tindice):boolean;
   var
      f:tindice;
      lleno:boolean;
   begin
       f:= 1;
       lleno:= true;

       while (lleno) and (f <= topeTab) do
          begin
             lleno:= (t[f][col] = jugador);
             f:= f + 1;
          end;

       llenocolumna:= lleno;
   end;

   function llenodiagonalder(var t:tablero;jugador:tjugador):boolean;
   var
     f,c:tindice;
     lleno:boolean;
   begin
       f:= 1;  c:= topeTab;
       lleno:= true;

       while (lleno) and (c <= topeTab) and (f <= topeTab) do
          begin
             lleno:= (t[f][c] = jugador);
             f:= f + 1;
             c:= c - 1;
          end;

       llenodiagonalder:= lleno;
   end;

   function llenodiagonalizq(var t:tablero;jugador:tjugador):boolean;
   var
     f,c:tindice;
     lleno:boolean;
   begin
       f:= 1;  c:= 1;
       lleno:= true;

       while (lleno) and (c <= topeTab) and (f <= topeTab) do
          begin
             lleno:= (t[f][c] = jugador);
             f:= f + 1;
             c:= c + 1;
          end;

       llenodiagonalizq:= lleno;
  end;


   function esganador(var t:tablero;jugador:tjugador):boolean;
   begin
       esganador:= false;

       if (llenofila(t,jugador,1)) then
          esganador:= true
       else
         if (llenofila(t,jugador,2)) then
            esganador:= true
         else
           if (llenofila(t,jugador,topeTab)) then
              esganador:= true
           else
             if (llenocolumna(t,jugador,1)) then
                esganador:= true
             else
               if (llenocolumna(t,jugador,2)) then
                  esganador:= true
               else
                 if (llenocolumna(t,jugador,topeTab)) then
                    esganador:= true
                 else
                   if (llenodiagonalder(t,jugador)) then
                      esganador:= true
                   else
                     if (llenodiagonalizq(t,jugador)) then
                       esganador:= true;

   end;


   function soniguales(var t:tablero;jugador:tjugador;fil,col,fil_2,col_2:tindice):boolean;
   begin
       if (esvalida(fil_2,col_2)) then
          soniguales:= (t[fil][col] = jugador) and (t[fil_2][col_2] = jugador)
       else
          soniguales:= false;
   end;


   procedure colocarultima(var t:tablero;fil,col,fil_2,col_2:tindice;var coloque:boolean);
   begin
       coloque := false;

       if (((fil = 1) and (col = 1) and (fil_2 = 2) and (col_2 = 1))  or
           ((fil = 1) and (col = topeTab) and (fil_2 = 2) and (col_2 = 2))  or
           ((fil = topeTab) and (col = 2) and (fil_2 = topeTab) and (col_2 = topeTab))) and
           (t[topeTab][1] = -1) then
          begin
              t[topeTab][1]:= pc;
              coloque:= true;
          end
       else
         if (((fil = 1) and (col = 1) and (fil_2 = 1) and (col_2 = 2))  or
             ((fil = 2) and (col = 2) and (fil_2 = topeTab) and (col_2 = 1))  or
             ((fil = 2) and (col = topeTab) and (fil_2 = topeTab) and (col_2 = topeTab))) and
             (t[1][topeTab] = -1) then
          begin
              t[1][topeTab]:= pc;
              coloque:= true;
          end
         else
           if (((fil = 1) and (col = 1) and (fil_2 = 2) and (col_2 = 2))  or
               ((fil = 1) and (col = topeTab) and (fil_2 = 2) and (col_2 = topeTab))  or
               ((fil = topeTab) and (col = 1) and (fil_2 = topeTab) and (col_2 = 2))) and
               (t[topeTab][topeTab] = -1) then
          begin
              t[topeTab][topeTab]:= pc;
              coloque:= true;
          end
           else
             if (((fil = 1) and (col = 2) and (fil_2 = 2) and (col_2 = 2))  or
                 ((fil = topeTab) and (col = 1) and (fil_2 = topeTab) and (col_2 = topeTab))) and
                 (t[topeTab][2] = -1) then
             begin
                t[topeTab][2]:= pc;
                coloque:= true;
             end
             else
               if (((fil = 1) and (col = 2) and (fil_2 = 1) and (col_2 = topeTab))  or
                   ((fil = 2) and (col = 1) and (fil_2 = topeTab) and (col_2 = 1))  or
                   ((fil = 2) and (col = 2) and (fil_2 = topeTab) and (col_2 = topeTab))) and
                   (t[1][1] = -1) then
               begin
                  t[1][1]:= pc;
                  coloque:= true;
               end
              else
                if (((fil = 2) and (col = 1) and (fil_2 = 2) and (col_2 = 2))  or
                    ((fil = 1) and (col = topeTab) and (fil_2 = topeTab) and (col_2 = topeTab))) and
                    (t[2][topeTab] = -1) then
                begin
                   t[2][topeTab]:= pc;
                   coloque:= true;
                end
                else
                  if (((fil = 2) and (col = 2) and (fil_2 = topeTab) and (col_2 = 2))  or
                      ((fil = 1) and (col = 1) and (fil_2 = 1) and (col_2 = topeTab))) and
                      (t[1][2] = -1) then
                  begin
                     t[1][2]:= pc;
                     coloque:= true;
                  end
                  else
                    if (((fil = 2) and (col = 2) and (fil_2 = 2) and (col_2 = topeTab))  or
                        ((fil = 1) and (col = 1) and (fil_2 = topeTab) and (col_2 = 1))) and
                        (t[2][1] = -1) then
                    begin
                       t[2][1]:= pc;
                       coloque:= true;
                    end
                    else
                      if (((fil = 1) and (col = 1) and (fil_2 = topeTab) and (col_2 = topeTab))  or
                          ((fil = 1) and (col = 2) and (fil_2 = topeTab) and (col_2 = 2))  or
                          ((fil = 1) and (col = topeTab) and (fil_2 = topeTab) and (col_2 = 1))  or
                          ((fil = 2) and (col = 1) and (fil_2 = 2) and (col_2 = topeTab))) and
                          (t[2][2] = -1) then
                      begin
                         t[2][2]:= pc;
                         coloque:= true;
                      end
   end;


   function posiblevictoria(var t:tablero;jugador:tjugador;fil,col:tindice):boolean;
   var
      puso:boolean;
   begin
       posiblevictoria:= false;

       if (soniguales(t,jugador,fil,col,fil,col+1)) then
         begin
            colocarultima(t,fil,col,fil,col+1,puso);
            posiblevictoria:= puso;
         end
       else
         if (soniguales(t,jugador,fil,col,fil+1,col+1)) then
           begin
              colocarultima(t,fil,col,fil+1,col+1,puso);
              posiblevictoria:= puso;
           end
          else
            if (soniguales(t,jugador,fil,col,fil+1,col)) then
              begin
                  colocarultima(t,fil,col,fil+1,col,puso);
                  posiblevictoria:= puso;
              end
            else
              if (soniguales(t,jugador,fil,col,fil+1,col-1)) then
                begin
                   colocarultima(t,fil,col,fil+1,col-1,puso);
                   posiblevictoria:= puso;
                end
              else
                if (soniguales(t,jugador,fil,col,fil,col-1)) then
                  begin
                     colocarultima(t,fil,col,fil,col-1,puso);
                     posiblevictoria:= puso;
                  end
                else
                  if (soniguales(t,jugador,fil,col,fil-1,col-1)) then
                    begin
                       colocarultima(t,fil,col,fil-1,col-1,puso);
                       posiblevictoria:= puso;
                    end
                  else
                    if (soniguales(t,jugador,fil,col,fil-1,col)) then
                      begin
                         colocarultima(t,fil,col,fil-1,col,puso);
                         posiblevictoria:= puso;
                      end
                    else
                      if (soniguales(t,jugador,fil,col,fil-1,col+1)) then
                        begin
                           colocarultima(t,fil,col,fil-1,col+1,puso);
                           posiblevictoria:= puso;
                        end
                      else
                        if (soniguales(t,jugador,fil,col,fil,col+2)) then
                          begin
                             colocarultima(t,fil,col,fil,col+2,puso);
                             posiblevictoria:= puso;
                          end
                        else
                          if (soniguales(t,jugador,fil,col,fil+2,col+2)) then
                            begin
                               colocarultima(t,fil,col,fil+2,col+2,puso);
                               posiblevictoria:= puso;
                            end
                          else
                            if (soniguales(t,jugador,fil,col,fil+2,col)) then
                              begin
                                 colocarultima(t,fil,col,fil+2,col,puso);
                                 posiblevictoria:= puso;
                              end
                            else
                              if (soniguales(t,jugador,fil,col,fil+2,col-2)) then
                                begin
                                   colocarultima(t,fil,col,fil+2,col-2,puso);
                                   posiblevictoria:= puso;
                                end;

   end;



   procedure lineacasicompleta (var t:tablero;jugador:tjugador;var complete:boolean);
   var
      f,c:tindice;
   begin
       f:= 1;
       complete:= false;

       while (not complete) and (f <= topeTab) do
           begin
               c:= 1;

               while (not complete) and (c <= topeTab) do
                   begin
                       if (t[f][c] = jugador) then
                          if (posiblevictoria(t,jugador,f,c)) then
                              complete:= true;
                       c:= c + 1;
                   end;
               f:= f + 1;
           end;
   end;

   procedure muevejugador(var t:tablero);
   var
      coord:tindice;
      vale:boolean;
   begin
       write('s','u',' ','t','u','r','n','o',':',' ');
       vale:= false;

       while (not vale) do
          begin
             readln(coord);

             if (coordValida(coord)) and (estalibre(t,coord)) then
               begin
                  vale:= true;
                  t[coord div 10][modulo(coord,10)]:= hombre;
               end
             else
               write('c','o','o','r','d','e','n','a','d','a','s',' ','i','n','v','a','l','i','d','a',
			   's',' ','v','u','e','l','v','a',' ','a',' ','e','s','c','r','i','b','i','r','l','a','s',':',' ');
          end;
   end;

	function random(n:integer):integer;
	var rand:integer;
	begin
		rand:=n;
		while(rand > 3) do
			rand:= rand - random(n-1);
			
		random := rand;		
	end;
   
   procedure movercualquiera(var t:tablero);
   var
      puedo:boolean;
      f,c:tindice;
   begin

       puedo:= false;

       if (t[2][2] = hombre) then
         begin
           while (not puedo) do
             begin
             f:= random(4);
             c:= random(5);

             if (t[f][c] = -1) then
               begin
                   puedo:= true;
                   t[f][c]:= pc;
               end;
            end;
         end
       else
          t[2][2]:= pc;
   end;

   procedure puedobloquear(var t:tablero;var blokie:boolean);
   begin
       lineacasicompleta(t,hombre,blokie);
   end;

   procedure jugaryganar(var t:tablero;var gane:boolean);
   begin
       lineacasicompleta(t,pc,gane);
   end;

   procedure muevepc (var t:tablero);
   var
      puedo,termine:boolean;
   begin
       puedobloquear(t,puedo);

       if (not puedo) then
         begin
            jugaryganar(t,termine);

            if (not termine) then
               movercualquiera(t);
          end;
   end;


   procedure jugartateti (var t:tablero;var gano,gane:boolean);
   var
      mov:integer;
   begin
        mov:= 1;

        while (mov <= 9) and (not gano) and (not gane) do
            begin
                mostrartablero(t);
                writeln(' ');
                muevejugador(t);
                mov:= mov + 1;
                mostrartablero(t);
				writeln(' ');

                if (mov > 5) and (esganador(t,hombre)) then
                  gano:= true
                else
                   if (mov <= 9) then
                     begin
                         muevepc(t);
						 write('J','u','e','g','a',' ','M','a','q','u','i','n','a','.');
						 writeln(' ');
                         mov:= mov + 1;

                         if (mov > 6) and (esganador(t,pc)) then
                            gane:= true;
                     end;
            end;

   end;

   procedure creartablero(var t:tablero);
   var
      f,c:tindice;
   begin
		f:=1;
       while (f <= topeTab) do { for f:= 1 to topeTab do}
	   begin
		  c:=1;
		  while (c <= topeTab) do {for c:= 1 to topeTab do}
		  begin
			t[f][c]:= -1;
			c:= succ(c);
		  end;
		  f:= succ(f);
	   end;
   end;

begin
   rpta:='s';
   
   while (rpta ='s') or (rpta = 'S') do
   begin
     ganohom:= false;
     ganopc:= false;
     creartablero(tab);
     jugartateti(tab,ganohom,ganopc);

     if (ganohom) then
       begin
          writeln(' ');
          write('F','e','l','i','c','i','t','a','c','i','o','n','e','s','!','!',' ','u','s','t','e','d',' ','g','a','n','o','.');
          writeln(' ');
       end
     else
        if (ganopc) then
           begin
              write('J','e','j','e','j','e',' ','t','e',' ','g','a','n','e','!','!','.');
              writeln(' ');
           end
        else
           begin
              writeln(' ');
              write('H','e','m','o','s',' ','e','m','p','a','t','a','d','o','.');
              writeln(' ');
           end;

    writeln(' ');
    write('D','e','s','e','a',' ','j','u','g','a','r',' ','d','e',' ','n','u','e','v','o',' ','(','s','|','n',')',':',' ');
    readln(rpta);
  end;
end.