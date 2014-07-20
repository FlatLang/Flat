#include <precompiled.h>
#include "NovaThreadStability.h"




void nova_static_ThreadStability_createThreads(ThreadStability* this, ExceptionData* exceptionData, StabilityTest* nova_0_program, ThreadImplementation** nova_0_threads, int nova_0_amount);
void nova_static_ThreadStability_checkMemoryAccess(ThreadStability* this, ExceptionData* exceptionData);
void nova_static_ThreadStability_joinThreads(ThreadStability* this, ExceptionData* exceptionData, ThreadImplementation** nova_0_threads, int nova_0_amount);

ThreadStability* nova_ThreadStability_ThreadStability(ExceptionData* exceptionData)
{
	ThreadStability* this = (ThreadStability*)1;
	
	{
	}
	
	return this;
}

void nova_del_ThreadStability(ThreadStability** this, ExceptionData* exceptionData)
{
	if (!*this)
	{
		return;
	}
	
	
	{
	}
	NOVA_FREE(*this);
}

void nova_static_ThreadStability_test(ThreadStability* this, ExceptionData* exceptionData, StabilityTest* nova_0_program)
{
	ThreadImplementation** nova_1_threads;
	
	nova_static_Console_writeLine((Object*)0, exceptionData, nova_String_String(exceptionData, "Checking Thread.nova with 20 Threads... "));
	nova_1_threads = (ThreadImplementation**)NOVA_MALLOC(sizeof(ThreadImplementation) * (20));
	nova_static_ThreadStability_createThreads((Object*)0, exceptionData, nova_0_program, nova_1_threads, 20);
	nova_static_ThreadStability_checkMemoryAccess((Object*)0, exceptionData);
	nova_static_ThreadStability_joinThreads((Object*)0, exceptionData, nova_1_threads, 20);
}

void nova_static_ThreadStability_createThreads(ThreadStability* this, ExceptionData* exceptionData, StabilityTest* nova_0_program, ThreadImplementation** nova_0_threads, int nova_0_amount)
{
	StabilityExceptionHandler* nova_1_handler;
	int nova_1_i;
	
	nova_1_handler = nova_StabilityExceptionHandler_StabilityExceptionHandler(exceptionData, nova_0_program);
	nova_1_i = 0;
	for (; nova_1_i < nova_0_amount; nova_1_i++)
	{
		nova_0_threads[nova_1_i] = nova_ThreadImplementation_ThreadImplementation(exceptionData);
		nova_Thread_start(nova_0_threads[nova_1_i], exceptionData);
	}
}

void nova_static_ThreadStability_checkMemoryAccess(ThreadStability* this, ExceptionData* exceptionData)
{
	int nova_1_i;
	
	nova_static_Thread_sleep((Object*)0, exceptionData, 30);
	nova_static_Console_write((Object*)0, exceptionData, nova_String_String(exceptionData, "Checking memory access with multi-threading... "));
	nova_1_i = 0;
	for (; nova_1_i < 100000; nova_1_i++)
	{
		String* nova_2_s;
		
		nova_2_s = (Object*)0->nova_static_Integer_toAString((Object*)0, exceptionData, nova_1_i);
	}
	nova_static_Console_writeLine((Object*)0, exceptionData, nova_String_String(exceptionData, "OK"));
}

void nova_static_ThreadStability_joinThreads(ThreadStability* this, ExceptionData* exceptionData, ThreadImplementation** nova_0_threads, int nova_0_amount)
{
	int nova_1_i;
	
	nova_1_i = 0;
	for (; nova_1_i < nova_0_amount; nova_1_i++)
	{
		nova_Thread_join(nova_0_threads[nova_1_i], exceptionData);
	}
}
