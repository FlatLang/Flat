#include <precompiled.h>
#include <stabilitytest/stabilitytest_NovaThreadImplementation.h>


nova_VTable_stabilitytest_NovaThreadImplementation nova_VTable_stabilitytest_NovaThreadImplementation_val =
{
	nova_standard_NovaObject_Nova0_toString,
	nova_standard_NovaObject_Nova0_equals,
	stabilitytest_NovaThreadImplementation_Nova0_run,
};
CCLASS_PRIVATE
(
	NOVA_THREAD_HANDLE* nova_standard_thread_NovaThread_Novahandle;
	
	int stabilitytest_NovaThreadImplementation_Novatimes;
	int stabilitytest_NovaThreadImplementation_Novamillis;
	
)
void stabilitytest_NovaThreadImplementationNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
	{
	}
}

stabilitytest_NovaThreadImplementation* stabilitytest_NovaThreadImplementation_Novaconstruct(stabilitytest_NovaThreadImplementation* this, nova_standard_exception_NovaExceptionData* exceptionData, int l0_Novatimes, int l0_Novamillis)
{
	CCLASS_NEW(stabilitytest_NovaThreadImplementation, this);
	this->vtable = &nova_VTable_stabilitytest_NovaThreadImplementation_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
	nova_standard_thread_NovaThread_Novasuper((nova_standard_thread_NovaThread*)this, 0);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_thread_NovaThread_Novathis((nova_standard_thread_NovaThread*)(this), exceptionData);
	stabilitytest_NovaThreadImplementation_Novasuper(this, 0);
	
	{
		stabilitytest_NovaThreadImplementation_Novathis(this, exceptionData, l0_Novatimes, l0_Novamillis);
	}
	
	return this;
}

void nova_del_ThreadImplementation(stabilitytest_NovaThreadImplementation** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	
	NOVA_FREE((*this)->prv);
	
	{
	}
	NOVA_FREE(*this);
}

void stabilitytest_NovaThreadImplementation_Novathis(stabilitytest_NovaThreadImplementation* this, nova_standard_exception_NovaExceptionData* exceptionData, int l0_Novatimes, int l0_Novamillis)
{
	this->prv->stabilitytest_NovaThreadImplementation_Novatimes = l0_Novatimes;
	this->prv->stabilitytest_NovaThreadImplementation_Novamillis = l0_Novamillis;
}

void stabilitytest_NovaThreadImplementation_Nova0_run(stabilitytest_NovaThreadImplementation* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	int l2_Novai;
	
	l2_Novai = 0;
	for (; l2_Novai < this->prv->stabilitytest_NovaThreadImplementation_Novatimes; l2_Novai++)
	{
		nova_standard_thread_NovaThread_static_Novasleep(0, exceptionData, (long_long)(this->prv->stabilitytest_NovaThreadImplementation_Novamillis));
	}
}

void stabilitytest_NovaThreadImplementation_Novasuper(stabilitytest_NovaThreadImplementation* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	this->prv->stabilitytest_NovaThreadImplementation_Novatimes = 0;
	this->prv->stabilitytest_NovaThreadImplementation_Novamillis = 0;
}
