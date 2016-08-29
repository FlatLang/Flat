#include <precompiled.h>
#include <nova/math/logic/nova_math_logic_Nova_StatementGroup.h>



nova_math_logic_Extension_VTable_StatementGroup nova_math_logic_Extension_VTable_StatementGroup_val =
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
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		(char(*)(nova_operators_Nova_Equals*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*))nova_Nova_Object_0_Nova_equals,
		0,
		0,
		0,
	},
	nova_Nova_Object_0_Nova_toString,
	nova_Nova_Object_0_Nova_equals,
	nova_Nova_Object_Accessor_Nova_hashCodeLong,
};


void nova_math_logic_Nova_StatementGroup_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_math_logic_Nova_StatementGroup* nova_math_logic_Nova_StatementGroup_Nova_construct(nova_math_logic_Nova_StatementGroup* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_Object* nova_math_logic_Nova_StatementGroup_Nova_statement, nova_datastruct_Nova_Bounds* nova_math_logic_Nova_StatementGroup_Nova_bounds)
{
	CCLASS_NEW(nova_math_logic_Nova_StatementGroup, this,);
	this->vtable = &nova_math_logic_Extension_VTable_StatementGroup_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_math_logic_Nova_StatementGroup_Nova_super(this, exceptionData);
	
	{
		nova_math_logic_Nova_StatementGroup_Nova_this(this, exceptionData, nova_math_logic_Nova_StatementGroup_Nova_statement, nova_math_logic_Nova_StatementGroup_Nova_bounds);
	}
	
	return this;
}

void nova_math_logic_Nova_StatementGroup_Nova_destroy(nova_math_logic_Nova_StatementGroup** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	nova_datastruct_Nova_Bounds_Nova_destroy(&(*this)->nova_math_logic_Nova_StatementGroup_Nova_bounds, exceptionData);
	
	NOVA_FREE(*this);
}

void nova_math_logic_Nova_StatementGroup_Nova_this(nova_math_logic_Nova_StatementGroup* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_Object* nova_math_logic_Nova_StatementGroup_Nova_statement, nova_datastruct_Nova_Bounds* nova_math_logic_Nova_StatementGroup_Nova_bounds)
{
	this->nova_math_logic_Nova_StatementGroup_Nova_statement = nova_math_logic_Nova_StatementGroup_Nova_statement;
	this->nova_math_logic_Nova_StatementGroup_Nova_bounds = nova_math_logic_Nova_StatementGroup_Nova_bounds;
}

void nova_math_logic_Nova_StatementGroup_Nova_super(nova_math_logic_Nova_StatementGroup* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_math_logic_Nova_StatementGroup_Nova_statement = (nova_Nova_Object*)nova_null;
	this->nova_math_logic_Nova_StatementGroup_Nova_bounds = (nova_datastruct_Nova_Bounds*)nova_null;
}

