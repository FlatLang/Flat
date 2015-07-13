#include <precompiled.h>
#include <nova/standard/math/logic/nova_standard_math_logic_Nova_StatementGroup.h>


nova_standard_math_logic_Extension_VTable_StatementGroup nova_standard_math_logic_Extension_VTable_StatementGroup_val =
{
	{
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
	},
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_Nova_Object_0_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
};


void nova_standard_math_logic_Nova_StatementGroupNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_math_logic_Nova_StatementGroup* nova_standard_math_logic_Nova_StatementGroup_Nova_construct(nova_standard_math_logic_Nova_StatementGroup* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_Object* l0_Nova_statement, nova_standard_datastruct_Nova_Bounds* l0_Nova_bounds)
{
	CCLASS_NEW(nova_standard_math_logic_Nova_StatementGroup, this,);
	this->vtable = &nova_standard_math_logic_Extension_VTable_StatementGroup_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_Nova_Object_2_Nova_this((nova_standard_Nova_Object*)(this), exceptionData);
	nova_standard_math_logic_Nova_StatementGroup_Nova_super(this, exceptionData);
	
	{
		nova_standard_math_logic_Nova_StatementGroup_Nova_this(this, exceptionData, l0_Nova_statement, l0_Nova_bounds);
	}
	
	return this;
}

void nova_standard_math_logic_Nova_StatementGroup_Nova_destroy(nova_standard_math_logic_Nova_StatementGroup** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	nova_standard_datastruct_Nova_Bounds_Nova_destroy(&(*this)->nova_standard_math_logic_Nova_StatementGroup_Nova_bounds, exceptionData);
	
	NOVA_FREE(*this);
}

void nova_standard_math_logic_Nova_StatementGroup_Nova_this(nova_standard_math_logic_Nova_StatementGroup* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_Object* l0_Nova_statement, nova_standard_datastruct_Nova_Bounds* l0_Nova_bounds)
{
	this->nova_standard_math_logic_Nova_StatementGroup_Nova_statement = l0_Nova_statement;
	this->nova_standard_math_logic_Nova_StatementGroup_Nova_bounds = l0_Nova_bounds;
}

void nova_standard_math_logic_Nova_StatementGroup_Nova_super(nova_standard_math_logic_Nova_StatementGroup* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_standard_math_logic_Nova_StatementGroup_Nova_statement = (nova_standard_Nova_Object*)nova_null;
	this->nova_standard_math_logic_Nova_StatementGroup_Nova_bounds = (nova_standard_datastruct_Nova_Bounds*)nova_null;
}

