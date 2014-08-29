#include <precompiled.h>
#include "NovaThreadDemo.h"


nova_VTable_ThreadDemo nova_VTable_ThreadDemo_val =
{
	nova_0_Object_toString,
	nova_0_Object_equals,
};

ThreadDemo* nova_ThreadDemo_construct(ThreadDemo* this, ExceptionData* exceptionData)
{
	CCLASS_NEW(ThreadDemo, this,);
	this->vtable = &nova_VTable_ThreadDemo_val;
	nova_Object_super((Object*)this, 0);
	nova_Object_this((Object*)(this), exceptionData);
	nova_ThreadDemo_super(this, 0);
	
	{
		nova_ThreadDemo_this(this, exceptionData);
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
		
		nova_2_thread = (Thread*)(nova_ThreadDemoImplementation_construct(0, exceptionData, (long_long)(100), nova_String_construct(0, exceptionData, "Thread1")));
		nova_2_thread2 = (Thread*)(nova_ThreadDemoImplementation_construct(0, exceptionData, (long_long)(100), nova_String_construct(0, exceptionData, "Thread2")));
		nova_2_start = nova_static_Time_currentTimeMillis(0, exceptionData);
		nova_Thread_start(nova_2_thread, exceptionData);
		nova_Thread_start(nova_2_thread2, exceptionData);
		nova_Thread_join(nova_2_thread, exceptionData);
		nova_Thread_join(nova_2_thread2, exceptionData);
		nova_2_end = nova_static_Time_currentTimeMillis(0, exceptionData);
		nova_static_0_Console_writeLine(0, exceptionData, nova_String_concat(nova_String_construct(0, exceptionData, "Time taken: "), exceptionData, nova_String_concat(nova_3_Long_toString(nova_Long_construct(0, exceptionData, (nova_2_end - nova_2_start)), exceptionData), exceptionData, nova_String_construct(0, exceptionData, "ms"))));
		nova_static_0_Console_write(0, exceptionData, nova_String_construct(0, exceptionData, "Run again? (Y/N) "));
		nova_1_c = nova_static_Console_readChar(0, exceptionData);
	}
	nova_static_0_Console_writeLine(0, exceptionData, nova_String_construct(0, exceptionData, "\nFinished"));
	nova_static_Console_waitForEnter(0, exceptionData);
}

void nova_ThreadDemo_this(ThreadDemo* this, ExceptionData* exceptionData)
{
}

void nova_ThreadDemo_super(ThreadDemo* this, ExceptionData* exceptionData)
{
}
