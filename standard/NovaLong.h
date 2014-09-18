#pragma once
#ifndef FILE_Long_NOVA
#define FILE_Long_NOVA

typedef struct Long Long;

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
#include "NovaLong.h"
#include <NovaFloat.h>
#include <NovaDouble.h>
#include <NovaChar.h>
#include <NovaDivideByZeroException.h>

typedef struct nova_VTable_Long
{
	String* (*nova_virtual_3_toString)(Long*, ExceptionData*);
	char (*nova_virtual_0_equals)(Object*, ExceptionData*, Object*);
	int (*nova_virtual_0_numDigits)(Number*, ExceptionData*, int);
	String* (*nova_virtual_1_toString)(Number*, ExceptionData*, int);
} nova_VTable_Long;

CCLASS_CLASS
(
	Long, 
	
	nova_VTable_Long* vtable;
	long_long nova_Long_value;
)

Long* nova_Long_construct(Long* this, ExceptionData* exceptionData, long_long nova_0_value);
void nova_del_Long(Long** this, ExceptionData* exceptionData);
void nova_Long_this(Long* this, ExceptionData* exceptionData, long_long nova_0_value);
int nova_static_Long_numDigits(Long* this, ExceptionData* exceptionData, long_long nova_0_number);
String* nova_static_2_Long_toString(Long* this, ExceptionData* exceptionData, long_long nova_0_value);
String* nova_3_Long_toString(Long* this, ExceptionData* exceptionData);
void nova_Long_super(Long* this, ExceptionData* exceptionData);

#endif