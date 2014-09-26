#include <precompiled.h>
#include <nova/standard/nova_standard_NovaUncaughtExceptionHandler.h>


nova_VTable_nova_standard_NovaUncaughtExceptionHandler nova_VTable_nova_standard_NovaUncaughtExceptionHandler_val =
{
	nova_standard_NovaObject_Novanull0_toString,
	nova_standard_NovaObject_Novanull0_equals,
};

nova_standard_NovaUncaughtExceptionHandler* nova_standard_NovaUncaughtExceptionHandler_Novanull0_construct(nova_standard_NovaUncaughtExceptionHandler* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_NovaUncaughtExceptionHandler, this,);
	this->vtable = &nova_VTable_nova_standard_NovaUncaughtExceptionHandler_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_NovaUncaughtExceptionHandler_Novasuper(this, 0);
	
	{
		nova_standard_NovaUncaughtExceptionHandler_Novathis(this, exceptionData);
	}
	
	return this;
}

void nova_del_UncaughtExceptionHandler(nova_standard_NovaUncaughtExceptionHandler** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

void nova_standard_NovaUncaughtExceptionHandler_NovaUncaughtExceptionHandler(nova_standard_NovaUncaughtExceptionHandler* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}

void nova_standard_NovaUncaughtExceptionHandler_NovauncaughtException(nova_standard_NovaUncaughtExceptionHandler* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaThread* l0_Novathread, nova_standard_exception_NovaException* l0_Novaexception)
{
}

void nova_standard_NovaUncaughtExceptionHandler_Novathis(nova_standard_NovaUncaughtExceptionHandler* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}

void nova_standard_NovaUncaughtExceptionHandler_Novasuper(nova_standard_NovaUncaughtExceptionHandler* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}
