#include <precompiled.h>
#include <nova/standard/logic/nova_standard_logic_NovaStatementGroup.h>


nova_VTable_nova_standard_logic_NovaStatementGroup nova_VTable_nova_standard_logic_NovaStatementGroup_val =
{
	nova_standard_NovaObject_Nova0_toString,
	nova_standard_NovaObject_Nova0_equals,
};
void nova_standard_logic_NovaStatementGroupNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
	{
	}
}

nova_standard_logic_NovaStatementGroup* nova_standard_logic_NovaStatementGroup_Novaconstruct(nova_standard_logic_NovaStatementGroup* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaObject* l0_Novastatement, nova_standard_datastruct_NovaBounds* l0_Novabounds)
{
	CCLASS_NEW(nova_standard_logic_NovaStatementGroup, this,);
	this->vtable = &nova_VTable_nova_standard_logic_NovaStatementGroup_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_logic_NovaStatementGroup_Novasuper(this, 0);
	
	{
		nova_standard_logic_NovaStatementGroup_Novathis(this, exceptionData, l0_Novastatement, l0_Novabounds);
	}
	
	return this;
}

void nova_del_StatementGroup(nova_standard_logic_NovaStatementGroup** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	nova_del_Bounds(&(*this)->nova_standard_logic_NovaStatementGroup_Novabounds, exceptionData);
	
	{
	}
	NOVA_FREE(*this);
}

void nova_standard_logic_NovaStatementGroup_Novathis(nova_standard_logic_NovaStatementGroup* this, nova_standard_exception_NovaExceptionData* exceptionData, nova_standard_NovaObject* l0_Novastatement, nova_standard_datastruct_NovaBounds* l0_Novabounds)
{
	this->nova_standard_logic_NovaStatementGroup_Novastatement = l0_Novastatement;
	this->nova_standard_logic_NovaStatementGroup_Novabounds = l0_Novabounds;
}

void nova_standard_logic_NovaStatementGroup_Novasuper(nova_standard_logic_NovaStatementGroup* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	this->nova_standard_logic_NovaStatementGroup_Novastatement = (nova_standard_NovaObject*)nova_null;
	this->nova_standard_logic_NovaStatementGroup_Novabounds = (nova_standard_datastruct_NovaBounds*)nova_null;
}
