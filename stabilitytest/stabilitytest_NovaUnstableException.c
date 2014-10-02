#include <precompiled.h>
#include <stabilitytest/stabilitytest_NovaUnstableException.h>


nova_VTable_stabilitytest_NovaUnstableException nova_VTable_stabilitytest_NovaUnstableException_val =
{
	nova_standard_NovaObject_Nova0_toString,
	nova_standard_NovaObject_Nova0_equals,
};
void stabilitytest_NovaUnstableExceptionNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
	{
	}
}

stabilitytest_NovaUnstableException* stabilitytest_NovaUnstableException_Nova0_construct(stabilitytest_NovaUnstableException* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	CCLASS_NEW(stabilitytest_NovaUnstableException, this,);
	this->vtable = &nova_VTable_stabilitytest_NovaUnstableException_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
	nova_standard_exception_NovaException_Novasuper((nova_standard_exception_NovaException*)this, 0);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_exception_NovaException_Novathis((nova_standard_exception_NovaException*)(this), exceptionData);
	stabilitytest_NovaUnstableException_Novasuper(this, 0);
	
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
