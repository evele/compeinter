program pasajesSubArray;
const ctte=-2;
type 	tcomponente=array[ctte..0]of char;
		tmatriz=array[1..3]of tcomponente;
var
  a: tmatriz;
  c:char;
  {1}{procedure p1(var x: tcomponente);
  {2}procedure p1(x: tcomponente);{}
  begin
    x[-1] := succ(x[-1]);
	write('P','1',':',x[-1]);	writeln(' ');
  end;

  {3}{procedure p2(var z: tcomponente);
  {4}procedure p2(z: tcomponente);{}
  begin
    z[-1] := chr(70);
	write('P','2',':',ord(z[-1]));writeln(' ');{}
	p1(z);
	{write('P','2',':',ord(z[-1]));writeln(' ');{}
  end;

  
begin
  a[1][-1] := 'p';{write('M',':',a[1][-1]);writeln(' ');}
  p2(a[1]);
  write('M','A','I','N',':',a[1][-1]);writeln(' ');
  {c:='p';
  write(c);{}
end.


{1-3}{		ok	}
{1-4}{		ok	}
{2-3}{		ok	}
{2-4}{		ok	}


