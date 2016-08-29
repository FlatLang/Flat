#include <precompiled.h>
#include <nova/math/nova_math_Nova_NumericTree.h>



nova_math_Extension_VTable_NumericTree nova_math_Extension_VTable_NumericTree_val =
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
	nova_math_Nova_NumericTree_0_Nova_toString,
	nova_Nova_Object_0_Nova_equals,
	nova_Nova_Object_Accessor_Nova_hashCodeLong,
};


CCLASS_PRIVATE
(
	nova_math_Nova_NumericOperation* nova_math_Nova_NumericTree_Nova_root;
	
)
void nova_math_Nova_NumericTree_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_math_Nova_NumericTree* nova_math_Nova_NumericTree_Nova_construct(nova_math_Nova_NumericTree* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* nova_math_Nova_NumericTree_Nova_statement)
{
	CCLASS_NEW(nova_math_Nova_NumericTree, this);
	this->vtable = &nova_math_Extension_VTable_NumericTree_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_math_Nova_NumericTree_Nova_super(this, exceptionData);
	
	{
		nova_math_Nova_NumericTree_Nova_this(this, exceptionData, nova_math_Nova_NumericTree_Nova_statement);
	}
	
	return this;
}

void nova_math_Nova_NumericTree_Nova_destroy(nova_math_Nova_NumericTree** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_math_Nova_NumericOperation_Nova_destroy(&(*this)->prv->nova_math_Nova_NumericTree_Nova_root, exceptionData);
	NOVA_FREE((*this)->prv);
	
	NOVA_FREE(*this);
}

void nova_math_Nova_NumericTree_Nova_this(nova_math_Nova_NumericTree* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* nova_math_Nova_NumericTree_Nova_statement)
{
	this->prv->nova_math_Nova_NumericTree_Nova_root = nova_math_Nova_NumericOperation_0_Nova_construct(0, exceptionData, nova_math_Nova_NumericTree_Nova_statement);
}

nova_Nova_String* nova_math_Nova_NumericTree_0_Nova_toString(nova_math_Nova_NumericTree* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	return nova_Nova_Object_virtual1_Nova_toString((nova_Nova_Object*)(this->prv->nova_math_Nova_NumericTree_Nova_root), exceptionData);
}

void nova_math_Nova_NumericTree_Nova_super(nova_math_Nova_NumericTree* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	this->prv->nova_math_Nova_NumericTree_Nova_root = (nova_math_Nova_NumericOperation*)nova_null;
}

