program subrango;

const 
   min = 0;
   max = 10;
   li = 40;
   ls = 20;

type 
    s1 = min..max;
    s2 = 1..12;
    s3 = -20..100;	 
    s4 = -20..max;
    s5 = +li..80;
    s6 = -li..min;
    s7 = -li..-ls;

var 
   v1 : s1;	
   v2 : s2;
   v3 : s3;	
   v4 : s4;
   v5 : s6;	
   v8 : s7;

begin
   v1 := 10;	
   v2 := 2;
   v3 := 30;	
   v4 := -10;
   v5 := 0;	
   v8 := -1;
end.