#pragma once
#ifndef FILE_Number_NOVA
#define FILE_Number_NOVA

typedef struct Number Number;

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
	String* (*nova_virtual_0_toString)(Object*, ExceptionData*);
	char (*nova_virtual_0_equals)(Object*, ExceptionData*, Object*);
	int (*nova_virtual_0_numDigits)(Number*, ExceptionData*, int);
	String* (*nova_virtual_1_toString)(Number*, ExceptionData*, int);
} nova_VTable_Number;

CCLASS_CLASS
(
	Number, 
	
	nova_VTable_Number* vtable;
)

Number* nova_0_Number_construct(Number* this, ExceptionData* exceptionData);
void nova_del_Number(Number** this, ExceptionData* exceptionData);
int nova_static_0_Number_numDigits(Number* this, ExceptionData* exceptionData, int nova_0_number);
String* nova_static_1_Number_toString(Number* this, ExceptionData* exceptionData, int nova_0_value);
void nova_Number_this(Number* this, ExceptionData* exceptionData);
void nova_Number_super(Number* this, ExceptionData* exceptionData);

#endif