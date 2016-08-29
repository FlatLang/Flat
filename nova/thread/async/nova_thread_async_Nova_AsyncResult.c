#include <precompiled.h>
#include <nova/thread/async/nova_thread_async_Nova_AsyncResult.h>



nova_thread_async_Extension_VTable_AsyncResult nova_thread_async_Extension_VTable_AsyncResult_val =
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


void nova_thread_async_Nova_AsyncResult_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_thread_async_Nova_AsyncResult* nova_thread_async_Nova_AsyncResult_Nova_construct(nova_thread_async_Nova_AsyncResult* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_thread_async_Nova_AsyncResult, this,);
	this->vtable = &nova_thread_async_Extension_VTable_AsyncResult_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_thread_async_Nova_AsyncResult_Nova_super(this, exceptionData);
	
	{
		nova_thread_async_Nova_AsyncResult_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void nova_thread_async_Nova_AsyncResult_Nova_destroy(nova_thread_async_Nova_AsyncResult** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

void nova_thread_async_Nova_AsyncResult_0_Nova_this(nova_thread_async_Nova_AsyncResult* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

void nova_thread_async_Nova_AsyncResult_Nova_super(nova_thread_async_Nova_AsyncResult* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

