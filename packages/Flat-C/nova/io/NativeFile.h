#ifndef NATIVE_NOVA_FILE
#define NATIVE_NOVA_FILE

#include <stdio.h>
#include <tinydir.h>

#ifdef __APPLE__
#include <sys/resource.h>
#endif

void setMaxOpenFiles(int num);
int getMaxOpenFiles();

int nova_getstr (char** lineptr, size_t* n, FILE* stream, char terminator, int offset);
int nova_getline (char** lineptr, size_t* n, FILE* stream);
int nova_read (char** lineptr, size_t* n, FILE* stream);

#endif