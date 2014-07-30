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
#include <NovaShort.h>
#include <NovaInteger.h>
#include "NovaLong.h"
#include <NovaFloat.h>
#include <NovaDouble.h>
#include <NovaChar.h>
#include <NovaDivideByZeroException.h>

typedef struct nova_VTable_Long
{
	String* (*nova_virtual_2_toString)(Long*, ExceptionData*);
	int (*nova_virtual_2_numDigits)(Number*, ExceptionData*, int);
	String* (*nova_virtual_3_toString)(Number*, ExceptionData*, int);
} nova_VTable_Long;

CCLASS_CLASS
(
	Long, 
	
	long_long nova_Long_value;
	nova_VTable_Long* vtable;
)

Long* nova_Long_Long(Long* this, ExceptionData* exceptionData, long_long nova_0_value);
void nova_del_Long(Long** this, ExceptionData* exceptionData);
int nova_static_1_Long_numDigits(Long* this, ExceptionData* exceptionData, long_long nova_0_number);
String* nova_static_1_Long_toString(Long* this, ExceptionData* exceptionData, long_long nova_0_value);
String* nova_2_Long_toString(Long* this, ExceptionData* exceptionData);

#endif