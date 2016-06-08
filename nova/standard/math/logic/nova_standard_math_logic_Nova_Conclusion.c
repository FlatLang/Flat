#include <precompiled.h>
#include <nova/standard/math/logic/nova_standard_math_logic_Nova_Conclusion.h>

nova_standard_math_logic_Extension_VTable_Conclusion nova_standard_math_logic_Extension_VTable_Conclusion_val =
{
	{
		0,
		(char(*)(nova_standard_operators_Nova_Equals*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*))nova_standard_Nova_Object_0_Nova_equals,
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
	},
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_math_logic_Nova_LogicalStatement_0_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
};


CCLASS_PRIVATE
(
	nova_standard_Nova_String* nova_standard_math_logic_Nova_LogicalStatement_Nova_statement;
	nova_standard_datastruct_list_Nova_Array* nova_standard_math_logic_Nova_LogicalStatement_Nova_components;
	
)
void nova_standard_math_logic_Nova_ConclusionNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_math_logic_Nova_Conclusion* nova_standard_math_logic_Nova_Conclusion_Nova_construct(nova_standard_math_logic_Nova_Conclusion* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* nova_standard_math_logic_Nova_Conclusion_Nova_statement)
{
	CCLASS_NEW(nova_standard_math_logic_Nova_Conclusion, this);
	this->vtable = &nova_standard_math_logic_Extension_VTable_Conclusion_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_math_Nova_Statement_Nova_super((nova_standard_math_Nova_Statement*)this, exceptionData);
	nova_standard_math_logic_Nova_LogicalStatement_2_Nova_super((nova_standard_math_logic_Nova_LogicalStatement*)this, exceptionData);
	nova_standard_math_logic_Nova_Conclusion_2_Nova_super(this, exceptionData);
	
	{
		nova_standard_math_logic_Nova_Conclusion_Nova_this(this, exceptionData, nova_standard_math_logic_Nova_Conclusion_Nova_statement);
	}
	
	return this;
}

void nova_standard_math_logic_Nova_Conclusion_Nova_destroy(nova_standard_math_logic_Nova_Conclusion** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	NOVA_FREE((*this)->prv);
	
	NOVA_FREE(*this);
}

void nova_standard_math_logic_Nova_Conclusion_Nova_this(nova_standard_math_logic_Nova_Conclusion* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* nova_standard_math_logic_Nova_Conclusion_Nova_statement)
{
	nova_standard_math_logic_Nova_LogicalStatement_0_Nova_this((nova_standard_math_logic_Nova_LogicalStatement*)(this), exceptionData, nova_standard_math_logic_Nova_Conclusion_Nova_statement);
}

void nova_standard_math_logic_Nova_Conclusion_2_Nova_super(nova_standard_math_logic_Nova_Conclusion* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

