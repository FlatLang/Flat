#include <precompiled.h>
#include <stabilitytest/stabilitytest_NovaStabilityTestException.h>


nova_VTable_stabilitytest_NovaStabilityTestException nova_VTable_stabilitytest_NovaStabilityTestException_val =
{
	nova_standard_NovaObject_Nova0_toString,
	nova_standard_NovaObject_Nova0_equals,
};
void stabilitytest_NovaStabilityTestExceptionNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
	{
	}
}

stabilitytest_NovaStabilityTestException* stabilitytest_NovaStabilityTestException_Nova0_construct(stabilitytest_NovaStabilityTestException* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	CCLASS_NEW(stabilitytest_NovaStabilityTestException, this,);
	this->vtable = &nova_VTable_stabilitytest_NovaStabilityTestException_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
	nova_standard_exception_NovaException_Novasuper((nova_standard_exception_NovaException*)this, 0);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_exception_NovaException_Novathis((nova_standard_exception_NovaException*)(this), exceptionData);
	stabilitytest_NovaStabilityTestException_Novasuper(this, 0);
	
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
