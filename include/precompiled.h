#ifndef NOVA_PRECOMPILED
#define NOVA_PRECOMPILED

#include <assert.h>
#include <math.h>
#include <stdio.h>
#include <stdlib.h>
#include <stddef.h>
#include <string.h>
#include <stdint.h>
#include <sys/timeb.h>

#include <assert.h>
#include <CClass.h>

#ifdef _WIN32
#	include <windows.h>
#elif defined(__APPLE__) || defined(__linux__)
#	include <pthread.h>
#	include <unistd.h>
#else
#	error Operating system not defined.
#endif

#ifdef USE_GC
#	include <gc.h>
#endif

#endif