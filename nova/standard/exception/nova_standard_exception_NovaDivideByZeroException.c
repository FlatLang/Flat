#include <precompiled.h>
#include <nova/standard/exception/nova_standard_exception_NovaDivideByZeroException.h>


nova_VTable_nova_standard_exception_NovaDivideByZeroException nova_VTable_nova_standard_exception_NovaDivideByZeroException_val =
{
	nova_standard_NovaObject_Nova0_toString,
	nova_standard_NovaObject_Nova0_equals,
};
void nova_standard_exception_NovaDivideByZeroExceptionNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
	{
	}
}

nova_standard_exception_NovaDivideByZeroException* nova_standard_exception_NovaDivideByZeroException_Nova0_construct(nova_standard_exception_NovaDivideByZeroException* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_exception_NovaDivideByZeroException, this,);
	this->vtable = &nova_VTable_nova_standard_exception_NovaDivideByZeroException_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
	nova_standard_exception_NovaException_Novasuper((nova_standard_exception_NovaException*)this, 0);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_exception_NovaException_Novathis((nova_standard_exception_NovaException*)(this), exceptionData);
	nova_standard_exception_NovaDivideByZeroException_Novasuper(this, 0);
	
	{
		nova_standard_exception_NovaDivideByZeroException_Novathis(this, exceptionData);
	}
	
	return this;
}

void nova_del_DivideByZeroException(nova_standard_exception_NovaDivideByZeroException** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

void nova_standard_exception_NovaDivideByZeroException_Novathis(nova_standard_exception_NovaDivideByZeroException* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}

void nova_standard_exception_NovaDivideByZeroException_Novasuper(nova_standard_exception_NovaDivideByZeroException* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}
