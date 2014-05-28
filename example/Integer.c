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
int nova_Integer_pow(ExceptionData* exceptionData, int nova_Integer_a_75, int nova_Integer_b_75);
int nova_Integer_positivePow(ExceptionData* exceptionData, int nova_Integer_a_84, int nova_Integer_b_84);

Integer* nova_Integer_Integer(ExceptionData* exceptionData, int nova_Integer_value_59)
{
	CCLASS_NEW(Integer, this,);
	
	this->nova_Integer_value = 0;
	{
		this->nova_Integer_value = nova_Integer_value_59;
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

int nova_Integer_numDigits(ExceptionData* exceptionData, int nova_Integer_number_69)
{
	int nova_Integer_size_69;
	
	if (nova_Integer_number_69 < 0)
	{
		return nova_Integer_numDigits(exceptionData, -nova_Integer_number_69) + 1;
	}
	nova_Integer_size_69 = 1;
	nova_Integer_number_69 = nova_Integer_number_69 / 10;
	while (nova_Integer_number_69 > 0)
	{
		nova_Integer_number_69 = nova_Integer_number_69 / 10;
		++nova_Integer_size_69;
	}
	return nova_Integer_size_69;
}

int nova_Integer_pow(ExceptionData* exceptionData, int nova_Integer_a_75, int nova_Integer_b_75)
{
	if (nova_Integer_b_75 == 0)
	{
		return 1;
	}
	else if (nova_Integer_b_75 > 0)
	{
		return nova_Integer_positivePow(exceptionData, nova_Integer_a_75, nova_Integer_b_75);
	}
	else
	{
		return 0;
	}
}

int nova_Integer_positivePow(ExceptionData* exceptionData, int nova_Integer_a_84, int nova_Integer_b_84)
{
	int nova_Integer_i_84;
	
	nova_Integer_i_84 = nova_Integer_b_84 - 2;
	
	for (; nova_Integer_i_84 >= 0; nova_Integer_i_84--)
	{
		nova_Integer_a_84 = nova_Integer_a_84 * nova_Integer_a_84;
	}
	return nova_Integer_a_84;
}

String* nova_Integer_toAString(ExceptionData* exceptionData, int nova_Integer_value_97)
{
	int nova_Integer_index_97;
	int nova_Integer_digits_97;
	char* nova_Integer_data_97;
	int nova_Integer_offset_97;
	int nova_Integer_nums_97;
	
	nova_Integer_index_97 = 0;
	nova_Integer_digits_97 = nova_Integer_numDigits(exceptionData, nova_Integer_value_97);
	nova_Integer_data_97 = (char*)malloc(sizeof(char) * (nova_Integer_digits_97 + 1));
	nova_Integer_data_97[nova_Integer_digits_97] = '\0';
	nova_Integer_offset_97 = 0;
	if (nova_Integer_value_97 < 0)
	{
		nova_Integer_data_97[0] = '-';
		nova_Integer_value_97 = -nova_Integer_value_97;
		nova_Integer_offset_97 = 1;
	}
	nova_Integer_nums_97 = nova_Integer_digits_97 - nova_Integer_offset_97;
	nova_Integer_digits_97 = nova_Integer_digits_97 - 1;
	while (nova_Integer_index_97 < nova_Integer_nums_97)
	{
		nova_Integer_data_97[nova_Integer_digits_97 - nova_Integer_index_97] = '0' + nova_Integer_value_97 % 10;
		nova_Integer_value_97 = nova_Integer_value_97 / 10;
		++nova_Integer_index_97;
	}
	return nova_String_String(exceptionData, nova_Integer_data_97);
}

String* nova_Integer_toString(Integer* this, ExceptionData* exceptionData)
{
	return nova_Integer_toAString(exceptionData, this->nova_Integer_value);
}
