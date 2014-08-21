#pragma once
#ifndef FILE_ThreadImplementation_NOVA
#define FILE_ThreadImplementation_NOVA

typedef struct ThreadImplementation ThreadImplementation;

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

typedef struct nova_VTable_ThreadImplementation
{
	void (*nova_virtual_0_run)(ThreadImplementation*, ExceptionData*);
} nova_VTable_ThreadImplementation;

CCLASS_CLASS
(
	ThreadImplementation, 
	
	nova_VTable_ThreadImplementation* vtable;
	struct Private* prv;
)

ThreadImplementation* nova_ThreadImplementation_construct(ThreadImplementation* this, ExceptionData* exceptionData, int nova_0_times, int nova_0_millis);
void nova_del_ThreadImplementation(ThreadImplementation** this, ExceptionData* exceptionData);
void nova_ThreadImplementation_this(ThreadImplementation* this, ExceptionData* exceptionData, int nova_0_times, int nova_0_millis);
void nova_0_ThreadImplementation_run(ThreadImplementation* this, ExceptionData* exceptionData);
void nova_ThreadImplementation_super(ThreadImplementation* this, ExceptionData* exceptionData);

#endif