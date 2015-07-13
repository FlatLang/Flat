#include <precompiled.h>
#include <nova/standard/primitive/number/nova_standard_primitive_number_Nova_Double.h>


nova_standard_primitive_number_Extension_VTable_Double nova_standard_primitive_number_Extension_VTable_Double_val =
{
	{
		(nova_standard_Nova_Object*(*)(nova_standard_operators_Nova_Multipliable*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*))nova_standard_primitive_number_Nova_Double_1_Nova_multiply,
		0,
		0,
		0,
		0,
		(nova_standard_Nova_Object*(*)(nova_standard_datastruct_Nova_Comparable*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*))nova_standard_primitive_number_Nova_Double_0_Nova_compareTo,
		0,
		0,
		0,
		0,
	},
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_primitive_number_Nova_Double_2_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
	nova_standard_primitive_number_Nova_Double_0_Nova_numDigits,
	nova_standard_primitive_number_Nova_Double_0_Nova_compareTo,
	nova_standard_primitive_number_Nova_Double_1_Nova_multiply,
};


void nova_standard_primitive_number_Nova_DoubleNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

nova_standard_primitive_number_Nova_Double* nova_standard_primitive_number_Nova_Double_Nova_construct(nova_standard_primitive_number_Nova_Double* this, nova_standard_exception_Nova_ExceptionData* exceptionData, double l0_Nova_value)
{
	CCLASS_NEW(nova_standard_primitive_number_Nova_Double, this,);
	this->vtable = &nova_standard_primitive_number_Extension_VTable_Double_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_primitive_Nova_Primitive_Nova_super((nova_standard_primitive_Nova_Primitive*)this, exceptionData);
	nova_standard_primitive_number_Nova_Number_2_Nova_super((nova_standard_primitive_number_Nova_Number*)this, exceptionData);
	nova_standard_Nova_Object_2_Nova_this((nova_standard_Nova_Object*)(this), exceptionData);
	nova_standard_primitive_Nova_Primitive_4_Nova_this((nova_standard_primitive_Nova_Primitive*)(this), exceptionData);
	nova_standard_primitive_number_Nova_Number_7_Nova_this((nova_standard_primitive_number_Nova_Number*)(this), exceptionData);
	nova_standard_primitive_number_Nova_Double_4_Nova_super(this, exceptionData);
	
	{
		nova_standard_primitive_number_Nova_Double_Nova_this(this, exceptionData, l0_Nova_value);
	}
	
	return this;
}

void nova_standard_primitive_number_Nova_Double_Nova_destroy(nova_standard_primitive_number_Nova_Double** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	
	NOVA_FREE(*this);
}

void nova_standard_primitive_number_Nova_Double_Nova_this(nova_standard_primitive_number_Nova_Double* this, nova_standard_exception_Nova_ExceptionData* exceptionData, double l0_Nova_value)
{
	this->nova_standard_primitive_number_Nova_Double_Nova_value = l0_Nova_value;
}

int nova_standard_primitive_number_Nova_Double_0_Nova_numDigits(nova_standard_primitive_number_Nova_Double* this, nova_standard_exception_Nova_ExceptionData* exceptionData, double l0_Nova_number)
{
	int l1_Nova_size;
	
	if (l0_Nova_number < 0)
	{
		return nova_standard_primitive_number_Nova_Double_0_Nova_numDigits((nova_standard_primitive_number_Nova_Double*)nova_null, exceptionData, -l0_Nova_number) + 1;
	}
	l0_Nova_number = l0_Nova_number / 10;
	l1_Nova_size = 1;
	for (; l0_Nova_number > 0; l1_Nova_size++)
	{
		l0_Nova_number = l0_Nova_number / 10;
	}
	return l1_Nova_size;
}

nova_standard_Nova_String* nova_standard_primitive_number_Nova_Double_Nova_genString(nova_standard_primitive_number_Nova_Double* this, nova_standard_exception_Nova_ExceptionData* exceptionData, char* l0_Nova_buffer, int l0_Nova_lastIndex)
{
	l0_Nova_buffer = (char*)(realloc((char*)(l0_Nova_buffer), (int)(++l0_Nova_lastIndex + 1)));
	l0_Nova_buffer[l0_Nova_lastIndex] = '\0';
	return nova_standard_Nova_String_1_Nova_construct(0, exceptionData, l0_Nova_buffer);
}

char* nova_standard_primitive_number_Nova_Double_Nova_genBuffer(nova_standard_primitive_number_Nova_Double* this, nova_standard_exception_Nova_ExceptionData* exceptionData, double l0_Nova_value)
{
	int l1_Nova_size;
	char* l1_Nova_buffer;
	
	l1_Nova_size = 11 + 1 + 15;
	l1_Nova_buffer = (char*)NOVA_MALLOC(sizeof(nova_standard_primitive_number_Nova_Char) * l1_Nova_size);
	sprintf((char*)(l1_Nova_buffer), (char*)("%.15f"), (double)(l0_Nova_value));
	return l1_Nova_buffer;
}

int nova_standard_primitive_number_Nova_Double_Nova_repetition(nova_standard_primitive_number_Nova_Double* this, nova_standard_exception_Nova_ExceptionData* exceptionData, char* l0_Nova_buffer, int l0_Nova_start)
{
	int l1_Nova_index;
	char l1_Nova_c;
	
	l1_Nova_index = l0_Nova_start;
	l1_Nova_c = l0_Nova_buffer[l1_Nova_index];
	while (l0_Nova_buffer[--l1_Nova_index] == l1_Nova_c);
	return l0_Nova_start - l1_Nova_index - 1;
}

int nova_standard_primitive_number_Nova_Double_Nova_lastSignificantDigit(nova_standard_primitive_number_Nova_Double* this, nova_standard_exception_Nova_ExceptionData* exceptionData, char* l0_Nova_buffer, int l0_Nova_start)
{
	while (l0_Nova_buffer[l0_Nova_start--] == '0');
	return l0_Nova_start + 1;
}

nova_standard_Nova_String* nova_standard_primitive_number_Nova_Double_1_Nova_toString(nova_standard_primitive_number_Nova_Double* this, nova_standard_exception_Nova_ExceptionData* exceptionData, double l0_Nova_value)
{
	char* l1_Nova_buffer;
	int l1_Nova_size;
	int l1_Nova_lastIndex;
	char l1_Nova_c;
	
	l1_Nova_buffer = nova_standard_primitive_number_Nova_Double_Nova_genBuffer((nova_standard_primitive_number_Nova_Double*)nova_null, exceptionData, l0_Nova_value);
	l1_Nova_size = (int)(strlen((char*)(l1_Nova_buffer)));
	l1_Nova_lastIndex = l1_Nova_size - 1;
	l1_Nova_c = l1_Nova_buffer[--l1_Nova_lastIndex];
	if (l1_Nova_c == '0' || l1_Nova_c == '9')
	{
		while (l1_Nova_buffer[l1_Nova_lastIndex--] == l1_Nova_c);
		if (l1_Nova_buffer[++l1_Nova_lastIndex] == '.')
		{
			l1_Nova_lastIndex++;
			return nova_standard_primitive_number_Nova_Double_Nova_genString((nova_standard_primitive_number_Nova_Double*)nova_null, exceptionData, l1_Nova_buffer, l1_Nova_lastIndex);
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
				return nova_standard_primitive_number_Nova_Double_Nova_genString((nova_standard_primitive_number_Nova_Double*)nova_null, exceptionData, l1_Nova_buffer, l1_Nova_lastIndex);
			}
		}
	}
	else
	{
		int l8_Nova_rep;
		
		l8_Nova_rep = nova_standard_primitive_number_Nova_Double_Nova_repetition((nova_standard_primitive_number_Nova_Double*)nova_null, exceptionData, l1_Nova_buffer, l1_Nova_lastIndex);
		if (l8_Nova_rep > 5)
		{
			l1_Nova_buffer[l1_Nova_lastIndex] = l1_Nova_c;
			if (l1_Nova_c >= '5')
			{
				l1_Nova_c++;
			}
			l1_Nova_buffer[++l1_Nova_lastIndex] = l1_Nova_c;
			return nova_standard_primitive_number_Nova_Double_Nova_genString((nova_standard_primitive_number_Nova_Double*)nova_null, exceptionData, l1_Nova_buffer, l1_Nova_lastIndex);
		}
	}
	l1_Nova_lastIndex = nova_standard_primitive_number_Nova_Double_Nova_lastSignificantDigit((nova_standard_primitive_number_Nova_Double*)nova_null, exceptionData, l1_Nova_buffer, l1_Nova_size - 1);
	return nova_standard_primitive_number_Nova_Double_Nova_genString((nova_standard_primitive_number_Nova_Double*)nova_null, exceptionData, l1_Nova_buffer, l1_Nova_lastIndex);
}

nova_standard_Nova_String* nova_standard_primitive_number_Nova_Double_2_Nova_toString(nova_standard_primitive_number_Nova_Double* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return nova_standard_primitive_number_Nova_Double_1_Nova_toString(this, exceptionData, this->nova_standard_primitive_number_Nova_Double_Nova_value);
}

double nova_standard_primitive_number_Nova_Double_Nova_parseDouble(nova_standard_primitive_number_Nova_Double* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* l0_Nova_str)
{
	char* l1_Nova_pEnd;
	
	return (double)strtod((char*)(l0_Nova_str->nova_standard_Nova_String_Nova_chars), (char**)&(l1_Nova_pEnd));
}

double nova_standard_primitive_number_Nova_Double_0_Nova_compareTo(nova_standard_primitive_number_Nova_Double* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_primitive_number_Nova_Double* l0_Nova_prim)
{
	double l1_Nova_other;
	
	l1_Nova_other = l0_Nova_prim->nova_standard_primitive_number_Nova_Double_Nova_value;
	return this->nova_standard_primitive_number_Nova_Double_Nova_value - l1_Nova_other;
}

double nova_standard_primitive_number_Nova_Double_1_Nova_multiply(nova_standard_primitive_number_Nova_Double* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_primitive_number_Nova_Double* l0_Nova_prim)
{
	double l1_Nova_value;
	
	l1_Nova_value = l0_Nova_prim->nova_standard_primitive_number_Nova_Double_Nova_value;
	return this->nova_standard_primitive_number_Nova_Double_Nova_value * l1_Nova_value;
}

void nova_standard_primitive_number_Nova_Double_4_Nova_super(nova_standard_primitive_number_Nova_Double* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_standard_primitive_number_Nova_Double_Nova_value = 0;
}

