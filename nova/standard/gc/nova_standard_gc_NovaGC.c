#include <precompiled.h>
#include <nova/standard/gc/nova_standard_gc_NovaGC.h>


nova_VTable_nova_standard_gc_NovaGC nova_VTable_nova_standard_gc_NovaGC_val =
{
	nova_standard_NovaObject_Nova0_toString,
	nova_standard_NovaObject_Nova0_equals,
};
void nova_standard_gc_NovaGCNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
	{
	}
}

nova_standard_gc_NovaGC* nova_standard_gc_NovaGC_Nova0_construct(nova_standard_gc_NovaGC* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_gc_NovaGC, this,);
	this->vtable = &nova_VTable_nova_standard_gc_NovaGC_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_gc_NovaGC_Novasuper(this, 0);
	
	{
		nova_standard_gc_NovaGC_Novathis(this, exceptionData);
	}
	
	return this;
}

void nova_del_GC(nova_standard_gc_NovaGC** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

void nova_standard_gc_NovaGC_static_Novainit(nova_standard_gc_NovaGC* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	GC_INIT();
}

long nova_standard_gc_NovaGC_static_NovagetFreeBytes(nova_standard_gc_NovaGC* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	return GC_get_free_bytes();
}

long nova_standard_gc_NovaGC_static_NovagetTotalBytes(nova_standard_gc_NovaGC* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	return GC_get_total_bytes();
}

long nova_standard_gc_NovaGC_static_NovagetHeapSize(nova_standard_gc_NovaGC* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	return GC_get_heap_size();
}

long nova_standard_gc_NovaGC_static_NovagetBytesSinceGC(nova_standard_gc_NovaGC* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	return GC_get_bytes_since_gc();
}

void nova_standard_gc_NovaGC_static_Novacollect(nova_standard_gc_NovaGC* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	GC_gcollect();
}

void nova_standard_gc_NovaGC_static_NovaenableIncremental(nova_standard_gc_NovaGC* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	GC_enable_incremental();
}

void nova_standard_gc_NovaGC_static_Novadump(nova_standard_gc_NovaGC* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	GC_dump();
}

void nova_standard_gc_NovaGC_Novathis(nova_standard_gc_NovaGC* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}

void nova_standard_gc_NovaGC_Novasuper(nova_standard_gc_NovaGC* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}
