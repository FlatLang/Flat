#include <precompiled.h>
#include <nova/standard/gc/nova_standard_gc_Nova_GC.h>


nova_VTable_nova_standard_gc_Nova_GC nova_VTable_nova_standard_gc_Nova_GC_val =
{
	nova_standard_Nova_Object_1_Nova_getHashCodeLong,
	nova_standard_Nova_Object_0_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
};





void nova_standard_gc_Nova_GCNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_gc_Nova_GC* nova_standard_gc_Nova_GC_2_Nova_construct(nova_standard_gc_Nova_GC* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_gc_Nova_GC, this,);
	this->vtable = &nova_VTable_nova_standard_gc_Nova_GC_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_Nova_Object_2_Nova_this((nova_standard_Nova_Object*)(this), exceptionData);
	nova_standard_gc_Nova_GC_Nova_super(this, exceptionData);
	
	{
		nova_standard_gc_Nova_GC_2_Nova_this(this, exceptionData);
	}
	
	return this;
}

void nova_standard_gc_Nova_GC_Nova_destroy(nova_standard_gc_Nova_GC** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

void nova_standard_gc_Nova_GC_Nova_init(nova_standard_gc_Nova_GC* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	nova_gc_init();
}

void nova_standard_gc_Nova_GC_Nova_collect(nova_standard_gc_Nova_GC* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	nova_gc_collect();
}

void nova_standard_gc_Nova_GC_Nova_enableIncremental(nova_standard_gc_Nova_GC* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	nova_gc_enableIncremental();
}

void nova_standard_gc_Nova_GC_Nova_dump(nova_standard_gc_Nova_GC* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	nova_gc_dump();
}

void nova_standard_gc_Nova_GC_2_Nova_this(nova_standard_gc_Nova_GC* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

int nova_standard_gc_Nova_GC_Accessor_Nova_freeBytes(nova_standard_gc_Nova_GC* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return (int)nova_gc_getFreeBytes();
}


int nova_standard_gc_Nova_GC_Accessor_Nova_totalBytes(nova_standard_gc_Nova_GC* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return (int)nova_gc_getTotalBytes();
}


int nova_standard_gc_Nova_GC_Accessor_Nova_heapSize(nova_standard_gc_Nova_GC* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return (int)nova_gc_getHeapSize();
}


int nova_standard_gc_Nova_GC_Accessor_Nova_bytesSinceGC(nova_standard_gc_Nova_GC* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return (int)nova_gc_getBytesSinceGC();
}


void nova_standard_gc_Nova_GC_Nova_super(nova_standard_gc_Nova_GC* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

