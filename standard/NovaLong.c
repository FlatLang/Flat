#include <precompiled.h>
#include "NovaLong.h"


nova_VTable_Long nova_VTable_Long_val =
{
	nova_2_Long_toString,
	nova_static_2_Number_numDigits,
	nova_static_3_Number_toString,
};

Long* nova_Long_Long(ExceptionData* exceptionData, long_long nova_0_value)
{
	CCLASS_NEW(Long, this,);
	
	this->nova_Long_value = 0;
	this->vtable = &nova_VTable_Long_val;
	{
		this->nova_Long_value = nova_0_value;
	}
	
	return this;
}

void nova_del_Long(Long** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

int nova_static_1_Long_numDigits(Long* this, ExceptionData* exceptionData, long_long nova_0_number)
{
	int nova_1_size;
	
	if (nova_0_number < 0)
	{
		return nova_static_1_Long_numDigits((Long*)0, exceptionData, -nova_0_number) + 1;
	}
	nova_0_number = nova_0_number / 10;
	nova_1_size = 1;
	for (; nova_0_number > 0; nova_1_size++)
	{
		nova_0_number = nova_0_number / 10;
	}
	return nova_1_size;
}

String* nova_static_1_Long_toString(Long* this, ExceptionData* exceptionData, long_long nova_0_value)
{
	int nova_1_charOffset;
	int nova_1_digits;
	char* nova_1_data;
	int nova_1_offset;
	int nova_1_nums;
	int nova_1_index;
	
	nova_1_charOffset = (int)('0');
	nova_1_digits = nova_static_1_Long_numDigits((Long*)0, exceptionData, nova_0_value);
	nova_1_data = (char*)NOVA_MALLOC(sizeof(char) * (nova_1_digits + 1));
	nova_1_data[nova_1_digits] = '\0';
	nova_1_offset = 0;
	if (nova_0_value < 0)
	{
		nova_1_data[0] = '-';
		nova_0_value = -nova_0_value;
		nova_1_offset = 1;
	}
	nova_1_nums = nova_1_digits-- - nova_1_offset;
	nova_1_index = 0;
	for (; nova_1_index < nova_1_nums; nova_1_index++)
	{
		nova_1_data[nova_1_digits - nova_1_index] = (char)(nova_1_charOffset + nova_0_value % 10);
		nova_0_value = nova_0_value / 10;
	}
	return nova_String_String(exceptionData, nova_1_data);
}

String* nova_2_Long_toString(Long* this, ExceptionData* exceptionData)
{
	return nova_static_1_Long_toString(this, exceptionData, this->nova_Long_value);
}
