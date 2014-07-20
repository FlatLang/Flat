#include <precompiled.h>
#include "NovaClosureDemo.h"

typedef int (*nova_1_0_closure)(void*, ExceptionData*, int, int);


void nova_ClosureDemo_callClosure(ClosureDemo* this, ExceptionData* exceptionData, nova_1_0_closure nova_0_closure, void* nova_ref_0_closure);
int nova_static_ClosureDemo_multiply(ClosureDemo* this, ExceptionData* exceptionData, int nova_0_value1, int nova_0_value2);
int nova_static_ClosureDemo_pow(ClosureDemo* this, ExceptionData* exceptionData, int nova_0_base, int nova_0_pow);

ClosureDemo* nova_ClosureDemo_ClosureDemo(ExceptionData* exceptionData)
{
		ClosureDemo* this = (ClosureDemo*)1;
		
		{
		}
		
		return this;
}

void nova_del_ClosureDemo(ClosureDemo** this, ExceptionData* exceptionData)
{
		if (!*this)
		{
				return;
		}
		
		
		{
		}
		NOVA_FREE(*this);
}

void nova_static_ClosureDemo_main(ClosureDemo* this, ExceptionData* exceptionData, String** nova_0_args)
{
		ClosureDemo* nova_1_demo;
		
		nova_1_demo = nova_ClosureDemo_ClosureDemo(exceptionData);
		nova_ClosureDemo_callClosure(nova_1_demo, exceptionData, (nova_1_0_closure)&nova_static_ClosureDemo_multiply, 0);
		nova_ClosureDemo_callClosure(nova_1_demo, exceptionData, (nova_1_0_closure)&nova_static_ClosureDemo_pow, 0);
		nova_static_Console_waitForEnter(0, exceptionData);
}

void nova_ClosureDemo_callClosure(ClosureDemo* this, ExceptionData* exceptionData, nova_1_0_closure nova_0_closure, void* nova_ref_0_closure)
{
		int nova_1_value;
		
		nova_1_value = nova_0_closure(this, exceptionData, 5, 3);
		nova_static_Console_writeLine(0, exceptionData, nova_String_concat(nova_String_String(exceptionData, "Closure returned "), exceptionData, nova_Integer_toString(nova_Integer_Integer(exceptionData, nova_1_value), exceptionData)));
}

int nova_static_ClosureDemo_multiply(ClosureDemo* this, ExceptionData* exceptionData, int nova_0_value1, int nova_0_value2)
{
		nova_static_Console_writeLine(0, exceptionData, nova_String_concat(nova_String_String(exceptionData, "multiply was called with "), exceptionData, nova_String_concat(nova_Integer_toString(nova_Integer_Integer(exceptionData, nova_0_value1), exceptionData), exceptionData, nova_String_concat(nova_String_String(exceptionData, " and "), exceptionData, nova_Integer_toString(nova_Integer_Integer(exceptionData, nova_0_value2), exceptionData)))));
		return nova_0_value1 * nova_0_value2;
}

int nova_static_ClosureDemo_pow(ClosureDemo* this, ExceptionData* exceptionData, int nova_0_base, int nova_0_pow)
{
		int nova_1_value;
		int nova_1_i;
		
		nova_static_Console_writeLine(0, exceptionData, nova_String_concat(nova_String_String(exceptionData, "pow was called with "), exceptionData, nova_String_concat(nova_Integer_toString(nova_Integer_Integer(exceptionData, nova_0_base), exceptionData), exceptionData, nova_String_concat(nova_String_String(exceptionData, " and "), exceptionData, nova_Integer_toString(nova_Integer_Integer(exceptionData, nova_0_pow), exceptionData)))));
		nova_1_value = nova_0_base;
		nova_1_i = 0;
		for (; nova_1_i < nova_0_pow - 1; nova_1_i++)
		{
				nova_1_value = nova_1_value * nova_0_base;
		}
		return nova_1_value;
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
				nova_static_ClosureDemo_main(0, exceptionData, args);
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