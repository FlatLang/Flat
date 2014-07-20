#include <precompiled.h>
#include "NovaStabilityTest.h"




StabilityTest* nova_StabilityTest_StabilityTest(ExceptionData* exceptionData)
{
		StabilityTest* this = (StabilityTest*)1;
		
		{
		}
		
		return this;
}

void nova_del_StabilityTest(StabilityTest** this, ExceptionData* exceptionData)
{
		if (!*this)
		{
				return;
		}
		
		
		{
		}
		NOVA_FREE(*this);
}

void nova_static_StabilityTest_main(StabilityTest* this, ExceptionData* exceptionData, String** nova_0_args)
{
		StabilityTest* nova_1_test;
		long_long nova_1_start;
		int nova_1_result;
		long_long nova_1_time;
		
		nova_1_test = nova_StabilityTest_StabilityTest(exceptionData);
		nova_1_start = nova_static_Time_currentTimeMillis((Object*)0, exceptionData);
		nova_1_result = nova_StabilityTest_runTests(nova_1_test, exceptionData);
		nova_1_time = nova_static_Time_currentTimeMillis((Object*)0, exceptionData) - nova_1_start;
		nova_static_Console_writeLine((Object*)0, exceptionData, nova_String_concat(nova_String_String(exceptionData, "Took "), exceptionData, nova_String_concat(nova_Long_toString(nova_Long_Long(exceptionData, nova_1_time), exceptionData), exceptionData, nova_String_String(exceptionData, "ms"))));
		nova_static_Console_waitForEnter((Object*)0, exceptionData);
}

int nova_StabilityTest_runTests(StabilityTest* this, ExceptionData* exceptionData)
{
		TRY
		{
				nova_ExceptionData_addCode(exceptionData, exceptionData, 2);
				
				{
						nova_static_ExceptionStability_test((Object*)0, exceptionData, this);
						nova_static_TimeStability_test((Object*)0, exceptionData, this);
						nova_static_ThreadStability_test((Object*)0, exceptionData, this);
						nova_static_FileStability_test((Object*)0, exceptionData, this);
						nova_static_SyntaxStability_test((Object*)0, exceptionData, this);
						nova_static_ClosureStability_test((Object*)0, exceptionData, this);
						nova_static_PolymorphismStability_test((Object*)0, exceptionData, this);
						nova_static_Console_writeLine((Object*)0, exceptionData, nova_String_String(exceptionData, "All OK"));
						return 0;
				}
		}
		CATCH (2)
		{
				return 1;
		}
		FINALLY
		{
		}
		END_TRY;
}

void nova_StabilityTest_fail(StabilityTest* this, ExceptionData* exceptionData, String* nova_0_message)
{
		if (nova_0_message->nova_String_length <= 0)
		{
				nova_0_message = nova_String_String(exceptionData, "Failed");
		}
		nova_static_Console_writeLine((Object*)0, exceptionData, nova_0_message);
		THROW(2);
}



int main(int argc, char** argvs)
{
		String** args;
		int      i;
		
		ExceptionData* exceptionData = 0;
		srand(currentTimeMillis());
		nova_static_GC_init((Object*)0, exceptionData);
		
		args = (String**)NOVA_MALLOC(argc * sizeof(String));
		
		for (i = 0; i < argc; i++)
		{
				char* str = (char*)NOVA_MALLOC(sizeof(char) * strlen(argvs[i]) + 1);
				copy_string(str, argvs[i]);
				args[i] = nova_String_String(0, str);
		}
		
		TRY
		{
				nova_static_StabilityTest_main(0, exceptionData, args);
		}
		CATCH (1)
		{
				printf("You broke it.");
				nova_static_Console_waitForEnter((Object*)0, exceptionData);
				
		}
		FINALLY
		{
				
		}
		END_TRY;
		NOVA_FREE(args);
		GC_gcollect();
		
		return 0;
}