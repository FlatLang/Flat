#include <precompiled.h>
#include <nova/primitive/number/nova_primitive_number_Nova_Long.h>



nova_primitive_number_Extension_VTable_Long nova_primitive_number_Extension_VTable_Long_val =
{
	{
		(int(*)(nova_datastruct_Nova_Comparable*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*))nova_primitive_number_Nova_Long_0_Nova_compareTo,
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
		(nova_Nova_Object*(*)(nova_operators_Nova_Multiply*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*))nova_primitive_number_Nova_Long_0_Nova_multiply,
		0,
		0,
	},
	nova_primitive_number_Nova_Long_3_Nova_toString,
	nova_Nova_Object_0_Nova_equals,
	nova_Nova_Object_Accessor_Nova_hashCodeLong,
	nova_primitive_number_Nova_Long_0_Nova_numDigits,
	nova_primitive_number_Nova_Long_0_Nova_compareTo,
	nova_primitive_number_Nova_Long_0_Nova_multiply,
};


long_long nova_primitive_number_Nova_Long_Nova_MAX_VALUE;
long_long nova_primitive_number_Nova_Long_Nova_MIN_VALUE;
void nova_primitive_number_Nova_Long_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
		nova_primitive_number_Nova_Long_Nova_MAX_VALUE = 9223372036854775807LL;
		nova_primitive_number_Nova_Long_Nova_MIN_VALUE = (-9223372036854775807LL - 1);
	}
}

nova_primitive_number_Nova_Long* nova_primitive_number_Nova_Long_Nova_construct(nova_primitive_number_Nova_Long* this, nova_exception_Nova_ExceptionData* exceptionData, long_long nova_primitive_number_Nova_Long_Nova_value)
{
	CCLASS_NEW(nova_primitive_number_Nova_Long, this,);
	this->vtable = &nova_primitive_number_Extension_VTable_Long_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_primitive_Nova_Primitive_Nova_super((nova_primitive_Nova_Primitive*)this, exceptionData);
	nova_primitive_number_Nova_Number_0_Nova_super((nova_primitive_number_Nova_Number*)this, exceptionData);
	nova_primitive_number_Nova_Long_2_Nova_super(this, exceptionData);
	
	{
		nova_primitive_number_Nova_Long_Nova_this(this, exceptionData, nova_primitive_number_Nova_Long_Nova_value);
	}
	
	return this;
}

void nova_primitive_number_Nova_Long_Nova_destroy(nova_primitive_number_Nova_Long** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	
	NOVA_FREE(*this);
}

void nova_primitive_number_Nova_Long_Nova_this(nova_primitive_number_Nova_Long* this, nova_exception_Nova_ExceptionData* exceptionData, long_long nova_primitive_number_Nova_Long_Nova_value)
{
	this->nova_primitive_number_Nova_Long_Nova_value = nova_primitive_number_Nova_Long_Nova_value;
}

int nova_primitive_number_Nova_Long_0_Nova_numDigits(nova_primitive_number_Nova_Long* this, nova_exception_Nova_ExceptionData* exceptionData, long_long nova_primitive_number_Nova_Long_Nova_number)
{
	int l1_Nova_size = 0;
	
	l1_Nova_size = (int)(1);
	if (nova_primitive_number_Nova_Long_Nova_number < 0)
	{
		l1_Nova_size++;
	}
	nova_primitive_number_Nova_Long_Nova_number = nova_primitive_number_Nova_Long_Nova_number / 10;
	while (nova_primitive_number_Nova_Long_Nova_number != 0)
	{
		nova_primitive_number_Nova_Long_Nova_number = nova_primitive_number_Nova_Long_Nova_number / 10;
		l1_Nova_size++;
	}
	return l1_Nova_size;
}

nova_Nova_String* nova_primitive_number_Nova_Long_2_Nova_toString(nova_primitive_number_Nova_Long* this, nova_exception_Nova_ExceptionData* exceptionData, long_long nova_primitive_number_Nova_Long_Nova_value)
{
	int l1_Nova_charOffset = 0;
	int l1_Nova_digits = 0;
	char* l1_Nova_data = (char*)nova_null;
	int l1_Nova_offset = 0;
	int l1_Nova_nums = 0;
	int l3_Nova_index = 0;
	
	l1_Nova_charOffset = (int)('0');
	l1_Nova_digits = (int)(nova_primitive_number_Nova_Long_0_Nova_numDigits(0, exceptionData, nova_primitive_number_Nova_Long_Nova_value));
	
	l1_Nova_data = (char*)NOVA_MALLOC(sizeof(nova_primitive_number_Nova_Char) * l1_Nova_digits + 1);
	l1_Nova_data[l1_Nova_digits] = '\0';
	l1_Nova_offset = (int)(0);
	if (nova_primitive_number_Nova_Long_Nova_value < 0)
	{
		l1_Nova_data[0] = '-';
		l1_Nova_offset = (int)(1);
	}
	l1_Nova_nums = l1_Nova_digits-- - l1_Nova_offset;
	l3_Nova_index = (int)0;
	for (; l3_Nova_index < (int)l1_Nova_nums; l3_Nova_index++)
	{
		l1_Nova_data[l1_Nova_digits - l3_Nova_index] = (char)((char)(l1_Nova_charOffset + nova_math_Nova_Math_0_Nova_abs(0, exceptionData, nova_primitive_number_Nova_Long_Nova_value % 10)));
		nova_primitive_number_Nova_Long_Nova_value = nova_primitive_number_Nova_Long_Nova_value / 10;
	}
	return nova_Nova_String_1_Nova_construct(0, exceptionData, l1_Nova_data);
}

nova_Nova_String* nova_primitive_number_Nova_Long_3_Nova_toString(nova_primitive_number_Nova_Long* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	return nova_primitive_number_Nova_Long_2_Nova_toString(0, exceptionData, this->nova_primitive_number_Nova_Long_Nova_value);
}

long_long nova_primitive_number_Nova_Long_0_Nova_compareTo(nova_primitive_number_Nova_Long* this, nova_exception_Nova_ExceptionData* exceptionData, long_long nova_primitive_number_Nova_Long_Nova_other)
{
	return this->nova_primitive_number_Nova_Long_Nova_value - nova_primitive_number_Nova_Long_Nova_other;
}

long_long nova_primitive_number_Nova_Long_0_Nova_multiply(nova_primitive_number_Nova_Long* this, nova_exception_Nova_ExceptionData* exceptionData, long_long nova_primitive_number_Nova_Long_Nova_value)
{
	return this->nova_primitive_number_Nova_Long_Nova_value * nova_primitive_number_Nova_Long_Nova_value;
}

void nova_primitive_number_Nova_Long_2_Nova_super(nova_primitive_number_Nova_Long* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_primitive_number_Nova_Long_Nova_value = 0;
}

