#include <precompiled.h>
#include <stabilitytest/stabilitytest_Nova_ExceptionStability.h>



stabilitytest_Extension_VTable_ExceptionStability stabilitytest_Extension_VTable_ExceptionStability_val =
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
	nova_Nova_Object_0_Nova_getHashCodeLong,
	nova_Nova_Object_0_Nova_toString,
	nova_Nova_Object_0_Nova_equals,
	stabilitytest_Nova_ExceptionStability_0_Nova_test,
};



void stabilitytest_Nova_ExceptionStability_Nova_testException(stabilitytest_Nova_ExceptionStability* this, nova_exception_Nova_ExceptionData* exceptionData);
void stabilitytest_Nova_ExceptionStability_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

stabilitytest_Nova_ExceptionStability* stabilitytest_Nova_ExceptionStability_Nova_ExceptionStability(stabilitytest_Nova_ExceptionStability* this, nova_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_StabilityTest* stabilitytest_Nova_ExceptionStability_Nova_program)
{
	CCLASS_NEW(stabilitytest_Nova_ExceptionStability, this,);
	this->vtable = &stabilitytest_Extension_VTable_ExceptionStability_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	stabilitytest_Nova_StabilityTestCase_Nova_super((stabilitytest_Nova_StabilityTestCase*)this, exceptionData);
	stabilitytest_Nova_ExceptionStability_0_Nova_super(this, exceptionData);
	
	{
		stabilitytest_Nova_ExceptionStability_0_Nova_this(this, exceptionData, stabilitytest_Nova_ExceptionStability_Nova_program);
	}
	
	return this;
}

void stabilitytest_Nova_ExceptionStability_Nova_destroy(stabilitytest_Nova_ExceptionStability** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

void stabilitytest_Nova_ExceptionStability_0_Nova_this(stabilitytest_Nova_ExceptionStability* this, nova_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_StabilityTest* stabilitytest_Nova_ExceptionStability_Nova_program)
{
	stabilitytest_Nova_StabilityTestCase_0_Nova_this((stabilitytest_Nova_StabilityTestCase*)(this), exceptionData, stabilitytest_Nova_ExceptionStability_Nova_program);
}

void stabilitytest_Nova_ExceptionStability_0_Nova_test(stabilitytest_Nova_ExceptionStability* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	char l1_Nova_worked = 0;
	
	nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_1_Nova_String(0, exceptionData, "Checking Exception handling..."));
	l1_Nova_worked = 0;
	TRY
	{
		novaEnv.nova_exception_ExceptionData.addCode(exceptionData, exceptionData, 1);
		
		{
			stabilitytest_Nova_ExceptionStability_Nova_testException(this, exceptionData);
		}
	}
	CATCH (1)
	{
		nova_exception_Nova_Exception* l2_Nova_e = (nova_exception_Nova_Exception*)nova_null;
		
		l2_Nova_e = (nova_exception_Nova_Exception*)exceptionData->nova_exception_Nova_ExceptionData_Nova_thrownException;
		l1_Nova_worked = 1;
	}
	FINALLY
	{
	}
	END_TRY;
	if (!l1_Nova_worked)
	{
		stabilitytest_Nova_StabilityTest_1_Nova_fail(this->stabilitytest_Nova_StabilityTestCase_Nova_program, exceptionData, nova_Nova_String_1_Nova_String(0, exceptionData, "Failed; uncaught Exception"));
	}
}

void stabilitytest_Nova_ExceptionStability_Nova_testException(stabilitytest_Nova_ExceptionStability* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	volatile char l1_Nova_worked = 0;
	
	l1_Nova_worked = 0;
	TRY
	{
		novaEnv.nova_exception_ExceptionData.addCode(exceptionData, exceptionData, 9);
		
		{
			nova_io_Nova_Console_0_Nova_write(0, exceptionData, nova_Nova_String_1_Nova_String(0, exceptionData, "Checking DivideByZeroException... "));
			TRY
			{
				novaEnv.nova_exception_ExceptionData.addCode(exceptionData, exceptionData, 8);
				
				{
					int l2_Nova_den = 0;
					int l2_Nova_i = 0;
					int nova_zero_check6 = 0;
					
					l2_Nova_den = (int)(0);
					nova_zero_check6 = l2_Nova_den;
					if (nova_zero_check6 == 0)
					{
						THROW(8, nova_exception_Nova_DivideByZeroException_Nova_DivideByZeroException(0, exceptionData));
					}
					l2_Nova_i = 43 / nova_zero_check6;
				}
			}
			CATCH (8)
			{
				nova_exception_Nova_DivideByZeroException* l5_Nova_e = (nova_exception_Nova_DivideByZeroException*)nova_null;
				
				l5_Nova_e = (nova_exception_Nova_DivideByZeroException*)exceptionData->nova_exception_Nova_ExceptionData_Nova_thrownException;
				l1_Nova_worked = 1;
				nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_1_Nova_String(0, exceptionData, "OK"));
				nova_io_Nova_Console_0_Nova_write(0, exceptionData, nova_Nova_String_1_Nova_String(0, exceptionData, "Checking StabilityTestException... "));
				THROW(9, stabilitytest_Nova_StabilityTestException_Nova_StabilityTestException(0, exceptionData));
			}
			FINALLY
			{
			}
			END_TRY;
			stabilitytest_Nova_StabilityTest_1_Nova_fail(this->stabilitytest_Nova_StabilityTestCase_Nova_program, exceptionData, nova_Nova_String_1_Nova_String(0, exceptionData, "Failed to catch DivideByZeroException"));
		}
	}
	CATCH (9)
	{
		stabilitytest_Nova_StabilityTestException* l7_Nova_e = (stabilitytest_Nova_StabilityTestException*)nova_null;
		
		l7_Nova_e = (stabilitytest_Nova_StabilityTestException*)exceptionData->nova_exception_Nova_ExceptionData_Nova_thrownException;
		if (!l1_Nova_worked)
		{
			stabilitytest_Nova_StabilityTest_1_Nova_fail(this->stabilitytest_Nova_StabilityTestCase_Nova_program, exceptionData, nova_Nova_String_1_Nova_String(0, exceptionData, "Failed; uncaught DivideByZeroException"));
		}
		nova_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_Nova_String_1_Nova_String(0, exceptionData, "OK"));
		THROW(1, nova_exception_Nova_Exception_0_Nova_Exception(0, exceptionData));
	}
	FINALLY
	{
	}
	END_TRY;
	if (!l1_Nova_worked)
	{
		stabilitytest_Nova_StabilityTest_1_Nova_fail(this->stabilitytest_Nova_StabilityTestCase_Nova_program, exceptionData, nova_Nova_String_1_Nova_String(0, exceptionData, "Failed; uncaught StabilityTestException"));
	}
}

void stabilitytest_Nova_ExceptionStability_0_Nova_super(stabilitytest_Nova_ExceptionStability* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

