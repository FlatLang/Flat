#include <precompiled.h>
#include "NovaStabilityTest.h"


nova_VTable_StabilityTest nova_VTable_StabilityTest_val =
{
		nova_2_Object_toString,
		nova_2_Object_equals,
};

StabilityTest* nova_StabilityTest_StabilityTest(ExceptionData* exceptionData)
{
		CCLASS_NEW(StabilityTest, this,);
		
		this->vtable = &nova_VTable_StabilityTest_val;
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
		nova_1_start = nova_static_Time_currentTimeMillis(0, exceptionData);
		nova_1_result = nova_StabilityTest_runTests(nova_1_test, exceptionData);
		nova_1_time = (long_long)(nova_static_Time_currentTimeMillis(0, exceptionData) - nova_1_start);
		nova_static_System_execute(0, exceptionData, nova_String_String(exceptionData, "/bin/ls"));
		nova_static_1_Console_writeLine(0, exceptionData, nova_String_concat(nova_String_String(exceptionData, (char*)("Took ")), exceptionData, nova_String_concat(nova_2_Long_toString(nova_Long_Long(exceptionData, nova_1_time), exceptionData), exceptionData, nova_String_String(exceptionData, (char*)("ms")))));
		nova_static_Console_waitForEnter(0, exceptionData);
}

int nova_StabilityTest_runTests(StabilityTest* this, ExceptionData* exceptionData)
{
		TRY
		{
				nova_ExceptionData_addCode(exceptionData, exceptionData, 2);
				
				{
						nova_static_ExceptionStability_test(0, exceptionData, this);
						nova_static_TimeStability_test(0, exceptionData, this);
						nova_static_ThreadStability_test(0, exceptionData, this);
						nova_static_FileStability_test(0, exceptionData, this);
						nova_static_SyntaxStability_test(0, exceptionData, this);
						nova_static_ClosureStability_test(0, exceptionData, this);
						nova_static_PolymorphismStability_test(0, exceptionData, this);
						nova_static_1_Console_writeLine(0, exceptionData, nova_String_String(exceptionData, "All OK"));
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

void nova_1_StabilityTest_fail(StabilityTest* this, ExceptionData* exceptionData)
{
		nova_2_StabilityTest_fail(this, exceptionData, nova_String_String(exceptionData, "Failed"));
}

void nova_2_StabilityTest_fail(StabilityTest* this, ExceptionData* exceptionData, String* nova_0_message)
{
		nova_static_1_Console_writeLine(0, exceptionData, nova_0_message);
		THROW(2);
}



int main(int argc, char** argvs)
{
		String** args;
		int      i;
		
		ExceptionData* exceptionData = 0;
		srand(currentTimeMillis());
		nova_static_GC_init(0, exceptionData);
		
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
				nova_static_Console_waitForEnter(0, exceptionData);
				
		}
		FINALLY
		{
				
		}
		END_TRY;
		NOVA_FREE(args);
		GC_gcollect();
		
		return 0;
}