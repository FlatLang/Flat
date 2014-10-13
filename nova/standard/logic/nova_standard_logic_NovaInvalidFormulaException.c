#include <precompiled.h>
#include <nova/standard/logic/nova_standard_logic_NovaInvalidFormulaException.h>


nova_VTable_nova_standard_logic_NovaInvalidFormulaException nova_VTable_nova_standard_logic_NovaInvalidFormulaException_val =
{
	nova_standard_NovaObject_Nova0_getHashCodeLong,
	nova_standard_NovaObject_Nova0_toString,
	nova_standard_NovaObject_Nova0_equals,
};
void nova_standard_logic_NovaInvalidFormulaExceptionNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
	{
	}
}

nova_standard_logic_NovaInvalidFormulaException* nova_standard_logic_NovaInvalidFormulaException_Novaconstruct(nova_standard_logic_NovaInvalidFormulaException* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novamessage)
{
	CCLASS_NEW(nova_standard_logic_NovaInvalidFormulaException, this,);
	this->vtable = &nova_VTable_nova_standard_logic_NovaInvalidFormulaException_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
	nova_standard_exception_NovaException_Novasuper((nova_standard_exception_NovaException*)this, 0);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_exception_NovaException_Nova0_this((nova_standard_exception_NovaException*)(this), exceptionData, l0_Novamessage);
	nova_standard_logic_NovaInvalidFormulaException_Novasuper(this, 0);
	
	{
		nova_standard_logic_NovaInvalidFormulaException_Novathis(this, exceptionData, l0_Novamessage);
	}
	
	return this;
}

void nova_del_InvalidFormulaException(nova_standard_logic_NovaInvalidFormulaException** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

void nova_standard_logic_NovaInvalidFormulaException_Novathis(nova_standard_logic_NovaInvalidFormulaException* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novamessage)
{
	nova_standard_exception_NovaException_Nova0_this((nova_standard_exception_NovaException*)(this), exceptionData, l0_Novamessage);
}

void nova_standard_logic_NovaInvalidFormulaException_Novasuper(nova_standard_logic_NovaInvalidFormulaException* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}
