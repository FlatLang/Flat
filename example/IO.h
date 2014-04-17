#ifndef FILE_IO_FATHOM
#define FILE_IO_FATHOM

typedef struct IO IO;

#include <CClass.h>
#include <ExceptionHandler.h>
#include "ExceptionData.h"
#include <stdio.h>
#include <stdio.h>
#include <stdlib.h>
#include "String.h"
#include <Fathom.h>

CLASS
(
	IO, 
	
	FUNC(void, println, IO* __o__, ExceptionData* __FATHOM__exception_data, String* text);
	FUNC(void, print, IO* __o__, ExceptionData* __FATHOM__exception_data, String* text);
	FUNC(void, printi, IO* __o__, ExceptionData* __FATHOM__exception_data, int j);
	FUNC(void, printl, IO* __o__, ExceptionData* __FATHOM__exception_data, long_long j);
	FUNC(int, getInt, IO* __o__, ExceptionData* __FATHOM__exception_data);
	FUNC(String*, getLine, IO* __o__, ExceptionData* __FATHOM__exception_data);
	FUNC(void, waitForEnter, IO* __o__, ExceptionData* __FATHOM__exception_data);
)

IO* new_IO(ExceptionData* __FATHOM__exception_data);
void del_IO(IO** __o__, ExceptionData* __FATHOM__exception_data);
extern IO* __static__IO;

#endif