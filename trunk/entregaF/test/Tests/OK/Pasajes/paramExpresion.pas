program pasajeExpresion;

procedure p(e:integer);
begin
 if(e<1)
 then begin
		write(e);		
	  end	
 else p(e-1);
 write(e);
end;

begin
 p(3+1);
end.