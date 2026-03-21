#include "NativeLibrary.h"

nova_library_handle nova_load_library(char* location) {
#ifdef _WIN32
    return LoadLibrary((LPCSTR)location);
#else
    return dlopen(location, RTLD_NOW);
#endif
}

void* nova_get_function(nova_library_handle handle, char* name) {
#ifdef _WIN32
    return GetProcAddress(handle, name);
#else
    return dlsym(handle, name);
#endif
}