#include <precompiled.h>
#include <nova/standard/logic/nova_standard_logic_NovaStatement.h>


nova_VTable_nova_standard_logic_NovaStatement nova_VTable_nova_standard_logic_NovaStatement_val =
{
	nova_standard_logic_NovaStatement_Nova0_toString,
	nova_standard_NovaObject_Nova0_equals,
};
CCLASS_PRIVATE
(
	nova_standard_NovaString* nova_standard_logic_NovaStatement_Novastatement;
	nova_standard_datastruct_NovaArrayList* nova_standard_logic_NovaStatement_Novacomponents;
	
)
void nova_standard_logic_NovaStatementNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
	{
	}
}

nova_standard_logic_NovaStatement* nova_standard_logic_NovaStatement_Nova0_construct(nova_standard_logic_NovaStatement* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novastatement)
{
	CCLASS_NEW(nova_standard_logic_NovaStatement, this);
	this->vtable = &nova_VTable_nova_standard_logic_NovaStatement_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_logic_NovaStatement_Novasuper(this, 0);
	
	{
		nova_standard_logic_NovaStatement_Novathis(this, exceptionData, l0_Novastatement);
	}
	
	return this;
}

void nova_del_Statement(nova_standard_logic_NovaStatement** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_del_String(&(*this)->prv->nova_standard_logic_NovaStatement_Novastatement, exceptionData);
	nova_del_ArrayList(&(*this)->prv->nova_standard_logic_NovaStatement_Novacomponents, exceptionData);
	NOVA_FREE((*this)->prv);
	
	{
	}
	NOVA_FREE(*this);
}

void nova_standard_logic_NovaStatement_Novathis(nova_standard_logic_NovaStatement* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaString* l0_Novastatement)
{
	this->prv->nova_standard_logic_NovaStatement_Novastatement = l0_Novastatement;
	this->prv->nova_standard_logic_NovaStatement_Novacomponents = nova_standard_datastruct_NovaArrayList_Nova0_construct(0, exceptionData);
}

nova_standard_NovaString* nova_standard_logic_NovaStatement_Nova0_toString(nova_standard_logic_NovaStatement* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	return this->prv->nova_standard_logic_NovaStatement_Novastatement;
}

void nova_standard_logic_NovaStatement_Novasuper(nova_standard_logic_NovaStatement* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	this->prv->nova_standard_logic_NovaStatement_Novastatement = (nova_standard_NovaString*)nova_null;
	this->prv->nova_standard_logic_NovaStatement_Novacomponents = (nova_standard_datastruct_NovaArrayList*)nova_null;
}
