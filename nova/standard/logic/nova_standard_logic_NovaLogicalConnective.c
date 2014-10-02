#include <precompiled.h>
#include <nova/standard/logic/nova_standard_logic_NovaLogicalConnective.h>


nova_VTable_nova_standard_logic_NovaLogicalConnective nova_VTable_nova_standard_logic_NovaLogicalConnective_val =
{
	nova_standard_NovaObject_Nova0_toString,
	nova_standard_NovaObject_Nova0_equals,
};
void nova_standard_logic_NovaLogicalConnectiveNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
	{
	}
}

nova_standard_logic_NovaLogicalConnective* nova_standard_logic_NovaLogicalConnective_Nova0_construct(nova_standard_logic_NovaLogicalConnective* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_logic_NovaLogicalConnective, this,);
	this->vtable = &nova_VTable_nova_standard_logic_NovaLogicalConnective_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, 0);
	nova_standard_logic_NovaStatementComponent_Novasuper((nova_standard_logic_NovaStatementComponent*)this, 0);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_logic_NovaStatementComponent_Novathis((nova_standard_logic_NovaStatementComponent*)(this), exceptionData);
	nova_standard_logic_NovaLogicalConnective_Novasuper(this, 0);
	
	{
		nova_standard_logic_NovaLogicalConnective_Novathis(this, exceptionData);
	}
	
	return this;
}

void nova_del_LogicalConnective(nova_standard_logic_NovaLogicalConnective** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

void nova_standard_logic_NovaLogicalConnective_Novathis(nova_standard_logic_NovaLogicalConnective* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}

void nova_standard_logic_NovaLogicalConnective_Novasuper(nova_standard_logic_NovaLogicalConnective* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}
