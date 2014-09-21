#pragma once
#ifndef FILE_UncaughtExceptionHandler_NOVA
#define FILE_UncaughtExceptionHandler_NOVA

typedef struct UncaughtExceptionHandler UncaughtExceptionHandler;

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
#include <NovaThread.h>

typedef struct nova_VTable_UncaughtExceptionHandler
{
	String* (*nova_virtual_0_toString)(Object*, ExceptionData*);
	char (*nova_virtual_0_equals)(Object*, ExceptionData*, Object*);
	void (*nova_virtual_0_uncaughtException)(UncaughtExceptionHandler*, ExceptionData*, Thread*, Exception*);
} nova_VTable_UncaughtExceptionHandler;

CCLASS_CLASS
(
	UncaughtExceptionHandler, 
	
	nova_VTable_UncaughtExceptionHandler* vtable;
)

UncaughtExceptionHandler* nova_0_UncaughtExceptionHandler_construct(UncaughtExceptionHandler* this, ExceptionData* exceptionData);
void nova_del_UncaughtExceptionHandler(UncaughtExceptionHandler** this, ExceptionData* exceptionData);
void nova_UncaughtExceptionHandler_UncaughtExceptionHandler(UncaughtExceptionHandler* this, ExceptionData* exceptionData);
void nova_0_UncaughtExceptionHandler_uncaughtException(UncaughtExceptionHandler* this, ExceptionData* exceptionData, Thread* nova_0_thread, Exception* nova_0_exception);
void nova_UncaughtExceptionHandler_this(UncaughtExceptionHandler* this, ExceptionData* exceptionData);
void nova_UncaughtExceptionHandler_super(UncaughtExceptionHandler* this, ExceptionData* exceptionData);

#endif