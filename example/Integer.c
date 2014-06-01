#include "Integer.h"
#include <gc.h>
#include <stdlib.h>
#include <CClass.h>
#include <ExceptionHandler.h>
#include <Fathom.h>
#include "ExceptionData.h"
#include "Object.h"
#include "String.h"
#include "Math.h"
#include "IO.h"
#include "Integer.h"
#include "DivideByZeroException.h"

Integer* nova_Integer_Integer(ExceptionData* exceptionData, int nova_0_value)
{
	CCLASS_NEW(Integer, this,);
	
	this->nova_Integer_value = 0;
	{
		this->nova_Integer_value = nova_0_value;
	}
	
	return this;
}

void nova_del_Integer(Integer** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	free(*this);
}

int nova_Integer_numDigits(ExceptionData* exceptionData, int nova_0_number)
{
	int nova_140_size;
	
	if (nova_0_number < 0)
	{
		return nova_Integer_numDigits(exceptionData, -nova_0_number) + 1;
	}
	nova_0_number = nova_0_number / 10;
	nova_140_size = 1;
	
	for (; nova_0_number > 0; nova_140_size++)
	{
		nova_0_number = nova_0_number / 10;
	}
	return nova_140_size;
}

String* nova_Integer_toAString(ExceptionData* exceptionData, int nova_0_value)
{
	int nova_144_digits;
	char* nova_144_data;
	int nova_144_offset;
	int nova_144_nums;
	int nova_252_index;
	
	nova_144_digits = nova_Integer_numDigits(exceptionData, nova_0_value);
	nova_144_data = (char*)GC_MALLOC(sizeof(char) * (nova_144_digits + 1));
	nova_144_data[nova_144_digits] = '\0';
	nova_144_offset = 0;
	if (nova_0_value < 0)
	{
		nova_144_data[0] = '-';
		nova_0_value = -nova_0_value;
		nova_144_offset = 1;
	}
	nova_144_nums = nova_144_digits-- - nova_144_offset;
	nova_252_index = 0;
	
	for (; nova_252_index < nova_144_nums; nova_252_index++)
	{
		nova_144_data[nova_144_digits - nova_252_index] = '0' + nova_0_value % 10;
		nova_0_value = nova_0_value / 10;
	}
	return nova_String_String(exceptionData, nova_144_data);
}

String* nova_Integer_toString(Integer* this, ExceptionData* exceptionData)
{
	return nova_Integer_toAString(exceptionData, this->nova_Integer_value);
}
