#include <precompiled.h>
#include <nova/standard/math/nova_standard_math_Nova_NumericOperation.h>

nova_standard_math_Extension_VTable_NumericOperation nova_standard_math_Extension_VTable_NumericOperation_val =
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
		0,
		0,
		(char(*)(nova_standard_operators_Nova_Equals*, nova_standard_exception_Nova_ExceptionData*, nova_standard_Nova_Object*))nova_standard_Nova_Object_0_Nova_equals,
		0,
		0,
		0,
	},
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_math_Nova_NumericOperation_1_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
};


CCLASS_PRIVATE
(
	nova_standard_Nova_Object* nova_standard_math_Nova_NumericOperand_Nova_data;
	
	nova_standard_math_Nova_NumericOperand* nova_standard_math_Nova_NumericOperation_Nova_leftOperand;
	nova_standard_math_Nova_NumericOperand* nova_standard_math_Nova_NumericOperation_Nova_rightOperand;
	nova_standard_Nova_String* nova_standard_math_Nova_NumericOperation_Nova_operator;
	
)

nova_standard_math_Nova_NumericOperand* nova_standard_math_Nova_NumericOperation_Nova_decodeOperand(nova_standard_math_Nova_NumericOperation* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* nova_standard_math_Nova_NumericOperation_Nova_operand);
int nova_standard_math_Nova_NumericOperation_0_Nova_getType(nova_standard_math_Nova_NumericOperation* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* nova_standard_math_Nova_NumericOperation_Nova_s);
int nova_standard_math_Nova_NumericOperation_1_Nova_getType(nova_standard_math_Nova_NumericOperation* this, nova_standard_exception_Nova_ExceptionData* exceptionData, char nova_standard_math_Nova_NumericOperation_Nova_c);
nova_standard_datastruct_Nova_Bounds* nova_standard_math_Nova_NumericOperation_Nova_searchNextType(nova_standard_math_Nova_NumericOperation* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* nova_standard_math_Nova_NumericOperation_Nova_str, int nova_standard_math_Nova_NumericOperation_Nova_start, int* ret1);
int nova_standard_math_Nova_NumericOperation_Nova_nextOperator(nova_standard_math_Nova_NumericOperation* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* nova_standard_math_Nova_NumericOperation_Nova_str, int nova_standard_math_Nova_NumericOperation_Nova_start);
int nova_standard_math_Nova_NumericOperation_Nova_getOperatorRank(nova_standard_math_Nova_NumericOperation* this, nova_standard_exception_Nova_ExceptionData* exceptionData, char nova_standard_math_Nova_NumericOperation_Nova_operator);
int nova_standard_math_Nova_NumericOperation_Nova_nextNonWhitespaceIndex(nova_standard_math_Nova_NumericOperation* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* nova_standard_math_Nova_NumericOperation_Nova_str, int nova_standard_math_Nova_NumericOperation_Nova_start, int nova_standard_math_Nova_NumericOperation_Nova_direction);
char nova_standard_math_Nova_NumericOperation_Nova_isLetter(nova_standard_math_Nova_NumericOperation* this, nova_standard_exception_Nova_ExceptionData* exceptionData, char nova_standard_math_Nova_NumericOperation_Nova_c);
char nova_standard_math_Nova_NumericOperation_Nova_isNumeric(nova_standard_math_Nova_NumericOperation* this, nova_standard_exception_Nova_ExceptionData* exceptionData, char nova_standard_math_Nova_NumericOperation_Nova_c);
char nova_standard_math_Nova_NumericOperation_Nova_isOperator(nova_standard_math_Nova_NumericOperation* this, nova_standard_exception_Nova_ExceptionData* exceptionData, char nova_standard_math_Nova_NumericOperation_Nova_c);
char nova_standard_math_Nova_NumericOperation_Nova_isWhitespace(nova_standard_math_Nova_NumericOperation* this, nova_standard_exception_Nova_ExceptionData* exceptionData, char nova_standard_math_Nova_NumericOperation_Nova_c);
char nova_standard_math_Nova_NumericOperation_Nova_contains(nova_standard_math_Nova_NumericOperation* this, nova_standard_exception_Nova_ExceptionData* exceptionData, char* nova_standard_math_Nova_NumericOperation_Nova_array, char nova_standard_math_Nova_NumericOperation_Nova_c, int nova_standard_math_Nova_NumericOperation_Nova_length);
int nova_standard_math_Nova_NumericOperation_Nova_GROUPING;
int nova_standard_math_Nova_NumericOperation_Nova_EXPONENT;
int nova_standard_math_Nova_NumericOperation_Nova_MULTIPLICATION;
int nova_standard_math_Nova_NumericOperation_Nova_DIVISION;
int nova_standard_math_Nova_NumericOperation_Nova_ADDITION;
int nova_standard_math_Nova_NumericOperation_Nova_SUBTRACTION;
int nova_standard_math_Nova_NumericOperation_Nova_MAX;
int nova_standard_math_Nova_NumericOperation_Nova_INVALID;
int nova_standard_math_Nova_NumericOperation_Nova_OPERATOR;
int nova_standard_math_Nova_NumericOperation_Nova_operatorsLength;
int nova_standard_math_Nova_NumericOperation_Nova_invalidOperatorsLength;
char* nova_standard_math_Nova_NumericOperation_Nova_VALID_OPERATORS;
char* nova_standard_math_Nova_NumericOperation_Nova_INVALID_OPERATORS;
int nova_standard_math_Nova_NumericOperation_Nova_whitespaceLength;
char* nova_standard_math_Nova_NumericOperation_Nova_WHITESPACE_CHARS;
int nova_standard_math_Nova_NumericOperation_Nova_NUMBER;
int nova_standard_math_Nova_NumericOperation_Nova_numbersLength;
char* nova_standard_math_Nova_NumericOperation_Nova_NUMERIC_CHARS;
int nova_standard_math_Nova_NumericOperation_Nova_VARIABLE;
void nova_standard_math_Nova_NumericOperationNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
		nova_standard_math_Nova_NumericOperation_Nova_GROUPING = 1;
		nova_standard_math_Nova_NumericOperation_Nova_EXPONENT = 2;
		nova_standard_math_Nova_NumericOperation_Nova_MULTIPLICATION = 3;
		nova_standard_math_Nova_NumericOperation_Nova_DIVISION = 3;
		nova_standard_math_Nova_NumericOperation_Nova_ADDITION = 4;
		nova_standard_math_Nova_NumericOperation_Nova_SUBTRACTION = 4;
		nova_standard_math_Nova_NumericOperation_Nova_MAX = 5;
		nova_standard_math_Nova_NumericOperation_Nova_INVALID = -1;
		nova_standard_math_Nova_NumericOperation_Nova_OPERATOR = 1;
		nova_standard_math_Nova_NumericOperation_Nova_operatorsLength = 14;
		nova_standard_math_Nova_NumericOperation_Nova_invalidOperatorsLength = 16;
		nova_standard_math_Nova_NumericOperation_Nova_VALID_OPERATORS = (char*)NOVA_MALLOC(sizeof(nova_standard_primitive_number_Nova_Char) * 14);
		nova_standard_math_Nova_NumericOperation_Nova_VALID_OPERATORS[0] = '-';
		nova_standard_math_Nova_NumericOperation_Nova_VALID_OPERATORS[1] = '+';
		nova_standard_math_Nova_NumericOperation_Nova_VALID_OPERATORS[2] = '!';
		nova_standard_math_Nova_NumericOperation_Nova_VALID_OPERATORS[3] = '=';
		nova_standard_math_Nova_NumericOperation_Nova_VALID_OPERATORS[4] = '%';
		nova_standard_math_Nova_NumericOperation_Nova_VALID_OPERATORS[5] = '^';
		nova_standard_math_Nova_NumericOperation_Nova_VALID_OPERATORS[6] = '*';
		nova_standard_math_Nova_NumericOperation_Nova_VALID_OPERATORS[7] = '/';
		nova_standard_math_Nova_NumericOperation_Nova_VALID_OPERATORS[8] = '>';
		nova_standard_math_Nova_NumericOperation_Nova_VALID_OPERATORS[9] = '<';
		nova_standard_math_Nova_NumericOperation_Nova_VALID_OPERATORS[10] = '[';
		nova_standard_math_Nova_NumericOperation_Nova_VALID_OPERATORS[11] = ']';
		nova_standard_math_Nova_NumericOperation_Nova_VALID_OPERATORS[12] = '(';
		nova_standard_math_Nova_NumericOperation_Nova_VALID_OPERATORS[13] = ')';
		nova_standard_math_Nova_NumericOperation_Nova_INVALID_OPERATORS = (char*)NOVA_MALLOC(sizeof(nova_standard_primitive_number_Nova_Char) * 16);
		nova_standard_math_Nova_NumericOperation_Nova_INVALID_OPERATORS[0] = '`';
		nova_standard_math_Nova_NumericOperation_Nova_INVALID_OPERATORS[1] = '~';
		nova_standard_math_Nova_NumericOperation_Nova_INVALID_OPERATORS[2] = '@';
		nova_standard_math_Nova_NumericOperation_Nova_INVALID_OPERATORS[3] = '#';
		nova_standard_math_Nova_NumericOperation_Nova_INVALID_OPERATORS[4] = '$';
		nova_standard_math_Nova_NumericOperation_Nova_INVALID_OPERATORS[5] = '&';
		nova_standard_math_Nova_NumericOperation_Nova_INVALID_OPERATORS[6] = '_';
		nova_standard_math_Nova_NumericOperation_Nova_INVALID_OPERATORS[7] = ';';
		nova_standard_math_Nova_NumericOperation_Nova_INVALID_OPERATORS[8] = ':';
		nova_standard_math_Nova_NumericOperation_Nova_INVALID_OPERATORS[9] = '\'';
		nova_standard_math_Nova_NumericOperation_Nova_INVALID_OPERATORS[10] = '"';
		nova_standard_math_Nova_NumericOperation_Nova_INVALID_OPERATORS[11] = '|';
		nova_standard_math_Nova_NumericOperation_Nova_INVALID_OPERATORS[12] = '\\';
		nova_standard_math_Nova_NumericOperation_Nova_INVALID_OPERATORS[13] = ',';
		nova_standard_math_Nova_NumericOperation_Nova_INVALID_OPERATORS[14] = '.';
		nova_standard_math_Nova_NumericOperation_Nova_INVALID_OPERATORS[15] = '?';
		nova_standard_math_Nova_NumericOperation_Nova_whitespaceLength = 4;
		nova_standard_math_Nova_NumericOperation_Nova_WHITESPACE_CHARS = (char*)NOVA_MALLOC(sizeof(nova_standard_primitive_number_Nova_Char) * 4);
		nova_standard_math_Nova_NumericOperation_Nova_WHITESPACE_CHARS[0] = ' ';
		nova_standard_math_Nova_NumericOperation_Nova_WHITESPACE_CHARS[1] = '\n';
		nova_standard_math_Nova_NumericOperation_Nova_WHITESPACE_CHARS[2] = '\r';
		nova_standard_math_Nova_NumericOperation_Nova_WHITESPACE_CHARS[3] = '\t';
		nova_standard_math_Nova_NumericOperation_Nova_NUMBER = 3;
		nova_standard_math_Nova_NumericOperation_Nova_numbersLength = 10;
		nova_standard_math_Nova_NumericOperation_Nova_NUMERIC_CHARS = (char*)NOVA_MALLOC(sizeof(nova_standard_primitive_number_Nova_Char) * 10);
		nova_standard_math_Nova_NumericOperation_Nova_NUMERIC_CHARS[0] = '0';
		nova_standard_math_Nova_NumericOperation_Nova_NUMERIC_CHARS[1] = '1';
		nova_standard_math_Nova_NumericOperation_Nova_NUMERIC_CHARS[2] = '2';
		nova_standard_math_Nova_NumericOperation_Nova_NUMERIC_CHARS[3] = '3';
		nova_standard_math_Nova_NumericOperation_Nova_NUMERIC_CHARS[4] = '4';
		nova_standard_math_Nova_NumericOperation_Nova_NUMERIC_CHARS[5] = '5';
		nova_standard_math_Nova_NumericOperation_Nova_NUMERIC_CHARS[6] = '6';
		nova_standard_math_Nova_NumericOperation_Nova_NUMERIC_CHARS[7] = '7';
		nova_standard_math_Nova_NumericOperation_Nova_NUMERIC_CHARS[8] = '8';
		nova_standard_math_Nova_NumericOperation_Nova_NUMERIC_CHARS[9] = '9';
		nova_standard_math_Nova_NumericOperation_Nova_VARIABLE = 4;
	}
}

nova_standard_math_Nova_NumericOperation* nova_standard_math_Nova_NumericOperation_Nova_construct(nova_standard_math_Nova_NumericOperation* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* nova_standard_math_Nova_NumericOperation_Nova_operation)
{
	CCLASS_NEW(nova_standard_math_Nova_NumericOperation, this);
	this->vtable = &nova_standard_math_Extension_VTable_NumericOperation_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_math_Nova_NumericOperand_Nova_super((nova_standard_math_Nova_NumericOperand*)this, exceptionData);
	nova_standard_math_Nova_NumericOperation_0_Nova_super(this, exceptionData);
	
	{
		nova_standard_math_Nova_NumericOperation_Nova_this(this, exceptionData, nova_standard_math_Nova_NumericOperation_Nova_operation);
	}
	
	return this;
}

nova_standard_math_Nova_NumericOperation* nova_standard_math_Nova_NumericOperation_0_Nova_construct(nova_standard_math_Nova_NumericOperation* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* nova_standard_math_Nova_NumericOperation_Nova_left, nova_standard_Nova_String* nova_standard_math_Nova_NumericOperation_Nova_operator, nova_standard_Nova_String* nova_standard_math_Nova_NumericOperation_Nova_right)
{
	CCLASS_NEW(nova_standard_math_Nova_NumericOperation, this);
	this->vtable = &nova_standard_math_Extension_VTable_NumericOperation_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_math_Nova_NumericOperand_Nova_super((nova_standard_math_Nova_NumericOperand*)this, exceptionData);
	nova_standard_math_Nova_NumericOperation_0_Nova_super(this, exceptionData);
	
	{
		nova_standard_math_Nova_NumericOperation_0_Nova_this(this, exceptionData, nova_standard_math_Nova_NumericOperation_Nova_left, nova_standard_math_Nova_NumericOperation_Nova_operator, nova_standard_math_Nova_NumericOperation_Nova_right);
	}
	
	return this;
}

nova_standard_math_Nova_NumericOperation* nova_standard_math_Nova_NumericOperation_1_Nova_construct(nova_standard_math_Nova_NumericOperation* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_math_Nova_NumericOperand* nova_standard_math_Nova_NumericOperation_Nova_left, nova_standard_Nova_String* nova_standard_math_Nova_NumericOperation_Nova_operator, nova_standard_math_Nova_NumericOperand* nova_standard_math_Nova_NumericOperation_Nova_right)
{
	CCLASS_NEW(nova_standard_math_Nova_NumericOperation, this);
	this->vtable = &nova_standard_math_Extension_VTable_NumericOperation_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_math_Nova_NumericOperand_Nova_super((nova_standard_math_Nova_NumericOperand*)this, exceptionData);
	nova_standard_math_Nova_NumericOperation_0_Nova_super(this, exceptionData);
	
	{
		nova_standard_math_Nova_NumericOperation_1_Nova_this(this, exceptionData, nova_standard_math_Nova_NumericOperation_Nova_left, nova_standard_math_Nova_NumericOperation_Nova_operator, nova_standard_math_Nova_NumericOperation_Nova_right);
	}
	
	return this;
}

void nova_standard_math_Nova_NumericOperation_Nova_destroy(nova_standard_math_Nova_NumericOperation** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	nova_standard_math_Nova_NumericOperand_Nova_destroy(&(*this)->prv->nova_standard_math_Nova_NumericOperation_Nova_leftOperand, exceptionData);
	nova_standard_math_Nova_NumericOperand_Nova_destroy(&(*this)->prv->nova_standard_math_Nova_NumericOperation_Nova_rightOperand, exceptionData);
	nova_standard_Nova_String_Nova_destroy(&(*this)->prv->nova_standard_math_Nova_NumericOperation_Nova_operator, exceptionData);
	NOVA_FREE((*this)->prv);
	
	NOVA_FREE(*this);
}

void nova_standard_math_Nova_NumericOperation_Nova_this(nova_standard_math_Nova_NumericOperation* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* nova_standard_math_Nova_NumericOperation_Nova_operation)
{
	nova_standard_datastruct_Nova_Bounds* l2_Nova_bounds1;
	int l2_Nova_index1;
	nova_standard_datastruct_Nova_Bounds* l2_Nova_bounds2;
	int l2_Nova_index2;
	nova_standard_datastruct_Nova_Bounds* l2_Nova_bounds3;
	nova_standard_Nova_String* l2_Nova_lh;
	nova_standard_Nova_String* l2_Nova_op;
	nova_standard_Nova_String* l2_Nova_rh;
	int l2_Nova_type1;
	int l2_Nova_type2;
	nova_standard_Nova_String* nova_local_0;
	nova_standard_Nova_String* nova_local_1;
	nova_standard_Nova_String* nova_local_2;
	
	l2_Nova_bounds1 = nova_standard_math_Nova_NumericOperation_Nova_searchNextType(this, exceptionData, nova_standard_math_Nova_NumericOperation_Nova_operation, 0, &l2_Nova_index1);
	l2_Nova_bounds2 = nova_standard_math_Nova_NumericOperation_Nova_searchNextType(this, exceptionData, nova_standard_math_Nova_NumericOperation_Nova_operation, l2_Nova_bounds1->nova_standard_datastruct_Nova_Bounds_Nova_end, &l2_Nova_index2);
	l2_Nova_bounds3 = (nova_standard_datastruct_Nova_Bounds*)((nova_standard_Nova_Object*)nova_null);
	nova_local_0 = l2_Nova_bounds1->vtable->nova_standard_datastruct_Nova_Bounds_virtual1_Nova_toString(l2_Nova_bounds1, exceptionData);
	nova_local_1 = l2_Nova_bounds2->vtable->nova_standard_datastruct_Nova_Bounds_virtual1_Nova_toString(l2_Nova_bounds2, exceptionData);
	nova_local_2 = nova_standard_primitive_number_Nova_Int_2_Nova_toString(0, exceptionData, l2_Nova_index1);
	nova_standard_io_Nova_Console_1_Nova_writeLine(0, exceptionData, nova_local_0->vtable->nova_standard_Nova_String_virtual0_Nova_concat(nova_local_0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_2_Nova_construct(0, exceptionData, ", "), exceptionData, nova_local_1->vtable->nova_standard_Nova_String_virtual0_Nova_concat(nova_local_1, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_2_Nova_construct(0, exceptionData, " and "), exceptionData, nova_local_2->vtable->nova_standard_Nova_String_virtual0_Nova_concat(nova_local_2, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_2_Nova_construct(0, exceptionData, ", "), exceptionData, nova_standard_primitive_number_Nova_Int_2_Nova_toString(0, exceptionData, l2_Nova_index2))))))));
	l2_Nova_lh = nova_standard_datastruct_Nova_Bounds_Nova_extractString(l2_Nova_bounds1, exceptionData, nova_standard_math_Nova_NumericOperation_Nova_operation);
	l2_Nova_op = nova_standard_datastruct_Nova_Bounds_Nova_extractString(l2_Nova_bounds2, exceptionData, nova_standard_math_Nova_NumericOperation_Nova_operation);
	l2_Nova_rh = (nova_standard_Nova_String*)((nova_standard_Nova_Object*)nova_null);
	l2_Nova_type1 = nova_standard_math_Nova_NumericOperation_0_Nova_getType(this, exceptionData, l2_Nova_lh);
	l2_Nova_type2 = nova_standard_math_Nova_NumericOperation_0_Nova_getType(this, exceptionData, l2_Nova_op);
	if (l2_Nova_type2 != nova_standard_math_Nova_NumericOperation_Nova_OPERATOR)
	{
		if (l2_Nova_type1 == nova_standard_math_Nova_NumericOperation_Nova_OPERATOR)
		{
			THROW(5, nova_standard_math_Nova_InvalidNumericStatementException_4_Nova_construct(0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "Missing left hand operand in operation '"), exceptionData, nova_standard_math_Nova_NumericOperation_Nova_operation->vtable->nova_standard_Nova_String_virtual0_Nova_concat(nova_standard_math_Nova_NumericOperation_Nova_operation, exceptionData, nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "'")))));
		}
		l2_Nova_rh = l2_Nova_op;
		l2_Nova_op = nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "*");
		l2_Nova_bounds3 = nova_standard_datastruct_Nova_Bounds_Nova_clone(l2_Nova_bounds2, exceptionData);
		l2_Nova_bounds2->nova_standard_datastruct_Nova_Bounds_Nova_end = l2_Nova_bounds2->nova_standard_datastruct_Nova_Bounds_Nova_start;
	}
	else
	{
		l2_Nova_bounds3 = nova_standard_math_Nova_NumericOperation_Nova_searchNextType(this, exceptionData, nova_standard_math_Nova_NumericOperation_Nova_operation, l2_Nova_bounds2->nova_standard_datastruct_Nova_Bounds_Nova_end, (int*)&(nova_garbageData));
		l2_Nova_rh = nova_standard_datastruct_Nova_Bounds_Nova_extractString(l2_Nova_bounds3, exceptionData, nova_standard_math_Nova_NumericOperation_Nova_operation);
	}
	if (l2_Nova_bounds3->nova_standard_datastruct_Nova_Bounds_Nova_end != nova_standard_math_Nova_NumericOperation_Nova_operation->nova_standard_Nova_String_Nova_size)
	{
	}
}

void nova_standard_math_Nova_NumericOperation_0_Nova_this(nova_standard_math_Nova_NumericOperation* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* nova_standard_math_Nova_NumericOperation_Nova_left, nova_standard_Nova_String* nova_standard_math_Nova_NumericOperation_Nova_operator, nova_standard_Nova_String* nova_standard_math_Nova_NumericOperation_Nova_right)
{
	nova_standard_math_Nova_NumericOperand* l2_Nova_leftOperator;
	nova_standard_math_Nova_NumericOperand* l2_Nova_rightOperator;
	
	l2_Nova_leftOperator = nova_standard_math_Nova_NumericOperation_Nova_decodeOperand(this, exceptionData, nova_standard_math_Nova_NumericOperation_Nova_left);
	l2_Nova_rightOperator = nova_standard_math_Nova_NumericOperation_Nova_decodeOperand(this, exceptionData, nova_standard_math_Nova_NumericOperation_Nova_right);
	if (nova_standard_math_Nova_NumericOperation_Nova_left == (nova_standard_Nova_String*)nova_null)
	{
		THROW(1, nova_standard_exception_Nova_Exception_4_Nova_construct(0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "Left operand '"), exceptionData, nova_standard_math_Nova_NumericOperation_Nova_left->vtable->nova_standard_Nova_String_virtual0_Nova_concat(nova_standard_math_Nova_NumericOperation_Nova_left, exceptionData, nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "' is invalid")))));
	}
	else if (nova_standard_math_Nova_NumericOperation_Nova_right == (nova_standard_Nova_String*)nova_null)
	{
		THROW(1, nova_standard_exception_Nova_Exception_4_Nova_construct(0, exceptionData, nova_standard_Nova_String_0_Nova_concat(nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "Right operand '"), exceptionData, nova_standard_math_Nova_NumericOperation_Nova_right->vtable->nova_standard_Nova_String_virtual0_Nova_concat(nova_standard_math_Nova_NumericOperation_Nova_right, exceptionData, nova_standard_Nova_String_2_Nova_construct(0, exceptionData, "' is invalid")))));
	}
	nova_standard_math_Nova_NumericOperation_1_Nova_this(this, exceptionData, this->prv->nova_standard_math_Nova_NumericOperation_Nova_leftOperand, nova_standard_math_Nova_NumericOperation_Nova_operator, this->prv->nova_standard_math_Nova_NumericOperation_Nova_rightOperand);
}

void nova_standard_math_Nova_NumericOperation_1_Nova_this(nova_standard_math_Nova_NumericOperation* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_math_Nova_NumericOperand* nova_standard_math_Nova_NumericOperation_Nova_left, nova_standard_Nova_String* nova_standard_math_Nova_NumericOperation_Nova_operator, nova_standard_math_Nova_NumericOperand* nova_standard_math_Nova_NumericOperation_Nova_right)
{
	this->prv->nova_standard_math_Nova_NumericOperation_Nova_leftOperand = nova_standard_math_Nova_NumericOperation_Nova_left;
	this->prv->nova_standard_math_Nova_NumericOperation_Nova_rightOperand = nova_standard_math_Nova_NumericOperation_Nova_right;
	this->prv->nova_standard_math_Nova_NumericOperation_Nova_operator = nova_standard_math_Nova_NumericOperation_Nova_operator;
}

nova_standard_math_Nova_NumericOperand* nova_standard_math_Nova_NumericOperation_Nova_decodeOperand(nova_standard_math_Nova_NumericOperation* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* nova_standard_math_Nova_NumericOperation_Nova_operand)
{
	return (nova_standard_math_Nova_NumericOperand*)nova_null;
}

int nova_standard_math_Nova_NumericOperation_0_Nova_getType(nova_standard_math_Nova_NumericOperation* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* nova_standard_math_Nova_NumericOperation_Nova_s)
{
	if (nova_standard_math_Nova_NumericOperation_Nova_s->nova_standard_Nova_String_Nova_size <= 0)
	{
		return 0;
	}
	return nova_standard_math_Nova_NumericOperation_1_Nova_getType((nova_standard_math_Nova_NumericOperation*)nova_null, exceptionData, nova_standard_math_Nova_NumericOperation_Nova_s->nova_standard_Nova_String_Nova_chars[0]);
}

int nova_standard_math_Nova_NumericOperation_1_Nova_getType(nova_standard_math_Nova_NumericOperation* this, nova_standard_exception_Nova_ExceptionData* exceptionData, char nova_standard_math_Nova_NumericOperation_Nova_c)
{
	if (nova_standard_math_Nova_NumericOperation_Nova_isNumeric((nova_standard_math_Nova_NumericOperation*)nova_null, exceptionData, nova_standard_math_Nova_NumericOperation_Nova_c))
	{
		return nova_standard_math_Nova_NumericOperation_Nova_NUMBER;
	}
	if (nova_standard_math_Nova_NumericOperation_Nova_isOperator((nova_standard_math_Nova_NumericOperation*)nova_null, exceptionData, nova_standard_math_Nova_NumericOperation_Nova_c))
	{
		return nova_standard_math_Nova_NumericOperation_Nova_OPERATOR;
	}
	if (nova_standard_math_Nova_NumericOperation_Nova_contains((nova_standard_math_Nova_NumericOperation*)nova_null, exceptionData, nova_standard_math_Nova_NumericOperation_Nova_INVALID_OPERATORS, nova_standard_math_Nova_NumericOperation_Nova_c, nova_standard_math_Nova_NumericOperation_Nova_invalidOperatorsLength))
	{
		return 0;
	}
	return nova_standard_math_Nova_NumericOperation_Nova_VARIABLE;
}

nova_standard_datastruct_Nova_Bounds* nova_standard_math_Nova_NumericOperation_Nova_searchNextType(nova_standard_math_Nova_NumericOperation* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* nova_standard_math_Nova_NumericOperation_Nova_str, int nova_standard_math_Nova_NumericOperation_Nova_start, int* ret1)
{
	nova_standard_datastruct_list_Nova_Array* l1_Nova_operators;
	int l1_Nova_minIndex;
	int l1_Nova_minRank;
	int l1_Nova_index;
	nova_standard_datastruct_Nova_Bounds* nova_local_0;
	
	l1_Nova_operators = nova_standard_datastruct_list_Nova_Array_0_Nova_construct(0, exceptionData);
	l1_Nova_minIndex = nova_standard_primitive_number_Nova_Int_Nova_MAX_VALUE;
	l1_Nova_minRank = nova_standard_primitive_number_Nova_Int_Nova_MIN_VALUE;
	l1_Nova_index = nova_standard_math_Nova_NumericOperation_Nova_nextOperator((nova_standard_math_Nova_NumericOperation*)nova_null, exceptionData, nova_standard_math_Nova_NumericOperation_Nova_str, nova_standard_math_Nova_NumericOperation_Nova_start);
	while (l1_Nova_index >= 0)
	{
		char l1_Nova_operator;
		int l1_Nova_rank;
		
		l1_Nova_operator = nova_standard_math_Nova_NumericOperation_Nova_str->nova_standard_Nova_String_Nova_chars[l1_Nova_index];
		l1_Nova_rank = nova_standard_math_Nova_NumericOperation_Nova_getOperatorRank((nova_standard_math_Nova_NumericOperation*)nova_null, exceptionData, l1_Nova_operator);
		if (l1_Nova_rank > l1_Nova_minRank)
		{
			l1_Nova_minIndex = l1_Nova_index;
			l1_Nova_minRank = l1_Nova_rank;
		}
		l1_Nova_index = nova_standard_math_Nova_NumericOperation_Nova_nextOperator((nova_standard_math_Nova_NumericOperation*)nova_null, exceptionData, nova_standard_math_Nova_NumericOperation_Nova_str, l1_Nova_index + 1);
	}
	if (l1_Nova_minIndex >= nova_standard_math_Nova_NumericOperation_Nova_str->nova_standard_Nova_String_Nova_size)
	{
		return (nova_standard_datastruct_Nova_Bounds*)nova_null;
	}
	l1_Nova_minIndex = nova_standard_math_Nova_NumericOperation_Nova_nextNonWhitespaceIndex((nova_standard_math_Nova_NumericOperation*)nova_null, exceptionData, nova_standard_math_Nova_NumericOperation_Nova_str, l1_Nova_minIndex - 1, -1) + 1;
	nova_local_0 = nova_standard_datastruct_Nova_Bounds_1_Nova_construct(0, exceptionData, nova_standard_math_Nova_NumericOperation_Nova_start, l1_Nova_minIndex);
	*ret1 = l1_Nova_index;
	return nova_local_0;
}

int nova_standard_math_Nova_NumericOperation_Nova_nextOperator(nova_standard_math_Nova_NumericOperation* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* nova_standard_math_Nova_NumericOperation_Nova_str, int nova_standard_math_Nova_NumericOperation_Nova_start)
{
	int l2_Nova_i;
	
	l2_Nova_i = nova_standard_math_Nova_NumericOperation_Nova_start;
	for (; l2_Nova_i < nova_standard_math_Nova_NumericOperation_Nova_str->nova_standard_Nova_String_Nova_size; l2_Nova_i++)
	{
		if (nova_standard_math_Nova_NumericOperation_Nova_isOperator((nova_standard_math_Nova_NumericOperation*)nova_null, exceptionData, nova_standard_math_Nova_NumericOperation_Nova_str->nova_standard_Nova_String_Nova_chars[l2_Nova_i]))
		{
			return l2_Nova_i;
		}
	}
	return -1;
}

int nova_standard_math_Nova_NumericOperation_Nova_getOperatorRank(nova_standard_math_Nova_NumericOperation* this, nova_standard_exception_Nova_ExceptionData* exceptionData, char nova_standard_math_Nova_NumericOperation_Nova_operator)
{
	switch (nova_standard_math_Nova_NumericOperation_Nova_operator)
	{
		case '-':
		case '+':
		return nova_standard_math_Nova_NumericOperation_Nova_ADDITION;
		case '/':
		case '*':
		return nova_standard_math_Nova_NumericOperation_Nova_MULTIPLICATION;
		case '^':
		return nova_standard_math_Nova_NumericOperation_Nova_EXPONENT;
		case '[':
		case '(':
			return nova_standard_math_Nova_NumericOperation_Nova_GROUPING;
			default:
			return nova_standard_math_Nova_NumericOperation_Nova_INVALID;
		}
	}
	
	int nova_standard_math_Nova_NumericOperation_Nova_nextNonWhitespaceIndex(nova_standard_math_Nova_NumericOperation* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* nova_standard_math_Nova_NumericOperation_Nova_str, int nova_standard_math_Nova_NumericOperation_Nova_start, int nova_standard_math_Nova_NumericOperation_Nova_direction)
	{
		while (nova_standard_math_Nova_NumericOperation_Nova_start >= 0 && nova_standard_math_Nova_NumericOperation_Nova_start < nova_standard_math_Nova_NumericOperation_Nova_str->nova_standard_Nova_String_Nova_size && nova_standard_math_Nova_NumericOperation_Nova_isWhitespace((nova_standard_math_Nova_NumericOperation*)nova_null, exceptionData, nova_standard_math_Nova_NumericOperation_Nova_str->nova_standard_Nova_String_Nova_chars[nova_standard_math_Nova_NumericOperation_Nova_start]))
		{
			nova_standard_math_Nova_NumericOperation_Nova_start = nova_standard_math_Nova_NumericOperation_Nova_start + nova_standard_math_Nova_NumericOperation_Nova_direction;
		}
		if (nova_standard_math_Nova_NumericOperation_Nova_start < 0 || nova_standard_math_Nova_NumericOperation_Nova_start >= nova_standard_math_Nova_NumericOperation_Nova_str->nova_standard_Nova_String_Nova_size)
		{
			return -1;
		}
		else
		{
			return nova_standard_math_Nova_NumericOperation_Nova_start - nova_standard_math_Nova_NumericOperation_Nova_direction;
		}
	}
	
	char nova_standard_math_Nova_NumericOperation_Nova_isLetter(nova_standard_math_Nova_NumericOperation* this, nova_standard_exception_Nova_ExceptionData* exceptionData, char nova_standard_math_Nova_NumericOperation_Nova_c)
	{
		return !nova_standard_math_Nova_NumericOperation_Nova_isOperator((nova_standard_math_Nova_NumericOperation*)nova_null, exceptionData, nova_standard_math_Nova_NumericOperation_Nova_c) && !nova_standard_math_Nova_NumericOperation_Nova_isWhitespace((nova_standard_math_Nova_NumericOperation*)nova_null, exceptionData, nova_standard_math_Nova_NumericOperation_Nova_c);
	}
	
	char nova_standard_math_Nova_NumericOperation_Nova_isNumeric(nova_standard_math_Nova_NumericOperation* this, nova_standard_exception_Nova_ExceptionData* exceptionData, char nova_standard_math_Nova_NumericOperation_Nova_c)
	{
		return nova_standard_math_Nova_NumericOperation_Nova_contains((nova_standard_math_Nova_NumericOperation*)nova_null, exceptionData, nova_standard_math_Nova_NumericOperation_Nova_NUMERIC_CHARS, nova_standard_math_Nova_NumericOperation_Nova_c, nova_standard_math_Nova_NumericOperation_Nova_numbersLength);
	}
	
	char nova_standard_math_Nova_NumericOperation_Nova_isOperator(nova_standard_math_Nova_NumericOperation* this, nova_standard_exception_Nova_ExceptionData* exceptionData, char nova_standard_math_Nova_NumericOperation_Nova_c)
	{
		return nova_standard_math_Nova_NumericOperation_Nova_contains((nova_standard_math_Nova_NumericOperation*)nova_null, exceptionData, nova_standard_math_Nova_NumericOperation_Nova_VALID_OPERATORS, nova_standard_math_Nova_NumericOperation_Nova_c, nova_standard_math_Nova_NumericOperation_Nova_operatorsLength);
	}
	
	char nova_standard_math_Nova_NumericOperation_Nova_isWhitespace(nova_standard_math_Nova_NumericOperation* this, nova_standard_exception_Nova_ExceptionData* exceptionData, char nova_standard_math_Nova_NumericOperation_Nova_c)
	{
		return nova_standard_math_Nova_NumericOperation_Nova_contains((nova_standard_math_Nova_NumericOperation*)nova_null, exceptionData, nova_standard_math_Nova_NumericOperation_Nova_WHITESPACE_CHARS, nova_standard_math_Nova_NumericOperation_Nova_c, nova_standard_math_Nova_NumericOperation_Nova_whitespaceLength);
	}
	
	char nova_standard_math_Nova_NumericOperation_Nova_contains(nova_standard_math_Nova_NumericOperation* this, nova_standard_exception_Nova_ExceptionData* exceptionData, char* nova_standard_math_Nova_NumericOperation_Nova_array, char nova_standard_math_Nova_NumericOperation_Nova_c, int nova_standard_math_Nova_NumericOperation_Nova_length)
	{
		int l2_Nova_i;
		
		l2_Nova_i = 0;
		for (; l2_Nova_i < nova_standard_math_Nova_NumericOperation_Nova_length; l2_Nova_i++)
		{
			if (nova_standard_math_Nova_NumericOperation_Nova_c == nova_standard_math_Nova_NumericOperation_Nova_array[l2_Nova_i])
			{
				return 1;
			}
		}
		return 0;
	}
	
	nova_standard_Nova_String* nova_standard_math_Nova_NumericOperation_1_Nova_toString(nova_standard_math_Nova_NumericOperation* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
	{
		nova_standard_Nova_String* nova_local_0;
		
		nova_local_0 = this->prv->nova_standard_math_Nova_NumericOperation_Nova_leftOperand->vtable->nova_standard_math_Nova_NumericOperand_virtual1_Nova_toString(this->prv->nova_standard_math_Nova_NumericOperation_Nova_leftOperand, exceptionData);
		return nova_local_0->vtable->nova_standard_Nova_String_virtual0_Nova_concat(nova_local_0, exceptionData, this->prv->nova_standard_math_Nova_NumericOperation_Nova_operator->vtable->nova_standard_Nova_String_virtual0_Nova_concat(this->prv->nova_standard_math_Nova_NumericOperation_Nova_operator, exceptionData, this->prv->nova_standard_math_Nova_NumericOperation_Nova_rightOperand->vtable->nova_standard_math_Nova_NumericOperand_virtual1_Nova_toString(this->prv->nova_standard_math_Nova_NumericOperation_Nova_rightOperand, exceptionData)));
	}
	
	void nova_standard_math_Nova_NumericOperation_0_Nova_super(nova_standard_math_Nova_NumericOperation* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
	{
		this->prv->nova_standard_math_Nova_NumericOperation_Nova_leftOperand = (nova_standard_math_Nova_NumericOperand*)nova_null;
		this->prv->nova_standard_math_Nova_NumericOperation_Nova_rightOperand = (nova_standard_math_Nova_NumericOperand*)nova_null;
		this->prv->nova_standard_math_Nova_NumericOperation_Nova_operator = (nova_standard_Nova_String*)nova_null;
	}
	
		