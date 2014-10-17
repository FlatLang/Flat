#include <precompiled.h>
#include <nova/standard/logic/nova_standard_logic_NovaStatementLetter.h>


nova_VTable_nova_standard_logic_NovaStatementLetter nova_VTable_nova_standard_logic_NovaStatementLetter_val =
{
	nova_standard_NovaObject_Nova0_getHashCodeLong,
	nova_standard_NovaObject_Nova0_toString,
	nova_standard_NovaObject_Nova0_equals,
};
CCLASS_PRIVATE
(
	nova_standard_NovaString* nova_standard_logic_NovaStatementLetter_Novaletter;
	nova_standard_NovaString* nova_standard_logic_NovaStatementLetter_Novarepresentation;
	
)
void nova_standard_logic_NovaStatementLetterNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
	{
	}
}

nova_standard_logic_NovaStatementLetter* nova_standard_logic_NovaStatementLetter_Novaconstruct(nova_standard_logic_NovaStatementLetter* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novaletter, nova_standard_NovaString* l0_Novarepresentation)
{
	CCLASS_NEW(nova_standard_logic_NovaStatementLetter, this);
	this->vtable = &nova_VTable_nova_standard_logic_NovaStatementLetter_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, exceptionData);
	nova_standard_logic_NovaStatementComponent_Novasuper((nova_standard_logic_NovaStatementComponent*)this, exceptionData);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_logic_NovaStatementComponent_Novathis((nova_standard_logic_NovaStatementComponent*)(this), exceptionData);
	nova_standard_logic_NovaStatementLetter_Novasuper(this, exceptionData);
	
	{
		nova_standard_logic_NovaStatementLetter_Novathis(this, exceptionData, l0_Novaletter, l0_Novarepresentation);
	}
	
	return this;
}

void nova_del_StatementLetter(nova_standard_logic_NovaStatementLetter** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_del_String(&(*this)->prv->nova_standard_logic_NovaStatementLetter_Novaletter, exceptionData);
	nova_del_String(&(*this)->prv->nova_standard_logic_NovaStatementLetter_Novarepresentation, exceptionData);
	NOVA_FREE((*this)->prv);
	
	{
	}
	NOVA_FREE(*this);
}

void nova_standard_logic_NovaStatementLetter_Novathis(nova_standard_logic_NovaStatementLetter* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novaletter, nova_standard_NovaString* l0_Novarepresentation)
{
	this->prv->nova_standard_logic_NovaStatementLetter_Novaletter = l0_Novaletter;
	this->prv->nova_standard_logic_NovaStatementLetter_Novarepresentation = l0_Novarepresentation;
}

void nova_standard_logic_NovaStatementLetter_Novasuper(nova_standard_logic_NovaStatementLetter* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	this->prv->nova_standard_logic_NovaStatementLetter_Novaletter = (nova_standard_NovaString*)nova_null;
	this->prv->nova_standard_logic_NovaStatementLetter_Novarepresentation = (nova_standard_NovaString*)nova_null;
}
