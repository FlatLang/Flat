#include <precompiled.h>
#include <nova/standard/math/nova_standard_math_Nova_Polynomial.h>

nova_standard_math_Extension_VTable_Polynomial nova_standard_math_Extension_VTable_Polynomial_val =
{
	{
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
		(char(*)(nova_standard_operators_Nova_Equals*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*))nova_standard_Nova_Object_0_Nova_equals,
		0,
		0,
		0,
	},
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_Nova_Object_0_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
};



char nova_standard_math_Nova_Polynomial_Nova_isLetter(nova_standard_math_Nova_Polynomial* this, nova_standard_exception_Nova_ExceptionData* exceptionData, char nova_standard_math_Nova_Polynomial_Nova_c);
char nova_standard_math_Nova_Polynomial_Nova_isSymbol(nova_standard_math_Nova_Polynomial* this, nova_standard_exception_Nova_ExceptionData* exceptionData, char nova_standard_math_Nova_Polynomial_Nova_c);
char nova_standard_math_Nova_Polynomial_Nova_isWhitespace(nova_standard_math_Nova_Polynomial* this, nova_standard_exception_Nova_ExceptionData* exceptionData, char nova_standard_math_Nova_Polynomial_Nova_c);
char nova_standard_math_Nova_Polynomial_Nova_contains(nova_standard_math_Nova_Polynomial* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_datastruct_list_Nova_CharArray* nova_standard_math_Nova_Polynomial_Nova_array, char nova_standard_math_Nova_Polynomial_Nova_c);
nova_standard_datastruct_list_Nova_CharArray* generated6(nova_standard_math_Nova_Polynomial* this, nova_standard_exception_Nova_ExceptionData* exceptionData);
nova_standard_datastruct_list_Nova_CharArray* generated7(nova_standard_math_Nova_Polynomial* this, nova_standard_exception_Nova_ExceptionData* exceptionData);
nova_standard_datastruct_list_Nova_CharArray* nova_standard_math_Nova_Polynomial_Nova_SYMBOLS_CHARS;
nova_standard_datastruct_list_Nova_CharArray* nova_standard_math_Nova_Polynomial_Nova_WHITESPACE_CHARS;
void nova_standard_math_Nova_PolynomialNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
		nova_standard_math_Nova_Polynomial_Nova_SYMBOLS_CHARS = generated6(0, exceptionData);
		nova_standard_math_Nova_Polynomial_Nova_WHITESPACE_CHARS = generated7(0, exceptionData);
	}
}

nova_standard_math_Nova_Polynomial* nova_standard_math_Nova_Polynomial_Nova_Polynomial(nova_standard_math_Nova_Polynomial* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* nova_standard_math_Nova_Polynomial_Nova_polynomial)
{
	CCLASS_NEW(nova_standard_math_Nova_Polynomial, this,);
	this->vtable = &nova_standard_math_Extension_VTable_Polynomial_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_math_Nova_Polynomial_Nova_super(this, exceptionData);
	
	{
		nova_standard_math_Nova_Polynomial_Nova_this(this, exceptionData, nova_standard_math_Nova_Polynomial_Nova_polynomial);
	}
	
	return this;
}

void nova_standard_math_Nova_Polynomial_Nova_destroy(nova_standard_math_Nova_Polynomial** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_standard_datastruct_list_Nova_DoubleArray_Nova_destroy(&(*this)->nova_standard_math_Nova_Polynomial_Nova_coefficients, exceptionData);
	nova_standard_datastruct_list_Nova_DoubleArray_Nova_destroy(&(*this)->nova_standard_math_Nova_Polynomial_Nova_degrees, exceptionData);
	nova_standard_datastruct_list_Nova_Array_Nova_destroy(&(*this)->nova_standard_math_Nova_Polynomial_Nova_signs, exceptionData);
	
	NOVA_FREE(*this);
}

void nova_standard_math_Nova_Polynomial_Nova_this(nova_standard_math_Nova_Polynomial* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* nova_standard_math_Nova_Polynomial_Nova_polynomial)
{
	int l2_Nova_start = 0;
	char l2_Nova_reading = 0;
	int l4_Nova_i = 0;
	
	l2_Nova_start = (int)(0);
	l2_Nova_reading = 0;
	this->nova_standard_math_Nova_Polynomial_Nova_coefficients = nova_standard_datastruct_list_Nova_DoubleArray_0_Nova_DoubleArray(0, exceptionData);
	this->nova_standard_math_Nova_Polynomial_Nova_degrees = nova_standard_datastruct_list_Nova_DoubleArray_0_Nova_DoubleArray(0, exceptionData);
	this->nova_standard_math_Nova_Polynomial_Nova_signs = nova_standard_datastruct_list_Nova_Array_0_Nova_Array(0, exceptionData);
	l4_Nova_i = (int)0;
	for (; l4_Nova_i < (int)nova_standard_math_Nova_Polynomial_Nova_polynomial->nova_standard_Nova_String_Nova_count; l4_Nova_i++)
	{
		if (!nova_standard_math_Nova_Polynomial_Nova_isLetter(0, exceptionData, (char)(intptr_t)(nova_standard_datastruct_list_Nova_Array_virtual1_Nova_get((nova_standard_datastruct_list_Nova_Array*)(nova_standard_math_Nova_Polynomial_Nova_polynomial->nova_standard_Nova_String_Nova_chars), exceptionData, l4_Nova_i))))
		{
			l2_Nova_reading = 0;
		}
		else if (!l2_Nova_reading)
		{
			l2_Nova_start = l4_Nova_i;
			l2_Nova_reading = 1;
		}
		else
		{
		}
	}
}

char nova_standard_math_Nova_Polynomial_Nova_isLetter(nova_standard_math_Nova_Polynomial* this, nova_standard_exception_Nova_ExceptionData* exceptionData, char nova_standard_math_Nova_Polynomial_Nova_c)
{
	return !nova_standard_math_Nova_Polynomial_Nova_isSymbol(0, exceptionData, nova_standard_math_Nova_Polynomial_Nova_c) && !nova_standard_math_Nova_Polynomial_Nova_isWhitespace(0, exceptionData, nova_standard_math_Nova_Polynomial_Nova_c);
}

char nova_standard_math_Nova_Polynomial_Nova_isSymbol(nova_standard_math_Nova_Polynomial* this, nova_standard_exception_Nova_ExceptionData* exceptionData, char nova_standard_math_Nova_Polynomial_Nova_c)
{
	return nova_standard_math_Nova_Polynomial_Nova_contains(0, exceptionData, nova_standard_math_Nova_Polynomial_Nova_SYMBOLS_CHARS, nova_standard_math_Nova_Polynomial_Nova_c);
}

char nova_standard_math_Nova_Polynomial_Nova_isWhitespace(nova_standard_math_Nova_Polynomial* this, nova_standard_exception_Nova_ExceptionData* exceptionData, char nova_standard_math_Nova_Polynomial_Nova_c)
{
	return nova_standard_math_Nova_Polynomial_Nova_contains(0, exceptionData, nova_standard_math_Nova_Polynomial_Nova_WHITESPACE_CHARS, nova_standard_math_Nova_Polynomial_Nova_c);
}

char nova_standard_math_Nova_Polynomial_Nova_contains(nova_standard_math_Nova_Polynomial* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_datastruct_list_Nova_CharArray* nova_standard_math_Nova_Polynomial_Nova_array, char nova_standard_math_Nova_Polynomial_Nova_c)
{
	int l2_Nova_i = 0;
	
	l2_Nova_i = (int)0;
	for (; l2_Nova_i < (int)nova_standard_math_Nova_Polynomial_Nova_array->nova_standard_datastruct_list_Nova_Array_Nova_count; l2_Nova_i++)
	{
		if (nova_standard_math_Nova_Polynomial_Nova_c == (char)(intptr_t)nova_standard_math_Nova_Polynomial_Nova_array->nova_standard_datastruct_list_Nova_Array_Nova_data[l2_Nova_i])
		{
			return 1;
		}
	}
	return 0;
}

nova_standard_datastruct_list_Nova_CharArray* generated6(nova_standard_math_Nova_Polynomial* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	char* l1_Nova_temp = (char*)nova_null;
	
	l1_Nova_temp = (char*)NOVA_MALLOC(sizeof(nova_standard_primitive_number_Nova_Char) * 23);
	l1_Nova_temp[0] = '-';
	l1_Nova_temp[1] = '+';
	l1_Nova_temp[2] = '~';
	l1_Nova_temp[3] = '!';
	l1_Nova_temp[4] = '=';
	l1_Nova_temp[5] = '%';
	l1_Nova_temp[6] = '^';
	l1_Nova_temp[7] = '&';
	l1_Nova_temp[8] = '|';
	l1_Nova_temp[9] = '*';
	l1_Nova_temp[10] = '/';
	l1_Nova_temp[11] = '>';
	l1_Nova_temp[12] = '<';
	l1_Nova_temp[13] = ',';
	l1_Nova_temp[14] = '"';
	l1_Nova_temp[15] = '\'';
	l1_Nova_temp[16] = '[';
	l1_Nova_temp[17] = ']';
	l1_Nova_temp[18] = '{';
	l1_Nova_temp[19] = '}';
	l1_Nova_temp[20] = ';';
	l1_Nova_temp[21] = '(';
	l1_Nova_temp[22] = ')';
	return nova_standard_datastruct_list_Nova_CharArray_2_Nova_CharArray(0, exceptionData, l1_Nova_temp, 23);
}

nova_standard_datastruct_list_Nova_CharArray* generated7(nova_standard_math_Nova_Polynomial* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	char* l1_Nova_temp = (char*)nova_null;
	
	l1_Nova_temp = (char*)NOVA_MALLOC(sizeof(nova_standard_primitive_number_Nova_Char) * 4);
	l1_Nova_temp[0] = ' ';
	l1_Nova_temp[1] = '\n';
	l1_Nova_temp[2] = '\r';
	l1_Nova_temp[3] = '\t';
	return nova_standard_datastruct_list_Nova_CharArray_2_Nova_CharArray(0, exceptionData, l1_Nova_temp, 4);
}

void nova_standard_math_Nova_Polynomial_Nova_super(nova_standard_math_Nova_Polynomial* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_standard_math_Nova_Polynomial_Nova_coefficients = (nova_standard_datastruct_list_Nova_DoubleArray*)nova_null;
	this->nova_standard_math_Nova_Polynomial_Nova_degrees = (nova_standard_datastruct_list_Nova_DoubleArray*)nova_null;
	this->nova_standard_math_Nova_Polynomial_Nova_signs = (nova_standard_datastruct_list_Nova_Array*)nova_null;
}

