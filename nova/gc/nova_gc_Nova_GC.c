#include <precompiled.h>
#include <nova/gc/nova_gc_Nova_GC.h>



nova_gc_Extension_VTable_GC nova_gc_Extension_VTable_GC_val =
{
	{
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		(char(*)(nova_operators_Nova_Equals*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*))nova_Nova_Object_0_Nova_equals,
		0,
		0,
		0,
	},
	nova_Nova_Object_0_Nova_toString,
	nova_Nova_Object_0_Nova_equals,
	nova_Nova_Object_Accessor_Nova_hashCodeLong,
};







void nova_gc_Nova_GC_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_gc_Nova_GC* nova_gc_Nova_GC_Nova_construct(nova_gc_Nova_GC* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_gc_Nova_GC, this,);
	this->vtable = &nova_gc_Extension_VTable_GC_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_gc_Nova_GC_Nova_super(this, exceptionData);
	
	{
		nova_gc_Nova_GC_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void nova_gc_Nova_GC_Nova_destroy(nova_gc_Nova_GC** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

void nova_gc_Nova_GC_Nova_init(nova_gc_Nova_GC* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	nova_gc_init();
}

void nova_gc_Nova_GC_Nova_collect(nova_gc_Nova_GC* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	nova_gc_collect();
}

void nova_gc_Nova_GC_Nova_enableIncremental(nova_gc_Nova_GC* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	nova_gc_enableIncremental();
}

void nova_gc_Nova_GC_Nova_dump(nova_gc_Nova_GC* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	nova_gc_dump();
}

void nova_gc_Nova_GC_0_Nova_this(nova_gc_Nova_GC* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

int nova_gc_Nova_GC_Accessor_Nova_freeBytes(nova_gc_Nova_GC* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	return (int)nova_gc_getFreeBytes();
}


int nova_gc_Nova_GC_Accessor_Nova_totalBytes(nova_gc_Nova_GC* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	return (int)nova_gc_getTotalBytes();
}


int nova_gc_Nova_GC_Accessor_Nova_heapSize(nova_gc_Nova_GC* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	return (int)nova_gc_getHeapSize();
}


int nova_gc_Nova_GC_Accessor_Nova_bytesSinceGC(nova_gc_Nova_GC* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	return (int)nova_gc_getBytesSinceGC();
}


void nova_gc_Nova_GC_Nova_super(nova_gc_Nova_GC* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

