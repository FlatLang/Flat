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
	},
	nova_standard_Nova_Object_1_Nova_getHashCodeLong,
	nova_standard_Nova_Object_0_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
};



void stabilitytest_Nova_ExceptionStability_Nova_testException(stabilitytest_Nova_ExceptionStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_StabilityTest* l0_Nova_program);
void stabilitytest_Nova_ExceptionStabilityNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

stabilitytest_Nova_ExceptionStability* stabilitytest_Nova_ExceptionStability_2_Nova_construct(stabilitytest_Nova_ExceptionStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(stabilitytest_Nova_ExceptionStability, this,);
	this->vtable = &stabilitytest_Extension_VTable_ExceptionStability_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_Nova_Object_2_Nova_this((nova_standard_Nova_Object*)(this), exceptionData);
	stabilitytest_Nova_ExceptionStability_Nova_super(this, exceptionData);
	
	{
		stabilitytest_Nova_ExceptionStability_2_Nova_this(this, exceptionData);
	}
	
	return this;
}

void stabilitytest_Nova_ExceptionStability_Nova_destroy(stabilitytest_Nova_ExceptionStability** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

void stabilitytest_Nova_ExceptionStability_Nova_test(stabilitytest_Nova_ExceptionStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_StabilityTest* l0_Nova_program)
{
	char l1_Nova_worked;
	
	nova_standard_io_Nova_Console_0_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Checking Exception handling..."));
	l1_Nova_worked = 0;
	TRY
	{
		novaEnv.nova_standard_exception_ExceptionData.addCode(exceptionData, exceptionData, 1);
		
		{
			stabilitytest_Nova_ExceptionStability_Nova_testException((stabilitytest_Nova_ExceptionStability*)nova_null, exceptionData, l0_Nova_program);
		}
	}
	CATCH (1)
	{
		nova_standard_exception_Nova_Exception* l2_Nova_e;
		
		l2_Nova_e = exceptionData->nova_standard_exception_Nova_ExceptionData_Nova_thrownException;
		l1_Nova_worked = 1;
	}
	FINALLY
	{
	}
	END_TRY;
	if (!l1_Nova_worked)
	{
		stabilitytest_Nova_StabilityTest_1_Nova_fail(l0_Nova_program, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Failed; uncaught Exception"));
	}
}

void stabilitytest_Nova_ExceptionStability_Nova_testException(stabilitytest_Nova_ExceptionStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_StabilityTest* l0_Nova_program)
{
	volatile char l1_Nova_worked;
	
	l1_Nova_worked = 0;
	TRY
	{
		novaEnv.nova_standard_exception_ExceptionData.addCode(exceptionData, exceptionData, 8);
		
		{
			nova_standard_io_Nova_Console_0_Nova_write(0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Checking DivideByZeroException... "));
			TRY
			{
				novaEnv.nova_standard_exception_ExceptionData.addCode(exceptionData, exceptionData, 7);
				
				{
					int l2_Nova_den;
					int l2_Nova_i;
					int nova_zero_check3;
					
					l2_Nova_den = 0;
					nova_zero_check3 = l2_Nova_den;
					if (nova_zero_check3 == 0)
					{
						THROW(7, nova_standard_exception_Nova_DivideByZeroException_0_Nova_construct(0, exceptionData));
					}
					l2_Nova_i = 43 / nova_zero_check3;
				}
			}
			CATCH (7)
			{
				nova_standard_exception_Nova_DivideByZeroException* l6_Nova_e;
				
				l6_Nova_e = (nova_standard_exception_Nova_DivideByZeroException*)(exceptionData->nova_standard_exception_Nova_ExceptionData_Nova_thrownException);
				l1_Nova_worked = 1;
				nova_standard_io_Nova_Console_0_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "OK"));
				nova_standard_io_Nova_Console_0_Nova_write(0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Checking StabilityTestException... "));
				THROW(8, stabilitytest_Nova_StabilityTestException_0_Nova_construct(0, exceptionData));
			}
			FINALLY
			{
			}
			END_TRY;
			stabilitytest_Nova_StabilityTest_1_Nova_fail(l0_Nova_program, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Failed to catch DivideByZeroException"));
		}
	}
	CATCH (8)
	{
		stabilitytest_Nova_StabilityTestException* l8_Nova_e;
		
		l8_Nova_e = (stabilitytest_Nova_StabilityTestException*)(exceptionData->nova_standard_exception_Nova_ExceptionData_Nova_thrownException);
		if (!l1_Nova_worked)
		{
			stabilitytest_Nova_StabilityTest_1_Nova_fail(l0_Nova_program, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Failed; uncaught DivideByZeroException"));
		}
		nova_standard_io_Nova_Console_0_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "OK"));
		THROW(1, nova_standard_exception_Nova_Exception_0_Nova_construct(0, exceptionData));
	}
	FINALLY
	{
	}
	END_TRY;
	if (!l1_Nova_worked)
	{
		stabilitytest_Nova_StabilityTest_1_Nova_fail(l0_Nova_program, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Failed; uncaught StabilityTestException"));
	}
}

void stabilitytest_Nova_ExceptionStability_2_Nova_this(stabilitytest_Nova_ExceptionStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

void stabilitytest_Nova_ExceptionStability_Nova_super(stabilitytest_Nova_ExceptionStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

