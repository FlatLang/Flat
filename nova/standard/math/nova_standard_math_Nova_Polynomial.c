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
char nova_standard_math_Nova_Polynomial_Nova_contains(nova_standard_math_Nova_Polynomial* this, nova_standard_exception_Nova_ExceptionData* exceptionData, char* nova_standard_math_Nova_Polynomial_Nova_array, char nova_standard_math_Nova_Polynomial_Nova_c, int nova_standard_math_Nova_Polynomial_Nova_length);
int nova_standard_math_Nova_Polynomial_Nova_symbolsLength;
char* nova_standard_math_Nova_Polynomial_Nova_SYMBOLS_CHARS;
int nova_standard_math_Nova_Polynomial_Nova_whitespaceLength;
char* nova_standard_math_Nova_Polynomial_Nova_WHITESPACE_CHARS;
void nova_standard_math_Nova_PolynomialNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
		nova_standard_math_Nova_Polynomial_Nova_symbolsLength = (int)(23);
		nova_standard_math_Nova_Polynomial_Nova_SYMBOLS_CHARS = (char*)NOVA_MALLOC(sizeof(nova_standard_primitive_number_Nova_Char) * 23);
		nova_standard_math_Nova_Polynomial_Nova_SYMBOLS_CHARS[0] = '-';
		nova_standard_math_Nova_Polynomial_Nova_SYMBOLS_CHARS[1] = '+';
		nova_standard_math_Nova_Polynomial_Nova_SYMBOLS_CHARS[2] = '~';
		nova_standard_math_Nova_Polynomial_Nova_SYMBOLS_CHARS[3] = '!';
		nova_standard_math_Nova_Polynomial_Nova_SYMBOLS_CHARS[4] = '=';
		nova_standard_math_Nova_Polynomial_Nova_SYMBOLS_CHARS[5] = '%';
		nova_standard_math_Nova_Polynomial_Nova_SYMBOLS_CHARS[6] = '^';
		nova_standard_math_Nova_Polynomial_Nova_SYMBOLS_CHARS[7] = '&';
		nova_standard_math_Nova_Polynomial_Nova_SYMBOLS_CHARS[8] = '|';
		nova_standard_math_Nova_Polynomial_Nova_SYMBOLS_CHARS[9] = '*';
		nova_standard_math_Nova_Polynomial_Nova_SYMBOLS_CHARS[10] = '/';
		nova_standard_math_Nova_Polynomial_Nova_SYMBOLS_CHARS[11] = '>';
		nova_standard_math_Nova_Polynomial_Nova_SYMBOLS_CHARS[12] = '<';
		nova_standard_math_Nova_Polynomial_Nova_SYMBOLS_CHARS[13] = ',';
		nova_standard_math_Nova_Polynomial_Nova_SYMBOLS_CHARS[14] = '"';
		nova_standard_math_Nova_Polynomial_Nova_SYMBOLS_CHARS[15] = '\'';
		nova_standard_math_Nova_Polynomial_Nova_SYMBOLS_CHARS[16] = '[';
		nova_standard_math_Nova_Polynomial_Nova_SYMBOLS_CHARS[17] = ']';
		nova_standard_math_Nova_Polynomial_Nova_SYMBOLS_CHARS[18] = '{';
		nova_standard_math_Nova_Polynomial_Nova_SYMBOLS_CHARS[19] = '}';
		nova_standard_math_Nova_Polynomial_Nova_SYMBOLS_CHARS[20] = ';';
		nova_standard_math_Nova_Polynomial_Nova_SYMBOLS_CHARS[21] = '(';
		nova_standard_math_Nova_Polynomial_Nova_SYMBOLS_CHARS[22] = ')';
		nova_standard_math_Nova_Polynomial_Nova_whitespaceLength = (int)(4);
		nova_standard_math_Nova_Polynomial_Nova_WHITESPACE_CHARS = (char*)NOVA_MALLOC(sizeof(nova_standard_primitive_number_Nova_Char) * 4);
		nova_standard_math_Nova_Polynomial_Nova_WHITESPACE_CHARS[0] = ' ';
		nova_standard_math_Nova_Polynomial_Nova_WHITESPACE_CHARS[1] = '\n';
		nova_standard_math_Nova_Polynomial_Nova_WHITESPACE_CHARS[2] = '\r';
		nova_standard_math_Nova_Polynomial_Nova_WHITESPACE_CHARS[3] = '\t';
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
	
	
	
	
	
	NOVA_FREE(*this);
}

void nova_standard_math_Nova_Polynomial_Nova_this(nova_standard_math_Nova_Polynomial* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* nova_standard_math_Nova_Polynomial_Nova_polynomial)
{
	int l2_Nova_start = 0;
	char l2_Nova_reading = 0;
	nova_standard_datastruct_list_Nova_Array* l2_Nova_coeffs = (nova_standard_datastruct_list_Nova_Array*)nova_null;
	nova_standard_datastruct_list_Nova_Array* l2_Nova_degrees = (nova_standard_datastruct_list_Nova_Array*)nova_null;
	nova_standard_datastruct_list_Nova_Array* l2_Nova_signs = (nova_standard_datastruct_list_Nova_Array*)nova_null;
	int l4_Nova_i = 0;
	
	l2_Nova_start = (int)(0);
	l2_Nova_reading = 0;
	l2_Nova_coeffs = nova_standard_datastruct_list_Nova_Array_0_Nova_Array(0, exceptionData);
	l2_Nova_degrees = nova_standard_datastruct_list_Nova_Array_0_Nova_Array(0, exceptionData);
	l2_Nova_signs = nova_standard_datastruct_list_Nova_Array_0_Nova_Array(0, exceptionData);
	l4_Nova_i = (int)0;
	for (; l4_Nova_i < (int)nova_standard_math_Nova_Polynomial_Nova_polynomial->nova_standard_Nova_String_Nova_count; l4_Nova_i++)
	{
		if (!nova_standard_math_Nova_Polynomial_Nova_isLetter(0, exceptionData, nova_standard_math_Nova_Polynomial_Nova_polynomial->nova_standard_Nova_String_Nova_chars[l4_Nova_i]))
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
	return nova_standard_math_Nova_Polynomial_Nova_contains(0, exceptionData, nova_standard_math_Nova_Polynomial_Nova_SYMBOLS_CHARS, nova_standard_math_Nova_Polynomial_Nova_c, nova_standard_math_Nova_Polynomial_Nova_symbolsLength);
}

char nova_standard_math_Nova_Polynomial_Nova_isWhitespace(nova_standard_math_Nova_Polynomial* this, nova_standard_exception_Nova_ExceptionData* exceptionData, char nova_standard_math_Nova_Polynomial_Nova_c)
{
	return nova_standard_math_Nova_Polynomial_Nova_contains(0, exceptionData, nova_standard_math_Nova_Polynomial_Nova_WHITESPACE_CHARS, nova_standard_math_Nova_Polynomial_Nova_c, nova_standard_math_Nova_Polynomial_Nova_whitespaceLength);
}

char nova_standard_math_Nova_Polynomial_Nova_contains(nova_standard_math_Nova_Polynomial* this, nova_standard_exception_Nova_ExceptionData* exceptionData, char* nova_standard_math_Nova_Polynomial_Nova_array, char nova_standard_math_Nova_Polynomial_Nova_c, int nova_standard_math_Nova_Polynomial_Nova_length)
{
	int l2_Nova_i = 0;
	
	l2_Nova_i = (int)0;
	for (; l2_Nova_i < (int)nova_standard_math_Nova_Polynomial_Nova_length; l2_Nova_i++)
	{
		if (nova_standard_math_Nova_Polynomial_Nova_c == nova_standard_math_Nova_Polynomial_Nova_array[l2_Nova_i])
		{
			return 1;
		}
	}
	return 0;
}

void nova_standard_math_Nova_Polynomial_Nova_super(nova_standard_math_Nova_Polynomial* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	this->nova_standard_math_Nova_Polynomial_Nova_coefficients = 0;
	this->nova_standard_math_Nova_Polynomial_Nova_degrees = 0;
	this->nova_standard_math_Nova_Polynomial_Nova_signs = 0;
}

