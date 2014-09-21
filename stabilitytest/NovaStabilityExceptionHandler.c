#include <precompiled.h>
#include "NovaStabilityExceptionHandler.h"


nova_VTable_StabilityExceptionHandler nova_VTable_StabilityExceptionHandler_val =
{
	nova_0_Object_toString,
	nova_0_Object_equals,
	nova_0_StabilityExceptionHandler_uncaughtException,
};
CCLASS_PRIVATE
(
	StabilityTest* nova_StabilityExceptionHandler_program;
	
)

StabilityExceptionHandler* nova_StabilityExceptionHandler_construct(StabilityExceptionHandler* this, ExceptionData* exceptionData, StabilityTest* nova_0_program)
{
	CCLASS_NEW(StabilityExceptionHandler, this);
	this->vtable = &nova_VTable_StabilityExceptionHandler_val;
	nova_Object_super((Object*)this, 0);
	nova_UncaughtExceptionHandler_super((UncaughtExceptionHandler*)this, 0);
	nova_Object_this((Object*)(this), exceptionData);
	nova_UncaughtExceptionHandler_this((UncaughtExceptionHandler*)(this), exceptionData);
	nova_StabilityExceptionHandler_super(this, 0);
	
	{
		nova_StabilityExceptionHandler_this(this, exceptionData, nova_0_program);
	}
	
	return this;
}

void nova_del_StabilityExceptionHandler(StabilityExceptionHandler** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_del_StabilityTest(&(*this)->prv->nova_StabilityExceptionHandler_program, exceptionData);
	NOVA_FREE((*this)->prv);
	
	{
	}
	NOVA_FREE(*this);
}

void nova_StabilityExceptionHandler_this(StabilityExceptionHandler* this, ExceptionData* exceptionData, StabilityTest* nova_0_program)
{
	this->prv->nova_StabilityExceptionHandler_program = nova_0_program;
}

void nova_0_StabilityExceptionHandler_uncaughtException(StabilityExceptionHandler* this, ExceptionData* exceptionData, Thread* nova_0_thread, Exception* nova_0_exception)
{
	nova_0_StabilityTest_fail(this->prv->nova_StabilityExceptionHandler_program, exceptionData);
}

void nova_StabilityExceptionHandler_super(StabilityExceptionHandler* this, ExceptionData* exceptionData)
{
	this->prv->nova_StabilityExceptionHandler_program = (StabilityTest*)nova_null;
}
