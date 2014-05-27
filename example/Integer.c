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
int nova_Integer_pow(ExceptionData* exceptionData, int nova_Integer_a_33, int nova_Integer_b_33);
int nova_Integer_positivePow(ExceptionData* exceptionData, int nova_Integer_a_43, int nova_Integer_b_43);

Integer* nova_Integer_Integer(ExceptionData* exceptionData, int nova_Integer_value_20)
{
	CCLASS_NEW(Integer, this,);
	
	this->nova_Integer_value = 0;
	{
		this->nova_Integer_value = nova_Integer_value_20;
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

int nova_Integer_numDigits(ExceptionData* exceptionData, int nova_Integer_number_28)
{
	int nova_Integer_size_28;
	
	if (nova_Integer_number_28 < 0)
	{
		return nova_Integer_numDigits(exceptionData, -nova_Integer_number_28) + 1;
	}
	nova_Integer_size_28 = 1;
	nova_Integer_number_28 = nova_Integer_number_28 / 10;
	while (nova_Integer_number_28 > 0)
	{
		nova_Integer_number_28 = nova_Integer_number_28 / 10;
		++nova_Integer_size_28;
	}
	return nova_Integer_size_28;
}

int nova_Integer_pow(ExceptionData* exceptionData, int nova_Integer_a_33, int nova_Integer_b_33)
{
	if (nova_Integer_b_33 == 0)
	{
		return 1;
	}
	else if (nova_Integer_b_33 > 0)
	{
		return nova_Integer_positivePow(exceptionData, nova_Integer_a_33, nova_Integer_b_33);
	}
	else
	{
		return 0;
	}
}

int nova_Integer_positivePow(ExceptionData* exceptionData, int nova_Integer_a_43, int nova_Integer_b_43)
{
	int nova_Integer_i_43;
	
	nova_Integer_i_43 = nova_Integer_b_43 - 2;
	
	for (; nova_Integer_i_43 >= 0; nova_Integer_i_43--)
	{
		nova_Integer_a_43 = nova_Integer_a_43 * nova_Integer_a_43;
	}
	return nova_Integer_a_43;
}

String* nova_Integer_toAString(ExceptionData* exceptionData, int nova_Integer_value_50)
{
	int nova_Integer_index_50;
	int nova_Integer_digits_50;
	char* nova_Integer_data_50;
	int nova_Integer_offset_50;
	int nova_Integer_nums_50;
	
	nova_Integer_index_50 = 0;
	nova_Integer_digits_50 = nova_Integer_numDigits(exceptionData, nova_Integer_value_50);
	nova_Integer_data_50 = (char*)malloc(sizeof(char) * (nova_Integer_digits_50 + 1));
	nova_Integer_data_50[nova_Integer_digits_50] = '\0';
	nova_Integer_offset_50 = 0;
	if (nova_Integer_value_50 < 0)
	{
		nova_Integer_data_50[0] = '-';
		nova_Integer_value_50 = -nova_Integer_value_50;
		nova_Integer_offset_50 = 1;
	}
	nova_Integer_nums_50 = nova_Integer_digits_50 - nova_Integer_offset_50;
	nova_Integer_digits_50 = nova_Integer_digits_50 - 1;
	while (nova_Integer_index_50 < nova_Integer_nums_50)
	{
		nova_Integer_data_50[nova_Integer_digits_50 - nova_Integer_index_50] = '0' + nova_Integer_value_50 % 10;
		nova_Integer_value_50 = nova_Integer_value_50 / 10;
		++nova_Integer_index_50;
	}
	return nova_String_String(exceptionData, nova_Integer_data_50);
}

String* nova_Integer_toString(Integer* this, ExceptionData* exceptionData)
{
	return nova_Integer_toAString(exceptionData, this->nova_Integer_value);
}
