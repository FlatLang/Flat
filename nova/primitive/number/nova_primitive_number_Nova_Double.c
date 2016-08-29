#include <precompiled.h>
#include <nova/primitive/number/nova_primitive_number_Nova_Double.h>



nova_primitive_number_Extension_VTable_Double nova_primitive_number_Extension_VTable_Double_val =
{
	{
		(int(*)(nova_datastruct_Nova_Comparable*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*))nova_primitive_number_Nova_Double_0_Nova_compareTo,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		0,
		(char(*)(nova_operators_Nova_Equals*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*))nova_Nova_Object_0_Nova_equals,
		(nova_Nova_Object*(*)(nova_operators_Nova_Multiply*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*))nova_primitive_number_Nova_Double_0_Nova_multiply,
		0,
		0,
	},
	nova_primitive_number_Nova_Double_3_Nova_toString,
	nova_Nova_Object_0_Nova_equals,
	nova_Nova_Object_Accessor_Nova_hashCodeLong,
	nova_primitive_number_Nova_Double_0_Nova_numDigits,
	nova_primitive_number_Nova_Double_0_Nova_compareTo,
	nova_primitive_number_Nova_Double_0_Nova_multiply,
};


void nova_primitive_number_Nova_Double_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_primitive_number_Nova_Double* nova_primitive_number_Nova_Double_Nova_construct(nova_primitive_number_Nova_Double* this, nova_exception_Nova_ExceptionData* exceptionData, double nova_primitive_number_Nova_Double_Nova_value)
{
	CCLASS_NEW(nova_primitive_number_Nova_Double, this,);
	this->vtable = &nova_primitive_number_Extension_VTable_Double_val;
	nova_Nova_Object_Nova_super((nova_Nova_Object*)this, exceptionData);
	nova_primitive_Nova_Primitive_Nova_super((nova_primitive_Nova_Primitive*)this, exceptionData);
	nova_primitive_number_Nova_Number_0_Nova_super((nova_primitive_number_Nova_Number*)this, exceptionData);
	nova_primitive_number_Nova_Double_2_Nova_super(this, exceptionData);
	
	{
		nova_primitive_number_Nova_Double_Nova_this(this, exceptionData, nova_primitive_number_Nova_Double_Nova_value);
	}
	
	return this;
}

void nova_primitive_number_Nova_Double_Nova_destroy(nova_primitive_number_Nova_Double** this, nova_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	
	NOVA_FREE(*this);
}

void nova_primitive_number_Nova_Double_Nova_this(nova_primitive_number_Nova_Double* this, nova_exception_Nova_ExceptionData* exceptionData, double nova_primitive_number_Nova_Double_Nova_value)
{
	this->nova_primitive_number_Nova_Double_Nova_value = nova_primitive_number_Nova_Double_Nova_value;
}

int nova_primitive_number_Nova_Double_0_Nova_numDigits(nova_primitive_number_Nova_Double* this, nova_exception_Nova_ExceptionData* exceptionData, double nova_primitive_number_Nova_Double_Nova_number)
{
	int l1_Nova_size = 0;
	
	if (nova_primitive_number_Nova_Double_Nova_number < 0)
	{
		return nova_primitive_number_Nova_Double_0_Nova_numDigits(0, exceptionData, -nova_primitive_number_Nova_Double_Nova_number) + 1;
	}
	nova_primitive_number_Nova_Double_Nova_number = nova_primitive_number_Nova_Double_Nova_number / 10;
	l1_Nova_size = (int)(1);
	while (nova_primitive_number_Nova_Double_Nova_number > 0)
	{
		nova_primitive_number_Nova_Double_Nova_number = nova_primitive_number_Nova_Double_Nova_number / 10;
		l1_Nova_size++;
	}
	return l1_Nova_size;
}

nova_Nova_String* nova_primitive_number_Nova_Double_Nova_genString(nova_primitive_number_Nova_Double* this, nova_exception_Nova_ExceptionData* exceptionData, char* nova_primitive_number_Nova_Double_Nova_buffer, int nova_primitive_number_Nova_Double_Nova_lastIndex)
{
	nova_primitive_number_Nova_Double_Nova_buffer = (char*)(realloc(nova_primitive_number_Nova_Double_Nova_buffer, ++nova_primitive_number_Nova_Double_Nova_lastIndex + 1));
	nova_primitive_number_Nova_Double_Nova_buffer[nova_primitive_number_Nova_Double_Nova_lastIndex] = '\0';
	return nova_Nova_String_1_Nova_construct(0, exceptionData, nova_primitive_number_Nova_Double_Nova_buffer);
}

char* nova_primitive_number_Nova_Double_Nova_genBuffer(nova_primitive_number_Nova_Double* this, nova_exception_Nova_ExceptionData* exceptionData, double nova_primitive_number_Nova_Double_Nova_value)
{
	int l1_Nova_size = 0;
	char* l1_Nova_buffer = (char*)nova_null;
	
	l1_Nova_size = (int)(11 + 1 + 15);
	
	l1_Nova_buffer = (char*)NOVA_MALLOC(sizeof(nova_primitive_number_Nova_Char) * l1_Nova_size);
	sprintf(l1_Nova_buffer, (char*)("%.15f"), nova_primitive_number_Nova_Double_Nova_value);
	return l1_Nova_buffer;
}

int nova_primitive_number_Nova_Double_Nova_repetition(nova_primitive_number_Nova_Double* this, nova_exception_Nova_ExceptionData* exceptionData, char* nova_primitive_number_Nova_Double_Nova_buffer, int nova_primitive_number_Nova_Double_Nova_start)
{
	int l1_Nova_index = 0;
	char l1_Nova_c = 0;
	
	l1_Nova_index = nova_primitive_number_Nova_Double_Nova_start;
	l1_Nova_c = nova_primitive_number_Nova_Double_Nova_buffer[l1_Nova_index];
	while (nova_primitive_number_Nova_Double_Nova_buffer[--l1_Nova_index] == l1_Nova_c);
	return nova_primitive_number_Nova_Double_Nova_start - l1_Nova_index - 1;
}

int nova_primitive_number_Nova_Double_Nova_lastSignificantDigit(nova_primitive_number_Nova_Double* this, nova_exception_Nova_ExceptionData* exceptionData, char* nova_primitive_number_Nova_Double_Nova_buffer, int nova_primitive_number_Nova_Double_Nova_start)
{
	while (nova_primitive_number_Nova_Double_Nova_buffer[nova_primitive_number_Nova_Double_Nova_start--] == '0');
	return nova_primitive_number_Nova_Double_Nova_start + 1;
}

nova_Nova_String* nova_primitive_number_Nova_Double_2_Nova_toString(nova_primitive_number_Nova_Double* this, nova_exception_Nova_ExceptionData* exceptionData, double nova_primitive_number_Nova_Double_Nova_value)
{
	char* l1_Nova_buffer = (char*)nova_null;
	int l1_Nova_size = 0;
	int l1_Nova_lastIndex = 0;
	char l1_Nova_c = 0;
	
	
	l1_Nova_buffer = nova_primitive_number_Nova_Double_Nova_genBuffer(0, exceptionData, nova_primitive_number_Nova_Double_Nova_value);
	l1_Nova_size = (int)(strlen(l1_Nova_buffer));
	l1_Nova_lastIndex = l1_Nova_size - 1;
	l1_Nova_c = l1_Nova_buffer[--l1_Nova_lastIndex];
	if (l1_Nova_c == '0' || l1_Nova_c == '9')
	{
		while (l1_Nova_buffer[l1_Nova_lastIndex--] == l1_Nova_c);
		if (l1_Nova_buffer[++l1_Nova_lastIndex] == '.')
		{
			l1_Nova_lastIndex++;
			return nova_primitive_number_Nova_Double_Nova_genString(0, exceptionData, l1_Nova_buffer, l1_Nova_lastIndex);
		}
		else
		{
			if (l1_Nova_lastIndex >= l1_Nova_size - 3 - 4)
			{
				l1_Nova_lastIndex = l1_Nova_size - 1;
			}
			else if (l1_Nova_c == '9')
			{
				l1_Nova_buffer[l1_Nova_lastIndex]++;
				return nova_primitive_number_Nova_Double_Nova_genString(0, exceptionData, l1_Nova_buffer, l1_Nova_lastIndex);
			}
		}
	}
	else
	{
		int l8_Nova_rep = 0;
		
		l8_Nova_rep = nova_primitive_number_Nova_Double_Nova_repetition(0, exceptionData, l1_Nova_buffer, l1_Nova_lastIndex);
		if (l8_Nova_rep > 5)
		{
			l1_Nova_buffer[l1_Nova_lastIndex] = l1_Nova_c;
			if (l1_Nova_c >= '5')
			{
				l1_Nova_c++;
			}
			l1_Nova_buffer[++l1_Nova_lastIndex] = l1_Nova_c;
			return nova_primitive_number_Nova_Double_Nova_genString(0, exceptionData, l1_Nova_buffer, l1_Nova_lastIndex);
		}
	}
	l1_Nova_lastIndex = nova_primitive_number_Nova_Double_Nova_lastSignificantDigit(0, exceptionData, l1_Nova_buffer, l1_Nova_size - 1);
	return nova_primitive_number_Nova_Double_Nova_genString(0, exceptionData, l1_Nova_buffer, l1_Nova_lastIndex);
}

nova_Nova_String* nova_primitive_number_Nova_Double_3_Nova_toString(nova_primitive_number_Nova_Double* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	return nova_primitive_number_Nova_Double_2_Nova_toString(0, exceptionData, this->nova_primitive_number_Nova_Double_Nova_value);
}

double nova_primitive_number_Nova_Double_Nova_parseDouble(nova_primitive_number_Nova_Double* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* nova_primitive_number_Nova_Double_Nova_str)
{
	char* l1_Nova_pEnd = (char*)nova_null;
	
	
	return (double)strtod((char*)(nova_primitive_number_Nova_Double_Nova_str->nova_Nova_String_Nova_chars->nova_datastruct_list_Nova_Array_Nova_data), &l1_Nova_pEnd);
}

double nova_primitive_number_Nova_Double_0_Nova_compareTo(nova_primitive_number_Nova_Double* this, nova_exception_Nova_ExceptionData* exceptionData, double nova_primitive_number_Nova_Double_Nova_other)
{
	return this->nova_primitive_number_Nova_Double_Nova_value - nova_primitive_number_Nova_Double_Nova_other;
}

double nova_primitive_number_Nova_Double_0_Nova_multiply(nova_primitive_number_Nova_Double* this, nova_exception_Nova_ExceptionData* exceptionData, double nova_primitive_number_Nova_Double_Nova_value)
{
	return this->nova_primitive_number_Nova_Double_Nova_value * nova_primitive_number_Nova_Double_Nova_value;
}

void nova_primitive_number_Nova_Double_2_Nova_super(nova_primitive_number_Nova_Double* this, nova_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_primitive_number_Nova_Double_Nova_value = 0;
}

