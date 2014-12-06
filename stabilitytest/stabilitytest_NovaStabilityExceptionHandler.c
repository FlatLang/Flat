#include <precompiled.h>
#include <stabilitytest/stabilitytest_NovaStabilityExceptionHandler.h>


nova_VTable_stabilitytest_NovaStabilityExceptionHandler nova_VTable_stabilitytest_NovaStabilityExceptionHandler_val =
{
	nova_standard_NovaObject_0_NovagetHashCodeLong,
	nova_standard_NovaObject_0_NovatoString,
	nova_standard_NovaObject_0_Novaequals,
	stabilitytest_NovaStabilityExceptionHandler_NovauncaughtException,
};
CCLASS_PRIVATE
(
	stabilitytest_NovaStabilityTest* stabilitytest_NovaStabilityExceptionHandler_Novaprogram;
	
)
void stabilitytest_NovaStabilityExceptionHandlerNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
	{
	}
}

stabilitytest_NovaStabilityExceptionHandler* stabilitytest_NovaStabilityExceptionHandler_Novaconstruct(stabilitytest_NovaStabilityExceptionHandler* this, nova_standard_exception_NovaExceptionData* exceptionData, stabilitytest_NovaStabilityTest* l0_Novaprogram)
{
	CCLASS_NEW(stabilitytest_NovaStabilityExceptionHandler, this);
	this->vtable = &nova_VTable_stabilitytest_NovaStabilityExceptionHandler_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, exceptionData);
	nova_standard_thread_NovaUncaughtExceptionHandler_Novasuper((nova_standard_thread_NovaUncaughtExceptionHandler*)this, exceptionData);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_thread_NovaUncaughtExceptionHandler_Novathis((nova_standard_thread_NovaUncaughtExceptionHandler*)(this), exceptionData);
	stabilitytest_NovaStabilityExceptionHandler_Novasuper(this, exceptionData);
	
	{
		stabilitytest_NovaStabilityExceptionHandler_Novathis(this, exceptionData, l0_Novaprogram);
	}
	
	return this;
}

void nova_del_StabilityExceptionHandler(stabilitytest_NovaStabilityExceptionHandler** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_del_StabilityTest(&(*this)->prv->stabilitytest_NovaStabilityExceptionHandler_Novaprogram, exceptionData);
	NOVA_FREE((*this)->prv);
	
	{
	}
	NOVA_FREE(*this);
}

void stabilitytest_NovaStabilityExceptionHandler_Novathis(stabilitytest_NovaStabilityExceptionHandler* this, nova_standard_exception_NovaExceptionData* exceptionData, stabilitytest_NovaStabilityTest* l0_Novaprogram)
{
	this->prv->stabilitytest_NovaStabilityExceptionHandler_Novaprogram = l0_Novaprogram;
}

void stabilitytest_NovaStabilityExceptionHandler_NovauncaughtException(stabilitytest_NovaStabilityExceptionHandler* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_thread_NovaThread* l0_Novathread, nova_standard_exception_NovaException* l0_Novaexception)
{
	stabilitytest_NovaStabilityTest_0_Novafail(this->prv->stabilitytest_NovaStabilityExceptionHandler_Novaprogram, exceptionData);
}

void stabilitytest_NovaStabilityExceptionHandler_Novasuper(stabilitytest_NovaStabilityExceptionHandler* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	this->prv->stabilitytest_NovaStabilityExceptionHandler_Novaprogram = (stabilitytest_NovaStabilityTest*)nova_null;
}
