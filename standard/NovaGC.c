#include <precompiled.h>

#include "NovaGC.h"

GC* nova_GC_GC(ExceptionData* exceptionData)
{
	GC* this = NULL;
	
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
	NOVA_FREE(*this);
}

void nova_GC_init(ExceptionData* exceptionData)
{
	GC_INIT();
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
