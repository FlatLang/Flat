#include <precompiled.h>
#include "NovaDouble.h"


nova_VTable_Double nova_VTable_Double_val =
{
	nova_1_Double_toString,
	nova_static_Number_numDigits,
	nova_static_Number_toString,
};

String* nova_static_Double_genString(Double* this, ExceptionData* exceptionData, char* nova_0_buffer, int nova_0_lastIndex);
char* nova_static_Double_genBuffer(Double* this, ExceptionData* exceptionData, double nova_0_value);
int nova_static_Double_repetition(Double* this, ExceptionData* exceptionData, char* nova_0_buffer, int nova_0_start);
int nova_static_Double_lastSignificantDigit(Double* this, ExceptionData* exceptionData, char* nova_0_buffer, int nova_0_start);

Double* nova_Double_construct(Double* this, ExceptionData* exceptionData, double nova_0_value)
{
	CCLASS_NEW(Double, this,);
	
	this->nova_Double_value = 0;
	this->vtable = &nova_VTable_Double_val;
	{
		this->nova_Double_value = nova_0_value;
	}
	
	return this;
}

void nova_del_Double(Double** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

int nova_static_Double_numDigits(Double* this, ExceptionData* exceptionData, double nova_0_number)
{
	int nova_1_size;
	
	if (nova_0_number < 0)
	{
		return nova_static_Double_numDigits((Double*)0, exceptionData, -nova_0_number) + 1;
	}
	nova_0_number = nova_0_number / 10;
	nova_1_size = 1;
	for (; nova_0_number > 0; nova_1_size++)
	{
		nova_0_number = nova_0_number / 10;
	}
	return nova_1_size;
}

String* nova_static_Double_genString(Double* this, ExceptionData* exceptionData, char* nova_0_buffer, int nova_0_lastIndex)
{
	nova_0_buffer = realloc(nova_0_buffer, ++nova_0_lastIndex + 1);
	nova_0_buffer[nova_0_lastIndex] = '\0';
	return nova_String_construct(0, exceptionData, nova_0_buffer);
}

char* nova_static_Double_genBuffer(Double* this, ExceptionData* exceptionData, double nova_0_value)
{
	int nova_1_size;
	char* nova_1_buffer;
	
	nova_1_size = 11 + 1 + 15;
	nova_1_buffer = (char*)NOVA_MALLOC(sizeof(char) * (nova_1_size));
	sprintf(nova_1_buffer, "%.15f", nova_0_value);
	return nova_1_buffer;
}

int nova_static_Double_repetition(Double* this, ExceptionData* exceptionData, char* nova_0_buffer, int nova_0_start)
{
	int nova_1_index;
	char nova_1_c;
	
	nova_1_index = nova_0_start;
	nova_1_c = nova_0_buffer[nova_1_index];
	while (nova_0_buffer[--nova_1_index] == nova_1_c);
	return nova_0_start - nova_1_index - 1;
}

int nova_static_Double_lastSignificantDigit(Double* this, ExceptionData* exceptionData, char* nova_0_buffer, int nova_0_start)
{
	while (nova_0_buffer[nova_0_start--] == '0');
	return nova_0_start + 1;
}

String* nova_static_0_Double_toString(Double* this, ExceptionData* exceptionData, double nova_0_value)
{
	char* nova_1_buffer;
	int nova_1_size;
	int nova_1_lastIndex;
	char nova_1_c;
	
	nova_1_buffer = nova_static_Double_genBuffer((Double*)0, exceptionData, nova_0_value);
	nova_1_size = strlen(nova_1_buffer);
	nova_1_lastIndex = nova_1_size - 1;
	nova_1_c = nova_1_buffer[--nova_1_lastIndex];
	if (nova_1_c == '0' || nova_1_c == '9')
	{
		while (nova_1_buffer[nova_1_lastIndex--] == nova_1_c);
		if (nova_1_buffer[++nova_1_lastIndex] == '.')
		{
			nova_1_lastIndex++;
			return nova_static_Double_genString((Double*)0, exceptionData, nova_1_buffer, nova_1_lastIndex);
		}
		else
		{
			if (nova_1_lastIndex >= nova_1_size - 3 - 4)
			{
				nova_1_lastIndex = nova_1_size - 1;
			}
			else if (nova_1_c == '9')
			{
				nova_1_buffer[nova_1_lastIndex]++;
				return nova_static_Double_genString((Double*)0, exceptionData, nova_1_buffer, nova_1_lastIndex);
			}
		}
	}
	else
	{
		int nova_9_rep;
		
		nova_9_rep = nova_static_Double_repetition((Double*)0, exceptionData, nova_1_buffer, nova_1_lastIndex);
		if (nova_9_rep > 5)
		{
			nova_1_buffer[nova_1_lastIndex] = nova_1_c;
			if (nova_1_c >= '5')
			{
				nova_1_c++;
			}
			nova_1_buffer[++nova_1_lastIndex] = nova_1_c;
			return nova_static_Double_genString((Double*)0, exceptionData, nova_1_buffer, nova_1_lastIndex);
		}
	}
	nova_1_lastIndex = nova_static_Double_lastSignificantDigit((Double*)0, exceptionData, nova_1_buffer, nova_1_size - 1);
	return nova_static_Double_genString((Double*)0, exceptionData, nova_1_buffer, nova_1_lastIndex);
}

String* nova_1_Double_toString(Double* this, ExceptionData* exceptionData)
{
	return nova_static_0_Double_toString(this, exceptionData, this->nova_Double_value);
}
