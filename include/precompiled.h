#pragma once
#ifndef NOVA_PRECOMPILED
#define NOVA_PRECOMPILED

#include <math.h>
#include <stdio.h>
#include <stdlib.h>
#include <stddef.h>
#include <string.h>
#include <stdint.h>
#include <sys/timeb.h>
#include <MacroLib.h>
#include <CClass.h>

#if defined(_WIN32)
#	include <windows.h>
#elif defined(__APPLE__) || defined(__linux__)
#	include <pthread.h>
#	include <unistd.h>
#else
#	error Operating system not supported.
#endif

#ifdef USE_GC
//#	define PARALLEL_MARK
#	define THREAD_LOCAL_ALLOC
#	ifdef _WIN32
#		define GC_THREADS
#	elif DEFINED(__APPLE__, __linux__)
#		define GC_PTHREADS
#	endif
#	include <gc.h>
#endif

#endif
