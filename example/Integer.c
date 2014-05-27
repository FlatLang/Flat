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
int nova_Integer_pow(ExceptionData* exceptionData, int nova_Integer_a_130, int nova_Integer_b_130);
int nova_Integer_positivePow(ExceptionData* exceptionData, int nova_Integer_a_140, int nova_Integer_b_140);

Integer* nova_Integer_Integer(ExceptionData* exceptionData, int nova_Integer_value_74)
{
	CCLASS_NEW(Integer, this,);
	
	this->nova_Integer_value = 0;
	{
		this->nova_Integer_value = nova_Integer_value_74;
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

int nova_Integer_numDigits(ExceptionData* exceptionData, int nova_Integer_number_90)
{
	int nova_Integer_size_90;
	
	if (nova_Integer_number_90 < 0)
	{
		return nova_Integer_numDigits(exceptionData, -nova_Integer_number_90) + 1;
	}
	nova_Integer_size_90 = 1;
	nova_Integer_number_90 = nova_Integer_number_90 / 10;
	while (nova_Integer_number_90 > 0)
	{
		nova_Integer_number_90 = nova_Integer_number_90 / 10;
		++nova_Integer_size_90;
	}
	return nova_Integer_size_90;
}

int nova_Integer_pow(ExceptionData* exceptionData, int nova_Integer_a_130, int nova_Integer_b_130)
{
	if (nova_Integer_b_130 == 0)
	{
		return 1;
	}
	else if (nova_Integer_b_130 > 0)
	{
		return nova_Integer_positivePow(exceptionData, nova_Integer_a_130, nova_Integer_b_130);
	}
	else
	{
		return 0;
	}
}

int nova_Integer_positivePow(ExceptionData* exceptionData, int nova_Integer_a_140, int nova_Integer_b_140)
{
	int nova_Integer_i_140;
	
	nova_Integer_i_140 = nova_Integer_b_140 - 2;
	
	for (; nova_Integer_i_140 >= 0; nova_Integer_i_140--)
	{
		nova_Integer_a_140 = nova_Integer_a_140 * nova_Integer_a_140;
	}
	return nova_Integer_a_140;
}

String* nova_Integer_toAString(ExceptionData* exceptionData, int nova_Integer_value_158)
{
	int nova_Integer_index_158;
	int nova_Integer_digits_158;
	char* nova_Integer_data_158;
	int nova_Integer_offset_158;
	int nova_Integer_nums_158;
	
	nova_Integer_index_158 = 0;
	nova_Integer_digits_158 = nova_Integer_numDigits(exceptionData, nova_Integer_value_158);
	nova_Integer_data_158 = (char*)malloc(sizeof(char) * (nova_Integer_digits_158 + 1));
	nova_Integer_data_158[nova_Integer_digits_158] = '\0';
	nova_Integer_offset_158 = 0;
	if (nova_Integer_value_158 < 0)
	{
		nova_Integer_data_158[0] = '-';
		nova_Integer_value_158 = -nova_Integer_value_158;
		nova_Integer_offset_158 = 1;
	}
	nova_Integer_nums_158 = nova_Integer_digits_158 - nova_Integer_offset_158;
	nova_Integer_digits_158 = nova_Integer_digits_158 - 1;
	while (nova_Integer_index_158 < nova_Integer_nums_158)
	{
		nova_Integer_data_158[nova_Integer_digits_158 - nova_Integer_index_158] = '0' + nova_Integer_value_158 % 10;
		nova_Integer_value_158 = nova_Integer_value_158 / 10;
		++nova_Integer_index_158;
	}
	return nova_String_String(exceptionData, nova_Integer_data_158);
}

String* nova_Integer_toString(Integer* this, ExceptionData* exceptionData)
{
	return nova_Integer_toAString(exceptionData, this->nova_Integer_value);
}
