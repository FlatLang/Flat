#include <precompiled.h>
#include "NovaMathDemo.h"


nova_VTable_MathDemo nova_VTable_MathDemo_val =
{
		nova_4_Object_toString,
		nova_2_Object_equals,
};

MathDemo* nova_MathDemo_MathDemo(MathDemo* this, ExceptionData* exceptionData)
{
		CCLASS_NEW(MathDemo, this,);
		
		this->vtable = &nova_VTable_MathDemo_val;
		{
		}
		
		return this;
}

void nova_del_MathDemo(MathDemo** this, ExceptionData* exceptionData)
{
		if (!*this)
		{
				return;
		}
		
		
		{
		}
		NOVA_FREE(*this);
}

void nova_static_MathDemo_main(MathDemo* this, ExceptionData* exceptionData, String** nova_0_args)
{
		int nova_1_iterations;
		long_long nova_1_start;
		int nova_1_i;
		long_long nova_1_time;
		
		nova_static_1_Console_writeLine(0, exceptionData, nova_String_String(0, exceptionData, (char*)("Beginning benchmark...")));
		nova_1_iterations = 999999;
		nova_1_start = nova_static_Time_currentTimeMillis(0, exceptionData);
		nova_1_i = 0;
		for (; nova_1_i < nova_1_iterations; nova_1_i++)
		{
				TRY
				{
						nova_ExceptionData_addCode(exceptionData, exceptionData, 1);
						
						{
								nova_static_Math_sin(0, exceptionData, (double)(nova_1_i));
						}
				}
				CATCH (1)
				{
				}
				FINALLY
				{
				}
				END_TRY;
		}
		nova_1_time = (long_long)(nova_static_Time_currentTimeMillis(0, exceptionData) - nova_1_start);
		nova_static_1_Console_writeLine(0, exceptionData, nova_String_concat(nova_2_Integer_toString(nova_Integer_Integer(0, exceptionData, nova_1_iterations), exceptionData), exceptionData, nova_String_concat(nova_String_String(0, exceptionData, (char*)(" iterations of Math.sin() in ")), exceptionData, nova_String_concat(nova_2_Long_toString(nova_Long_Long(0, exceptionData, nova_1_time), exceptionData), exceptionData, nova_String_String(0, exceptionData, (char*)("ms"))))));
		nova_static_Console_waitForEnter(0, exceptionData);
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
				args[i] = nova_String_String(0, 0, str);
		}
		
		TRY
		{
				nova_static_MathDemo_main(0, exceptionData, args);
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