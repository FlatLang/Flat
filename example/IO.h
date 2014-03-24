#ifndef FILE_IO_FATHOM
#define FILE_IO_FATHOM

#include "CClass.h"
#include "ExceptionHandler.h"
#include <stdio.h>
#include <stdlib.h>
#include "String.h"
#include <Fathom.h>

CLASS
(
IO, 

FUNC(void*, println, IO* __o__, String* text);
FUNC(void*, print, IO* __o__, String* text);
FUNC(void*, printi, IO* __o__, int j);
FUNC(int, getInt, IO* __o__);
FUNC(String*, getLine, IO* __o__);
FUNC(void*, waitForEnter, IO* __o__);
)

IO* new_IO();
void del_IO(IO* __o__);
extern IO* __static__IO;

#endif