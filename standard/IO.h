#ifndef FILE_IO_NOVA
#define FILE_IO_NOVA

typedef struct IO IO;

#include <Fathom.h>
#include <gc.h>
#include <stdlib.h>
#include <CClass.h>
#include <ExceptionHandler.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include "Math.h"
#include "IO.h"
#include "Integer.h"
#include "Long.h"
#include "DivideByZeroException.h"
#include <stdio.h>
#include <stdlib.h>
#include "String.h"

IO* nova_IO_IO(ExceptionData* exceptionData);
void nova_del_IO(IO** this, ExceptionData* exceptionData);
void nova_IO_println(ExceptionData* exceptionData, String* nova_0_text);
void nova_IO_print(ExceptionData* exceptionData, String* nova_0_text);
void nova_IO_printi(ExceptionData* exceptionData, int nova_0_j);
void nova_IO_printl(ExceptionData* exceptionData, long_long nova_0_j);
int nova_IO_getInt(ExceptionData* exceptionData);
char nova_IO_getChar(ExceptionData* exceptionData);
String* nova_IO_getLine(ExceptionData* exceptionData);
void nova_IO_waitForEnter(ExceptionData* exceptionData);

#endif