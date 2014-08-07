#pragma once
#ifndef FILE_Number_NOVA
#define FILE_Number_NOVA

typedef struct Number Number;

#include <Nova.h>
#include <ExceptionHandler.h>
#include <NovaExceptionData.h>
#include <NovaObject.h>
#include <NovaString.h>
#include <NovaSystem.h>
#include <NovaException.h>
#include <NovaMath.h>
#include <NovaConsole.h>
#include <NovaGC.h>
#include "NovaNumber.h"
#include <NovaByte.h>
#include <NovaShort.h>
#include <NovaInt.h>
#include <NovaLong.h>
#include <NovaFloat.h>
#include <NovaDouble.h>
#include <NovaChar.h>
#include <NovaDivideByZeroException.h>

typedef struct nova_VTable_Number
{
	int (*nova_virtual_numDigits)(Number*, ExceptionData*, int);
	String* (*nova_virtual_toString)(Number*, ExceptionData*, int);
	String* (*nova_virtual_toString)(Object*, ExceptionData*);
	char (*nova_virtual_equals)(Object*, ExceptionData*, Object*);
} nova_VTable_Number;

CCLASS_CLASS
(
	Number, 
	
	nova_VTable_Number* vtable;
)

Number* nova_Number_construct(Number* this, ExceptionData* exceptionData);
void nova_del_Number(Number** this, ExceptionData* exceptionData);
int nova_static_Number_numDigits(Number* this, ExceptionData* exceptionData, int nova_0_number);
String* nova_static_Number_toString(Number* this, ExceptionData* exceptionData, int nova_0_value);

#endif