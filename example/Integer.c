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
int nova_Integer_pow(ExceptionData* exceptionData, int nova_Integer_a_171, int nova_Integer_b_171);
int nova_Integer_positivePow(ExceptionData* exceptionData, int nova_Integer_a_182, int nova_Integer_b_182);

Integer* nova_Integer_Integer(ExceptionData* exceptionData, int nova_Integer_value_12)
{
	CCLASS_NEW(Integer, this,);
	
	this->nova_Integer_value = 0;
	{
		this->nova_Integer_value = nova_Integer_value_12;
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

int nova_Integer_numDigits(ExceptionData* exceptionData, int nova_Integer_number_162)
{
	int nova_Integer_size_162;
	
	if (nova_Integer_number_162 < 0)
	{
		return nova_Integer_numDigits(exceptionData, -nova_Integer_number_162) + 1;
	}
	nova_Integer_size_162 = 1;
	nova_Integer_number_162 = nova_Integer_number_162 / 10;
	while (nova_Integer_number_162 > 0)
	{
		nova_Integer_number_162 = nova_Integer_number_162 / 10;
		++nova_Integer_size_162;
	}
	return nova_Integer_size_162;
}

int nova_Integer_pow(ExceptionData* exceptionData, int nova_Integer_a_171, int nova_Integer_b_171)
{
	if (nova_Integer_b_171 == 0)
	{
		return 1;
	}
	else if (nova_Integer_b_171 > 0)
	{
		return nova_Integer_positivePow(exceptionData, nova_Integer_a_171, nova_Integer_b_171);
	}
	else
	{
		return 0;
	}
}

int nova_Integer_positivePow(ExceptionData* exceptionData, int nova_Integer_a_182, int nova_Integer_b_182)
{
	int nova_Integer_i_182;
	
	nova_Integer_i_182 = nova_Integer_b_182 - 2;
	
	for (; nova_Integer_i_182 >= 0; nova_Integer_i_182--)
	{
		nova_Integer_a_182 = nova_Integer_a_182 * nova_Integer_a_182;
	}
	return nova_Integer_a_182;
}

String* nova_Integer_toAString(ExceptionData* exceptionData, int nova_Integer_value_189)
{
	int nova_Integer_index_189;
	int nova_Integer_digits_189;
	char* nova_Integer_data_189;
	int nova_Integer_offset_189;
	int nova_Integer_nums_189;
	
	nova_Integer_index_189 = 0;
	nova_Integer_digits_189 = nova_Integer_numDigits(exceptionData, nova_Integer_value_189);
	nova_Integer_data_189 = (char*)malloc(sizeof(char) * (nova_Integer_digits_189 + 1));
	nova_Integer_data_189[nova_Integer_digits_189] = '\0';
	nova_Integer_offset_189 = 0;
	if (nova_Integer_value_189 < 0)
	{
		nova_Integer_data_189[0] = '-';
		nova_Integer_value_189 = -nova_Integer_value_189;
		nova_Integer_offset_189 = 1;
	}
	nova_Integer_nums_189 = nova_Integer_digits_189 - nova_Integer_offset_189;
	nova_Integer_digits_189 = nova_Integer_digits_189 - 1;
	while (nova_Integer_index_189 < nova_Integer_nums_189)
	{
		nova_Integer_data_189[nova_Integer_digits_189 - nova_Integer_index_189] = '0' + nova_Integer_value_189 % 10;
		nova_Integer_value_189 = nova_Integer_value_189 / 10;
		++nova_Integer_index_189;
	}
	return nova_String_String(exceptionData, nova_Integer_data_189);
}

String* nova_Integer_toString(Integer* this, ExceptionData* exceptionData)
{
	return nova_Integer_toAString(exceptionData, this->nova_Integer_value);
}
