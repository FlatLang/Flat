#include <precompiled.h>
#include <stabilitytest/stabilitytest_Nova_ThreadImplementation.h>


stabilitytest_Extension_VTable_ThreadImplementation stabilitytest_Extension_VTable_ThreadImplementation_val =
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
	},
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_Nova_Object_0_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
	stabilitytest_Nova_ThreadImplementation_0_Nova_run,
};


CCLASS_PRIVATE
(
	NOVA_THREAD_HANDLE* nova_standard_thread_Nova_Thread_Nova_handle;
	
	int stabilitytest_Nova_ThreadImplementation_Nova_times;
	int stabilitytest_Nova_ThreadImplementation_Nova_millis;
	
)
void stabilitytest_Nova_ThreadImplementationNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

stabilitytest_Nova_ThreadImplementation* stabilitytest_Nova_ThreadImplementation_Nova_construct(stabilitytest_Nova_ThreadImplementation* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int l0_Nova_times, int l0_Nova_millis)
{
	CCLASS_NEW(stabilitytest_Nova_ThreadImplementation, this);
	this->vtable = &stabilitytest_Extension_VTable_ThreadImplementation_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_thread_Nova_Thread_Nova_super((nova_standard_thread_Nova_Thread*)this, exceptionData);
	nova_standard_Nova_Object_2_Nova_this((nova_standard_Nova_Object*)(this), exceptionData);
	nova_standard_thread_Nova_Thread_2_Nova_this((nova_standard_thread_Nova_Thread*)(this), exceptionData);
	stabilitytest_Nova_ThreadImplementation_2_Nova_super(this, exceptionData);
	
	{
		stabilitytest_Nova_ThreadImplementation_Nova_this(this, exceptionData, l0_Nova_times, l0_Nova_millis);
	}
	
	return this;
}

void stabilitytest_Nova_ThreadImplementation_Nova_destroy(stabilitytest_Nova_ThreadImplementation** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	
	NOVA_FREE((*this)->prv);
	
	NOVA_FREE(*this);
}

void stabilitytest_Nova_ThreadImplementation_Nova_this(stabilitytest_Nova_ThreadImplementation* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int l0_Nova_times, int l0_Nova_millis)
{
	this->prv->stabilitytest_Nova_ThreadImplementation_Nova_times = l0_Nova_times;
	this->prv->stabilitytest_Nova_ThreadImplementation_Nova_millis = l0_Nova_millis;
}

void stabilitytest_Nova_ThreadImplementation_0_Nova_run(stabilitytest_Nova_ThreadImplementation* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	int l1_Nova_i;
	
	l1_Nova_i = 0;
	for (; l1_Nova_i < this->prv->stabilitytest_Nova_ThreadImplementation_Nova_times; l1_Nova_i++)
	{
		nova_standard_thread_Nova_Thread_Nova_sleep(0, exceptionData, (long)(this->prv->stabilitytest_Nova_ThreadImplementation_Nova_millis));
	}
}

void stabilitytest_Nova_ThreadImplementation_2_Nova_super(stabilitytest_Nova_ThreadImplementation* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->prv->stabilitytest_Nova_ThreadImplementation_Nova_times = 0;
	this->prv->stabilitytest_Nova_ThreadImplementation_Nova_millis = 0;
}

