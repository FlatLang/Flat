#include <precompiled.h>
#include "NovaGC.h"


nova_VTable_GC nova_VTable_GC_val =
{
	nova_Object_toString,
	nova_Object_equals,
};

GC* nova_GC_construct(GC* this, ExceptionData* exceptionData)
{
	CCLASS_NEW(GC, this,);
	
	this->vtable = &nova_VTable_GC_val;
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

void nova_static_GC_init(GC* this, ExceptionData* exceptionData)
{
	GC_INIT();
}

long_long nova_static_GC_getFreeBytes(GC* this, ExceptionData* exceptionData)
{
	return GC_get_free_bytes();
}

long_long nova_static_GC_getTotalBytes(GC* this, ExceptionData* exceptionData)
{
	return GC_get_total_bytes();
}

long_long nova_static_GC_getHeapSize(GC* this, ExceptionData* exceptionData)
{
	return GC_get_heap_size();
}

long_long nova_static_GC_getBytesSinceGC(GC* this, ExceptionData* exceptionData)
{
	return GC_get_bytes_since_gc();
}

void nova_static_GC_collect(GC* this, ExceptionData* exceptionData)
{
	GC_gcollect();
}

void nova_static_GC_enableIncremental(GC* this, ExceptionData* exceptionData)
{
	GC_enable_incremental();
}

void nova_static_GC_dump(GC* this, ExceptionData* exceptionData)
{
	GC_dump();
}
