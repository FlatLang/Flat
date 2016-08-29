#include <precompiled.h>
#include <nova/math/nova_math_Nova_NumericOperand.h>



nova_math_Extension_VTable_NumericOperand nova_math_Extension_VTable_NumericOperand_val =
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
	nova_math_Nova_NumericOperand_0_Nova_toString,
	nova_Nova_Object_0_Nova_equals,
	nova_Nova_Object_Accessor_Nova_hashCodeLong,
};


CCLASS_PRIVATE
(
	nova_Nova_Object* nova_math_Nova_NumericOperand_Nova_data;
	
)
void nova_math_Nova_NumericOperand_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_math_Nova_NumericOperand* nova_math_Nova_NumericOperand_Nova_construct(nova_math_Nova_NumericOperand* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_Object* nova_math_Nova_NumericOperand_Nova_data)
{
	CCLASS_NEW(nova_math_Nova_NumericOperand, this);
	this->vtable = &nova_math_Extension_VTable_NumericOperand_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_math_Nova_NumericOperand_Nova_super(this, exceptionData);
	
	{
		nova_math_Nova_NumericOperand_2_Nova_this(this, exceptionData, nova_math_Nova_NumericOperand_Nova_data);
	}
	
	return this;
}

void nova_math_Nova_NumericOperand_Nova_destroy(nova_math_Nova_NumericOperand** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE((*this)->prv);
	
	NOVA_FREE(*this);
}

void nova_math_Nova_NumericOperand_2_Nova_this(nova_math_Nova_NumericOperand* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_Object* nova_math_Nova_NumericOperand_Nova_data)
{
	this->prv->nova_math_Nova_NumericOperand_Nova_data = nova_math_Nova_NumericOperand_Nova_data;
}

nova_Nova_String* nova_math_Nova_NumericOperand_0_Nova_toString(nova_math_Nova_NumericOperand* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	return (nova_Nova_String*)(nova_Nova_Object_virtual1_Nova_toString((nova_Nova_Object*)(this->prv->nova_math_Nova_NumericOperand_Nova_data), exceptionData));
}

void nova_math_Nova_NumericOperand_Nova_super(nova_math_Nova_NumericOperand* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	this->prv->nova_math_Nova_NumericOperand_Nova_data = (nova_Nova_Object*)nova_null;
}

