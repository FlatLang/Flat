#include "Long.h"
#include <Fathom.h>
#include <gc.h>
#include <stdlib.h>
#include <CClass.h>
#include <ExceptionHandler.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include "Math.h"
#include "IO.h"
#include "Integer.h"
#include "Long.h"
#include "DivideByZeroException.h"


Long* nova_Long_Long(ExceptionData* exceptionData, long_long nova_0_value)
{
	CCLASS_NEW(Long, this,);
	
	this->nova_Long_value = 0;
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
	free(*this);
}

int nova_Long_numDigits(ExceptionData* exceptionData, long_long nova_0_number)
{
	int nova_166_size;
	
	if (nova_0_number < 0)
	{
		return nova_Long_numDigits(exceptionData, -nova_0_number) + 1;
	}
	nova_0_number = nova_0_number / 10;
	nova_166_size = 1;
	
	for (; nova_0_number > 0; nova_166_size++)
	{
		nova_0_number = nova_0_number / 10;
	}
	nova_IO_println(exceptionData, nova_String_String(exceptionData, ""));
	return nova_166_size;
}

String* nova_Long_toAString(ExceptionData* exceptionData, long_long nova_0_value)
{
	int nova_171_digits;
	char* nova_171_data;
	int nova_171_offset;
	int nova_171_nums;
	int nova_372_index;
	
	nova_171_digits = nova_Long_numDigits(exceptionData, nova_0_value);
	nova_171_data = (char*)malloc(sizeof(char) * (nova_171_digits + 1));
	nova_171_data[nova_171_digits] = '\0';
	nova_171_offset = 0;
	if (nova_0_value < 0)
	{
		nova_171_data[0] = '-';
		nova_0_value = -nova_0_value;
		nova_171_offset = 1;
	}
	nova_171_nums = nova_171_digits-- - nova_171_offset;
	nova_372_index = 0;
	
	for (; nova_372_index < nova_171_nums; nova_372_index++)
	{
		nova_171_data[nova_171_digits - nova_372_index] = '0' + nova_0_value % 10;
		nova_0_value = nova_0_value / 10;
	}
	return nova_String_String(exceptionData, nova_171_data);
}

String* nova_Long_toString(Long* this, ExceptionData* exceptionData)
{
	return nova_Long_toAString(exceptionData, this->nova_Long_value);
}
