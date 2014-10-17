#include <precompiled.h>
#include <nova/standard/logic/nova_standard_logic_NovaStatementComponent.h>


nova_VTable_nova_standard_logic_NovaStatementComponent nova_VTable_nova_standard_logic_NovaStatementComponent_val =
{
	nova_standard_NovaObject_Nova0_getHashCodeLong,
	nova_standard_NovaObject_Nova0_toString,
	nova_standard_NovaObject_Nova0_equals,
};
void nova_standard_logic_NovaStatementComponentNova_init_static(nova_standard_exception_NovaExceptionData* exceptionData)
{
	{
	}
}

nova_standard_logic_NovaStatementComponent* nova_standard_logic_NovaStatementComponent_Nova0_construct(nova_standard_logic_NovaStatementComponent* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_logic_NovaStatementComponent, this,);
	this->vtable = &nova_VTable_nova_standard_logic_NovaStatementComponent_val;
	nova_standard_NovaObject_Novasuper((nova_standard_NovaObject*)this, exceptionData);
	nova_standard_NovaObject_Novathis((nova_standard_NovaObject*)(this), exceptionData);
	nova_standard_logic_NovaStatementComponent_Novasuper(this, exceptionData);
	
	{
		nova_standard_logic_NovaStatementComponent_Novathis(this, exceptionData);
	}
	
	return this;
}

void nova_del_StatementComponent(nova_standard_logic_NovaStatementComponent** this, nova_standard_exception_NovaExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

void nova_standard_logic_NovaStatementComponent_Novathis(nova_standard_logic_NovaStatementComponent* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}

void nova_standard_logic_NovaStatementComponent_Novasuper(nova_standard_logic_NovaStatementComponent* this, nova_standard_exception_NovaExceptionData* exceptionData)
{
}
