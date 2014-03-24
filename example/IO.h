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

FUNC(void*, println, IO* __o__, jmp_buf __Fathom__jmp_buf, String* text);
FUNC(void*, print, IO* __o__, jmp_buf __Fathom__jmp_buf, String* text);
FUNC(void*, printi, IO* __o__, jmp_buf __Fathom__jmp_buf, int j);
FUNC(int, getInt, IO* __o__, jmp_buf __Fathom__jmp_buf);
FUNC(String*, getLine, IO* __o__, jmp_buf __Fathom__jmp_buf);
FUNC(void*, waitForEnter, IO* __o__, jmp_buf __Fathom__jmp_buf);
)

IO* new_IO(jmp_buf __Fathom__jmp_buf);
void del_IO(IO* __o__);
extern IO* __static__IO;

#endif