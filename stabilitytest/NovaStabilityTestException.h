#pragma once
#ifndef FILE_StabilityTestException_NOVA
#define FILE_StabilityTestException_NOVA

typedef struct StabilityTestException StabilityTestException;

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
#include <NovaChar.h>
#include <NovaDivideByZeroException.h>

typedef struct nova_VTable_StabilityTestException
{
	String* (*nova_virtual_0_toString)(Object*, ExceptionData*);
	char (*nova_virtual_0_equals)(Object*, ExceptionData*, Object*);
} nova_VTable_StabilityTestException;

CCLASS_CLASS
(
	StabilityTestException, 
	
	nova_VTable_StabilityTestException* vtable;
)

StabilityTestException* nova_0_StabilityTestException_construct(StabilityTestException* this, ExceptionData* exceptionData);
void nova_del_StabilityTestException(StabilityTestException** this, ExceptionData* exceptionData);
void nova_StabilityTestException_this(StabilityTestException* this, ExceptionData* exceptionData);
void nova_StabilityTestException_super(StabilityTestException* this, ExceptionData* exceptionData);

#endif