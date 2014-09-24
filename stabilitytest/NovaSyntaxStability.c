#include <precompiled.h>
#include "NovaSyntaxStability.h"


nova_VTable_SyntaxStability nova_VTable_SyntaxStability_val =
{
	nova_0_Object_toString,
	nova_0_Object_equals,
};

void nova_static_SyntaxStability_checkLoops(SyntaxStability* this, ExceptionData* exceptionData, StabilityTest* nova_0_program);
void nova_static_SyntaxStability_checkWhileLoops(SyntaxStability* this, ExceptionData* exceptionData, StabilityTest* nova_0_program);
void nova_static_SyntaxStability_checkForLoops(SyntaxStability* this, ExceptionData* exceptionData, StabilityTest* nova_0_program);
void nova_static_SyntaxStability_checkUntil(SyntaxStability* this, ExceptionData* exceptionData, StabilityTest* nova_0_program);
Object* nova_static_SyntaxStability_test1(SyntaxStability* this, ExceptionData* exceptionData);
Object* nova_static_SyntaxStability_test2(SyntaxStability* this, ExceptionData* exceptionData);
Object* nova_static_SyntaxStability_test3(SyntaxStability* this, ExceptionData* exceptionData);

SyntaxStability* nova_0_SyntaxStability_construct(SyntaxStability* this, ExceptionData* exceptionData)
{
	CCLASS_NEW(SyntaxStability, this,);
	this->vtable = &nova_VTable_SyntaxStability_val;
	nova_Object_super((Object*)this, 0);
	nova_Object_this((Object*)(this), exceptionData);
	nova_SyntaxStability_super(this, 0);
	
	{
		nova_SyntaxStability_this(this, exceptionData);
	}
	
	return this;
}

void nova_del_SyntaxStability(SyntaxStability** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

void nova_static_SyntaxStability_test(SyntaxStability* this, ExceptionData* exceptionData, StabilityTest* nova_0_program)
{
	nova_static_SyntaxStability_checkLoops((SyntaxStability*)nova_null, exceptionData, nova_0_program);
	nova_static_SyntaxStability_checkUntil((SyntaxStability*)nova_null, exceptionData, nova_0_program);
}

void nova_static_SyntaxStability_checkLoops(SyntaxStability* this, ExceptionData* exceptionData, StabilityTest* nova_0_program)
{
	nova_static_SyntaxStability_checkWhileLoops((SyntaxStability*)nova_null, exceptionData, nova_0_program);
	nova_static_SyntaxStability_checkForLoops((SyntaxStability*)nova_null, exceptionData, nova_0_program);
}

void nova_static_SyntaxStability_checkWhileLoops(SyntaxStability* this, ExceptionData* exceptionData, StabilityTest* nova_0_program)
{
	int nova_1_num;
	int nova_1_num2;
	int nova_1_num3;
	
	nova_static_0_Console_write(0, exceptionData, nova_String_construct(0, exceptionData, "Checking inline while loop... "));
	nova_1_num = 0;
	nova_1_num2 = 0;
	nova_1_num3 = 0;
	while (nova_1_num < 100)
	{
		nova_1_num++;
	}
	while (nova_1_num2 < 100)
	{
		nova_1_num2++;
	}
	while (nova_1_num3 < 100)
	{
		if (1)
		{
			if (1)
			{
				nova_1_num3++;
			}
		}
	}
	if (nova_1_num != 100 || nova_1_num2 != 100 || nova_1_num3 != 100)
	{
		nova_1_StabilityTest_fail(nova_0_program, exceptionData, nova_String_construct(0, exceptionData, "Inline while loop failed."));
	}
	nova_static_0_Console_writeLine(0, exceptionData, nova_String_construct(0, exceptionData, "OK"));
}

void nova_static_SyntaxStability_checkForLoops(SyntaxStability* this, ExceptionData* exceptionData, StabilityTest* nova_0_program)
{
	int nova_1_num;
	int nova_1_num2;
	int nova_1_num3;
	int nova_2_j;
	int nova_3_i;
	int nova_4_q;
	
	nova_static_0_Console_write(0, exceptionData, nova_String_construct(0, exceptionData, "Checking inline for loop... "));
	nova_1_num = 0;
	nova_1_num2 = 0;
	nova_1_num3 = 0;
	nova_2_j = 0;
	for (; nova_2_j < 100; nova_2_j++)
	{
		nova_1_num++;
	}
	nova_3_i = 0;
	for (; nova_3_i < 100; nova_3_i++)
	{
		nova_1_num2++;
	}
	nova_4_q = 0;
	for (; nova_4_q < 100; nova_4_q++)
	{
		if (1)
		{
			if (1)
			{
				nova_1_num3++;
			}
		}
	}
	if (nova_1_num != 100 || nova_1_num2 != 100 || nova_1_num3 != 100)
	{
		nova_1_StabilityTest_fail(nova_0_program, exceptionData, nova_String_construct(0, exceptionData, "Inline for loop failed."));
	}
	nova_static_0_Console_writeLine(0, exceptionData, nova_String_construct(0, exceptionData, "OK"));
}

void nova_static_SyntaxStability_checkUntil(SyntaxStability* this, ExceptionData* exceptionData, StabilityTest* nova_0_program)
{
	Object* nova_1_obj;
	
	nova_static_0_Console_write(0, exceptionData, nova_String_construct(0, exceptionData, "Checking until statement... "));
	nova_1_obj = (Object*)nova_null;
	if (!(nova_1_obj != (Object*)nova_null))
	{
		nova_1_obj = nova_static_SyntaxStability_test1((SyntaxStability*)nova_null, exceptionData);
		if (!(nova_1_obj != (Object*)nova_null))
		{
			nova_1_obj = nova_static_SyntaxStability_test2((SyntaxStability*)nova_null, exceptionData);
			if (!(nova_1_obj != (Object*)nova_null))
			{
				nova_1_obj = nova_static_SyntaxStability_test3((SyntaxStability*)nova_null, exceptionData);
				if (!(nova_1_obj != (Object*)nova_null))
				{
					nova_1_StabilityTest_fail(nova_0_program, exceptionData, nova_String_construct(0, exceptionData, "Failed to stop after correct condition"));
				}
			}
		}
	}
	if (nova_1_obj == (Object*)nova_null)
	{
		nova_1_StabilityTest_fail(nova_0_program, exceptionData, nova_String_construct(0, exceptionData, "Failed to reach correct condition"));
	}
	nova_static_0_Console_writeLine(0, exceptionData, nova_String_construct(0, exceptionData, "OK"));
}

Object* nova_static_SyntaxStability_test1(SyntaxStability* this, ExceptionData* exceptionData)
{
	return (Object*)nova_null;
}

Object* nova_static_SyntaxStability_test2(SyntaxStability* this, ExceptionData* exceptionData)
{
	return (Object*)nova_null;
}

Object* nova_static_SyntaxStability_test3(SyntaxStability* this, ExceptionData* exceptionData)
{
	return nova_0_Object_construct(0, exceptionData);
}

void nova_SyntaxStability_this(SyntaxStability* this, ExceptionData* exceptionData)
{
}

void nova_SyntaxStability_super(SyntaxStability* this, ExceptionData* exceptionData)
{
}
