#include "Integer.h"
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
int nova_Integer_pow(ExceptionData* exceptionData, int nova_Integer_a_154, int nova_Integer_b_154);
int nova_Integer_positivePow(ExceptionData* exceptionData, int nova_Integer_a_163, int nova_Integer_b_163);

Integer* nova_Integer_Integer(ExceptionData* exceptionData, int nova_Integer_value_144)
{
	CCLASS_NEW(Integer, this,);
	
	this->nova_Integer_value = 0;
	{
		this->nova_Integer_value = nova_Integer_value_144;
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

int nova_Integer_numDigits(ExceptionData* exceptionData, int nova_Integer_number_147)
{
	int nova_Integer_size_147;
	
	if (nova_Integer_number_147 < 0)
	{
		return nova_Integer_numDigits(exceptionData, -nova_Integer_number_147) + 1;
	}
	nova_Integer_size_147 = 1;
	nova_Integer_number_147 = nova_Integer_number_147 / 10;
	while (nova_Integer_number_147 > 0)
	{
		nova_Integer_number_147 = nova_Integer_number_147 / 10;
		++nova_Integer_size_147;
	}
	return nova_Integer_size_147;
}

int nova_Integer_pow(ExceptionData* exceptionData, int nova_Integer_a_154, int nova_Integer_b_154)
{
	if (nova_Integer_b_154 == 0)
	{
		return 1;
	}
	else if (nova_Integer_b_154 > 0)
	{
		return nova_Integer_positivePow(exceptionData, nova_Integer_a_154, nova_Integer_b_154);
	}
	else
	{
		return 0;
	}
}

int nova_Integer_positivePow(ExceptionData* exceptionData, int nova_Integer_a_163, int nova_Integer_b_163)
{
	int nova_Integer_i_163;
	
	nova_Integer_i_163 = nova_Integer_b_163 - 2;
	
	for (; nova_Integer_i_163 >= 0; nova_Integer_i_163--)
	{
		nova_Integer_a_163 = nova_Integer_a_163 * nova_Integer_a_163;
	}
	return nova_Integer_a_163;
}

String* nova_Integer_toAString(ExceptionData* exceptionData, int nova_Integer_value_171)
{
	int nova_Integer_index_171;
	int nova_Integer_digits_171;
	char* nova_Integer_data_171;
	int nova_Integer_offset_171;
	int nova_Integer_nums_171;
	
	nova_Integer_index_171 = 0;
	nova_Integer_digits_171 = nova_Integer_numDigits(exceptionData, nova_Integer_value_171);
	nova_Integer_data_171 = (char*)malloc(sizeof(char) * (nova_Integer_digits_171 + 1));
	nova_Integer_data_171[nova_Integer_digits_171] = '\0';
	nova_Integer_offset_171 = 0;
	if (nova_Integer_value_171 < 0)
	{
		nova_Integer_data_171[0] = '-';
		nova_Integer_value_171 = -nova_Integer_value_171;
		nova_Integer_offset_171 = 1;
	}
	nova_Integer_nums_171 = nova_Integer_digits_171 - nova_Integer_offset_171;
	nova_Integer_digits_171 = nova_Integer_digits_171 - 1;
	while (nova_Integer_index_171 < nova_Integer_nums_171)
	{
		nova_Integer_data_171[nova_Integer_digits_171 - nova_Integer_index_171] = '0' + nova_Integer_value_171 % 10;
		nova_Integer_value_171 = nova_Integer_value_171 / 10;
		++nova_Integer_index_171;
	}
	return nova_String_String(exceptionData, nova_Integer_data_171);
}

String* nova_Integer_toString(Integer* this, ExceptionData* exceptionData)
{
	return nova_Integer_toAString(exceptionData, this->nova_Integer_value);
}
