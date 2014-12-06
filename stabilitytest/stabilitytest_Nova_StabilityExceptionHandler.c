#include <precompiled.h>
#include <stabilitytest/stabilitytest_Nova_StabilityExceptionHandler.h>


nova_VTable_stabilitytest_Nova_StabilityExceptionHandler nova_VTable_stabilitytest_Nova_StabilityExceptionHandler_val =
{
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_Nova_Object_0_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
	stabilitytest_Nova_StabilityExceptionHandler_Nova_uncaughtException,
};
CCLASS_PRIVATE
(
	stabilitytest_Nova_StabilityTest* stabilitytest_Nova_StabilityExceptionHandler_Nova_program;
	
)
void stabilitytest_Nova_StabilityExceptionHandlerNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

stabilitytest_Nova_StabilityExceptionHandler* stabilitytest_Nova_StabilityExceptionHandler_Nova_construct(stabilitytest_Nova_StabilityExceptionHandler* this, nova_standard_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_StabilityTest* l0_Nova_program)
{
	CCLASS_NEW(stabilitytest_Nova_StabilityExceptionHandler, this);
	this->vtable = &nova_VTable_stabilitytest_Nova_StabilityExceptionHandler_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_thread_Nova_UncaughtExceptionHandler_Nova_super((nova_standard_thread_Nova_UncaughtExceptionHandler*)this, exceptionData);
	nova_standard_Nova_Object_Nova_this((nova_standard_Nova_Object*)(this), exceptionData);
	nova_standard_thread_Nova_UncaughtExceptionHandler_Nova_this((nova_standard_thread_Nova_UncaughtExceptionHandler*)(this), exceptionData);
	stabilitytest_Nova_StabilityExceptionHandler_Nova_super(this, exceptionData);
	
	{
		stabilitytest_Nova_StabilityExceptionHandler_Nova_this(this, exceptionData, l0_Nova_program);
	}
	
	return this;
}

void stabilitytest_Nova_StabilityExceptionHandler_Nova_destroy(stabilitytest_Nova_StabilityExceptionHandler** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	stabilitytest_Nova_StabilityTest_Nova_destroy(&(*this)->prv->stabilitytest_Nova_StabilityExceptionHandler_Nova_program, exceptionData);
	NOVA_FREE((*this)->prv);
	
	{
	}
	NOVA_FREE(*this);
}

void stabilitytest_Nova_StabilityExceptionHandler_Nova_this(stabilitytest_Nova_StabilityExceptionHandler* this, nova_standard_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_StabilityTest* l0_Nova_program)
{
	this->prv->stabilitytest_Nova_StabilityExceptionHandler_Nova_program = l0_Nova_program;
}

void stabilitytest_Nova_StabilityExceptionHandler_Nova_uncaughtException(stabilitytest_Nova_StabilityExceptionHandler* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_thread_Nova_Thread* l0_Nova_thread, nova_standard_exception_Nova_Exception* l0_Nova_exception)
{
	stabilitytest_Nova_StabilityTest_0_Nova_fail(this->prv->stabilitytest_Nova_StabilityExceptionHandler_Nova_program, exceptionData);
}

void stabilitytest_Nova_StabilityExceptionHandler_Nova_super(stabilitytest_Nova_StabilityExceptionHandler* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->prv->stabilitytest_Nova_StabilityExceptionHandler_Nova_program = (stabilitytest_Nova_StabilityTest*)nova_null;
}

