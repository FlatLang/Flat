#include <precompiled.h>
#include <stabilitytest/stabilitytest_NovaUnstableException.h>


nova_VTable_stabilitytest_NovaUnstableException nova_VTable_stabilitytest_NovaUnstableException_val =
{
	nova_standard_NovaObject_0_NovagetHashCodeLong,
	nova_standard_NovaObject_0_NovatoString,
	nova_standard_NovaObject_0_Novaequals,
};
void stabilitytest_NovaUnstableExceptionNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
	{
	}
}

stabilitytest_NovaUnstableException* stabilitytest_NovaUnstableException_0_Novaconstruct(stabilitytest_NovaUnstableException* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	CCLASS_NEW(stabilitytest_NovaUnstableException, this,);
	this->vtable = &nova_VTable_stabilitytest_NovaUnstableException_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, exceptionData);
	nova_standard_exception_NovaException_Novasuper((nova_standard_exception_NovaException*)this, exceptionData);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_exception_NovaException_Novathis((nova_standard_exception_NovaException*)(this), exceptionData);
	stabilitytest_NovaUnstableException_Novasuper(this, exceptionData);
	
	{
		stabilitytest_NovaUnstableException_Novathis(this, exceptionData);
	}
	
	return this;
}

void nova_del_UnstableException(stabilitytest_NovaUnstableException** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

void stabilitytest_NovaUnstableException_Novathis(stabilitytest_NovaUnstableException* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}

void stabilitytest_NovaUnstableException_Novasuper(stabilitytest_NovaUnstableException* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}
