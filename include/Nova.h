#ifndef NOVA_NOVA
#define NOVA_NOVA

#include <MacroLib.h>

//#define nova_Int long
//#define nova_Long long long
//#define nova_Char char
//#define nova_Byte char
//#define nova_Bool char
//#define nova_Float float
//#define nova_Double double
//#define nova_void void
//#define nova_Object void*
//#define nova_Anything void*

#include <stdio.h>

//#include <nova/primitive/nova_primitive_Nova_Null.h>
//#include <nova/exception/nova_exception_Nova_ExceptionData.h>

typedef struct nova_exception_Nova_ExceptionData nova_exception_Nova_ExceptionData;
typedef struct nova_primitive_Nova_Null nova_primitive_Nova_Null;

extern nova_primitive_Nova_Null* nova_null;
extern void* nova_garbageData;

typedef void (*del_function)(void* reference, nova_exception_Nova_ExceptionData* exceptionData);

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
void nova_free_array(void** array, int* dimensionSizes, int dimension, int dimensions, del_function function, nova_exception_Nova_ExceptionData* exceptionData);

#endif
