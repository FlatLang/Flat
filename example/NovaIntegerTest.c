#include <precompiled.h>

#include "NovaIntegerTest.h"

IntegerTest* nova_IntegerTest_IntegerTest(ExceptionData* exceptionData)
{
		IntegerTest* this = NULL;
		
		{
		}
		
		return this;
}

void nova_del_IntegerTest(IntegerTest** this, ExceptionData* exceptionData)
{
		if (!*this)
		{
				return;
		}
		
		
		{
		}
		NOVA_FREE(*this);
}

void nova_IntegerTest_main(ExceptionData* exceptionData, String** nova_0_args)
{
		char nova_1_c;
		
		nova_1_c = 'y';
		while (nova_1_c == 'y' || nova_1_c == 'Y')
		{
				long_long nova_2_start;
				int nova_3_i;
				long_long nova_2_end;
				
				nova_2_start = currentTimeMillis();
				nova_3_i = 0;
				
				for (; nova_3_i < 999999; nova_3_i++)
				{
						nova_Long_toAString(exceptionData, nova_3_i);
				}
				nova_2_end = currentTimeMillis();
				nova_IO_println(exceptionData, nova_String_concat(nova_String_String(exceptionData, "Time taken: "), exceptionData, nova_String_concat(nova_Long_toString(nova_Long_Long(exceptionData, (nova_2_end - nova_2_start)), exceptionData), exceptionData, nova_String_String(exceptionData, "ms"))));
				nova_IO_print(exceptionData, nova_String_String(exceptionData, "Run again? (Y/N)"));
				nova_1_c = nova_IO_getChar(exceptionData);
		}
		nova_IO_println(exceptionData, nova_String_String(exceptionData, "\nFinished"));
		nova_IO_waitForEnter(exceptionData);
}



int main(int argc, char** argvs)
{
		String** args;
		int      i;
		
		ExceptionData* exceptionData = 0;
		srand(currentTimeMillis());
		nova_GC_init(exceptionData);
		
		args = (String**)NOVA_MALLOC(argc * sizeof(String));
		
		for (i = 0; i < argc; i++)
		{
				char* str = (char*)NOVA_MALLOC(sizeof(char) * strlen(argvs[i]) + 1);
				copy_string(str, argvs[i]);
				args[i] = nova_String_String(0, str);
		}
		
		TRY
		{
				nova_IntegerTest_main(exceptionData, args);
		}
		CATCH (1)
		{
				printf("You broke it.");
				nova_IO_waitForEnter(0);
		}
		FINALLY
		{
				
		}
		END_TRY;
		NOVA_FREE(args);
		GC_gcollect();
		
		return 0;
}