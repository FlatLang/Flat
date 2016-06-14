#include <precompiled.h>
#include <nova/standard/math/nova_standard_math_Nova_NumericTree.h>

nova_standard_math_Extension_VTable_NumericTree nova_standard_math_Extension_VTable_NumericTree_val =
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
		(char(*)(nova_standard_operators_Nova_Equals*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*))nova_standard_Nova_Object_0_Nova_equals,
		0,
	},
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_math_Nova_NumericTree_1_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
};


CCLASS_PRIVATE
(
	nova_standard_math_Nova_NumericOperation* nova_standard_math_Nova_NumericTree_Nova_root;
	
)
void nova_standard_math_Nova_NumericTreeNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_math_Nova_NumericTree* nova_standard_math_Nova_NumericTree_Nova_construct(nova_standard_math_Nova_NumericTree* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* nova_standard_math_Nova_NumericTree_Nova_statement)
{
	CCLASS_NEW(nova_standard_math_Nova_NumericTree, this);
	this->vtable = &nova_standard_math_Extension_VTable_NumericTree_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_math_Nova_NumericTree_Nova_super(this, exceptionData);
	
	{
		nova_standard_math_Nova_NumericTree_Nova_this(this, exceptionData, nova_standard_math_Nova_NumericTree_Nova_statement);
	}
	
	return this;
}

void nova_standard_math_Nova_NumericTree_Nova_destroy(nova_standard_math_Nova_NumericTree** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_standard_math_Nova_NumericOperation_Nova_destroy(&(*this)->prv->nova_standard_math_Nova_NumericTree_Nova_root, exceptionData);
	NOVA_FREE((*this)->prv);
	
	{
	}
	NOVA_FREE(*this);
}

void nova_standard_math_Nova_NumericTree_Nova_this(nova_standard_math_Nova_NumericTree* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* nova_standard_math_Nova_NumericTree_Nova_statement)
{
	this->prv->nova_standard_math_Nova_NumericTree_Nova_root = nova_standard_math_Nova_NumericOperation_Nova_construct(0, exceptionData, nova_standard_math_Nova_NumericTree_Nova_statement);
}

nova_standard_Nova_String* nova_standard_math_Nova_NumericTree_1_Nova_toString(nova_standard_math_Nova_NumericTree* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return this->prv->nova_standard_math_Nova_NumericTree_Nova_root->vtable->nova_standard_math_Nova_NumericOperation_virtual1_Nova_toString(this->prv->nova_standard_math_Nova_NumericTree_Nova_root, exceptionData);
}

void nova_standard_math_Nova_NumericTree_Nova_super(nova_standard_math_Nova_NumericTree* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->prv->nova_standard_math_Nova_NumericTree_Nova_root = (nova_standard_math_Nova_NumericOperation*)nova_null;
}

