#pragma once
#ifndef FILE_StabilityExceptionHandler_NOVA
#define FILE_StabilityExceptionHandler_NOVA

typedef struct StabilityExceptionHandler StabilityExceptionHandler;

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
#include <NovaUncaughtExceptionHandler.h>
#include <NovaThread.h>
#include <NovaStabilityTest.h>

typedef struct nova_VTable_StabilityExceptionHandler
{
	String* (*nova_virtual_0_toString)(Object*, ExceptionData*);
	char (*nova_virtual_0_equals)(Object*, ExceptionData*, Object*);
	void (*nova_virtual_0_uncaughtException)(StabilityExceptionHandler*, ExceptionData*, Thread*, Exception*);
} nova_VTable_StabilityExceptionHandler;

CCLASS_CLASS
(
	StabilityExceptionHandler, 
	
	nova_VTable_StabilityExceptionHandler* vtable;
	struct Private* prv;
)

StabilityExceptionHandler* nova_StabilityExceptionHandler_construct(StabilityExceptionHandler* this, ExceptionData* exceptionData, StabilityTest* nova_0_program);
void nova_del_StabilityExceptionHandler(StabilityExceptionHandler** this, ExceptionData* exceptionData);
void nova_StabilityExceptionHandler_this(StabilityExceptionHandler* this, ExceptionData* exceptionData, StabilityTest* nova_0_program);
void nova_0_StabilityExceptionHandler_uncaughtException(StabilityExceptionHandler* this, ExceptionData* exceptionData, Thread* nova_0_thread, Exception* nova_0_exception);
void nova_StabilityExceptionHandler_super(StabilityExceptionHandler* this, ExceptionData* exceptionData);

#endif