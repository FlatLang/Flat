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
		0,
		(char(*)(nova_operators_Nova_Equals*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*))nova_Nova_Object_0_Nova_equals,
		0,
		0,
		0,
	},
	nova_Nova_Object_0_Nova_toString,
	nova_Nova_Object_0_Nova_equals,
	nova_Nova_Object_Accessor_Nova_hashCodeLong,
	stabilitytest_Nova_TimeStability_0_Nova_test,
};


void stabilitytest_Nova_TimeStability_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

stabilitytest_Nova_TimeStability* stabilitytest_Nova_TimeStability_Nova_construct(stabilitytest_Nova_TimeStability* this, nova_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_StabilityTest* stabilitytest_Nova_TimeStability_Nova_program)
{
	CCLASS_NEW(stabilitytest_Nova_TimeStability, this,);
	this->vtable = &stabilitytest_Extension_VTable_TimeStability_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	stabilitytest_Nova_StabilityTestCase_Nova_super((stabilitytest_Nova_StabilityTestCase*)this, exceptionData);
	stabilitytest_Nova_TimeStability_0_Nova_super(this, exceptionData);
	
	{
		stabilitytest_Nova_TimeStability_0_Nova_this(this, exceptionData, stabilitytest_Nova_TimeStability_Nova_program);
	}
	
	return this;
}

void stabilitytest_Nova_TimeStability_Nova_destroy(stabilitytest_Nova_TimeStability** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

void stabilitytest_Nova_TimeStability_0_Nova_this(stabilitytest_Nova_TimeStability* this, nova_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_StabilityTest* stabilitytest_Nova_TimeStability_Nova_program)
{
	stabilitytest_Nova_StabilityTestCase_0_Nova_this((stabilitytest_Nova_StabilityTestCase*)(this), exceptionData, stabilitytest_Nova_TimeStability_Nova_program);
}

void stabilitytest_Nova_TimeStability_0_Nova_test(stabilitytest_Nova_TimeStability* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	nova_time_Nova_Timer* l1_Nova_timer = (nova_time_Nova_Timer*)nova_null;
	
	nova_io_Nova_Console_0_Nova_write(0, exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("Checking Time.nova... ")));
	l1_Nova_timer = nova_time_Nova_Timer_Nova_construct(0, exceptionData);
	nova_time_Nova_Timer_Nova_start(l1_Nova_timer, exceptionData);
	nova_thread_Nova_Thread_Nova_sleep(0, exceptionData, 100);
	nova_time_Nova_Timer_Nova_stop(l1_Nova_timer, exceptionData);
	if (nova_time_Nova_Timer_Accessor_Nova_duration(l1_Nova_timer, exceptionData) >= 100 && nova_time_Nova_Timer_Accessor_Nova_duration(l1_Nova_timer, exceptionData) < 130)
	{
		nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("OK")));
	}
	else
	{
		stabilitytest_Nova_StabilityTest_1_Nova_fail(this->stabilitytest_Nova_StabilityTestCase_Nova_program, exceptionData, nova_Nova_String_0_Nova_concat(nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("Failed; expected 100ms, found ")), exceptionData, nova_Nova_String_virtual1_Nova_concat((nova_Nova_String*)(nova_primitive_number_Nova_Long_2_Nova_toString(0, exceptionData, nova_time_Nova_Timer_Accessor_Nova_duration(l1_Nova_timer, exceptionData))), exceptionData, nova_Nova_String_1_Nova_construct(0, exceptionData, (char*)("ms")))));
	}
}

void stabilitytest_Nova_TimeStability_0_Nova_super(stabilitytest_Nova_TimeStability* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

