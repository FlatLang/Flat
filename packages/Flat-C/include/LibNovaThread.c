#include <precompiled.h>
#include "LibNovaThread.h"

void lib_nova_thread_create(NOVA_THREAD_HANDLE* handle, NOVA_THREAD_FUNC_TYPE func, NOVA_THREAD_FUNC_ARG arg)
{
#if defined(_WIN32)
	DWORD id;

	*handle = (NOVA_THREAD_HANDLE*)new_thread(NULL, 0, *func, arg, 0, &id);
#elif defined(__APPLE__) || defined(__linux__)
	new_thread(handle, NULL, func, arg);
#endif
}

#if defined(__APPLE__) || defined(__linux__)

void thread_nanosleep2(struct timespec req);

void thread_nanosleep(long_long nanos)
{
	struct timespec req;
	
	req.tv_sec  = nanos / 1000000000;
	req.tv_nsec = nanos % 1000000000;
	
	thread_nanosleep2(req);
}

void thread_nanosleep2(struct timespec req)
{
	struct timespec rem;
	
	if (nanosleep(&req, &rem) < 0)
	{
		thread_nanosleep2(rem);
	}
}

#endif
