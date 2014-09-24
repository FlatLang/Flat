#pragma once
#ifndef FILE_Char_NOVA
#define FILE_Char_NOVA

typedef struct Char Char;

#include <Nova.h>
#include <ExceptionHandler.h>
#include <NovaExceptionData.h>
#include <NovaNull.h>
#include <NovaObject.h>
#include <NovaString.h>
#include <NovaSystem.h>
#include <NovaException.h>
#include <NovaMath.h>
#include <NovaConsole.h>
#include <NovaGC.h>
#include <NovaNumber.h>
#include <NovaByte.h>
#include <NovaShort.h>
#include <NovaInt.h>
#include <NovaLong.h>
#include <NovaFloat.h>
#include <NovaDouble.h>
#include "NovaChar.h"
#include <NovaDivideByZeroException.h>

typedef struct nova_VTable_Char
{
	String* (*nova_virtual_0_toString)(Char*, ExceptionData*);
	char (*nova_virtual_0_equals)(Object*, ExceptionData*, Object*);
	int (*nova_virtual_0_numDigits)(Number*, ExceptionData*, int);
	String* (*nova_virtual_1_toString)(Char*, ExceptionData*, char);
} nova_VTable_Char;

CCLASS_CLASS
(
	Char, 
	
	nova_VTable_Char* vtable;
	char nova_Char_value;
)

Char* nova_Char_construct(Char* this, ExceptionData* exceptionData, char nova_0_value);
void nova_del_Char(Char** this, ExceptionData* exceptionData);
void nova_Char_this(Char* this, ExceptionData* exceptionData, char nova_0_value);
String* nova_1_Char_toString(Char* this, ExceptionData* exceptionData, char nova_0_c);
String* nova_0_Char_toString(Char* this, ExceptionData* exceptionData);
void nova_Char_super(Char* this, ExceptionData* exceptionData);

#endif