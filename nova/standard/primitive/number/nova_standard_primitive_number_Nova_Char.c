#include <precompiled.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_Nova_Char.h>

nova_standard_primitive_number_Extension_VTable_Char nova_standard_primitive_number_Extension_VTable_Char_val =
{
	{
		(int(*)(nova_standard_datastruct_Nova_Comparable*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*))nova_standard_primitive_number_Nova_Char_0_Nova_compareTo,
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
		(nova_standard_Nova_Object*(*)(nova_standard_operators_Nova_Multiply*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*))nova_standard_primitive_number_Nova_Char_0_Nova_multiply,
		0,
		0,
	},
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_primitive_number_Nova_Char_3_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
	nova_standard_primitive_number_Nova_Number_0_Nova_numDigits,
	nova_standard_primitive_number_Nova_Char_0_Nova_compareTo,
	nova_standard_primitive_number_Nova_Char_0_Nova_multiply,
};


void nova_standard_primitive_number_Nova_CharNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_primitive_number_Nova_Char* nova_standard_primitive_number_Nova_Char_Nova_construct(nova_standard_primitive_number_Nova_Char* this, nova_standard_exception_Nova_ExceptionData* exceptionData, char nova_standard_primitive_number_Nova_Char_Nova_value)
{
	CCLASS_NEW(nova_standard_primitive_number_Nova_Char, this,);
	this->vtable = &nova_standard_primitive_number_Extension_VTable_Char_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_primitive_Nova_Primitive_Nova_super((nova_standard_primitive_Nova_Primitive*)this, exceptionData);
	nova_standard_primitive_number_Nova_Number_0_Nova_super((nova_standard_primitive_number_Nova_Number*)this, exceptionData);
	nova_standard_primitive_number_Nova_Char_2_Nova_super(this, exceptionData);
	
	{
		nova_standard_primitive_number_Nova_Char_Nova_this(this, exceptionData, nova_standard_primitive_number_Nova_Char_Nova_value);
	}
	
	return this;
}

void nova_standard_primitive_number_Nova_Char_Nova_destroy(nova_standard_primitive_number_Nova_Char** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	
	NOVA_FREE(*this);
}

void nova_standard_primitive_number_Nova_Char_Nova_this(nova_standard_primitive_number_Nova_Char* this, nova_standard_exception_Nova_ExceptionData* exceptionData, char nova_standard_primitive_number_Nova_Char_Nova_value)
{
	this->nova_standard_primitive_number_Nova_Char_Nova_value = nova_standard_primitive_number_Nova_Char_Nova_value;
}

nova_standard_Nova_String* nova_standard_primitive_number_Nova_Char_2_Nova_toString(nova_standard_primitive_number_Nova_Char* this, nova_standard_exception_Nova_ExceptionData* exceptionData, char nova_standard_primitive_number_Nova_Char_Nova_c)
{
	return nova_standard_Nova_String_1_Nova_construct(0, exceptionData, nova_standard_primitive_number_Nova_Char_Nova_c);
}

nova_standard_Nova_String* nova_standard_primitive_number_Nova_Char_3_Nova_toString(nova_standard_primitive_number_Nova_Char* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return nova_standard_primitive_number_Nova_Char_2_Nova_toString(0, exceptionData, this->nova_standard_primitive_number_Nova_Char_Nova_value);
}

char nova_standard_primitive_number_Nova_Char_0_Nova_toLowerCase(nova_standard_primitive_number_Nova_Char* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return (char)0;
}

char nova_standard_primitive_number_Nova_Char_1_Nova_toLowerCase(nova_standard_primitive_number_Nova_Char* this, nova_standard_exception_Nova_ExceptionData* exceptionData, char nova_standard_primitive_number_Nova_Char_Nova_c)
{
	int l1_Nova_id;
	
	l1_Nova_id = (int)nova_standard_primitive_number_Nova_Char_Nova_c;
	if (l1_Nova_id >= 65 && l1_Nova_id <= 90)
	{
		return (char)(l1_Nova_id + 32);
	}
	return nova_standard_primitive_number_Nova_Char_Nova_c;
}

char nova_standard_primitive_number_Nova_Char_Nova_toUpperCase(nova_standard_primitive_number_Nova_Char* this, nova_standard_exception_Nova_ExceptionData* exceptionData, char nova_standard_primitive_number_Nova_Char_Nova_c)
{
	int l1_Nova_id;
	
	l1_Nova_id = (int)nova_standard_primitive_number_Nova_Char_Nova_c;
	if (l1_Nova_id >= 97 && l1_Nova_id <= 122)
	{
		return (char)(l1_Nova_id - 32);
	}
	return nova_standard_primitive_number_Nova_Char_Nova_c;
}

int nova_standard_primitive_number_Nova_Char_0_Nova_compareTo(nova_standard_primitive_number_Nova_Char* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_primitive_number_Nova_Char* nova_standard_primitive_number_Nova_Char_Nova_prim)
{
	char l1_Nova_other;
	
	l1_Nova_other = nova_standard_primitive_number_Nova_Char_Nova_prim->nova_standard_primitive_number_Nova_Char_Nova_value;
	return (int)this->nova_standard_primitive_number_Nova_Char_Nova_value - l1_Nova_other;
}

char nova_standard_primitive_number_Nova_Char_0_Nova_multiply(nova_standard_primitive_number_Nova_Char* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_primitive_number_Nova_Char* nova_standard_primitive_number_Nova_Char_Nova_prim)
{
	char l1_Nova_value;
	
	l1_Nova_value = nova_standard_primitive_number_Nova_Char_Nova_prim->nova_standard_primitive_number_Nova_Char_Nova_value;
	return this->nova_standard_primitive_number_Nova_Char_Nova_value * l1_Nova_value;
}

void nova_standard_primitive_number_Nova_Char_2_Nova_super(nova_standard_primitive_number_Nova_Char* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_standard_primitive_number_Nova_Char_Nova_value = 0;
}

