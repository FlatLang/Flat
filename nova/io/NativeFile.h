#ifndef NATIVE_NOVA_FILE
#define NATIVE_NOVA_FILE

#include <stdio.h>

#ifdef __APPLE__
#include <sys/resource.h>
#endif

void setMaxOpenFiles(int num);
int getMaxOpenFiles();

#endif
