#ifndef NATIVE_NOVA_SYSTEM
#define NATIVE_NOVA_SYSTEM

#include <stdlib.h>
#include <stdio.h>
#include <string.h>
#include <nova/standard/nova_standard_NovaString.h>

#if defined(__APPLE__) || defined(__linux__)
#include <unistd.h>
#elif defined(_WIN32)
#include <process.h>
#include <io.h>
#endif

typedef void (*error_func)(void*, nova_standard_exception_NovaExceptionData*, int, nova_standard_NovaString*, char);

FILE* getPipe(char command[], error_func func, void* ref);

#endif
