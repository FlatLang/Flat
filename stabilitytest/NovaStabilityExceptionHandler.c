#include <precompiled.h>
#include "NovaStabilityExceptionHandler.h"


nova_VTable_StabilityExceptionHandler nova_VTable_StabilityExceptionHandler_val =
{
	nova_StabilityExceptionHandler_uncaughtException,
};
CCLASS_PRIVATE
(
	StabilityTest* nova_StabilityExceptionHandler_program;
	
)

StabilityExceptionHandler* nova_StabilityExceptionHandler_StabilityExceptionHandler(ExceptionData* exceptionData, StabilityTest* nova_0_program)
{
	CCLASS_NEW(StabilityExceptionHandler, this);
	
	this->prv->nova_StabilityExceptionHandler_program = (StabilityTest*)0;
	this->vtable = &nova_VTable_StabilityExceptionHandler_val;
	{
		this->prv->nova_StabilityExceptionHandler_program = nova_0_program;
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

void nova_StabilityExceptionHandler_uncaughtException(StabilityExceptionHandler* this, ExceptionData* exceptionData, Thread* nova_0_thread, Exception* nova_0_exception)
{
	nova_StabilityTest_fail((StabilityTest*)(this->prv->nova_StabilityExceptionHandler_program), exceptionData, nova_String_String(exceptionData, ""));
}
