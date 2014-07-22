#include <precompiled.h>
#include "NovaThreadDemo.h"




ThreadDemo* nova_ThreadDemo_ThreadDemo(ExceptionData* exceptionData)
{
	ThreadDemo* this = (ThreadDemo*)1;
	
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
		
		nova_2_thread = (Thread*)(nova_ThreadDemoImplementation_ThreadDemoImplementation(exceptionData, (long_long)(100), nova_String_String(exceptionData, "Thread1")));
		nova_2_thread2 = (Thread*)(nova_ThreadDemoImplementation_ThreadDemoImplementation(exceptionData, (long_long)(100), nova_String_String(exceptionData, "Thread2")));
		nova_2_start = nova_static_Time_currentTimeMillis((Time*)(0), exceptionData);
		nova_Thread_start((Thread*)(nova_2_thread), exceptionData);
		nova_Thread_start((Thread*)(nova_2_thread2), exceptionData);
		nova_Thread_join((Thread*)(nova_2_thread), exceptionData);
		nova_Thread_join((Thread*)(nova_2_thread2), exceptionData);
		nova_2_end = nova_static_Time_currentTimeMillis((Time*)(0), exceptionData);
		nova_static_Console_writeLine((Console*)(0), exceptionData, nova_String_concat(nova_String_String(exceptionData, (char*)("Time taken: ")), exceptionData, nova_String_concat(nova_Long_toString((Long*)(nova_Long_Long(exceptionData, (nova_2_end - nova_2_start))), exceptionData), exceptionData, nova_String_String(exceptionData, (char*)("ms")))));
		nova_static_Console_write((Console*)(0), exceptionData, nova_String_String(exceptionData, "Run again? (Y/N) "));
		nova_1_c = nova_static_Console_readChar((Console*)(0), exceptionData);
	}
	nova_static_Console_writeLine((Console*)(0), exceptionData, nova_String_String(exceptionData, "\nFinished"));
	nova_static_Console_waitForEnter((Console*)(0), exceptionData);
}
