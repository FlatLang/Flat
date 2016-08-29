#include <precompiled.h>
#include <nova/math/nova_math_Nova_NumericStatement.h>



nova_math_Extension_VTable_NumericStatement nova_math_Extension_VTable_NumericStatement_val =
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
	nova_math_Nova_NumericStatement_0_Nova_toString,
	nova_Nova_Object_0_Nova_equals,
	nova_Nova_Object_Accessor_Nova_hashCodeLong,
};


CCLASS_PRIVATE
(
	nova_math_Nova_NumericTree* nova_math_Nova_NumericStatement_Nova_tree;
	
)
void nova_math_Nova_NumericStatement_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_math_Nova_NumericStatement* nova_math_Nova_NumericStatement_Nova_construct(nova_math_Nova_NumericStatement* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* nova_math_Nova_NumericStatement_Nova_statement)
{
	CCLASS_NEW(nova_math_Nova_NumericStatement, this);
	this->vtable = &nova_math_Extension_VTable_NumericStatement_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_math_Nova_Statement_Nova_super((nova_math_Nova_Statement*)this, exceptionData);
	nova_math_Nova_NumericStatement_0_Nova_super(this, exceptionData);
	
	{
		nova_math_Nova_NumericStatement_1_Nova_this(this, exceptionData, nova_math_Nova_NumericStatement_Nova_statement);
	}
	
	return this;
}

void nova_math_Nova_NumericStatement_Nova_destroy(nova_math_Nova_NumericStatement** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_math_Nova_NumericTree_Nova_destroy(&(*this)->prv->nova_math_Nova_NumericStatement_Nova_tree, exceptionData);
	NOVA_FREE((*this)->prv);
	
	NOVA_FREE(*this);
}

void nova_math_Nova_NumericStatement_1_Nova_this(nova_math_Nova_NumericStatement* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* nova_math_Nova_NumericStatement_Nova_statement)
{
	this->prv->nova_math_Nova_NumericStatement_Nova_tree = nova_math_Nova_NumericTree_Nova_construct(0, exceptionData, nova_Nova_String_Nova_trim(nova_math_Nova_NumericStatement_Nova_statement, exceptionData));
}

nova_Nova_String* nova_math_Nova_NumericStatement_0_Nova_toString(nova_math_Nova_NumericStatement* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	return nova_Nova_Object_virtual1_Nova_toString((nova_Nova_Object*)(this->prv->nova_math_Nova_NumericStatement_Nova_tree), exceptionData);
}

void nova_math_Nova_NumericStatement_0_Nova_super(nova_math_Nova_NumericStatement* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	this->prv->nova_math_Nova_NumericStatement_Nova_tree = (nova_math_Nova_NumericTree*)nova_null;
}

