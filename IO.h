#ifndef FILE_IO_FATHOM
#define FILE_IO_FATHOM

#include "CClass.h"
#include "stdio.h"
#include "String.h"

CLASS
(
	IO, 
	
	FUNC(void, println, IO* __o__, String text);
	FUNC(void, print, IO* __o__, String text);
)

#endif