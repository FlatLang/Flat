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
#include <time.h>

#if defined(_WIN32)
#	ifdef _MSC_VER
//#		define _CRT_SECURE_NO_WARNINGS
#	endif
#	ifndef WIN32_LEAN_AND_MEAN
#		define WIN32_LEAN_AND_MEAN
#	endif
#	include <windows.h>
#else
#	include <pthread.h>
#	include <unistd.h>
#endif

#ifdef USE_GC
//#	define PARALLEL_MARK
#	define THREAD_LOCAL_ALLOC
#	ifdef _WIN32
#		define GC_THREADS
#	else
#		define GC_PTHREADS
#	endif
#	include <gc.h>
#endif

#include <MacroLib.h>
#include <CClass.h>

#endif
