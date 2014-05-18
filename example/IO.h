#ifndef FILE_IO_FATHOM
#define FILE_IO_FATHOM

typedef struct IO IO;

#include <stdlib.h>
#include <CClass.h>
#include <ExceptionHandler.h>
#include <Fathom.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include "Math.h"
#include "DivideByZeroException.h"
#include <stdio.h>
#include <stdlib.h>
#include "String.h"

CCLASS_CLASS
(
	IO
)


IO* fathom_IO_IO(ExceptionData* exceptionData);
void fathom_del_IO(IO** this, ExceptionData* exceptionData);
void fathom_IO_println(ExceptionData* exceptionData, String* fathom_text_23);
void fathom_IO_print(ExceptionData* exceptionData, String* fathom_text_30);
void fathom_IO_printi(ExceptionData* exceptionData, int fathom_j_35);
void fathom_IO_printl(ExceptionData* exceptionData, long_long fathom_j_42);
int fathom_IO_getInt(ExceptionData* exceptionData);
char fathom_IO_getChar(ExceptionData* exceptionData);
String* fathom_IO_getLine(ExceptionData* exceptionData);
void fathom_IO_waitForEnter(ExceptionData* exceptionData);
#endif