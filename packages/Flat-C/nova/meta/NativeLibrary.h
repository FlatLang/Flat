#ifndef NOVA_NATIVE_LIBRARY
#define NOVA_NATIVE_LIBRARY

#include <Nova.h>

#ifdef _WIN32
#   define nova_library_handle HINSTANCE
#else
#	include <dlfcn.h>
#	define nova_library_handle void*
#endif

nova_library_handle nova_load_library(char* location);
void* nova_get_function(nova_library_handle handle, char* name);

#endif