#include <precompiled.h>
#include <nova/math/logic/nova_math_logic_Nova_Hypothesis.h>



nova_math_logic_Extension_VTable_Hypothesis nova_math_logic_Extension_VTable_Hypothesis_val =
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
	nova_math_logic_Nova_LogicalStatement_0_Nova_toString,
	nova_Nova_Object_0_Nova_equals,
	nova_Nova_Object_Accessor_Nova_hashCodeLong,
};


CCLASS_PRIVATE
(
	nova_Nova_String* nova_math_logic_Nova_LogicalStatement_Nova_statement;
	nova_datastruct_list_Nova_Array* nova_math_logic_Nova_LogicalStatement_Nova_components;
	
)
void nova_math_logic_Nova_Hypothesis_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_math_logic_Nova_Hypothesis* nova_math_logic_Nova_Hypothesis_Nova_construct(nova_math_logic_Nova_Hypothesis* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* nova_math_logic_Nova_Hypothesis_Nova_statement)
{
	CCLASS_NEW(nova_math_logic_Nova_Hypothesis, this);
	this->vtable = &nova_math_logic_Extension_VTable_Hypothesis_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_math_Nova_Statement_Nova_super((nova_math_Nova_Statement*)this, exceptionData);
	nova_math_logic_Nova_LogicalStatement_0_Nova_super((nova_math_logic_Nova_LogicalStatement*)this, exceptionData);
	nova_math_logic_Nova_Hypothesis_2_Nova_super(this, exceptionData);
	
	{
		nova_math_logic_Nova_Hypothesis_0_Nova_this(this, exceptionData, nova_math_logic_Nova_Hypothesis_Nova_statement);
	}
	
	return this;
}

void nova_math_logic_Nova_Hypothesis_Nova_destroy(nova_math_logic_Nova_Hypothesis** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	NOVA_FREE((*this)->prv);
	
	NOVA_FREE(*this);
}

void nova_math_logic_Nova_Hypothesis_0_Nova_this(nova_math_logic_Nova_Hypothesis* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* nova_math_logic_Nova_Hypothesis_Nova_statement)
{
	nova_math_logic_Nova_LogicalStatement_0_Nova_this((nova_math_logic_Nova_LogicalStatement*)(this), exceptionData, nova_math_logic_Nova_Hypothesis_Nova_statement);
}

void nova_math_logic_Nova_Hypothesis_2_Nova_super(nova_math_logic_Nova_Hypothesis* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

