#include <precompiled.h>
#include <nova/thread/nova_thread_Nova_UncaughtExceptionHandler.h>



nova_thread_Extension_VTable_UncaughtExceptionHandler nova_thread_Extension_VTable_UncaughtExceptionHandler_val =
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
	nova_thread_Nova_UncaughtExceptionHandler_0_Nova_uncaughtException,
};


void nova_thread_Nova_UncaughtExceptionHandler_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_thread_Nova_UncaughtExceptionHandler* nova_thread_Nova_UncaughtExceptionHandler_Nova_construct(nova_thread_Nova_UncaughtExceptionHandler* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_thread_Nova_UncaughtExceptionHandler, this,);
	this->vtable = &nova_thread_Extension_VTable_UncaughtExceptionHandler_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_thread_Nova_UncaughtExceptionHandler_Nova_super(this, exceptionData);
	
	{
		nova_thread_Nova_UncaughtExceptionHandler_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void nova_thread_Nova_UncaughtExceptionHandler_Nova_destroy(nova_thread_Nova_UncaughtExceptionHandler** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

void nova_thread_Nova_UncaughtExceptionHandler_0_Nova_this(nova_thread_Nova_UncaughtExceptionHandler* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

void nova_thread_Nova_UncaughtExceptionHandler_0_Nova_uncaughtException(nova_thread_Nova_UncaughtExceptionHandler* this, nova_exception_Nova_ExceptionData* exceptionData, nova_thread_Nova_Thread* nova_thread_Nova_UncaughtExceptionHandler_Nova_thread, nova_exception_Nova_Exception* nova_thread_Nova_UncaughtExceptionHandler_Nova_exception)
{
}

void nova_thread_Nova_UncaughtExceptionHandler_Nova_super(nova_thread_Nova_UncaughtExceptionHandler* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

void nova_thread_Nova_UncaughtExceptionHandler_virtual1_Nova_uncaughtException(nova_thread_Nova_UncaughtExceptionHandler* this, nova_exception_Nova_ExceptionData* exceptionData, nova_thread_Nova_Thread* nova_thread_Nova_UncaughtExceptionHandler_Nova_thread, nova_exception_Nova_Exception* nova_thread_Nova_UncaughtExceptionHandler_Nova_exception)
{
	this->vtable->nova_thread_Nova_UncaughtExceptionHandler_virtual1_Nova_uncaughtException((nova_thread_Nova_UncaughtExceptionHandler*)(this), exceptionData, nova_thread_Nova_UncaughtExceptionHandler_Nova_thread, nova_thread_Nova_UncaughtExceptionHandler_Nova_exception);
}

