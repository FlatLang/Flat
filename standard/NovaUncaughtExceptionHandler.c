#include <precompiled.h>
#include "NovaUncaughtExceptionHandler.h"


nova_VTable_UncaughtExceptionHandler nova_VTable_UncaughtExceptionHandler_val =
{
	nova_0_Object_toString,
	nova_0_Object_equals,
	nova_0_UncaughtExceptionHandler_uncaughtException,
};

UncaughtExceptionHandler* nova_0_UncaughtExceptionHandler_construct(UncaughtExceptionHandler* this, ExceptionData* exceptionData)
{
	CCLASS_NEW(UncaughtExceptionHandler, this,);
	this->vtable = &nova_VTable_UncaughtExceptionHandler_val;
	nova_Object_super((Object*)this, 0);
	nova_Object_this((Object*)(this), exceptionData);
	nova_UncaughtExceptionHandler_super(this, 0);
	
	{
		nova_UncaughtExceptionHandler_this(this, exceptionData);
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

void nova_UncaughtExceptionHandler_UncaughtExceptionHandler(UncaughtExceptionHandler* this, ExceptionData* exceptionData)
{
}

void nova_0_UncaughtExceptionHandler_uncaughtException(UncaughtExceptionHandler* this, ExceptionData* exceptionData, Thread* nova_0_thread, Exception* nova_0_exception)
{
}

void nova_UncaughtExceptionHandler_this(UncaughtExceptionHandler* this, ExceptionData* exceptionData)
{
}

void nova_UncaughtExceptionHandler_super(UncaughtExceptionHandler* this, ExceptionData* exceptionData)
{
}
