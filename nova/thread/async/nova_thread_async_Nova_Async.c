#include <precompiled.h>
#include <nova/thread/async/nova_thread_async_Nova_Async.h>



nova_thread_async_Extension_VTable_Async nova_thread_async_Extension_VTable_Async_val =
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


void nova_thread_async_Nova_Async_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_thread_async_Nova_Async* nova_thread_async_Nova_Async_Nova_construct(nova_thread_async_Nova_Async* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_thread_async_Nova_Async, this,);
	this->vtable = &nova_thread_async_Extension_VTable_Async_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_thread_async_Nova_Async_Nova_super(this, exceptionData);
	
	{
		nova_thread_async_Nova_Async_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void nova_thread_async_Nova_Async_Nova_destroy(nova_thread_async_Nova_Async** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

nova_thread_async_Nova_AsyncResult* nova_thread_async_Nova_Async_Nova_execute(nova_thread_async_Nova_Async* this, nova_exception_Nova_ExceptionData* exceptionData, nova_thread_async_Nova_Async_closure3_Nova_func nova_thread_async_Nova_Async_Nova_func, void* nova_thread_async_Nova_Async_ref_Nova_func, void* func_context)
{
	nova_thread_async_Nova_AsyncResult* l1_Nova_result = (nova_thread_async_Nova_AsyncResult*)nova_null;
	
	l1_Nova_result = nova_thread_async_Nova_AsyncResult_Nova_construct(0, exceptionData);
	nova_thread_async_Nova_Async_Nova_func(nova_thread_async_Nova_Async_ref_Nova_func, exceptionData, func_context);
	return l1_Nova_result;
}

void nova_thread_async_Nova_Async_0_Nova_this(nova_thread_async_Nova_Async* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

void nova_thread_async_Nova_Async_Nova_super(nova_thread_async_Nova_Async* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

