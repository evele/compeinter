> ConfigurarSVNenEclipse

Pequeña guía de cómo configurar SVN en Eclipse
Introducción

Una lista de pasos a seguir para tener SVN funcionando con Eclipse 3.2.2 (estos pasos deberían funcionar para cualquier versión desde 3.2 en adelante)
Instalación del plugin de SVN en Eclipse

  * En Eclipse: Help, Software Updates, Find and Install...
  * Search for new features to install, Next >
  * New Remote Site...
> > o Name: Subclipse
> > o URL: http://subclipse.tigris.org/update_1.4.x
  * Dejar seleccionado sólo Subclipse, Finish
  * Cuando pregunte, darle aceptar licencia, Install all y reiniciar Eclipse

Conectar a nuestro repositorio compeinter

  * File, New, Project...

  * SVN / Checkout Projects from SVN, Next >

  * Create a new repository location (hay que hacerlo la primera vez)
> > o Url: https://compeinter.googlecode.com/svn/trunk/

  * Aparece el mensaje:


> Error validating server certificate for https://compeinter.googlecode.com:443:

> - Unknown certificate issuer

> Fingerprint: 89:87:5e:ca:0b:03:d2:83:db:7b:3f:20:5d:d0:76:c7:76:6b:cb:b2
> Distinguished name: Certification Services Division, Thawte Consulting cc, Cape Town, Western Cape, ZA

> Darle Accept Permanently

  * Ahora aparece un cuadrito que pide usuario y password
> > o Usuario: el de Google (ejemplo: inux2012)
> > o Password: la que Google informa en la sección Source, en el link "When prompted, enter your generated googlecode.com password."

  * Listo. Ya podemos ver la lista de carpetas/proyectos del repositorio.


> Seleccionar uno y clickear Next >

  * Dejar las opciones por default y presionar Finish

  * IMPORTANTE: vuelve a aparecer un cuadro de "New project", porque Eclipse no sabe qué tipo de proyecto es (si es un proyecto Java, C++, o no asociado a un lenguaje). Darle Java/ Java Project, Next >, nombre: el nombre del proyecto que bajaron (el primero es analex), Finish. Va a aparecer un cartelito diciendo que los proyectos Java contienen otros recursos que van a ser reemplazados por los recursos del repositorio, hay que darle Aceptar.