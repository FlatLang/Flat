#include "test.h"
#include "Thread.h"

FATHOM_THREAD_FUNC func(FATHOM_THREAD_FUNC_ARG arg)
{
	int i;
	
	for (i = 0; i < 1000000000 / 2; i++)
	{
		sin(i);
	}

	return 0;
}

void waitForEnter()
{
	char* nova_1_c;
	
	nova_1_c = (char*)malloc(sizeof(char) * (2));
	fgets(nova_1_c, 2, stdin);
}

int main()
{
	long_long start, end;
	
	FATHOM_THREAD_HANDLE handle, handle2;
	
	lib_fathom_thread_create(&handle, func, 0);
	lib_fathom_thread_create(&handle2, func, 0);
	
	start = currentTimeMillis();
	
	lib_fathom_thread_join(handle);
	lib_fathom_thread_join(handle2);
	
	end = currentTimeMillis();
	
	printf("Time: %llu\n", (end - start));

	waitForEnter();
	
	return 0;
}