#pragma once
#ifndef FILE_Array_NOVA
#define FILE_Array_NOVA

typedef struct Array Array;

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
#include <NovaNumber.h>
#include <NovaByte.h>
#include <NovaShort.h>
#include <NovaInt.h>
#include <NovaLong.h>
#include <NovaFloat.h>
#include <NovaDouble.h>
#include <NovaChar.h>
#include <NovaDivideByZeroException.h>

typedef struct nova_VTable_Array
{
	String* (*nova_virtual_2_toString)(Object*, ExceptionData*);
	char (*nova_virtual_2_equals)(Object*, ExceptionData*, Object*);
} nova_VTable_Array;

CCLASS_CLASS
(
	Array, 
	
	int nova_Array_length;
	nova_VTable_Array* vtable;
)

Array* nova_Array_Array(Array* this, ExceptionData* exceptionData);
void nova_del_Array(Array** this, ExceptionData* exceptionData);

#endif