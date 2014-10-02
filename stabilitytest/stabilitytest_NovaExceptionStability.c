#include <precompiled.h>
#include <stabilitytest/stabilitytest_NovaExceptionStability.h>


nova_VTable_stabilitytest_NovaExceptionStability nova_VTable_stabilitytest_NovaExceptionStability_val =
{
	nova_standard_NovaObject_Nova0_toString,
	nova_standard_NovaObject_Nova0_equals,
};

void stabilitytest_NovaExceptionStability_static_NovatestException(stabilitytest_NovaExceptionStability* this, nova_standard_exception_NovaExceptionData* exceptionData, stabilitytest_NovaStabilityTest* l0_Novaprogram);
void stabilitytest_NovaExceptionStabilityNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
	{
	}
}

stabilitytest_NovaExceptionStability* stabilitytest_NovaExceptionStability_Nova0_construct(stabilitytest_NovaExceptionStability* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	CCLASS_NEW(stabilitytest_NovaExceptionStability, this,);
	this->vtable = &nova_VTable_stabilitytest_NovaExceptionStability_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	stabilitytest_NovaExceptionStability_Novasuper(this, 0);
	
	{
		stabilitytest_NovaExceptionStability_Novathis(this, exceptionData);
	}
	
	return this;
}

void nova_del_ExceptionStability(stabilitytest_NovaExceptionStability** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

void stabilitytest_NovaExceptionStability_static_Novatest(stabilitytest_NovaExceptionStability* this, nova_standard_exception_NovaExceptionData* exceptionData, stabilitytest_NovaStabilityTest* l0_Novaprogram)
{
	char l1_Novaworked;
	
	nova_standard_io_NovaConsole_static_Nova0_write(0, exceptionData, nova_standard_NovaString_Novaconstruct(0, exceptionData, "Checking Exception handling... "));
	l1_Novaworked = 0;
	TRY
	{
		nova_standard_exception_NovaExceptionData_NovaaddCode(exceptionData, exceptionData, 1);
		
		{
			stabilitytest_NovaExceptionStability_static_NovatestException((stabilitytest_NovaExceptionStability*)nova_null, exceptionData, l0_Novaprogram);
		}
	}
	CATCH (1)
	{
		nova_standard_exception_NovaException* l3_Novae;
		
		l3_Novae = exceptionData->nova_standard_exception_NovaExceptionData_NovathrownException;
		l1_Novaworked = 1;
	}
	FINALLY
	{
	}
	END_TRY;
	if (!l1_Novaworked)
	{
		stabilitytest_NovaStabilityTest_Nova1_fail(l0_Novaprogram, exceptionData, nova_standard_NovaString_Novaconstruct(0, exceptionData, "Failed; uncaught Exception"));
	}
	nova_standard_io_NovaConsole_static_Nova0_writeLine(0, exceptionData, nova_standard_NovaString_Novaconstruct(0, exceptionData, "OK"));
}

void stabilitytest_NovaExceptionStability_static_NovatestException(stabilitytest_NovaExceptionStability* this, nova_standard_exception_NovaExceptionData* exceptionData, stabilitytest_NovaStabilityTest* l0_Novaprogram)
{
	volatile char l1_Novaworked;
	
	l1_Novaworked = 0;
	TRY
	{
		nova_standard_exception_NovaExceptionData_NovaaddCode(exceptionData, exceptionData, 6);
		
		{
			TRY
			{
				nova_standard_exception_NovaExceptionData_NovaaddCode(exceptionData, exceptionData, 5);
				
				{
					int l3_Novaden;
					int l3_Novai;
					int nova_zero_check4;
					
					l3_Novaden = 0;
					nova_zero_check4 = l3_Novaden;
					if (nova_zero_check4 == 0)
					{
						THROW(5, nova_standard_exception_NovaDivideByZeroException_Nova0_construct(0, exceptionData));
					}
					l3_Novai = 43 / nova_zero_check4;
				}
			}
			CATCH (5)
			{
				nova_standard_exception_NovaDivideByZeroException* l7_Novae;
				
				l7_Novae = (nova_standard_exception_NovaDivideByZeroException*)(exceptionData->nova_standard_exception_NovaExceptionData_NovathrownException);
				l1_Novaworked = 1;
				THROW(6, stabilitytest_NovaStabilityTestException_Nova0_construct(0, exceptionData));
			}
			FINALLY
			{
			}
			END_TRY;
		}
	}
	CATCH (6)
	{
		stabilitytest_NovaStabilityTestException* l9_Novae;
		
		l9_Novae = (stabilitytest_NovaStabilityTestException*)(exceptionData->nova_standard_exception_NovaExceptionData_NovathrownException);
		if (!l1_Novaworked)
		{
			stabilitytest_NovaStabilityTest_Nova1_fail(l0_Novaprogram, exceptionData, nova_standard_NovaString_Novaconstruct(0, exceptionData, "Failed; uncaught DivideByZeroException"));
		}
		THROW(1, nova_standard_exception_NovaException_Nova0_construct(0, exceptionData));
	}
	FINALLY
	{
	}
	END_TRY;
	if (!l1_Novaworked)
	{
		stabilitytest_NovaStabilityTest_Nova1_fail(l0_Novaprogram, exceptionData, nova_standard_NovaString_Novaconstruct(0, exceptionData, "Failed; uncaught StabilityTestException"));
	}
}

void stabilitytest_NovaExceptionStability_Novathis(stabilitytest_NovaExceptionStability* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}

void stabilitytest_NovaExceptionStability_Novasuper(stabilitytest_NovaExceptionStability* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}
