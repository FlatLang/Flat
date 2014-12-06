#include <precompiled.h>
#include <stabilitytest/stabilitytest_NovaStabilityTestException.h>


nova_VTable_stabilitytest_NovaStabilityTestException nova_VTable_stabilitytest_NovaStabilityTestException_val =
{
	nova_standard_NovaObject_0_NovagetHashCodeLong,
	nova_standard_NovaObject_0_NovatoString,
	nova_standard_NovaObject_0_Novaequals,
};
void stabilitytest_NovaStabilityTestExceptionNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
	{
	}
}

stabilitytest_NovaStabilityTestException* stabilitytest_NovaStabilityTestException_0_Novaconstruct(stabilitytest_NovaStabilityTestException* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	CCLASS_NEW(stabilitytest_NovaStabilityTestException, this,);
	this->vtable = &nova_VTable_stabilitytest_NovaStabilityTestException_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, exceptionData);
	nova_standard_exception_NovaException_Novasuper((nova_standard_exception_NovaException*)this, exceptionData);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_exception_NovaException_Novathis((nova_standard_exception_NovaException*)(this), exceptionData);
	stabilitytest_NovaStabilityTestException_Novasuper(this, exceptionData);
	
	{
		stabilitytest_NovaStabilityTestException_Novathis(this, exceptionData);
	}
	
	return this;
}

void nova_del_StabilityTestException(stabilitytest_NovaStabilityTestException** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

void stabilitytest_NovaStabilityTestException_Novathis(stabilitytest_NovaStabilityTestException* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}

void stabilitytest_NovaStabilityTestException_Novasuper(stabilitytest_NovaStabilityTestException* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}
