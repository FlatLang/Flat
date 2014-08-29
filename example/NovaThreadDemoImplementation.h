#pragma once
#ifndef FILE_ThreadDemoImplementation_NOVA
#define FILE_ThreadDemoImplementation_NOVA

typedef struct ThreadDemoImplementation ThreadDemoImplementation;

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
#include <NovaThread.h>

typedef struct nova_VTable_ThreadDemoImplementation
{
	void (*nova_virtual_0_run)(ThreadDemoImplementation*, ExceptionData*);
} nova_VTable_ThreadDemoImplementation;

CCLASS_CLASS
(
	ThreadDemoImplementation, 
	
	nova_VTable_ThreadDemoImplementation* vtable;
	struct Private* prv;
)

ThreadDemoImplementation* nova_ThreadDemoImplementation_construct(ThreadDemoImplementation* this, ExceptionData* exceptionData, long_long nova_0_millis, String* nova_0_word);
void nova_del_ThreadDemoImplementation(ThreadDemoImplementation** this, ExceptionData* exceptionData);
void nova_ThreadDemoImplementation_this(ThreadDemoImplementation* this, ExceptionData* exceptionData, long_long nova_0_millis, String* nova_0_word);
void nova_0_ThreadDemoImplementation_run(ThreadDemoImplementation* this, ExceptionData* exceptionData);
void nova_ThreadDemoImplementation_super(ThreadDemoImplementation* this, ExceptionData* exceptionData);

#endif