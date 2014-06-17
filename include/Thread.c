#include "precompiled.h"
#include "Thread.h"

void lib_fathom_thread_create(FATHOM_THREAD_HANDLE* handle, FATHOM_THREAD_FUNC_TYPE func, FATHOM_THREAD_FUNC_ARG arg)
{
#if defined(_WIN32)
	DWORD id;

	*handle = (FATHOM_THREAD_HANDLE*)new_thread(NULL, 0, *func, arg, 0, &id);
#elif defined(__APPLE__)
	new_thread(handle, NULL, func, arg);
#endif
}
