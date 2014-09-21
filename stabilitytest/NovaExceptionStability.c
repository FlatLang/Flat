#include <precompiled.h>
#include "NovaExceptionStability.h"


nova_VTable_ExceptionStability nova_VTable_ExceptionStability_val =
{
	nova_0_Object_toString,
	nova_0_Object_equals,
};

void nova_static_ExceptionStability_testException(ExceptionStability* this, ExceptionData* exceptionData, StabilityTest* nova_0_program);

ExceptionStability* nova_0_ExceptionStability_construct(ExceptionStability* this, ExceptionData* exceptionData)
{
	CCLASS_NEW(ExceptionStability, this,);
	this->vtable = &nova_VTable_ExceptionStability_val;
	nova_Object_super((Object*)this, 0);
	nova_Object_this((Object*)(this), exceptionData);
	nova_ExceptionStability_super(this, 0);
	
	{
		nova_ExceptionStability_this(this, exceptionData);
	}
	
	return this;
}

void nova_del_ExceptionStability(ExceptionStability** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

void nova_static_ExceptionStability_test(ExceptionStability* this, ExceptionData* exceptionData, StabilityTest* nova_0_program)
{
	char nova_1_worked;
	
	nova_static_0_Console_write(0, exceptionData, nova_String_construct(0, exceptionData, "Checking Exception handling... "));
	nova_1_worked = 0;
	TRY
	{
		nova_ExceptionData_addCode(exceptionData, exceptionData, 1);
		
		{
			nova_static_ExceptionStability_testException((ExceptionStability*)nova_null, exceptionData, nova_0_program);
		}
	}
	CATCH (1)
	{
		nova_1_worked = 1;
	}
	FINALLY
	{
	}
	END_TRY;
	if (!nova_1_worked)
	{
		nova_1_StabilityTest_fail(nova_0_program, exceptionData, nova_String_construct(0, exceptionData, "Failed; uncaught Exception"));
	}
	nova_static_0_Console_writeLine(0, exceptionData, nova_String_construct(0, exceptionData, "OK"));
}

void nova_static_ExceptionStability_testException(ExceptionStability* this, ExceptionData* exceptionData, StabilityTest* nova_0_program)
{
	volatile char nova_1_worked;
	
	nova_1_worked = 0;
	TRY
	{
		nova_ExceptionData_addCode(exceptionData, exceptionData, 5);
		
		{
			TRY
			{
				nova_ExceptionData_addCode(exceptionData, exceptionData, 4);
				
				{
					int nova_3_den;
					int nova_3_i;
					int nova_zero_check4;
					
					nova_3_den = 0;
					nova_zero_check4 = nova_3_den;
					if (nova_zero_check4 == 0)
					{
						THROW(4);
					}
					nova_3_i = 43 / nova_zero_check4;
				}
			}
			CATCH (4)
			{
				nova_1_worked = 1;
				THROW(5);
			}
			FINALLY
			{
			}
			END_TRY;
		}
	}
	CATCH (5)
	{
		if (!nova_1_worked)
		{
			nova_1_StabilityTest_fail(nova_0_program, exceptionData, nova_String_construct(0, exceptionData, "Failed; uncaught DivideByZeroException"));
		}
		THROW(1);
	}
	FINALLY
	{
	}
	END_TRY;
	if (!nova_1_worked)
	{
		nova_1_StabilityTest_fail(nova_0_program, exceptionData, nova_String_construct(0, exceptionData, "Failed; uncaught StabilityTestException"));
	}
}

void nova_ExceptionStability_this(ExceptionStability* this, ExceptionData* exceptionData)
{
}

void nova_ExceptionStability_super(ExceptionStability* this, ExceptionData* exceptionData)
{
}
