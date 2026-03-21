#ifndef NATIVE_NOVA_SYSTEM
#define NATIVE_NOVA_SYSTEM

#include <stdlib.h>
#include <stdio.h>
#include <string.h>

#if defined(__APPLE__) || defined(__linux__)
#include <unistd.h>
#elif defined(_WIN32)
#include <process.h>
#include <io.h>
#endif

#include <Nova.h>

typedef void (*error_func)(void*, int, nova_Nova_String*, char, void*);

FILE* getPipe(char command[], error_func func, void* ref, void* context);

#endif
