#include <precompiled.h>
#include "NovaSyntaxStability.h"




void nova_static_SyntaxStability_checkLoops(SyntaxStability* this, ExceptionData* exceptionData, StabilityTest* nova_0_program);
void nova_static_SyntaxStability_checkWhileLoops(SyntaxStability* this, ExceptionData* exceptionData, StabilityTest* nova_0_program);
void nova_static_SyntaxStability_checkForLoops(SyntaxStability* this, ExceptionData* exceptionData, StabilityTest* nova_0_program);
void nova_static_SyntaxStability_checkUntil(SyntaxStability* this, ExceptionData* exceptionData, StabilityTest* nova_0_program);
Object* nova_static_SyntaxStability_test1(SyntaxStability* this, ExceptionData* exceptionData);
Object* nova_static_SyntaxStability_test2(SyntaxStability* this, ExceptionData* exceptionData);
Object* nova_static_SyntaxStability_test3(SyntaxStability* this, ExceptionData* exceptionData);

SyntaxStability* nova_SyntaxStability_SyntaxStability(ExceptionData* exceptionData)
{
	SyntaxStability* this = (SyntaxStability*)1;
	
	{
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
	nova_static_SyntaxStability_checkLoops((SyntaxStability*)0, exceptionData, nova_0_program);
	nova_static_SyntaxStability_checkUntil((SyntaxStability*)0, exceptionData, nova_0_program);
}

void nova_static_SyntaxStability_checkLoops(SyntaxStability* this, ExceptionData* exceptionData, StabilityTest* nova_0_program)
{
	nova_static_SyntaxStability_checkWhileLoops((SyntaxStability*)0, exceptionData, nova_0_program);
	nova_static_SyntaxStability_checkForLoops((SyntaxStability*)0, exceptionData, nova_0_program);
}

void nova_static_SyntaxStability_checkWhileLoops(SyntaxStability* this, ExceptionData* exceptionData, StabilityTest* nova_0_program)
{
	int nova_1_num;
	int nova_1_num2;
	int nova_1_num3;
	
	nova_static_Console_write((Console*)(0), exceptionData, nova_String_String(exceptionData, "Checking inline while loop... "));
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
		nova_StabilityTest_fail((StabilityTest*)(nova_0_program), exceptionData, nova_String_String(exceptionData, "Inline while loop failed."));
	}
	nova_static_Console_writeLine((Console*)(0), exceptionData, nova_String_String(exceptionData, "OK"));
}

void nova_static_SyntaxStability_checkForLoops(SyntaxStability* this, ExceptionData* exceptionData, StabilityTest* nova_0_program)
{
	int nova_1_num;
	int nova_1_num2;
	int nova_1_num3;
	int nova_1_j;
	int nova_1_i;
	int nova_1_q;
	
	nova_static_Console_write((Console*)(0), exceptionData, nova_String_String(exceptionData, "Checking inline for loop... "));
	nova_1_num = 0;
	nova_1_num2 = 0;
	nova_1_num3 = 0;
	nova_1_j = 0;
	for (; nova_1_j < 100; nova_1_j++)
	{
		nova_1_num++;
	}
	nova_1_i = 0;
	for (; nova_1_i < 100; nova_1_i++)
	{
		nova_1_num2++;
	}
	nova_1_q = 0;
	for (; nova_1_q < 100; nova_1_q++)
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
		nova_StabilityTest_fail((StabilityTest*)(nova_0_program), exceptionData, nova_String_String(exceptionData, "Inline for loop failed."));
	}
	nova_static_Console_writeLine((Console*)(0), exceptionData, nova_String_String(exceptionData, "OK"));
}

void nova_static_SyntaxStability_checkUntil(SyntaxStability* this, ExceptionData* exceptionData, StabilityTest* nova_0_program)
{
	Object* nova_1_obj;
	
	nova_static_Console_write((Console*)(0), exceptionData, nova_String_String(exceptionData, "Checking until statement... "));
	nova_1_obj = (Object*)0;
	if (!(nova_1_obj != (Object*)0))
	{
		nova_1_obj = nova_static_SyntaxStability_test1((SyntaxStability*)0, exceptionData);
		if (!(nova_1_obj != (Object*)0))
		{
			nova_1_obj = nova_static_SyntaxStability_test2((SyntaxStability*)0, exceptionData);
			if (!(nova_1_obj != (Object*)0))
			{
				nova_1_obj = nova_static_SyntaxStability_test3((SyntaxStability*)0, exceptionData);
				if (!(nova_1_obj != (Object*)0))
				{
					nova_StabilityTest_fail((StabilityTest*)(nova_0_program), exceptionData, nova_String_String(exceptionData, "Failed to stop after correct condition"));
				}
			}
		}
	}
	if (nova_1_obj == (Object*)0)
	{
		nova_StabilityTest_fail((StabilityTest*)(nova_0_program), exceptionData, nova_String_String(exceptionData, "Failed to reach correct condition"));
	}
	nova_static_Console_writeLine((Console*)(0), exceptionData, nova_String_String(exceptionData, "OK"));
}

Object* nova_static_SyntaxStability_test1(SyntaxStability* this, ExceptionData* exceptionData)
{
	return (Object*)0;
}

Object* nova_static_SyntaxStability_test2(SyntaxStability* this, ExceptionData* exceptionData)
{
	return (Object*)0;
}

Object* nova_static_SyntaxStability_test3(SyntaxStability* this, ExceptionData* exceptionData)
{
	return nova_Object_Object(exceptionData);
}
