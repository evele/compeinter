Program Hanoi;

var
   M:integer;
   rpta:char;

  Procedure MoverDisco(N:integer;fuente,destino:char);
  begin
     write('M','o','v','e','r',' ','d','i','s','c','o',' ',N,' ','d','e',' ',fuente,' ','a',' ',destino);
	 writeln(' ');
  end;

  procedure mover(N:integer;fuente,destino,aux:char);
  begin
     if (N = 1) then
        MoverDisco(N,fuente,destino)
     else
       begin
          Mover(N-1,fuente,aux,destino);
          MoverDisco(N,fuente,destino);
          Mover(N-1,aux,destino,fuente);
       end;
  end;

begin
  rpta:='s';
  
  while (rpta = 's') or (rpta = 'S') do
  begin
      write('I','n','g','r','e','s','e',' ','e','l',' ','n','u','m','e','r','o',' ','d','e',' ','d','i','s','c','o','s',':',' ');
      readln(M);
      writeln(' ');
      mover(M,'1','3','2');
      writeln(' ');
      write('D','e','s','e','a',' ','p','r','o','b','a','r',' ','c','o','n',' ','o','t','r','o',' ','n','u','m','e','r','o',' ','(','S','/','N',')',':',' ');
      read(rpta);
      writeln(' ');
      writeln(' ');
  end;

end.