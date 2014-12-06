#include <precompiled.h>
#include <nova/standard/math/logic/nova_standard_math_logic_Nova_LogicalConnective.h>


nova_VTable_nova_standard_math_logic_Nova_LogicalConnective nova_VTable_nova_standard_math_logic_Nova_LogicalConnective_val =
{
	nova_standard_Nova_Object_1_Nova_getHashCodeLong,
	nova_standard_Nova_Object_0_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
};
void nova_standard_math_logic_Nova_LogicalConnectiveNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_math_logic_Nova_LogicalConnective* nova_standard_math_logic_Nova_LogicalConnective_Nova_construct(nova_standard_math_logic_Nova_LogicalConnective* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_math_logic_Nova_LogicalConnective, this,);
	this->vtable = &nova_VTable_nova_standard_math_logic_Nova_LogicalConnective_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_math_logic_Nova_StatementComponent_Nova_super((nova_standard_math_logic_Nova_StatementComponent*)this, exceptionData);
	nova_standard_Nova_Object_Nova_this((nova_standard_Nova_Object*)(this), exceptionData);
	nova_standard_math_logic_Nova_StatementComponent_Nova_this((nova_standard_math_logic_Nova_StatementComponent*)(this), exceptionData);
	nova_standard_math_logic_Nova_LogicalConnective_Nova_super(this, exceptionData);
	
	{
		nova_standard_math_logic_Nova_LogicalConnective_Nova_this(this, exceptionData);
	}
	
	return this;
}

void nova_standard_math_logic_Nova_LogicalConnective_Nova_destroy(nova_standard_math_logic_Nova_LogicalConnective** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

void nova_standard_math_logic_Nova_LogicalConnective_Nova_this(nova_standard_math_logic_Nova_LogicalConnective* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

void nova_standard_math_logic_Nova_LogicalConnective_Nova_super(nova_standard_math_logic_Nova_LogicalConnective* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

