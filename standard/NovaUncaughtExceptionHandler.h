#pragma once
#ifndef FILE_UncaughtExceptionHandler_NOVA
#define FILE_UncaughtExceptionHandler_NOVA

typedef struct UncaughtExceptionHandler UncaughtExceptionHandler;

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
#include <NovaLong.h>
#include <NovaFloat.h>
#include <NovaDouble.h>
#include <NovaChar.h>
#include <NovaDivideByZeroException.h>
#include <NovaThread.h>

typedef struct nova_VTable_UncaughtExceptionHandler
{
	String* (*nova_virtual_4_toString)(Object*, ExceptionData*);
	char (*nova_virtual_2_equals)(Object*, ExceptionData*, Object*);
} nova_VTable_UncaughtExceptionHandler;

CCLASS_CLASS
(
	UncaughtExceptionHandler, 
	
	nova_VTable_UncaughtExceptionHandler* vtable;
)

UncaughtExceptionHandler* nova_2_UncaughtExceptionHandler_UncaughtExceptionHandler(UncaughtExceptionHandler* this, ExceptionData* exceptionData);
void nova_del_UncaughtExceptionHandler(UncaughtExceptionHandler** this, ExceptionData* exceptionData);
void nova_1_UncaughtExceptionHandler_UncaughtExceptionHandler(UncaughtExceptionHandler* this, ExceptionData* exceptionData);
void nova_UncaughtExceptionHandler_uncaughtException(UncaughtExceptionHandler* this, ExceptionData* exceptionData, Thread* nova_0_thread, Exception* nova_0_exception);

#endif