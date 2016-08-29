#include <precompiled.h>
#include <nova/primitive/number/nova_primitive_number_Nova_Number.h>



nova_primitive_number_Extension_VTable_Number nova_primitive_number_Extension_VTable_Number_val =
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
	nova_Nova_Object_0_Nova_toString,
	nova_Nova_Object_0_Nova_equals,
	nova_Nova_Object_Accessor_Nova_hashCodeLong,
	nova_primitive_number_Nova_Number_0_Nova_numDigits,
};


void nova_primitive_number_Nova_Number_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_primitive_number_Nova_Number* nova_primitive_number_Nova_Number_Nova_construct(nova_primitive_number_Nova_Number* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_primitive_number_Nova_Number, this,);
	this->vtable = &nova_primitive_number_Extension_VTable_Number_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_primitive_Nova_Primitive_Nova_super((nova_primitive_Nova_Primitive*)this, exceptionData);
	nova_primitive_number_Nova_Number_0_Nova_super(this, exceptionData);
	
	{
		nova_primitive_number_Nova_Number_4_Nova_this(this, exceptionData);
	}
	
	return this;
}

void nova_primitive_number_Nova_Number_Nova_destroy(nova_primitive_number_Nova_Number** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

int nova_primitive_number_Nova_Number_0_Nova_numDigits(nova_primitive_number_Nova_Number* this, nova_exception_Nova_ExceptionData* exceptionData, nova_primitive_number_Nova_Number* nova_primitive_number_Nova_Number_Nova_number)
{
	return (int)-1;
}

void nova_primitive_number_Nova_Number_4_Nova_this(nova_primitive_number_Nova_Number* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

void nova_primitive_number_Nova_Number_0_Nova_super(nova_primitive_number_Nova_Number* this, nova_exception_Nova_ExceptionData* exceptionData)
{
}

int nova_primitive_number_Nova_Number_virtual0_Nova_numDigits(nova_primitive_number_Nova_Number* this, nova_exception_Nova_ExceptionData* exceptionData, nova_primitive_number_Nova_Number* nova_primitive_number_Nova_Number_Nova_number)
{
	return this->vtable->nova_primitive_number_Nova_Number_virtual0_Nova_numDigits(0, exceptionData, nova_primitive_number_Nova_Number_Nova_number);
}

