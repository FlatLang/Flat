#include <precompiled.h>
#include <bank/bank_NovaBankQueryException.h>


nova_VTable_bank_NovaBankQueryException nova_VTable_bank_NovaBankQueryException_val =
{
	nova_standard_NovaObject_Nova0_getHashCodeLong,
	nova_standard_NovaObject_Nova0_toString,
	nova_standard_NovaObject_Nova0_equals,
};
void bank_NovaBankQueryExceptionNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
	{
	}
}

bank_NovaBankQueryException* bank_NovaBankQueryException_Nova1_construct(bank_NovaBankQueryException* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novamessage)
{
	CCLASS_NEW(bank_NovaBankQueryException, this,);
	this->vtable = &nova_VTable_bank_NovaBankQueryException_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, exceptionData);
	nova_standard_exception_NovaException_Novasuper((nova_standard_exception_NovaException*)this, exceptionData);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_exception_NovaException_Nova0_this((nova_standard_exception_NovaException*)(this), exceptionData, l0_Novamessage);
	bank_NovaBankQueryException_Novasuper(this, exceptionData);
	
	{
		bank_NovaBankQueryException_Nova0_this(this, exceptionData, l0_Novamessage);
	}
	
	return this;
}

void nova_del_BankQueryException(bank_NovaBankQueryException** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

void bank_NovaBankQueryException_Nova0_this(bank_NovaBankQueryException* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novamessage)
{
	nova_standard_exception_NovaException_Nova0_this((nova_standard_exception_NovaException*)(this), exceptionData, l0_Novamessage);
}

void bank_NovaBankQueryException_Novasuper(bank_NovaBankQueryException* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}
