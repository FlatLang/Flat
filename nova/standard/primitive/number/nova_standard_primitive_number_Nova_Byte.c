#include <precompiled.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_Nova_Byte.h>


nova_VTable_nova_standard_primitive_number_Nova_Byte nova_VTable_nova_standard_primitive_number_Nova_Byte_val =
{
	nova_standard_Nova_Object_1_Nova_getHashCodeLong,
	nova_standard_primitive_number_Nova_Byte_2_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
	nova_standard_primitive_number_Nova_Byte_Nova_numDigits,
};
void nova_standard_primitive_number_Nova_ByteNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_primitive_number_Nova_Byte* nova_standard_primitive_number_Nova_Byte_Nova_construct(nova_standard_primitive_number_Nova_Byte* this, nova_standard_exception_Nova_ExceptionData* exceptionData, char l0_Nova_value)
{
	CCLASS_NEW(nova_standard_primitive_number_Nova_Byte, this,);
	this->vtable = &nova_VTable_nova_standard_primitive_number_Nova_Byte_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_primitive_Nova_Primitive_Nova_super((nova_standard_primitive_Nova_Primitive*)this, exceptionData);
	nova_standard_primitive_number_Nova_Number_2_Nova_super((nova_standard_primitive_number_Nova_Number*)this, exceptionData);
	nova_standard_Nova_Object_2_Nova_this((nova_standard_Nova_Object*)(this), exceptionData);
	nova_standard_primitive_Nova_Primitive_4_Nova_this((nova_standard_primitive_Nova_Primitive*)(this), exceptionData);
	nova_standard_primitive_number_Nova_Number_6_Nova_this((nova_standard_primitive_number_Nova_Number*)(this), exceptionData);
	nova_standard_primitive_number_Nova_Byte_4_Nova_super(this, exceptionData);
	
	{
		nova_standard_primitive_number_Nova_Byte_Nova_this(this, exceptionData, l0_Nova_value);
	}
	
	return this;
}

void nova_standard_primitive_number_Nova_Byte_Nova_destroy(nova_standard_primitive_number_Nova_Byte** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	
	NOVA_FREE(*this);
}

void nova_standard_primitive_number_Nova_Byte_Nova_this(nova_standard_primitive_number_Nova_Byte* this, nova_standard_exception_Nova_ExceptionData* exceptionData, char l0_Nova_value)
{
	this->nova_standard_primitive_number_Nova_Byte_Nova_value = l0_Nova_value;
}

int nova_standard_primitive_number_Nova_Byte_Nova_numDigits(nova_standard_primitive_number_Nova_Byte* this, nova_standard_exception_Nova_ExceptionData* exceptionData, char l0_Nova_number)
{
	return nova_standard_primitive_number_Nova_Long_0_Nova_numDigits(0, exceptionData, (long)(l0_Nova_number));
}

nova_standard_Nova_String* nova_standard_primitive_number_Nova_Byte_1_Nova_toString(nova_standard_primitive_number_Nova_Byte* this, nova_standard_exception_Nova_ExceptionData* exceptionData, char l0_Nova_value)
{
	return nova_standard_primitive_number_Nova_Long_1_Nova_toString(0, exceptionData, (long)(l0_Nova_value));
}

nova_standard_Nova_String* nova_standard_primitive_number_Nova_Byte_2_Nova_toString(nova_standard_primitive_number_Nova_Byte* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return nova_standard_primitive_number_Nova_Byte_1_Nova_toString(this, exceptionData, this->nova_standard_primitive_number_Nova_Byte_Nova_value);
}

void nova_standard_primitive_number_Nova_Byte_4_Nova_super(nova_standard_primitive_number_Nova_Byte* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_standard_primitive_number_Nova_Byte_Nova_value = 0;
}

