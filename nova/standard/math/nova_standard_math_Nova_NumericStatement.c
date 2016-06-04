#include <precompiled.h>
#include <nova/standard/math/nova_standard_math_Nova_NumericStatement.h>

nova_standard_math_Extension_VTable_NumericStatement nova_standard_math_Extension_VTable_NumericStatement_val =
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
	},
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_math_Nova_NumericStatement_0_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
};


CCLASS_PRIVATE
(
	nova_standard_math_Nova_NumericTree* nova_standard_math_Nova_NumericStatement_Nova_tree;
	
)
void nova_standard_math_Nova_NumericStatementNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_math_Nova_NumericStatement* nova_standard_math_Nova_NumericStatement_Nova_construct(nova_standard_math_Nova_NumericStatement* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* nova_standard_math_Nova_NumericStatement_Nova_statement)
{
	CCLASS_NEW(nova_standard_math_Nova_NumericStatement, this);
	this->vtable = &nova_standard_math_Extension_VTable_NumericStatement_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_math_Nova_Statement_Nova_super((nova_standard_math_Nova_Statement*)this, exceptionData);
	nova_standard_Nova_Object_2_Nova_this((nova_standard_Nova_Object*)(this), exceptionData);
	nova_standard_math_Nova_Statement_2_Nova_this((nova_standard_math_Nova_Statement*)(this), exceptionData);
	nova_standard_math_Nova_NumericStatement_2_Nova_super(this, exceptionData);
	
	{
		nova_standard_math_Nova_NumericStatement_Nova_this(this, exceptionData, nova_standard_math_Nova_NumericStatement_Nova_statement);
	}
	
	return this;
}

void nova_standard_math_Nova_NumericStatement_Nova_destroy(nova_standard_math_Nova_NumericStatement** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_standard_math_Nova_NumericTree_Nova_destroy(&(*this)->prv->nova_standard_math_Nova_NumericStatement_Nova_tree, exceptionData);
	NOVA_FREE((*this)->prv);
	
	NOVA_FREE(*this);
}

void nova_standard_math_Nova_NumericStatement_Nova_this(nova_standard_math_Nova_NumericStatement* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* nova_standard_math_Nova_NumericStatement_Nova_statement)
{
	this->prv->nova_standard_math_Nova_NumericStatement_Nova_tree = nova_standard_math_Nova_NumericTree_Nova_construct(0, exceptionData, nova_standard_Nova_String_Nova_trim(nova_standard_math_Nova_NumericStatement_Nova_statement, exceptionData));
}

nova_standard_Nova_String* nova_standard_math_Nova_NumericStatement_0_Nova_toString(nova_standard_math_Nova_NumericStatement* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return this->prv->nova_standard_math_Nova_NumericStatement_Nova_tree->vtable->nova_standard_math_Nova_NumericTree_virtual0_Nova_toString(this->prv->nova_standard_math_Nova_NumericStatement_Nova_tree, exceptionData);
}

void nova_standard_math_Nova_NumericStatement_2_Nova_super(nova_standard_math_Nova_NumericStatement* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->prv->nova_standard_math_Nova_NumericStatement_Nova_tree = (nova_standard_math_Nova_NumericTree*)nova_null;
}

