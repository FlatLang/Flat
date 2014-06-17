#include <precompiled.h>

#include "NovaDouble.h"

String* nova_Double_genString(ExceptionData* exceptionData, char* nova_0_buffer, int nova_0_lastIndex);
char* nova_Double_genBuffer(ExceptionData* exceptionData, double nova_0_value);
int nova_Double_repetition(ExceptionData* exceptionData, char* nova_0_buffer, int nova_0_start);
int nova_Double_lastSignificantDigit(ExceptionData* exceptionData, char* nova_0_buffer, int nova_0_start);

Double* nova_Double_Double(ExceptionData* exceptionData, double nova_0_value)
{
	CCLASS_NEW(Double, this,);
	
	this->nova_Double_value = 0;
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

int nova_Double_numDigits(ExceptionData* exceptionData, double nova_0_number)
{
	return nova_Long_numDigits(exceptionData, nova_0_number);
}

String* nova_Double_genString(ExceptionData* exceptionData, char* nova_0_buffer, int nova_0_lastIndex)
{
	nova_0_buffer = realloc(nova_0_buffer, ++nova_0_lastIndex + 1);
	nova_0_buffer[nova_0_lastIndex] = '\0';
	return nova_String_String(exceptionData, nova_0_buffer);
}

char* nova_Double_genBuffer(ExceptionData* exceptionData, double nova_0_value)
{
	int nova_1_size;
	char* nova_1_buffer;
	
	nova_1_size = 11 + 1 + 15;
	nova_1_buffer = (char*)NOVA_MALLOC(sizeof(char) * (nova_1_size));
	sprintf(nova_1_buffer, "%.15f", nova_0_value);
	return nova_1_buffer;
}

int nova_Double_repetition(ExceptionData* exceptionData, char* nova_0_buffer, int nova_0_start)
{
	int nova_1_index;
	char nova_1_c;
	
	nova_1_index = nova_0_start;
	nova_1_c = nova_0_buffer[nova_1_index];
	while (nova_0_buffer[--nova_1_index] == nova_1_c)
	;return nova_0_start - nova_1_index - 1;
}

int nova_Double_lastSignificantDigit(ExceptionData* exceptionData, char* nova_0_buffer, int nova_0_start)
{
	while (nova_0_buffer[nova_0_start--] == '0')
	;return nova_0_start + 1;
}

String* nova_Double_toAString(ExceptionData* exceptionData, double nova_0_value)
{
	char* nova_1_buffer;
	int nova_1_size;
	int nova_1_lastIndex;
	char nova_1_c;
	
	nova_1_buffer = nova_Double_genBuffer(exceptionData, nova_0_value);
	nova_1_size = strlen(nova_1_buffer);
	nova_1_lastIndex = nova_1_size - 1;
	nova_1_c = nova_1_buffer[--nova_1_lastIndex];
	if (nova_1_c == '0' || nova_1_c == '9')
	{
		while (nova_1_buffer[nova_1_lastIndex--] == nova_1_c)
		;if (nova_1_buffer[++nova_1_lastIndex] == '.')
		{
			nova_1_lastIndex++;
			return nova_Double_genString(exceptionData, nova_1_buffer, nova_1_lastIndex);
		}
		else
		{
			if (nova_1_lastIndex >= nova_1_size - 3 - 4)
			{
				nova_1_lastIndex = nova_1_size - 1;
			}
			else
			{
				if (nova_1_c == '9')
				nova_1_buffer[nova_1_lastIndex]++;
				return nova_Double_genString(exceptionData, nova_1_buffer, nova_1_lastIndex);
			}
		}
	}
	else
	{
		int nova_9_rep;
		
		nova_9_rep = nova_Double_repetition(exceptionData, nova_1_buffer, nova_1_lastIndex);
		if (nova_9_rep > 5)
		{
			nova_1_buffer[nova_1_lastIndex] = nova_1_c;
			if (nova_1_c >= '5')
			{
				nova_1_c++;
			}
			nova_1_buffer[++nova_1_lastIndex] = nova_1_c;
			return nova_Double_genString(exceptionData, nova_1_buffer, nova_1_lastIndex);
		}
	}
	nova_1_lastIndex = nova_Double_lastSignificantDigit(exceptionData, nova_1_buffer, nova_1_size - 1);
	return nova_Double_genString(exceptionData, nova_1_buffer, nova_1_lastIndex);
}

String* nova_Double_toString(Double* this, ExceptionData* exceptionData)
{
	return nova_Double_toAString(exceptionData, this->nova_Double_value);
}
