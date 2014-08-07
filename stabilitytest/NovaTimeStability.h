#pragma once
#ifndef FILE_TimeStability_NOVA
#define FILE_TimeStability_NOVA

typedef struct TimeStability TimeStability;

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
#include <NovaTime.h>
#include <NovaThread.h>
#include <NovaStabilityTest.h>

typedef struct nova_VTable_TimeStability
{
	String* (*nova_virtual_toString)(Object*, ExceptionData*);
	char (*nova_virtual_equals)(Object*, ExceptionData*, Object*);
} nova_VTable_TimeStability;

CCLASS_CLASS
(
	TimeStability, 
	
	nova_VTable_TimeStability* vtable;
)

TimeStability* nova_TimeStability_construct(TimeStability* this, ExceptionData* exceptionData);
void nova_del_TimeStability(TimeStability** this, ExceptionData* exceptionData);
void nova_static_TimeStability_test(TimeStability* this, ExceptionData* exceptionData, StabilityTest* nova_0_program);

#endif