#ifndef FILE_IO_FATHOM
#define FILE_IO_FATHOM

typedef struct IO IO;

#include <CClass.h>
#include <ExceptionHandler.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include "DivideByZeroException.h"
#include <stdio.h>
#include <stdlib.h>
#include "String.h"
#include <Fathom.h>

CCLASS_CLASS
(
IO
)

IO* fathom_IO_IO(ExceptionData* exceptionData);
void fathom_del_IO(IO** this, ExceptionData* exceptionData);
void fathom_IO_println(ExceptionData* exceptionData, String* fathom_text_39);
void fathom_IO_print(ExceptionData* exceptionData, String* fathom_text_71);
void fathom_IO_printi(ExceptionData* exceptionData, int fathom_j_75);
void fathom_IO_printl(ExceptionData* exceptionData, long_long fathom_j_78);
int fathom_IO_getInt(ExceptionData* exceptionData);
String* fathom_IO_getLine(ExceptionData* exceptionData);
void fathom_IO_waitForEnter(ExceptionData* exceptionData);
extern IO* __static__IO;

#endif