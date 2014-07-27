#pragma once
#ifndef FILE_Thread_NOVA
#define FILE_Thread_NOVA

typedef struct Thread Thread;

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
#include <NativeThread.h>

typedef struct nova_VTable_Thread
{
	void (*nova_virtual_2_run)(Thread*, ExceptionData*);
	String* (*nova_virtual_2_toString)(Object*, ExceptionData*);
	char (*nova_virtual_2_equals)(Object*, ExceptionData*, Object*);
} nova_VTable_Thread;

CCLASS_CLASS
(
	Thread, 
	
	nova_VTable_Thread* vtable;
	struct Private* prv;
)

Thread* nova_Thread_Thread(ExceptionData* exceptionData);
void nova_del_Thread(Thread** this, ExceptionData* exceptionData);
void nova_Thread_start(Thread* this, ExceptionData* exceptionData);
void nova_Thread_join(Thread* this, ExceptionData* exceptionData);
void nova_static_Thread_sleep(Thread* this, ExceptionData* exceptionData, long_long nova_0_millis);
void nova_2_Thread_run(Thread* this, ExceptionData* exceptionData);

#endif