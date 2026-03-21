#ifndef NOVA_NOVA
#define NOVA_NOVA

typedef struct nova_funcStruct {
    void* func;
    void* ref;
    void* context;
} nova_funcStruct;

#ifdef _WIN32
#   ifndef _WIN32_WINNT
#       define _WIN32_WINNT 0x0600
#   endif
#   define _WIN32_IE 0x0900

#   include <windows.h>
#   include <commctrl.h>

#   define NOVA_CODE_CONTEXT CONTEXT
#else
#   define NOVA_CODE_CONTEXT void
#endif

#include <math.h>
#include <stdio.h>
#include <stdlib.h>
#include <stddef.h>
#include <string.h>
#include <stdint.h>
#include <sys/timeb.h>
#include <time.h>

#include <NovaExceptionHandling.h>

#if defined(_WIN32)
#	ifdef _MSC_VER
//#		define _CRT_SECURE_NO_WARNINGS
#	endif
#	ifndef WIN32_LEAN_AND_MEAN
#		define WIN32_LEAN_AND_MEAN
#	endif
#else
#	include <pthread.h>
#	include <unistd.h>
#endif

#ifdef USE_GC
//#	define PARALLEL_MARK
#	define THREAD_LOCAL_ALLOC
#	define GC_THREADS
#	ifndef _WIN32
#		define GC_PTHREADS
#	endif
#	include <gc.h>
#endif

#include <MacroLib.h>
#include <CClass.h>

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

#define PCRE2_CODE_UNIT_WIDTH 8

typedef void (*nova_basic_function_type)();
typedef void (*nova_vtable_callback)(const char* name, void** handle);

extern nova_basic_function_type* nova_interface_vtable;

void initVTables(nova_basic_function_type* interfaceFunctions, nova_vtable_callback callback);

#include <stdio.h>
#include <VTableDeclarations.h>
// #include <InterfaceVTable.h>
// #include <ExceptionHandler.h>
// #include <NovaClassData.h>

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
void arrayCopy(void* dest, int dIndex, const void* src, int sIndex, int len, int destArrLen);

char* ufgets(FILE* stream);

void copy_string(char* target, char* source);

void** nova_gen_array(void** array, int* dimensionSizes, int dimension, int dimensions, int size);
void nova_free_array(void** array, int* dimensionSizes, int dimension, int dimensions, del_function function, nova_exception_Nova_ExceptionData* exceptionData);

nova_funcStruct* nova_get_funcStruct1(void* func);
nova_funcStruct* nova_get_funcStruct2(void* func, void* ref);
nova_funcStruct* nova_get_funcStruct3(void* func, void* ref, void* context);

#endif