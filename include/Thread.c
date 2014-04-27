#include "Thread.h"

void lib_fathom_thread_create(FATHOM_THREAD_HANDLE* handle, FATHOM_THREAD_FUNC_TYPE func)
{
#if defined(_WIN32)
	int* arg;
	arg = (int*)malloc(sizeof(int));
	*arg = 0;
	
	DWORD id;
	
	*handle = CreateThread(NULL, 0, *func, arg, 0, &id);
#elif defined(__APPLE__)
	pthread_create(handle, NULL, func, "processing");
#endif
}