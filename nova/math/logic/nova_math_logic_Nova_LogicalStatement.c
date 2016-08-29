#include <precompiled.h>
#include <nova/math/logic/nova_math_logic_Nova_LogicalStatement.h>



nova_math_logic_Extension_VTable_LogicalStatement nova_math_logic_Extension_VTable_LogicalStatement_val =
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
void nova_math_logic_Nova_LogicalStatement_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_math_logic_Nova_LogicalStatement* nova_math_logic_Nova_LogicalStatement_Nova_construct(nova_math_logic_Nova_LogicalStatement* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* nova_math_logic_Nova_LogicalStatement_Nova_statement)
{
	CCLASS_NEW(nova_math_logic_Nova_LogicalStatement, this);
	this->vtable = &nova_math_logic_Extension_VTable_LogicalStatement_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_math_Nova_Statement_Nova_super((nova_math_Nova_Statement*)this, exceptionData);
	nova_math_logic_Nova_LogicalStatement_0_Nova_super(this, exceptionData);
	
	{
		nova_math_logic_Nova_LogicalStatement_0_Nova_this(this, exceptionData, nova_math_logic_Nova_LogicalStatement_Nova_statement);
	}
	
	return this;
}

void nova_math_logic_Nova_LogicalStatement_Nova_destroy(nova_math_logic_Nova_LogicalStatement** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_Nova_String_Nova_destroy(&(*this)->prv->nova_math_logic_Nova_LogicalStatement_Nova_statement, exceptionData);
	nova_datastruct_list_Nova_Array_Nova_destroy(&(*this)->prv->nova_math_logic_Nova_LogicalStatement_Nova_components, exceptionData);
	NOVA_FREE((*this)->prv);
	
	NOVA_FREE(*this);
}

void nova_math_logic_Nova_LogicalStatement_0_Nova_this(nova_math_logic_Nova_LogicalStatement* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* nova_math_logic_Nova_LogicalStatement_Nova_statement)
{
	this->prv->nova_math_logic_Nova_LogicalStatement_Nova_statement = nova_math_logic_Nova_LogicalStatement_Nova_statement;
	this->prv->nova_math_logic_Nova_LogicalStatement_Nova_components = nova_datastruct_list_Nova_Array_0_Nova_construct(0, exceptionData);
}

nova_Nova_String* nova_math_logic_Nova_LogicalStatement_0_Nova_toString(nova_math_logic_Nova_LogicalStatement* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	return this->prv->nova_math_logic_Nova_LogicalStatement_Nova_statement;
}

void nova_math_logic_Nova_LogicalStatement_0_Nova_super(nova_math_logic_Nova_LogicalStatement* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	this->prv->nova_math_logic_Nova_LogicalStatement_Nova_statement = (nova_Nova_String*)nova_null;
	this->prv->nova_math_logic_Nova_LogicalStatement_Nova_components = (nova_datastruct_list_Nova_Array*)nova_null;
}

