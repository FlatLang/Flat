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
#include <NovaByte.h>
#include <NovaShort.h>
#include <NovaInt.h>
#include <NovaLong.h>
#include <NovaFloat.h>
#include <NovaDouble.h>
#include <NovaChar.h>
#include <NovaDivideByZeroException.h>
#include <stdio.h>
#include <stdlib.h>

typedef struct nova_VTable_Console
{
	String* (*nova_virtual_2_toString)(Object*, ExceptionData*);
	char (*nova_virtual_2_equals)(Object*, ExceptionData*, Object*);
} nova_VTable_Console;

CCLASS_CLASS
(
	Console, 
	
	nova_VTable_Console* vtable;
)

Console* nova_Console_Console(Console* this, ExceptionData* exceptionData);
void nova_del_Console(Console** this, ExceptionData* exceptionData);
void nova_static_1_Console_writeLine(Console* this, ExceptionData* exceptionData, String* nova_0_text);
void nova_static_2_Console_writeLine(Console* this, ExceptionData* exceptionData, Object* nova_0_obj);
void nova_static_3_Console_writeLine(Console* this, ExceptionData* exceptionData, double nova_0_num);
void nova_static_4_Console_writeLine(Console* this, ExceptionData* exceptionData, float nova_0_num);
void nova_static_5_Console_writeLine(Console* this, ExceptionData* exceptionData, long_long nova_0_num);
void nova_static_6_Console_writeLine(Console* this, ExceptionData* exceptionData, int nova_0_num);
void nova_static_7_Console_writeLine(Console* this, ExceptionData* exceptionData, short nova_0_num);
void nova_static_8_Console_writeLine(Console* this, ExceptionData* exceptionData, char nova_0_num);
void nova_static_9_Console_writeLine(Console* this, ExceptionData* exceptionData, char nova_0_c);
void nova_static_1_Console_write(Console* this, ExceptionData* exceptionData, String* nova_0_text);
void nova_static_2_Console_write(Console* this, ExceptionData* exceptionData, Object* nova_0_obj);
void nova_static_3_Console_write(Console* this, ExceptionData* exceptionData, double nova_0_num);
void nova_static_4_Console_write(Console* this, ExceptionData* exceptionData, float nova_0_num);
void nova_static_5_Console_write(Console* this, ExceptionData* exceptionData, long_long nova_0_num);
void nova_static_6_Console_write(Console* this, ExceptionData* exceptionData, int nova_0_num);
void nova_static_7_Console_write(Console* this, ExceptionData* exceptionData, short nova_0_num);
void nova_static_8_Console_write(Console* this, ExceptionData* exceptionData, char nova_0_num);
void nova_static_9_Console_write(Console* this, ExceptionData* exceptionData, char nova_0_c);
int nova_static_Console_readInt(Console* this, ExceptionData* exceptionData);
double nova_static_Console_readDouble(Console* this, ExceptionData* exceptionData);
char nova_static_Console_readChar(Console* this, ExceptionData* exceptionData);
String* nova_static_Console_readLine(Console* this, ExceptionData* exceptionData);
void nova_static_Console_waitForEnter(Console* this, ExceptionData* exceptionData);

#endif