#include "GC.h"
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

GC* nova_GC_GC(ExceptionData* exceptionData)
{
	CCLASS_NEW(GC, this,);
	
	{
	}
	
	return this;
}

void nova_del_GC(GC** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	free(*this);
}

void nova_GC_init(ExceptionData* exceptionData)
{
	GC_INIT();
	nova_GC_enableIncremental(exceptionData);
	nova_GC_setFreeSpaceDivisor(exceptionData, 1);
}

long_long nova_GC_getFreeBytes(ExceptionData* exceptionData)
{
	return GC_get_free_bytes();
}

long_long nova_GC_getTotalBytes(ExceptionData* exceptionData)
{
	return GC_get_total_bytes();
}

long_long nova_GC_getHeapSize(ExceptionData* exceptionData)
{
	return GC_get_heap_size();
}

long_long nova_GC_getBytesSinceGC(ExceptionData* exceptionData)
{
	return GC_get_bytes_since_gc();
}

void nova_GC_setFreeSpaceDivisor(ExceptionData* exceptionData, int nova_0_divisor)
{
	GC_free_space_divisor = nova_0_divisor;
}

void nova_GC_collect(ExceptionData* exceptionData)
{
	GC_gcollect();
}

void nova_GC_enableIncremental(ExceptionData* exceptionData)
{
	GC_enable_incremental();
}

void nova_GC_dump(ExceptionData* exceptionData)
{
	GC_dump();
}
