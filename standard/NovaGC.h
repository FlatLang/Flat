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
#include <NovaInteger.h>
#include <NovaLong.h>
#include <NovaDouble.h>
#include <NovaChar.h>
#include <NovaDivideByZeroException.h>
#include <gc.h>



GC* nova_GC_GC(ExceptionData* exceptionData);
void nova_del_GC(GC** this, ExceptionData* exceptionData);
void nova_static_GC_init(GC* this, ExceptionData* exceptionData);
long_long nova_static_GC_getFreeBytes(GC* this, ExceptionData* exceptionData);
long_long nova_static_GC_getTotalBytes(GC* this, ExceptionData* exceptionData);
long_long nova_static_GC_getHeapSize(GC* this, ExceptionData* exceptionData);
long_long nova_static_GC_getBytesSinceGC(GC* this, ExceptionData* exceptionData);
void nova_static_GC_collect(GC* this, ExceptionData* exceptionData);
void nova_static_GC_enableIncremental(GC* this, ExceptionData* exceptionData);
void nova_static_GC_dump(GC* this, ExceptionData* exceptionData);

#endif