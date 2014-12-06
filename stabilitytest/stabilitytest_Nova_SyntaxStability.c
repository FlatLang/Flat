#include <precompiled.h>
#include <stabilitytest/stabilitytest_Nova_SyntaxStability.h>


nova_VTable_stabilitytest_Nova_SyntaxStability nova_VTable_stabilitytest_Nova_SyntaxStability_val =
{
	nova_standard_Nova_Object_0_Nova_getHashCodeLong,
	nova_standard_Nova_Object_0_Nova_toString,
	nova_standard_Nova_Object_0_Nova_equals,
};

void stabilitytest_Nova_SyntaxStability_Nova_checkSwitchStatements(stabilitytest_Nova_SyntaxStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_StabilityTest* l0_Nova_program);
void stabilitytest_Nova_SyntaxStability_Nova_checkLoops(stabilitytest_Nova_SyntaxStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_StabilityTest* l0_Nova_program);
void stabilitytest_Nova_SyntaxStability_Nova_checkWhileLoops(stabilitytest_Nova_SyntaxStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_StabilityTest* l0_Nova_program);
void stabilitytest_Nova_SyntaxStability_Nova_checkForLoops(stabilitytest_Nova_SyntaxStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_StabilityTest* l0_Nova_program);
void stabilitytest_Nova_SyntaxStability_Nova_checkUntil(stabilitytest_Nova_SyntaxStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_StabilityTest* l0_Nova_program);
nova_standard_Nova_Object* stabilitytest_Nova_SyntaxStability_Nova_test1(stabilitytest_Nova_SyntaxStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData);
nova_standard_Nova_Object* stabilitytest_Nova_SyntaxStability_Nova_test2(stabilitytest_Nova_SyntaxStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData);
nova_standard_Nova_Object* stabilitytest_Nova_SyntaxStability_Nova_test3(stabilitytest_Nova_SyntaxStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData);
void stabilitytest_Nova_SyntaxStability_Nova_checkMultipleReturnValues(stabilitytest_Nova_SyntaxStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_StabilityTest* l0_Nova_program);
int stabilitytest_Nova_SyntaxStability_Nova_ret2(stabilitytest_Nova_SyntaxStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int* ret1);
int stabilitytest_Nova_SyntaxStability_Nova_swap(stabilitytest_Nova_SyntaxStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int l0_Nova_a, int l0_Nova_b, int* ret1);
nova_standard_Nova_String* stabilitytest_Nova_SyntaxStability_Nova_swap2(stabilitytest_Nova_SyntaxStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* l0_Nova_a, nova_standard_Nova_String* l0_Nova_b, nova_standard_Nova_String** ret1);
void stabilitytest_Nova_SyntaxStabilityNova_init_static(nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	{
	}
}

stabilitytest_Nova_SyntaxStability* stabilitytest_Nova_SyntaxStability_2_Nova_construct(stabilitytest_Nova_SyntaxStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	CCLASS_NEW(stabilitytest_Nova_SyntaxStability, this,);
	this->vtable = &nova_VTable_stabilitytest_Nova_SyntaxStability_val;
	nova_standard_Nova_Object_Nova_super((nova_standard_Nova_Object*)this, exceptionData);
	nova_standard_Nova_Object_Nova_this((nova_standard_Nova_Object*)(this), exceptionData);
	stabilitytest_Nova_SyntaxStability_Nova_super(this, exceptionData);
	
	{
		stabilitytest_Nova_SyntaxStability_Nova_this(this, exceptionData);
	}
	
	return this;
}

void stabilitytest_Nova_SyntaxStability_Nova_destroy(stabilitytest_Nova_SyntaxStability** this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

void stabilitytest_Nova_SyntaxStability_Nova_test(stabilitytest_Nova_SyntaxStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_StabilityTest* l0_Nova_program)
{
	stabilitytest_Nova_SyntaxStability_Nova_checkLoops((stabilitytest_Nova_SyntaxStability*)nova_null, exceptionData, l0_Nova_program);
	stabilitytest_Nova_SyntaxStability_Nova_checkUntil((stabilitytest_Nova_SyntaxStability*)nova_null, exceptionData, l0_Nova_program);
	stabilitytest_Nova_SyntaxStability_Nova_checkSwitchStatements((stabilitytest_Nova_SyntaxStability*)nova_null, exceptionData, l0_Nova_program);
	stabilitytest_Nova_SyntaxStability_Nova_checkMultipleReturnValues((stabilitytest_Nova_SyntaxStability*)nova_null, exceptionData, l0_Nova_program);
}

void stabilitytest_Nova_SyntaxStability_Nova_checkSwitchStatements(stabilitytest_Nova_SyntaxStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_StabilityTest* l0_Nova_program)
{
	int l1_Nova_num;
	char l1_Nova_worked;
	int l1_Nova_val;
	char l1_Nova_worked2;
	char nova_local_0;
	int nova_local_1;
	
	nova_standard_io_Nova_Console_0_Nova_write(0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Checking simple switch statement... "));
	l1_Nova_num = 3;
	l1_Nova_worked = 0;
	switch ((l1_Nova_num))
	{
		case 1:
		stabilitytest_Nova_StabilityTest_1_Nova_fail(l0_Nova_program, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Switch accepted incorrect case"));
		break;
		case 2:
		stabilitytest_Nova_StabilityTest_1_Nova_fail(l0_Nova_program, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Switch accepted incorrect case"));
		break;
		case 3:
		l1_Nova_worked = 1;
		break;
		case 4:
		stabilitytest_Nova_StabilityTest_1_Nova_fail(l0_Nova_program, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Switch accepted incorrect case"));
		break;
		default:
		stabilitytest_Nova_StabilityTest_1_Nova_fail(l0_Nova_program, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Switch failed to accept the correct case"));
	}
	if (!l1_Nova_worked)
	{
		stabilitytest_Nova_StabilityTest_1_Nova_fail(l0_Nova_program, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "FAIL"));
	}
	nova_standard_io_Nova_Console_0_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "OK"));
	nova_standard_io_Nova_Console_0_Nova_write(0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Checking simple switch statement with indentation... "));
	l1_Nova_num = 3;
	l1_Nova_worked = 0;
	switch ((l1_Nova_num))
	{
		case 1:
		stabilitytest_Nova_StabilityTest_1_Nova_fail(l0_Nova_program, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Switch accepted incorrect case"));
		break;
		case 2:
		stabilitytest_Nova_StabilityTest_1_Nova_fail(l0_Nova_program, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Switch accepted incorrect case"));
		break;
		case 3:
		l1_Nova_worked = 1;
		break;
		case 4:
		stabilitytest_Nova_StabilityTest_1_Nova_fail(l0_Nova_program, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Switch accepted incorrect case"));
		break;
		default:
		stabilitytest_Nova_StabilityTest_1_Nova_fail(l0_Nova_program, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Switch failed to accept the correct case"));
	}
	if (!l1_Nova_worked)
	{
		stabilitytest_Nova_StabilityTest_1_Nova_fail(l0_Nova_program, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "FAIL"));
	}
	nova_standard_io_Nova_Console_0_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "OK"));
	nova_standard_io_Nova_Console_0_Nova_write(0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Checking simple switch statement with scopes... "));
	l1_Nova_num = 3;
	l1_Nova_worked = 0;
	switch ((l1_Nova_num))
	{
		case 1:
		stabilitytest_Nova_StabilityTest_1_Nova_fail(l0_Nova_program, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Switch accepted incorrect case"));
		break;
		case 2:
		stabilitytest_Nova_StabilityTest_1_Nova_fail(l0_Nova_program, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Switch accepted incorrect case"));
		break;
		case 3:
		l1_Nova_worked = 1;
		break;
		case 4:
		stabilitytest_Nova_StabilityTest_1_Nova_fail(l0_Nova_program, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Switch accepted incorrect case"));
		break;
		default:
		stabilitytest_Nova_StabilityTest_1_Nova_fail(l0_Nova_program, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Switch failed to accept the correct case"));
	}
	if (!l1_Nova_worked)
	{
		stabilitytest_Nova_StabilityTest_1_Nova_fail(l0_Nova_program, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "FAIL"));
	}
	nova_standard_io_Nova_Console_0_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "OK"));
	nova_standard_io_Nova_Console_0_Nova_write(0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Checking switch statement with variable case values... "));
	l1_Nova_val = 1;
	l1_Nova_num = 3;
	l1_Nova_worked = 0;
	if ((l1_Nova_num) == (l1_Nova_val++))
	{
		stabilitytest_Nova_StabilityTest_1_Nova_fail(l0_Nova_program, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Switch accepted incorrect case"));
	}
	else if ((l1_Nova_num) == l1_Nova_val++)
	{
		stabilitytest_Nova_StabilityTest_1_Nova_fail(l0_Nova_program, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Switch accepted incorrect case"));
	}
	else if ((l1_Nova_num) == (l1_Nova_val++))
	{
		l1_Nova_worked = 1;
	}
	else if ((l1_Nova_num) == l1_Nova_val++)
	{
		stabilitytest_Nova_StabilityTest_1_Nova_fail(l0_Nova_program, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Switch accepted incorrect case"));
	}
	else
	{
		stabilitytest_Nova_StabilityTest_1_Nova_fail(l0_Nova_program, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Switch failed to accept the correct case"));
	}
	if (!l1_Nova_worked)
	{
		stabilitytest_Nova_StabilityTest_1_Nova_fail(l0_Nova_program, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "FAIL"));
	}
	nova_standard_io_Nova_Console_0_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "OK"));
	nova_standard_io_Nova_Console_0_Nova_write(0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Checking switch statement with variable case values and fallthrough... "));
	l1_Nova_val = 1;
	l1_Nova_num = 3;
	l1_Nova_worked = 0;
	l1_Nova_worked2 = 0;
	nova_local_1 = (l1_Nova_num++);
	do
	{
		if (nova_local_1 == l1_Nova_val++)
		{
			stabilitytest_Nova_StabilityTest_1_Nova_fail(l0_Nova_program, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Switch accepted incorrect case"));
			break;
		}
		else if (nova_local_1 == l1_Nova_val++)
		{
			stabilitytest_Nova_StabilityTest_1_Nova_fail(l0_Nova_program, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Switch accepted incorrect case"));
			break;
		}
		else if (nova_local_1 == l1_Nova_val++)
		{
			l1_Nova_worked = 1;
			nova_local_0 = 1;
		}
		if (nova_local_0 || nova_local_1 == l1_Nova_val++)
		{
			l1_Nova_worked2 = 1;
			break;
		}
		else
		{
			stabilitytest_Nova_StabilityTest_1_Nova_fail(l0_Nova_program, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Switch failed to accept the correct case"));
		}
	}
	while (0);
	if (!l1_Nova_worked || !l1_Nova_worked2)
	{
		stabilitytest_Nova_StabilityTest_1_Nova_fail(l0_Nova_program, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "FAIL"));
	}
	nova_standard_io_Nova_Console_0_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "OK"));
}

void stabilitytest_Nova_SyntaxStability_Nova_checkLoops(stabilitytest_Nova_SyntaxStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_StabilityTest* l0_Nova_program)
{
	stabilitytest_Nova_SyntaxStability_Nova_checkWhileLoops((stabilitytest_Nova_SyntaxStability*)nova_null, exceptionData, l0_Nova_program);
	stabilitytest_Nova_SyntaxStability_Nova_checkForLoops((stabilitytest_Nova_SyntaxStability*)nova_null, exceptionData, l0_Nova_program);
}

void stabilitytest_Nova_SyntaxStability_Nova_checkWhileLoops(stabilitytest_Nova_SyntaxStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_StabilityTest* l0_Nova_program)
{
	int l1_Nova_num;
	int l1_Nova_num2;
	int l1_Nova_num3;
	
	nova_standard_io_Nova_Console_0_Nova_write(0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Checking inline while loop... "));
	l1_Nova_num = 0;
	l1_Nova_num2 = 0;
	l1_Nova_num3 = 0;
	while (l1_Nova_num < 100)
	{
		l1_Nova_num++;
	}
	while (l1_Nova_num2 < 100)
	{
		l1_Nova_num2++;
	}
	while (l1_Nova_num3 < 100)
	{
		if (1)
		{
			if (1)
			{
				l1_Nova_num3++;
			}
		}
	}
	if (l1_Nova_num != 100 || l1_Nova_num2 != 100 || l1_Nova_num3 != 100)
	{
		stabilitytest_Nova_StabilityTest_1_Nova_fail(l0_Nova_program, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Inline while loop failed."));
	}
	nova_standard_io_Nova_Console_0_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "OK"));
}

void stabilitytest_Nova_SyntaxStability_Nova_checkForLoops(stabilitytest_Nova_SyntaxStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_StabilityTest* l0_Nova_program)
{
	int l1_Nova_num;
	int l1_Nova_num2;
	int l1_Nova_num3;
	int l2_Nova_i;
	int l3_Nova_i;
	int l4_Nova_i;
	
	nova_standard_io_Nova_Console_0_Nova_write(0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Checking inline for loop... "));
	l1_Nova_num = 0;
	l1_Nova_num2 = 0;
	l1_Nova_num3 = 0;
	l2_Nova_i = 0;
	for (; l2_Nova_i < 100; l2_Nova_i++)
	{
		l1_Nova_num++;
	}
	l3_Nova_i = 0;
	for (; l3_Nova_i < 100; l3_Nova_i++)
	{
		l1_Nova_num2++;
	}
	l4_Nova_i = 0;
	for (; l4_Nova_i < 100; l4_Nova_i++)
	{
		if (1)
		{
			if (1)
			{
				l1_Nova_num3++;
			}
		}
	}
	if (l1_Nova_num != 100 || l1_Nova_num2 != 100 || l1_Nova_num3 != 100)
	{
		stabilitytest_Nova_StabilityTest_1_Nova_fail(l0_Nova_program, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Inline for loop failed."));
	}
	nova_standard_io_Nova_Console_0_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "OK"));
}

void stabilitytest_Nova_SyntaxStability_Nova_checkUntil(stabilitytest_Nova_SyntaxStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_StabilityTest* l0_Nova_program)
{
	nova_standard_Nova_Object* l1_Nova_obj;
	
	nova_standard_io_Nova_Console_0_Nova_write(0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Checking until statement... "));
	l1_Nova_obj = (nova_standard_Nova_Object*)nova_null;
	if (!(l1_Nova_obj != (nova_standard_Nova_Object*)nova_null))
	{
		l1_Nova_obj = stabilitytest_Nova_SyntaxStability_Nova_test1((stabilitytest_Nova_SyntaxStability*)nova_null, exceptionData);
		if (!(l1_Nova_obj != (nova_standard_Nova_Object*)nova_null))
		{
			l1_Nova_obj = stabilitytest_Nova_SyntaxStability_Nova_test2((stabilitytest_Nova_SyntaxStability*)nova_null, exceptionData);
			if (!(l1_Nova_obj != (nova_standard_Nova_Object*)nova_null))
			{
				l1_Nova_obj = stabilitytest_Nova_SyntaxStability_Nova_test3((stabilitytest_Nova_SyntaxStability*)nova_null, exceptionData);
				if (!(l1_Nova_obj != (nova_standard_Nova_Object*)nova_null))
				{
					stabilitytest_Nova_StabilityTest_1_Nova_fail(l0_Nova_program, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Failed to stop after correct condition"));
				}
			}
		}
	}
	if (l1_Nova_obj == (nova_standard_Nova_Object*)nova_null)
	{
		stabilitytest_Nova_StabilityTest_1_Nova_fail(l0_Nova_program, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Failed to reach correct condition"));
	}
	nova_standard_io_Nova_Console_0_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "OK"));
}

nova_standard_Nova_Object* stabilitytest_Nova_SyntaxStability_Nova_test1(stabilitytest_Nova_SyntaxStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return (nova_standard_Nova_Object*)nova_null;
}

nova_standard_Nova_Object* stabilitytest_Nova_SyntaxStability_Nova_test2(stabilitytest_Nova_SyntaxStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return (nova_standard_Nova_Object*)nova_null;
}

nova_standard_Nova_Object* stabilitytest_Nova_SyntaxStability_Nova_test3(stabilitytest_Nova_SyntaxStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
	return nova_standard_Nova_Object_2_Nova_construct(0, exceptionData);
}

void stabilitytest_Nova_SyntaxStability_Nova_checkMultipleReturnValues(stabilitytest_Nova_SyntaxStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, stabilitytest_Nova_StabilityTest* l0_Nova_program)
{
	int l1_Nova_ret1;
	int l1_Nova_ret2;
	nova_standard_Nova_String* l1_Nova_s1;
	nova_standard_Nova_String* l1_Nova_s2;
	
	nova_standard_io_Nova_Console_0_Nova_write(0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Checking methods with multiple return values... "));
	l1_Nova_ret1 = 1;
	l1_Nova_ret2 = 2;
	l1_Nova_ret1 = stabilitytest_Nova_SyntaxStability_Nova_ret2((stabilitytest_Nova_SyntaxStability*)nova_null, exceptionData, &l1_Nova_ret2);
	if (l1_Nova_ret1 != 5 || l1_Nova_ret2 != 2)
	{
		stabilitytest_Nova_StabilityTest_1_Nova_fail(l0_Nova_program, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Failed to pass over second return value"));
	}
	l1_Nova_ret1 = 1;
	l1_Nova_ret2 = 2;
	l1_Nova_ret1 = stabilitytest_Nova_SyntaxStability_Nova_swap((stabilitytest_Nova_SyntaxStability*)nova_null, exceptionData, l1_Nova_ret1, l1_Nova_ret2, &l1_Nova_ret2);
	if (l1_Nova_ret1 != 2 || l1_Nova_ret2 != 1)
	{
		stabilitytest_Nova_StabilityTest_1_Nova_fail(l0_Nova_program, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Failed to swap primitive values with multiple return values"));
	}
	l1_Nova_s1 = nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "hello");
	l1_Nova_s2 = nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "world");
	l1_Nova_s1 = stabilitytest_Nova_SyntaxStability_Nova_swap2((stabilitytest_Nova_SyntaxStability*)nova_null, exceptionData, l1_Nova_s1, l1_Nova_s2, &l1_Nova_s2);
	if (!l1_Nova_s1->vtable->nova_standard_Nova_String_virtual_Nova_equals(l1_Nova_s1, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "world")) || !l1_Nova_s2->vtable->nova_standard_Nova_String_virtual_Nova_equals(l1_Nova_s2, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "hello")))
	{
		stabilitytest_Nova_StabilityTest_1_Nova_fail(l0_Nova_program, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "Failed to swap String values with multiple return values"));
	}
	nova_standard_io_Nova_Console_0_Nova_writeLine(0, exceptionData, nova_standard_Nova_String_1_Nova_construct(0, exceptionData, "OK"));
}

int stabilitytest_Nova_SyntaxStability_Nova_ret2(stabilitytest_Nova_SyntaxStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int* ret1)
{
	return 5;
}

int stabilitytest_Nova_SyntaxStability_Nova_swap(stabilitytest_Nova_SyntaxStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, int l0_Nova_a, int l0_Nova_b, int* ret1)
{
	int nova_local_0;
	
	nova_local_0 = l0_Nova_b;
	*ret1 = l0_Nova_a;
	return nova_local_0;
}

nova_standard_Nova_String* stabilitytest_Nova_SyntaxStability_Nova_swap2(stabilitytest_Nova_SyntaxStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData, nova_standard_Nova_String* l0_Nova_a, nova_standard_Nova_String* l0_Nova_b, nova_standard_Nova_String** ret1)
{
	nova_standard_Nova_String* nova_local_0;
	
	nova_local_0 = l0_Nova_b;
	*ret1 = l0_Nova_a;
	return nova_local_0;
}

void stabilitytest_Nova_SyntaxStability_Nova_this(stabilitytest_Nova_SyntaxStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

void stabilitytest_Nova_SyntaxStability_Nova_super(stabilitytest_Nova_SyntaxStability* this, nova_standard_exception_Nova_ExceptionData* exceptionData)
{
}

