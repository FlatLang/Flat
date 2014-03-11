#ifndef FILE_IO_FATHOM
#define FILE_IO_FATHOM

include "CClass.h"
include "stdio.h"

CLASS
(
	IO, 
	
	FUNC(void, println, IO* o, String text);
	FUNC(void, print, IO* o, String text);
)

#endif