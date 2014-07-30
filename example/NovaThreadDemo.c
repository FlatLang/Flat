#include <precompiled.h>
#include "NovaThreadDemo.h"


nova_VTable_ThreadDemo nova_VTable_ThreadDemo_val =
{
		nova_4_Object_toString,
		nova_2_Object_equals,
};

ThreadDemo* nova_ThreadDemo_ThreadDemo(ThreadDemo* this, ExceptionData* exceptionData)
{
		CCLASS_NEW(ThreadDemo, this,);
		
		this->vtable = &nova_VTable_ThreadDemo_val;
		{
		}
		
		return this;
}

void nova_del_ThreadDemo(ThreadDemo** this, ExceptionData* exceptionData)
{
		if (!*this)
		{
				return;
		}
		
		
		{
		}
		NOVA_FREE(*this);
}

void nova_static_ThreadDemo_main(ThreadDemo* this, ExceptionData* exceptionData, String** nova_0_args)
{
		char nova_1_c;
		
		nova_1_c = 'y';
		while (nova_1_c == 'y' || nova_1_c == 'Y')
		{
				Thread* nova_2_thread;
				Thread* nova_2_thread2;
				long_long nova_2_start;
				long_long nova_2_end;
				
				nova_2_thread = (Thread*)(nova_ThreadDemoImplementation_ThreadDemoImplementation(0, exceptionData, (long_long)(100), nova_String_String(0, exceptionData, (char*)("Thread1"))));
				nova_2_thread2 = (Thread*)(nova_ThreadDemoImplementation_ThreadDemoImplementation(0, exceptionData, (long_long)(100), nova_String_String(0, exceptionData, (char*)("Thread2"))));
				nova_2_start = nova_static_Time_currentTimeMillis(0, exceptionData);
				nova_Thread_start(nova_2_thread, exceptionData);
				nova_Thread_start(nova_2_thread2, exceptionData);
				nova_Thread_join(nova_2_thread, exceptionData);
				nova_Thread_join(nova_2_thread2, exceptionData);
				nova_2_end = nova_static_Time_currentTimeMillis(0, exceptionData);
				nova_static_1_Console_writeLine(0, exceptionData, nova_String_concat(nova_String_String(0, exceptionData, (char*)("Time taken: ")), exceptionData, nova_String_concat(nova_2_Long_toString(nova_Long_Long(0, exceptionData, (nova_2_end - nova_2_start)), exceptionData), exceptionData, nova_String_String(0, exceptionData, (char*)("ms")))));
				nova_static_1_Console_write(0, exceptionData, nova_String_String(0, exceptionData, (char*)("Run again? (Y/N) ")));
				nova_1_c = nova_static_Console_readChar(0, exceptionData);
		}
		nova_static_1_Console_writeLine(0, exceptionData, nova_String_String(0, exceptionData, (char*)("\nFinished")));
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
				nova_static_ThreadDemo_main(0, exceptionData, args);
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