#include <precompiled.h>
#include <stabilitytest/stabilitytest_Nova_TimeStability.h>

stabilitytest_Extension_VTable_TimeStability stabilitytest_Extension_VTable_TimeStability_val =
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
		(char(*)(nova_standard_operators_Nova_Equals*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*))nova_standard_Nova_Object_0_Nova_equals,
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

stabilitytest_Nova_TimeStability* stabilitytest_Nova_TimeStability_Nova_TimeStability(stabilitytest_Nova_TimeStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_StabilityTest* stabilitytest_Nova_TimeStability_Nova_program)
{
	CCLASS_NEW(stabilitytest_Nova_TimeStability, this,);
	this->vtable = &stabilitytest_Extension_VTable_TimeStability_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	stabilitytest_Nova_StabilityTestCase_Nova_super((stabilitytest_Nova_StabilityTestCase*)this, exceptionData);
	stabilitytest_Nova_TimeStability_0_Nova_super(this, exceptionData);
	
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

void stabilitytest_Nova_TimeStability_0_Nova_this(stabilitytest_Nova_TimeStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_StabilityTest* stabilitytest_Nova_TimeStability_Nova_program)
{
	stabilitytest_Nova_StabilityTestCase_0_Nova_this((stabilitytest_Nova_StabilityTestCase*)(this), exceptionData, stabilitytest_Nova_TimeStability_Nova_program);
}

void stabilitytest_Nova_TimeStability_0_Nova_test(stabilitytest_Nova_TimeStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	nova_standard_time_Nova_Timer* l1_Nova_timer = (nova_standard_time_Nova_Timer*)nova_null;
	
	nova_standard_io_Nova_Console_0_Nova_write(0, exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Checking Time.nova... "));
	l1_Nova_timer = nova_standard_time_Nova_Timer_Nova_Timer(0, exceptionData);
	nova_standard_time_Nova_Timer_Nova_start(l1_Nova_timer, exceptionData);
	nova_standard_thread_Nova_Thread_Nova_sleep(0, exceptionData, 100);
	nova_standard_time_Nova_Timer_Nova_stop(l1_Nova_timer, exceptionData);
	if (nova_standard_time_Nova_Timer_Accessor_Nova_duration(l1_Nova_timer, exceptionData) >= 100 && nova_standard_time_Nova_Timer_Accessor_Nova_duration(l1_Nova_timer, exceptionData) < 130)
	{
		nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, "OK"));
	}
	else
	{
		stabilitytest_Nova_StabilityTest_1_Nova_fail(this->stabilitytest_Nova_StabilityTestCase_Nova_program, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_1_Nova_String(0, exceptionData, "Failed; expected 100ms, found "), exceptionData, nova_standard_Nova_String_virtual1_Nova_concat((nova_standard_Nova_String*)(nova_standard_primitive_number_Nova_Long_2_Nova_toString(0, exceptionData, nova_standard_time_Nova_Timer_Accessor_Nova_duration(l1_Nova_timer, exceptionData))), exceptionData, nova_standard_Nova_String_1_Nova_String(0, exceptionData, "ms"))));
	}
}

void stabilitytest_Nova_TimeStability_0_Nova_super(stabilitytest_Nova_TimeStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

