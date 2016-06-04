#include <precompiled.h>
#include <stabilitytest/stabilitytest_Nova_TimeStability.h>

stabilitytest_Extension_VTable_TimeStability stabilitytest_Extension_VTable_TimeStability_val =
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
	},
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_Nova_Object_0_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
	stabilitytest_Nova_TimeStability_0_Nova_test,
};


void stabilitytest_Nova_TimeStabilityNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

stabilitytest_Nova_TimeStability* stabilitytest_Nova_TimeStability_0_Nova_construct(stabilitytest_Nova_TimeStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_StabilityTest* stabilitytest_Nova_TimeStability_Nova_program)
{
	CCLASS_NEW(stabilitytest_Nova_TimeStability, this,);
	this->vtable = &stabilitytest_Extension_VTable_TimeStability_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	stabilitytest_Nova_StabilityTestCase_Nova_super((stabilitytest_Nova_StabilityTestCase*)this, exceptionData);
	nova_standard_Nova_Object_2_Nova_this((nova_standard_Nova_Object*)(this), exceptionData);
	stabilitytest_Nova_StabilityTestCase_0_Nova_this((stabilitytest_Nova_StabilityTestCase*)(this), exceptionData, stabilitytest_Nova_TimeStability_Nova_program);
	stabilitytest_Nova_TimeStability_2_Nova_super(this, exceptionData);
	
	{
		stabilitytest_Nova_TimeStability_0_Nova_this(this, exceptionData, stabilitytest_Nova_TimeStability_Nova_program);
	}
	
	return this;
}

void stabilitytest_Nova_TimeStability_Nova_destroy(stabilitytest_Nova_TimeStability** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

void stabilitytest_Nova_TimeStability_0_Nova_test(stabilitytest_Nova_TimeStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	long l1_Nova_start;
	long l1_Nova_time;
	
	nova_standard_io_Nova_Console_0_Nova_write(0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Checking Time.nova... "));
	l1_Nova_start = nova_standard_time_Nova_Time_Nova_currentTimeMillis(0, exceptionData);
	nova_standard_thread_Nova_Thread_Nova_sleep(0, exceptionData, (long)(100));
	l1_Nova_time = nova_standard_time_Nova_Time_Nova_currentTimeMillis(0, exceptionData) - l1_Nova_start;
	if (l1_Nova_time >= 100 && l1_Nova_time < 130)
	{
		nova_standard_io_Nova_Console_0_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "OK"));
	}
	else
	{
		nova_standard_Nova_String* nova_local_0;
		
		nova_local_0 = nova_standard_primitive_number_Nova_Long_1_Nova_toString(0, exceptionData, l1_Nova_time);
		stabilitytest_Nova_StabilityTest_1_Nova_fail(this->stabilitytest_Nova_StabilityTestCase_Nova_program, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Failed; expected 100ms, found "), exceptionData, nova_local_0->vtable->nova_standard_Nova_String_virtual0_Nova_concat(nova_local_0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "ms"))));
	}
}

void stabilitytest_Nova_TimeStability_0_Nova_this(stabilitytest_Nova_TimeStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_StabilityTest* stabilitytest_Nova_TimeStability_Nova_program)
{
}

void stabilitytest_Nova_TimeStability_2_Nova_super(stabilitytest_Nova_TimeStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

