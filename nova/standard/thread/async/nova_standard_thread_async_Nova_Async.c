#include <precompiled.h>
#include <nova/standard/thread/async/nova_standard_thread_async_Nova_Async.h>

nova_standard_thread_async_Extension_VTable_Async nova_standard_thread_async_Extension_VTable_Async_val =
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
		(char(*)(nova_standard_operators_Nova_Equals*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*))nova_standard_Nova_Object_0_Nova_equals,
		0,
		0,
		0,
	},
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_Nova_Object_0_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
};


void nova_standard_thread_async_Nova_AsyncNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_thread_async_Nova_Async* nova_standard_thread_async_Nova_Async_Nova_Async(nova_standard_thread_async_Nova_Async* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_thread_async_Nova_Async, this,);
	this->vtable = &nova_standard_thread_async_Extension_VTable_Async_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_thread_async_Nova_Async_Nova_super(this, exceptionData);
	
	{
		nova_standard_thread_async_Nova_Async_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void nova_standard_thread_async_Nova_Async_Nova_destroy(nova_standard_thread_async_Nova_Async** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

nova_standard_thread_async_Nova_AsyncResult* nova_standard_thread_async_Nova_Async_Nova_execute(nova_standard_thread_async_Nova_Async* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_thread_async_Nova_Async_closure3_Nova_func nova_standard_thread_async_Nova_Async_Nova_func, void* nova_standard_thread_async_Nova_Async_ref_Nova_func)
{
	nova_standard_thread_async_Nova_AsyncResult* l1_Nova_result = (nova_standard_thread_async_Nova_AsyncResult*)nova_null;
	nova_standard_thread_Nova_Thread* l1_Nova_thread = (nova_standard_thread_Nova_Thread*)nova_null;
	
	l1_Nova_result = nova_standard_thread_async_Nova_AsyncResult_Nova_AsyncResult(0, exceptionData);
	l1_Nova_thread = nova_standard_thread_Nova_Thread_Nova_Thread(0, exceptionData);
	nova_standard_thread_Nova_Thread_Nova_start(l1_Nova_thread, exceptionData);
	return l1_Nova_result;
}

void nova_standard_thread_async_Nova_Async_0_Nova_this(nova_standard_thread_async_Nova_Async* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

void nova_standard_thread_async_Nova_Async_Nova_super(nova_standard_thread_async_Nova_Async* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

