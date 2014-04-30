#include "Thread.h"
#include "Fathom.h"
#include <stdio.h>
#include <math.h>

FATHOM_THREAD_FUNC func(FATHOM_THREAD_FUNC_ARG arg)
{
	int i;
	
	for (i = 0; i < 1000000000 / 2; i++)
	{
		sin(i);
	}

	return 0;
}

int main()
{
	long_long start, end;
	
	FATHOM_THREAD_HANDLE handle, handle2;
	
	lib_fathom_thread_create(&handle, func);
	lib_fathom_thread_create(&handle2, func);
	
	start = currentTimeMillis();
	
	lib_fathom_thread_join(handle);
	lib_fathom_thread_join(handle2);
	
	end = currentTimeMillis();
	
	printf("Time: %llu\n", (end - start));
	
	return 0;
}