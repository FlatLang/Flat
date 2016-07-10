#include <precompiled.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_Nova_Number.h>

nova_standard_primitive_number_Extension_VTable_Number nova_standard_primitive_number_Extension_VTable_Number_val =
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
		0,
		(char(*)(nova_standard_operators_Nova_Equals*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*))nova_standard_Nova_Object_0_Nova_equals,
		0,
		0,
		0,
	},
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_Nova_Object_1_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
	nova_standard_primitive_number_Nova_Number_0_Nova_numDigits,
};


void nova_standard_primitive_number_Nova_NumberNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_primitive_number_Nova_Number* nova_standard_primitive_number_Nova_Number_Nova_Number(nova_standard_primitive_number_Nova_Number* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(nova_standard_primitive_number_Nova_Number, this,);
	this->vtable = &nova_standard_primitive_number_Extension_VTable_Number_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_primitive_Nova_Primitive_Nova_super((nova_standard_primitive_Nova_Primitive*)this, exceptionData);
	nova_standard_primitive_number_Nova_Number_0_Nova_super(this, exceptionData);
	
	{
		nova_standard_primitive_number_Nova_Number_4_Nova_this(this, exceptionData);
	}
	
	return this;
}

void nova_standard_primitive_number_Nova_Number_Nova_destroy(nova_standard_primitive_number_Nova_Number** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	NOVA_FREE(*this);
}

int nova_standard_primitive_number_Nova_Number_0_Nova_numDigits(nova_standard_primitive_number_Nova_Number* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_primitive_number_Nova_Number* nova_standard_primitive_number_Nova_Number_Nova_number)
{
	return (int)-1;
}

void nova_standard_primitive_number_Nova_Number_4_Nova_this(nova_standard_primitive_number_Nova_Number* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

void nova_standard_primitive_number_Nova_Number_0_Nova_super(nova_standard_primitive_number_Nova_Number* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

int nova_standard_primitive_number_Nova_Number_virtual0_Nova_numDigits(nova_standard_primitive_number_Nova_Number* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_primitive_number_Nova_Number* nova_standard_primitive_number_Nova_Number_Nova_number)
{
	return this->vtable->nova_standard_primitive_number_Nova_Number_virtual0_Nova_numDigits(0, exceptionData, nova_standard_primitive_number_Nova_Number_Nova_number);
}

