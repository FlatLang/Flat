#pragma once
#ifndef FILE_IO_NOVA
#define FILE_IO_NOVA

typedef struct IO IO;

#include <Nova.h>
#include <ExceptionHandler.h>
#include "NovaExceptionData.h"
#include "NovaObject.h"
#include "NovaString.h"
#include "NovaMath.h"
#include "NovaInteger.h"
#include "NovaLong.h"
#include "NovaDouble.h"
#include "NovaChar.h"
#include "NovaDivideByZeroException.h"
#include <stdio.h>
#include <stdlib.h>
#include "NovaString.h"

IO* nova_IO_IO(ExceptionData* exceptionData);
void nova_del_IO(IO** this, ExceptionData* exceptionData);
void nova_IO_println(ExceptionData* exceptionData, String* nova_0_text);
void nova_IO_print(ExceptionData* exceptionData, String* nova_0_text);
void nova_IO_printi(ExceptionData* exceptionData, int nova_0_j);
void nova_IO_printd(ExceptionData* exceptionData, double nova_0_j);
void nova_IO_printl(ExceptionData* exceptionData, long_long nova_0_j);
int nova_IO_getInt(ExceptionData* exceptionData);
double nova_IO_getDouble(ExceptionData* exceptionData);
char nova_IO_getChar(ExceptionData* exceptionData);
String* nova_IO_getLine(ExceptionData* exceptionData);
void nova_IO_waitForEnter(ExceptionData* exceptionData);

#endif