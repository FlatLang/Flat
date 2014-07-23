#pragma once
#ifndef FILE_ThreadStability_NOVA
#define FILE_ThreadStability_NOVA

typedef struct ThreadStability ThreadStability;

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
#include <NovaInteger.h>
#include <NovaLong.h>
#include <NovaDouble.h>
#include <NovaChar.h>
#include <NovaDivideByZeroException.h>
#include <NovaThread.h>
#include <NovaStabilityTest.h>
#include <NovaStabilityExceptionHandler.h>
#include <NovaThreadImplementation.h>

typedef struct nova_VTable_ThreadStability
{
	String* (*nova_virtual_toString)(Object*, ExceptionData*);
	char (*nova_virtual_equals)(Object*, ExceptionData*, Object*);
} nova_VTable_ThreadStability;

CCLASS_CLASS
(
	ThreadStability, 
	
	nova_VTable_ThreadStability* vtable;
)

ThreadStability* nova_ThreadStability_ThreadStability(ExceptionData* exceptionData);
void nova_del_ThreadStability(ThreadStability** this, ExceptionData* exceptionData);
void nova_static_ThreadStability_test(ThreadStability* this, ExceptionData* exceptionData, StabilityTest* nova_0_program);

#endif