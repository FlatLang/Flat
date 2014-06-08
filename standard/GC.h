#ifndef FILE_GC_NOVA
#define FILE_GC_NOVA

typedef struct GC GC;

#include <Fathom.h>
#include <gc.h>
#include <stdlib.h>
#include <CClass.h>
#include <ExceptionHandler.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include "Math.h"
#include "IO.h"
#include "Integer.h"
#include "Long.h"
#include "DivideByZeroException.h"
#include <gc.h>

GC* nova_GC_GC(ExceptionData* exceptionData);
void nova_del_GC(GC** this, ExceptionData* exceptionData);
void nova_GC_init(ExceptionData* exceptionData);
long_long nova_GC_getFreeBytes(ExceptionData* exceptionData);
long_long nova_GC_getTotalBytes(ExceptionData* exceptionData);
long_long nova_GC_getHeapSize(ExceptionData* exceptionData);
long_long nova_GC_getBytesSinceGC(ExceptionData* exceptionData);
void nova_GC_setFreeSpaceDivisor(ExceptionData* exceptionData, int nova_0_divisor);
void nova_GC_collect(ExceptionData* exceptionData);
void nova_GC_enableIncremental(ExceptionData* exceptionData);
void nova_GC_dump(ExceptionData* exceptionData);

#endif