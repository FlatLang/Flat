#include <precompiled.h>
#include <nova/standard/thread/nova_standard_thread_Nova_UncaughtExceptionHandler.h>

nova_standard_thread_Extension_VTable_UncaughtExceptionHandler nova_standard_thread_Extension_VTable_UncaughtExceptionHandler_val =
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
		(char(*)(nova_standard_operators_Nova_Equals*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*))nova_standard_Nova_Object_0_Nova_equals,
		0,
		0,
		0,
	},
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_Nova_Object_1_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
	nova_standard_thread_Nova_UncaughtExceptionHandler_0_Nova_uncaughtException,
};


void nova_standard_thread_Nova_UncaughtExceptionHandlerNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_thread_Nova_UncaughtExceptionHandler* nova_standard_thread_Nova_UncaughtExceptionHandler_0_Nova_construct(nova_standard_thread_Nova_UncaughtExceptionHandler* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_thread_Nova_UncaughtExceptionHandler, this,);
	this->vtable = &nova_standard_thread_Extension_VTable_UncaughtExceptionHandler_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_thread_Nova_UncaughtExceptionHandler_Nova_super(this, exceptionData);
	
	{
		nova_standard_thread_Nova_UncaughtExceptionHandler_0_Nova_this(this, exceptionData);
	}
	
	return this;
}

void nova_standard_thread_Nova_UncaughtExceptionHandler_Nova_destroy(nova_standard_thread_Nova_UncaughtExceptionHandler** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

void nova_standard_thread_Nova_UncaughtExceptionHandler_Nova_UncaughtExceptionHandler(nova_standard_thread_Nova_UncaughtExceptionHandler* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

void nova_standard_thread_Nova_UncaughtExceptionHandler_0_Nova_uncaughtException(nova_standard_thread_Nova_UncaughtExceptionHandler* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_thread_Nova_Thread* nova_standard_thread_Nova_UncaughtExceptionHandler_Nova_thread, nova_standard_exception_Nova_Exception* nova_standard_thread_Nova_UncaughtExceptionHandler_Nova_exception)
{
}

void nova_standard_thread_Nova_UncaughtExceptionHandler_0_Nova_this(nova_standard_thread_Nova_UncaughtExceptionHandler* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

void nova_standard_thread_Nova_UncaughtExceptionHandler_Nova_super(nova_standard_thread_Nova_UncaughtExceptionHandler* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

void nova_standard_thread_Nova_UncaughtExceptionHandler_virtual1_Nova_uncaughtException(nova_standard_thread_Nova_UncaughtExceptionHandler* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_thread_Nova_Thread* nova_standard_thread_Nova_UncaughtExceptionHandler_Nova_thread, nova_standard_exception_Nova_Exception* nova_standard_thread_Nova_UncaughtExceptionHandler_Nova_exception)
{
	this->vtable->nova_standard_thread_Nova_UncaughtExceptionHandler_virtual1_Nova_uncaughtException((nova_standard_thread_Nova_UncaughtExceptionHandler*)(this), exceptionData, nova_standard_thread_Nova_UncaughtExceptionHandler_Nova_thread, nova_standard_thread_Nova_UncaughtExceptionHandler_Nova_exception);
}

