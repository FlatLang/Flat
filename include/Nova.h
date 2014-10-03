#ifndef NOVA_NOVA
#define NOVA_NOVA

#include <nova/standard/primitive/nova_standard_primitive_NovaNull.h>
#include <nova/standard/exception/nova_standard_exception_NovaExceptionData.h>

typedef void (*del_function)(void* reference, nova_standard_exception_NovaExceptionData* exceptionData);

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

char* ufgets(FILE* stream);

void copy_string(char* target, char* source);

void** nova_gen_array(void** array, int* dimensionSizes, int dimension, int dimensions, int size);
void nova_free_array(void** array, int* dimensionSizes, int dimension, int dimensions, del_function function, nova_standard_exception_NovaExceptionData* exceptionData);

extern nova_standard_primitive_NovaNull* nova_null;

#endif
