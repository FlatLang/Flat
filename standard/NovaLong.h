#pragma once
#ifndef FILE_Long_NOVA
#define FILE_Long_NOVA

typedef struct Long Long;

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
#include "NovaLong.h"
#include <NovaFloat.h>
#include <NovaDouble.h>
#include <NovaChar.h>
#include <NovaDivideByZeroException.h>

typedef struct nova_VTable_Long
{
	String* (*nova_virtual_1_toString)(Long*, ExceptionData*);
	int (*nova_virtual_numDigits)(Number*, ExceptionData*, int);
	String* (*nova_virtual_toString)(Number*, ExceptionData*, int);
} nova_VTable_Long;

CCLASS_CLASS
(
	Long, 
	
	long_long nova_Long_value;
	nova_VTable_Long* vtable;
)

Long* nova_Long_construct(Long* this, ExceptionData* exceptionData, long_long nova_0_value);
void nova_del_Long(Long** this, ExceptionData* exceptionData);
int nova_static_Long_numDigits(Long* this, ExceptionData* exceptionData, long_long nova_0_number);
String* nova_static_0_Long_toString(Long* this, ExceptionData* exceptionData, long_long nova_0_value);
String* nova_1_Long_toString(Long* this, ExceptionData* exceptionData);

#endif