#include "Thread.h"
#include <stdio.h>

FATHOM_THREAD_FUNC func(FATHOM_THREAD_FUNC_ARG arg)
{
	int i;
	
	for (i = 0; i < 50; i++)
	{
		printf("i is now: %d\n", i);
		
		lib_fathom_thread_sleep(100);
	}
}

int main()
{
	FATHOM_THREAD_HANDLE handle;
	
	lib_fathom_thread_create(&handle, func);
	
	lib_fathom_thread_join(handle);
	
	return 0;
}