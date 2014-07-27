#include <precompiled.h>
#include "NovaUncaughtExceptionHandler.h"


nova_VTable_UncaughtExceptionHandler nova_VTable_UncaughtExceptionHandler_val =
{
	nova_2_UncaughtExceptionHandler_uncaughtException,
	nova_2_Object_toString,
	nova_2_Object_equals,
};

UncaughtExceptionHandler* nova_UncaughtExceptionHandler_UncaughtExceptionHandler(ExceptionData* exceptionData)
{
	CCLASS_NEW(UncaughtExceptionHandler, this,);
	
	this->vtable = &nova_VTable_UncaughtExceptionHandler_val;
	{
	}
	
	return this;
}

void nova_del_UncaughtExceptionHandler(UncaughtExceptionHandler** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

void nova_2_UncaughtExceptionHandler_uncaughtException(UncaughtExceptionHandler* this, ExceptionData* exceptionData, Thread* nova_0_thread, Exception* nova_0_exception)
{
}
