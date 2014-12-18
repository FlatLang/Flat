#include <precompiled.h>
#include <nova/standard/math/nova_standard_math_Nova_StatementComponent.h>


nova_VTable_nova_standard_math_Nova_StatementComponent nova_VTable_nova_standard_math_Nova_StatementComponent_val =
{
	nova_standard_Nova_Object_1_Nova_getHashCodeLong,
	nova_standard_math_Nova_StatementComponent_0_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
};
void nova_standard_math_Nova_StatementComponentNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_math_Nova_StatementComponent* nova_standard_math_Nova_StatementComponent_Nova_construct(nova_standard_math_Nova_StatementComponent* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_Object* l0_Nova_data)
{
	CCLASS_NEW(nova_standard_math_Nova_StatementComponent, this,);
	this->vtable = &nova_VTable_nova_standard_math_Nova_StatementComponent_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_Nova_Object_2_Nova_this((nova_standard_Nova_Object*)(this), exceptionData);
	nova_standard_math_Nova_StatementComponent_Nova_super(this, exceptionData);
	
	{
		nova_standard_math_Nova_StatementComponent_Nova_this(this, exceptionData, l0_Nova_data);
	}
	
	return this;
}

void nova_standard_math_Nova_StatementComponent_Nova_destroy(nova_standard_math_Nova_StatementComponent** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	nova_standard_math_Nova_StatementComponent_Nova_destroy(&(*this)->nova_standard_math_Nova_StatementComponent_Nova_next, exceptionData);
	
	NOVA_FREE(*this);
}

void nova_standard_math_Nova_StatementComponent_Nova_this(nova_standard_math_Nova_StatementComponent* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_Object* l0_Nova_data)
{
	this->nova_standard_math_Nova_StatementComponent_Nova_data = l0_Nova_data;
}

nova_standard_Nova_String* nova_standard_math_Nova_StatementComponent_0_Nova_toString(nova_standard_math_Nova_StatementComponent* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return this->nova_standard_math_Nova_StatementComponent_Nova_data->vtable->nova_standard_Nova_Object_virtual0_Nova_toString(this->nova_standard_math_Nova_StatementComponent_Nova_data, exceptionData);
}

void nova_standard_math_Nova_StatementComponent_Nova_super(nova_standard_math_Nova_StatementComponent* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_standard_math_Nova_StatementComponent_Nova_data = (nova_standard_Nova_Object*)nova_null;
	this->nova_standard_math_Nova_StatementComponent_Nova_next = (nova_standard_math_Nova_StatementComponent*)nova_null;
}

