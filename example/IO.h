#ifndef FILE_IO_FATHOM
#define FILE_IO_FATHOM

typedef struct IO IO;

#include <CClass.h>
#include <ExceptionHandler.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include <stdio.h>
#include <stdlib.h>
#include "String.h"
#include <Fathom.h>

CLASS
(
	IO, 
	
	, struct Private* prv;
)

IO* fathom_IO_IO(ExceptionData* exceptionData);
void fathom_del_IO(IO** reference, ExceptionData* exceptionData);
void fathom_IO_println(IO* reference, ExceptionData* exceptionData, String* fathom_text_18);
void fathom_IO_print(IO* reference, ExceptionData* exceptionData, String* fathom_text_21);
void fathom_IO_printi(IO* reference, ExceptionData* exceptionData, int fathom_j_24);
void fathom_IO_printl(IO* reference, ExceptionData* exceptionData, long_long fathom_j_27);
int fathom_IO_getInt(IO* reference, ExceptionData* exceptionData);
String* fathom_IO_getLine(IO* reference, ExceptionData* exceptionData);
void fathom_IO_waitForEnter(IO* reference, ExceptionData* exceptionData);
extern IO* __static__IO;

#endif