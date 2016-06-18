#include <precompiled.h>
#include <stabilitytest/stabilitytest_Nova_StabilityTest.h>

stabilitytest_Extension_VTable_StabilityTest stabilitytest_Extension_VTable_StabilityTest_val =
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
		(char(*)(nova_standard_operators_Nova_Equals*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*))nova_standard_Nova_Object_0_Nova_equals,
		0,
		0,
		0,
	},
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_Nova_Object_1_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
	stabilitytest_Nova_StabilityTest_0_Nova_test,
};


void stabilitytest_Nova_StabilityTestNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

stabilitytest_Nova_StabilityTest* stabilitytest_Nova_StabilityTest_0_Nova_construct(stabilitytest_Nova_StabilityTest* this, nova_standard_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_StabilityTest* stabilitytest_Nova_StabilityTest_Nova_program)
{
	CCLASS_NEW(stabilitytest_Nova_StabilityTest, this,);
	this->vtable = &stabilitytest_Extension_VTable_StabilityTest_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	stabilitytest_Nova_StabilityTestCase_Nova_super((stabilitytest_Nova_StabilityTestCase*)this, exceptionData);
	stabilitytest_Nova_StabilityTest_0_Nova_super(this, exceptionData);
	
	{
		stabilitytest_Nova_StabilityTest_0_Nova_this(this, exceptionData, stabilitytest_Nova_StabilityTest_Nova_program);
	}
	
	return this;
}

void stabilitytest_Nova_StabilityTest_Nova_destroy(stabilitytest_Nova_StabilityTest** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

void stabilitytest_Nova_StabilityTest_Nova_main(stabilitytest_Nova_StabilityTest* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String** stabilitytest_Nova_StabilityTest_Nova_args)
{
	stabilitytest_Nova_StabilityTest* l1_Nova_n;
	stabilitytest_Nova_StabilityTest* l1_Nova_test;
	nova_standard_time_Nova_Timer* l1_Nova_timer;
	nova_standard_Nova_String* nova_local_0;
	
	l1_Nova_n = (stabilitytest_Nova_StabilityTest*)((nova_standard_Nova_Object*)nova_null);
	l1_Nova_test = stabilitytest_Nova_StabilityTest_0_Nova_construct(0, exceptionData, l1_Nova_n);
	l1_Nova_timer = nova_standard_time_Nova_Timer_Nova_start(nova_standard_time_Nova_Timer_0_Nova_construct(0, exceptionData), exceptionData);
	l1_Nova_test->vtable->stabilitytest_Nova_StabilityTest_virtual0_Nova_test(l1_Nova_test, exceptionData);
	nova_standard_time_Nova_Timer_Nova_stop(l1_Nova_timer, exceptionData);
	nova_local_0 = nova_standard_primitive_number_Nova_Long_2_Nova_toString(0, exceptionData, nova_standard_time_Nova_Timer_Accessor_Nova_duration(l1_Nova_timer, exceptionData));
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "Took "), exceptionData, nova_local_0->vtable->nova_standard_Nova_String_virtual0_Nova_concat(nova_local_0, exceptionData, nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "ms"))));
	nova_standard_io_Nova_Console_Nova_waitForEnter(0, exceptionData);
}

void stabilitytest_Nova_StabilityTest_0_Nova_this(stabilitytest_Nova_StabilityTest* this, nova_standard_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_StabilityTest* stabilitytest_Nova_StabilityTest_Nova_program)
{
	stabilitytest_Nova_StabilityTestCase_0_Nova_this((stabilitytest_Nova_StabilityTestCase*)(this), exceptionData, stabilitytest_Nova_StabilityTest_Nova_program);
}

void stabilitytest_Nova_StabilityTest_0_Nova_test(stabilitytest_Nova_StabilityTest* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	TRY
	{
		novaEnv.nova_standard_exception_ExceptionData.addCode(exceptionData, exceptionData, 10);
		
		{
			stabilitytest_Nova_ExceptionStability_0_Nova_test(stabilitytest_Nova_ExceptionStability_0_Nova_construct(0, exceptionData, this), exceptionData);
			stabilitytest_Nova_TimeStability_0_Nova_test(stabilitytest_Nova_TimeStability_0_Nova_construct(0, exceptionData, this), exceptionData);
			stabilitytest_Nova_ThreadStability_0_Nova_test(stabilitytest_Nova_ThreadStability_0_Nova_construct(0, exceptionData, this), exceptionData);
			stabilitytest_Nova_FileStability_0_Nova_test(stabilitytest_Nova_FileStability_0_Nova_construct(0, exceptionData, this), exceptionData);
			stabilitytest_Nova_SyntaxStability_0_Nova_test(stabilitytest_Nova_SyntaxStability_0_Nova_construct(0, exceptionData, this), exceptionData);
			stabilitytest_Nova_ClosureStability_Nova_test(stabilitytest_Nova_ClosureStability_Nova_construct(0, exceptionData, this), exceptionData);
			stabilitytest_Nova_PolymorphismStability_0_Nova_test(stabilitytest_Nova_PolymorphismStability_0_Nova_construct(0, exceptionData, this), exceptionData);
			stabilitytest_Nova_NetworkStability_0_Nova_test(stabilitytest_Nova_NetworkStability_0_Nova_construct(0, exceptionData, this), exceptionData);
			nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "All OK"));
		}
	}
	CATCH (10)
	{
		stabilitytest_Nova_UnstableException* l2_Nova_e;
		
		l2_Nova_e = (stabilitytest_Nova_UnstableException*)(exceptionData->nova_standard_exception_Nova_ExceptionData_Nova_thrownException);
		nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "Unstable exception thrown: "), exceptionData, l2_Nova_e->nova_standard_exception_Nova_Exception_Nova_message));
	}
	FINALLY
	{
	}
	END_TRY;
}

void stabilitytest_Nova_StabilityTest_0_Nova_fail(stabilitytest_Nova_StabilityTest* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	stabilitytest_Nova_StabilityTest_1_Nova_fail(this, exceptionData, nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "Failed"));
}

void stabilitytest_Nova_StabilityTest_1_Nova_fail(stabilitytest_Nova_StabilityTest* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* stabilitytest_Nova_StabilityTest_Nova_message)
{
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, stabilitytest_Nova_StabilityTest_Nova_message);
	THROW(10, stabilitytest_Nova_UnstableException_3_Nova_construct(0, exceptionData));
}

void stabilitytest_Nova_StabilityTest_0_Nova_super(stabilitytest_Nova_StabilityTest* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

