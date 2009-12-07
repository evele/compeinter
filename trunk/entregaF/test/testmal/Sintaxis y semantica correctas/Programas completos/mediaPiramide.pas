Program piramide;

type
    Tdigito= 1..9;

Var
   d: Tdigito;
   ch :char;

    Procedure MediaPiramide (n: Tdigito);

      Procedure renglon (dig: Tdigito);
      begin
          if (dig =1)  then  write (dig)

          else
             begin
               Write (dig);
               renglon ((dig-1));
             end;
      end;

   begin

      if (n=1) then writeln (n)

      else
        begin
           MediaPiramide(n-1);
           renglon (n);
           writeln(' ');;
         end;
   end;

begin
	ch := 's';
	
	while (ch='s')or(ch='S') do
	begin
		write('I','n','g','r','e','s','e',' ','u','n',' ','n','u','m','e','r','o',':',' ');
		read(d);
		writeln(' ');
		MediaPiramide(d);
		writeln(' ');
		writeln(' ');
		write( 'D','e','s','e','a',' ','i','n','t','e','n','t','a','r',' ','c','o','n',' ','o','t','r','o',' ','n','u','m','e','r','o',' ','(','s','/','n',')');
		readln(ch);
	end;
end.


