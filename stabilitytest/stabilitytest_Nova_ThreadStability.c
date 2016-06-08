#include <precompiled.h>
#include <stabilitytest/stabilitytest_Nova_ThreadStability.h>

stabilitytest_Extension_VTable_ThreadStability stabilitytest_Extension_VTable_ThreadStability_val =
{
	{
		0,
		(char(*)(nova_standard_operators_Nova_Equals*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*))nova_standard_Nova_Object_0_Nova_equals,
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
	},
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_Nova_Object_0_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
	stabilitytest_Nova_ThreadStability_0_Nova_test,
};



void stabilitytest_Nova_ThreadStability_Nova_createThreads(stabilitytest_Nova_ThreadStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_ThreadImplementation** stabilitytest_Nova_ThreadStability_Nova_threads, int stabilitytest_Nova_ThreadStability_Nova_amount);
void stabilitytest_Nova_ThreadStability_Nova_checkMemoryAccess(stabilitytest_Nova_ThreadStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData);
void stabilitytest_Nova_ThreadStability_Nova_joinThreads(stabilitytest_Nova_ThreadStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_ThreadImplementation** stabilitytest_Nova_ThreadStability_Nova_threads, int stabilitytest_Nova_ThreadStability_Nova_amount);
void stabilitytest_Nova_ThreadStabilityNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

stabilitytest_Nova_ThreadStability* stabilitytest_Nova_ThreadStability_0_Nova_construct(stabilitytest_Nova_ThreadStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_StabilityTest* stabilitytest_Nova_ThreadStability_Nova_program)
{
	CCLASS_NEW(stabilitytest_Nova_ThreadStability, this,);
	this->vtable = &stabilitytest_Extension_VTable_ThreadStability_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	stabilitytest_Nova_StabilityTestCase_Nova_super((stabilitytest_Nova_StabilityTestCase*)this, exceptionData);
	stabilitytest_Nova_ThreadStability_2_Nova_super(this, exceptionData);
	
	{
		stabilitytest_Nova_ThreadStability_0_Nova_this(this, exceptionData, stabilitytest_Nova_ThreadStability_Nova_program);
	}
	
	return this;
}

void stabilitytest_Nova_ThreadStability_Nova_destroy(stabilitytest_Nova_ThreadStability** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

void stabilitytest_Nova_ThreadStability_0_Nova_test(stabilitytest_Nova_ThreadStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	stabilitytest_Nova_ThreadImplementation** l1_Nova_threads;
	
	nova_standard_io_Nova_Console_0_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Checking Thread.nova with 20 Threads... "));
	l1_Nova_threads = (stabilitytest_Nova_ThreadImplementation**)NOVA_MALLOC(sizeof(stabilitytest_Nova_ThreadImplementation) * 20);
	stabilitytest_Nova_ThreadStability_Nova_createThreads(this, exceptionData, l1_Nova_threads, 20);
	stabilitytest_Nova_ThreadStability_Nova_checkMemoryAccess(this, exceptionData);
	stabilitytest_Nova_ThreadStability_Nova_joinThreads(this, exceptionData, l1_Nova_threads, 20);
}

void stabilitytest_Nova_ThreadStability_Nova_createThreads(stabilitytest_Nova_ThreadStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_ThreadImplementation** stabilitytest_Nova_ThreadStability_Nova_threads, int stabilitytest_Nova_ThreadStability_Nova_amount)
{
	stabilitytest_Nova_StabilityExceptionHandler* l1_Nova_handler;
	int l2_Nova_i;
	
	l1_Nova_handler = stabilitytest_Nova_StabilityExceptionHandler_Nova_construct(0, exceptionData, this->stabilitytest_Nova_StabilityTestCase_Nova_program);
	l2_Nova_i = 0;
	for (; l2_Nova_i < stabilitytest_Nova_ThreadStability_Nova_amount; l2_Nova_i++)
	{
		stabilitytest_Nova_ThreadStability_Nova_threads[l2_Nova_i] = stabilitytest_Nova_ThreadImplementation_Nova_construct(0, exceptionData, 10, 10);
		nova_standard_thread_Nova_Thread_Nova_start((nova_standard_thread_Nova_Thread*)(stabilitytest_Nova_ThreadStability_Nova_threads[l2_Nova_i]), exceptionData);
	}
}

void stabilitytest_Nova_ThreadStability_Nova_checkMemoryAccess(stabilitytest_Nova_ThreadStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	int l2_Nova_i;
	
	nova_standard_thread_Nova_Thread_Nova_sleep(0, exceptionData, (long)(30));
	nova_standard_io_Nova_Console_0_Nova_write(0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Checking memory access with multi-threading... "));
	l2_Nova_i = 0;
	for (; l2_Nova_i < 1000; l2_Nova_i++)
	{
		nova_standard_Nova_String* l2_Nova_s;
		
		l2_Nova_s = nova_standard_primitive_number_Nova_Int_1_Nova_toString(0, exceptionData, l2_Nova_i);
	}
	nova_standard_io_Nova_Console_0_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "OK"));
}

void stabilitytest_Nova_ThreadStability_Nova_joinThreads(stabilitytest_Nova_ThreadStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_ThreadImplementation** stabilitytest_Nova_ThreadStability_Nova_threads, int stabilitytest_Nova_ThreadStability_Nova_amount)
{
	int l2_Nova_i;
	
	l2_Nova_i = 0;
	for (; l2_Nova_i < stabilitytest_Nova_ThreadStability_Nova_amount; l2_Nova_i++)
	{
		nova_standard_thread_Nova_Thread_Nova_join((nova_standard_thread_Nova_Thread*)(stabilitytest_Nova_ThreadStability_Nova_threads[l2_Nova_i]), exceptionData);
	}
}

void stabilitytest_Nova_ThreadStability_0_Nova_this(stabilitytest_Nova_ThreadStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_StabilityTest* stabilitytest_Nova_ThreadStability_Nova_program)
{
}

void stabilitytest_Nova_ThreadStability_2_Nova_super(stabilitytest_Nova_ThreadStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

