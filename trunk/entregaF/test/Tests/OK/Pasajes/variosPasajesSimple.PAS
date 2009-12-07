program variosPasajesArray;
var
  a: array [1..2] of integer;
  
  {1}{procedure p1(var x: integer);
  {2}procedure p1(x: integer);{}
  begin
    x := succ(x);
	write('P','1',':',x);	writeln(' ');
  end;

  {3}{procedure p2(var z: integer);
  {4}procedure p2(z: integer);{}
  begin
    z := z+5;
	{rite('P','2',':',z);writeln(' ');{}
	p1(z);
	write('P','2',':',z);writeln(' ');{}
  end;

  
begin
  a[1] := 3;
  p2(a[1]);
  write('M','A','I','N',':',a[1]);writeln(' ');
end.


{1-3}{		ok	}
{1-4}{		ok	}
{2-3}{		ok	}
{2-4}{		ok	}