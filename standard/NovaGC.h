#pragma once
#ifndef FILE_GC_NOVA
#define FILE_GC_NOVA

typedef struct GC GC;

#include <Nova.h>
#include <ExceptionHandler.h>
#include <NovaExceptionData.h>
#include <NovaObject.h>
#include <NovaString.h>
#include <NovaSystem.h>
#include <NovaException.h>
#include <NovaMath.h>
#include <NovaConsole.h>
#include "NovaGC.h"
#include <NovaNumber.h>
#include <NovaByte.h>
#include <NovaShort.h>
#include <NovaInt.h>
#include <NovaLong.h>
#include <NovaFloat.h>
#include <NovaDouble.h>
#include <NovaChar.h>
#include <NovaDivideByZeroException.h>
#include <gc.h>

typedef struct nova_VTable_GC
{
	String* (*nova_virtual_0_toString)(Object*, ExceptionData*);
	char (*nova_virtual_0_equals)(Object*, ExceptionData*, Object*);
} nova_VTable_GC;

CCLASS_CLASS
(
	GC, 
	
	nova_VTable_GC* vtable;
)

GC* nova_0_GC_construct(GC* this, ExceptionData* exceptionData);
void nova_del_GC(GC** this, ExceptionData* exceptionData);
void nova_static_GC_init(GC* this, ExceptionData* exceptionData);
long_long nova_static_GC_getFreeBytes(GC* this, ExceptionData* exceptionData);
long_long nova_static_GC_getTotalBytes(GC* this, ExceptionData* exceptionData);
long_long nova_static_GC_getHeapSize(GC* this, ExceptionData* exceptionData);
long_long nova_static_GC_getBytesSinceGC(GC* this, ExceptionData* exceptionData);
void nova_static_GC_collect(GC* this, ExceptionData* exceptionData);
void nova_static_GC_enableIncremental(GC* this, ExceptionData* exceptionData);
void nova_static_GC_dump(GC* this, ExceptionData* exceptionData);
void nova_GC_this(GC* this, ExceptionData* exceptionData);
void nova_GC_super(GC* this, ExceptionData* exceptionData);

#endif