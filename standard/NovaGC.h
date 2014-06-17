#pragma once
#ifndef FILE_GC_NOVA
#define FILE_GC_NOVA

typedef struct GC GC;

#include <Nova.h>
#include <ExceptionHandler.h>
#include "NovaExceptionData.h"
#include "NovaObject.h"
#include "NovaString.h"
#include "NovaMath.h"
#include "NovaIO.h"
#include "NovaInteger.h"
#include "NovaLong.h"
#include "NovaDouble.h"
#include "NovaChar.h"
#include "NovaDivideByZeroException.h"
#include <gc.h>

GC* nova_GC_GC(ExceptionData* exceptionData);
void nova_del_GC(GC** this, ExceptionData* exceptionData);
void nova_GC_init(ExceptionData* exceptionData);
long_long nova_GC_getFreeBytes(ExceptionData* exceptionData);
long_long nova_GC_getTotalBytes(ExceptionData* exceptionData);
long_long nova_GC_getHeapSize(ExceptionData* exceptionData);
long_long nova_GC_getBytesSinceGC(ExceptionData* exceptionData);
void nova_GC_collect(ExceptionData* exceptionData);
void nova_GC_enableIncremental(ExceptionData* exceptionData);
void nova_GC_dump(ExceptionData* exceptionData);

#endif