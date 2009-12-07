PROGRAM Ambientes;
VAR A1:INTEGER; A2:BOOLEAN;

    PROCEDURE Bloque3(A1:INTEGER; VAR B2:BOOLEAN);
    VAR C1,D2:INTEGER;
    BEGIN {Bloque3}
         C1:=10;
         D2:=15;
         A2:=FALSE;
		 if(A2)
			THEN begin
					WRITE(C1,D2,A1);
				end
			ELSE begin
					WRITE(C1,D2,444);
				end;
    END;

	PROCEDURE Bloque1;
	VAR A1,B2:INTEGER;

		   PROCEDURE Bloque2;
		   VAR C1,B2:INTEGER;
		   BEGIN {Bloque2}
				WRITE(A1);
		   END;

    BEGIN {Bloque1}
        A1:=20;
        Bloque2;
        WRITE(A1);
    END;

BEGIN {Ambientes}
     A1:=200; A2:=TRUE;
     Bloque1;
     Bloque3(A1,A2);
END.
