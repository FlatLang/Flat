#pragma once
#ifndef FILE_Console_NOVA
#define FILE_Console_NOVA

typedef struct Console Console;

#include <Nova.h>
#include <ExceptionHandler.h>
#include <NovaExceptionData.h>
#include <NovaObject.h>
#include <NovaString.h>
#include <NovaSystem.h>
#include <NovaException.h>
#include <NovaMath.h>
#include "NovaConsole.h"
#include <NovaGC.h>
#include <NovaNumber.h>
#include <NovaInteger.h>
#include <NovaLong.h>
#include <NovaDouble.h>
#include <NovaChar.h>
#include <NovaDivideByZeroException.h>
#include <stdio.h>
#include <stdlib.h>



Console* nova_Console_Console(ExceptionData* exceptionData);
void nova_del_Console(Console** this, ExceptionData* exceptionData);
void nova_static_Console_writeLine(Console* this, ExceptionData* exceptionData, String* nova_0_text);
void nova_static_Console_write(Console* this, ExceptionData* exceptionData, String* nova_0_text);
void nova_static_Console_writei(Console* this, ExceptionData* exceptionData, int nova_0_j);
void nova_static_Console_writed(Console* this, ExceptionData* exceptionData, double nova_0_j);
void nova_static_Console_writel(Console* this, ExceptionData* exceptionData, long_long nova_0_j);
int nova_static_Console_readInt(Console* this, ExceptionData* exceptionData);
double nova_static_Console_readDouble(Console* this, ExceptionData* exceptionData);
char nova_static_Console_readChar(Console* this, ExceptionData* exceptionData);
String* nova_static_Console_readLine(Console* this, ExceptionData* exceptionData);
void nova_static_Console_waitForEnter(Console* this, ExceptionData* exceptionData);

#endif