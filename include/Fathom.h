#ifndef FATHOM_H
#define FATHOM_H

#include <stdio.h>
#include <stdlib.h>
#include <stddef.h>

extern char* ufgets(FILE* stream);

/**
 * @dest Destination array
 * @dIndex Index to which we will start copying
 * @src Source array
 * @sIndex Index from which we will start copying
 * @len Number of elements that will be copied from source array
 * @destArrLen The length of the destination array (hence C doesn't know any length info about passed arrays)
 * @size The size of the type of the array (ex: if the array of type long, put in this parameter sizeof(long))
 */
void arrayCopy(void* dest, int dIndex, const void* src, int sIndex, int len, int destArrLen, size_t size);

#endif