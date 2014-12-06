#include <precompiled.h>
#include <nova/standard/math/nova_standard_math_Nova_NumericOperand.h>


nova_VTable_nova_standard_math_Nova_NumericOperand nova_VTable_nova_standard_math_Nova_NumericOperand_val =
{
	nova_standard_Nova_Object_1_Nova_getHashCodeLong,
	nova_standard_math_Nova_NumericOperand_0_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
};
CCLASS_PRIVATE
(
	nova_standard_Nova_Object* nova_standard_math_Nova_NumericOperand_Nova_data;
	
)
void nova_standard_math_Nova_NumericOperandNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_math_Nova_NumericOperand* nova_standard_math_Nova_NumericOperand_2_Nova_construct(nova_standard_math_Nova_NumericOperand* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_Object* l0_Nova_data)
{
	CCLASS_NEW(nova_standard_math_Nova_NumericOperand, this);
	this->vtable = &nova_VTable_nova_standard_math_Nova_NumericOperand_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_Nova_Object_Nova_this((nova_standard_Nova_Object*)(this), exceptionData);
	nova_standard_math_Nova_NumericOperand_Nova_super(this, exceptionData);
	
	{
		nova_standard_math_Nova_NumericOperand_Nova_this(this, exceptionData, l0_Nova_data);
	}
	
	return this;
}

void nova_standard_math_Nova_NumericOperand_Nova_destroy(nova_standard_math_Nova_NumericOperand** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE((*this)->prv);
	
	{
	}
	NOVA_FREE(*this);
}

void nova_standard_math_Nova_NumericOperand_Nova_this(nova_standard_math_Nova_NumericOperand* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_Object* l0_Nova_data)
{
	this->prv->nova_standard_math_Nova_NumericOperand_Nova_data = l0_Nova_data;
}

nova_standard_Nova_String* nova_standard_math_Nova_NumericOperand_0_Nova_toString(nova_standard_math_Nova_NumericOperand* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return this->prv->nova_standard_math_Nova_NumericOperand_Nova_data->vtable->nova_standard_Nova_Object_virtual0_Nova_toString((nova_standard_Nova_Object*)(this->prv->nova_standard_math_Nova_NumericOperand_Nova_data), exceptionData);
}

void nova_standard_math_Nova_NumericOperand_Nova_super(nova_standard_math_Nova_NumericOperand* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->prv->nova_standard_math_Nova_NumericOperand_Nova_data = (nova_standard_Nova_Object*)nova_null;
}

