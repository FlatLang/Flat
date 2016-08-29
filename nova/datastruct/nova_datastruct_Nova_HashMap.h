#pragma once
#ifndef FILE_nova_datastruct_Nova_HashMap_NOVA
#define FILE_nova_datastruct_Nova_HashMap_NOVA

typedef struct nova_datastruct_Nova_HashMap nova_datastruct_Nova_HashMap;

typedef struct nova_exception_Nova_ExceptionData nova_exception_Nova_ExceptionData;
typedef struct nova_datastruct_Nova_Pair nova_datastruct_Nova_Pair;
typedef struct nova_datastruct_Nova_HashMap nova_datastruct_Nova_HashMap;
typedef struct nova_Nova_Object nova_Nova_Object;

typedef char (*nova_datastruct_Nova_HashMap_closure1_Nova_func)(void*, nova_exception_Nova_ExceptionData*, nova_datastruct_Nova_Pair*, void*);
typedef char (*nova_datastruct_Nova_HashMap_closure2_Nova_func)(void*, nova_exception_Nova_ExceptionData*, nova_datastruct_Nova_Pair*, void*);
typedef char (*nova_datastruct_Nova_HashMap_closure3_Nova_func)(void*, nova_exception_Nova_ExceptionData*, nova_datastruct_Nova_Pair*, void*);
typedef char (*nova_datastruct_Nova_HashMap_closure4_Nova_func)(void*, nova_exception_Nova_ExceptionData*, nova_datastruct_Nova_Pair*, void*);
typedef char (*nova_datastruct_Nova_HashMap_closure5_Nova_func)(void*, nova_exception_Nova_ExceptionData*, nova_datastruct_Nova_Pair*, void*);
typedef char (*nova_datastruct_Nova_HashMap_closure6_Nova_func)(void*, nova_exception_Nova_ExceptionData*, nova_datastruct_Nova_Pair*, void*);
typedef nova_Nova_Object* (*nova_datastruct_Nova_HashMap_closure7_Nova_func)(void*, nova_exception_Nova_ExceptionData*, nova_datastruct_Nova_Pair*, int, nova_datastruct_Nova_HashMap*, void*);
typedef nova_Nova_Object* (*nova_datastruct_Nova_HashMap_closure8_Nova_func)(void*, nova_exception_Nova_ExceptionData*, nova_datastruct_Nova_Pair*, int, nova_datastruct_Nova_HashMap*, void*);
typedef nova_Nova_Object* (*nova_datastruct_Nova_HashMap_closure9_Nova_func)(void*, nova_exception_Nova_ExceptionData*, nova_datastruct_Nova_Pair*, int, nova_datastruct_Nova_HashMap*, void*);
typedef char (*nova_datastruct_Nova_HashMap_closure10_Nova_func)(void*, nova_exception_Nova_ExceptionData*, nova_datastruct_Nova_Pair*, int, nova_datastruct_Nova_HashMap*, void*);
typedef char (*nova_datastruct_Nova_HashMap_closure11_Nova_func)(void*, nova_exception_Nova_ExceptionData*, nova_datastruct_Nova_Pair*, int, nova_datastruct_Nova_HashMap*, void*);
typedef char (*nova_datastruct_Nova_HashMap_closure12_Nova_func)(void*, nova_exception_Nova_ExceptionData*, nova_datastruct_Nova_Pair*, int, nova_datastruct_Nova_HashMap*, void*);
typedef char (*nova_datastruct_Nova_HashMap_closure13_Nova_func)(void*, nova_exception_Nova_ExceptionData*, nova_datastruct_Nova_Pair*, void*);
typedef char (*nova_datastruct_Nova_HashMap_closure14_Nova_func)(void*, nova_exception_Nova_ExceptionData*, nova_datastruct_Nova_Pair*, void*);
typedef char (*nova_datastruct_Nova_HashMap_closure15_Nova_func)(void*, nova_exception_Nova_ExceptionData*, nova_datastruct_Nova_Pair*, void*);
typedef void (*nova_datastruct_Nova_HashMap_closure16_Nova_func)(void*, nova_exception_Nova_ExceptionData*, nova_datastruct_Nova_Pair*, int, nova_datastruct_Nova_HashMap*, void*);
typedef void (*nova_datastruct_Nova_HashMap_closure17_Nova_func)(void*, nova_exception_Nova_ExceptionData*, nova_datastruct_Nova_Pair*, int, nova_datastruct_Nova_HashMap*, void*);
typedef void (*nova_datastruct_Nova_HashMap_closure18_Nova_func)(void*, nova_exception_Nova_ExceptionData*, nova_datastruct_Nova_Pair*, int, nova_datastruct_Nova_HashMap*, void*);

#include <Nova.h>
#include <ExceptionHandler.h>
#include <InterfaceVTable.h>
#include <nova/exception/nova_exception_Nova_ExceptionData.h>
#include <nova/exception/nova_exception_Nova_Exception.h>
#include <nova/exception/nova_exception_Nova_DivideByZeroException.h>
#include <nova/io/nova_io_Nova_Console.h>
#include <nova/primitive/number/nova_primitive_number_Nova_Number.h>
#include <nova/primitive/number/nova_primitive_number_Nova_Byte.h>
#include <nova/primitive/number/nova_primitive_number_Nova_Short.h>
#include <nova/primitive/number/nova_primitive_number_Nova_Int.h>
#include <nova/primitive/number/nova_primitive_number_Nova_Long.h>
#include <nova/primitive/number/nova_primitive_number_Nova_Float.h>
#include <nova/primitive/number/nova_primitive_number_Nova_Double.h>
#include <nova/primitive/nova_primitive_Nova_Null.h>
#include <nova/primitive/number/nova_primitive_number_Nova_Char.h>
#include <nova/primitive/nova_primitive_Nova_Bool.h>
#include <nova/datastruct/list/nova_datastruct_list_Nova_Array.h>
#include <nova/datastruct/list/nova_datastruct_list_Nova_IntArray.h>
#include <nova/datastruct/list/nova_datastruct_list_Nova_CharArray.h>
#include <nova/datastruct/list/nova_datastruct_list_Nova_DoubleArray.h>
#include <nova/datastruct/list/nova_datastruct_list_Nova_IntRange.h>
#include <nova/thread/nova_thread_Nova_Thread.h>
#include <nova/thread/async/nova_thread_async_Nova_Async.h>
#include <nova/gc/nova_gc_Nova_GC.h>
#include <nova/math/nova_math_Nova_Math.h>
#include <nova/nova_Nova_Object.h>
#include <nova/nova_Nova_String.h>
#include <nova/nova_Nova_System.h>
#include <nova/nova_Nova_Class.h>
#include <nova/datastruct/list/nova_datastruct_list_Nova_List.h>
#include <nova/datastruct/nova_datastruct_Nova_Pair.h>
#include <nova/datastruct/list/nova_datastruct_list_Nova_ArrayIterator.h>


typedef struct nova_datastruct_Extension_VTable_HashMap nova_datastruct_Extension_VTable_HashMap;
struct nova_datastruct_Extension_VTable_HashMap
{
	nova_Interface_VTable itable;
	nova_Nova_String* (*nova_Nova_Object_virtual1_Nova_toString)(nova_Nova_Object*, nova_exception_Nova_ExceptionData*);
	char (*nova_operators_Nova_Equals_virtual0_Nova_equals)(nova_Nova_Object*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*);
	long_long (*nova_Nova_Object_virtual_Accessor_Nova_hashCodeLong)(nova_Nova_Object*, nova_exception_Nova_ExceptionData*);
	nova_datastruct_list_Nova_Array* (*nova_datastruct_list_Nova_List_virtual0_Nova_toArray)(nova_datastruct_Nova_HashMap*, nova_exception_Nova_ExceptionData*);
	char (*nova_datastruct_list_Nova_List_virtual0_Nova_contains)(nova_datastruct_Nova_HashMap*, nova_exception_Nova_ExceptionData*, nova_datastruct_Nova_Pair*);
	char (*nova_datastruct_list_Nova_List_virtual0_Nova_any)(nova_datastruct_Nova_HashMap*, nova_exception_Nova_ExceptionData*, nova_datastruct_Nova_HashMap_closure3_Nova_func nova_datastruct_Nova_HashMap_Nova_func, void* nova_datastruct_Nova_HashMap_ref_Nova_func, void* func_context);
	char (*nova_datastruct_list_Nova_List_virtual0_Nova_all)(nova_datastruct_Nova_HashMap*, nova_exception_Nova_ExceptionData*, nova_datastruct_Nova_HashMap_closure6_Nova_func nova_datastruct_Nova_HashMap_Nova_func, void* nova_datastruct_Nova_HashMap_ref_Nova_func, void* func_context);
	nova_datastruct_list_Nova_Array* (*nova_datastruct_list_Nova_List_virtual0_Nova_map)(nova_datastruct_Nova_HashMap*, nova_exception_Nova_ExceptionData*, nova_datastruct_Nova_HashMap_closure9_Nova_func nova_datastruct_Nova_HashMap_Nova_func, void* nova_datastruct_Nova_HashMap_ref_Nova_func, void* func_context);
	nova_datastruct_list_Nova_Array* (*nova_datastruct_list_Nova_List_virtual0_Nova_filter)(nova_datastruct_Nova_HashMap*, nova_exception_Nova_ExceptionData*, nova_datastruct_Nova_HashMap_closure12_Nova_func nova_datastruct_Nova_HashMap_Nova_func, void* nova_datastruct_Nova_HashMap_ref_Nova_func, void* func_context);
	nova_Nova_String* (*nova_datastruct_list_Nova_List_virtual0_Nova_join)(nova_datastruct_Nova_HashMap*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);
	nova_datastruct_Nova_Pair* (*nova_datastruct_list_Nova_List_virtual0_Nova_skip)(nova_datastruct_Nova_HashMap*, nova_exception_Nova_ExceptionData*, int);
	nova_datastruct_Nova_Pair* (*nova_datastruct_list_Nova_List_virtual0_Nova_take)(nova_datastruct_Nova_HashMap*, nova_exception_Nova_ExceptionData*, int);
	nova_datastruct_list_Nova_Array* (*nova_datastruct_list_Nova_List_virtual0_Nova_reverse)(nova_datastruct_Nova_HashMap*, nova_exception_Nova_ExceptionData*);
	nova_datastruct_Nova_Pair* (*nova_datastruct_list_Nova_List_virtual0_Nova_firstWhere)(nova_datastruct_Nova_HashMap*, nova_exception_Nova_ExceptionData*, nova_datastruct_Nova_HashMap_closure15_Nova_func nova_datastruct_Nova_HashMap_Nova_func, void* nova_datastruct_Nova_HashMap_ref_Nova_func, void* func_context);
	void (*nova_datastruct_list_Nova_List_virtual0_Nova_forEach)(nova_datastruct_Nova_HashMap*, nova_exception_Nova_ExceptionData*, nova_datastruct_Nova_HashMap_closure18_Nova_func nova_datastruct_Nova_HashMap_Nova_func, void* nova_datastruct_Nova_HashMap_ref_Nova_func, void* func_context);
	nova_datastruct_Nova_HashMap* (*nova_datastruct_Nova_HashMap_virtual1_Nova_put)(nova_datastruct_Nova_HashMap*, nova_exception_Nova_ExceptionData*, nova_Nova_Object*, nova_Nova_Object*);
};

extern nova_datastruct_Extension_VTable_HashMap nova_datastruct_Extension_VTable_HashMap_val;


CCLASS_CLASS
(
	nova_datastruct_Nova_HashMap, 
	
	nova_datastruct_Extension_VTable_HashMap* vtable;
	int nova_datastruct_Nova_HashMap_Nova_size;
	struct Private* prv;
)

void nova_datastruct_Nova_HashMap_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData);
nova_datastruct_Nova_HashMap* nova_datastruct_Nova_HashMap_0_Nova_construct(nova_datastruct_Nova_HashMap* this, nova_exception_Nova_ExceptionData* exceptionData);
nova_datastruct_Nova_HashMap* nova_datastruct_Nova_HashMap_1_Nova_construct(nova_datastruct_Nova_HashMap* this, nova_exception_Nova_ExceptionData* exceptionData, int nova_datastruct_Nova_HashMap_Nova_bucketCount, int nova_datastruct_Nova_HashMap_Nova_bucketSize);
void nova_datastruct_Nova_HashMap_Nova_destroy(nova_datastruct_Nova_HashMap** this, nova_exception_Nova_ExceptionData* exceptionData);
void nova_datastruct_Nova_HashMap_0_Nova_this(nova_datastruct_Nova_HashMap* this, nova_exception_Nova_ExceptionData* exceptionData);
void nova_datastruct_Nova_HashMap_1_Nova_this(nova_datastruct_Nova_HashMap* this, nova_exception_Nova_ExceptionData* exceptionData, int nova_datastruct_Nova_HashMap_Nova_bucketCount, int nova_datastruct_Nova_HashMap_Nova_bucketSize);
nova_datastruct_list_Nova_Array* nova_datastruct_Nova_HashMap_Nova_toArray(nova_datastruct_Nova_HashMap* this, nova_exception_Nova_ExceptionData* exceptionData);
char nova_datastruct_Nova_HashMap_Nova_contains(nova_datastruct_Nova_HashMap* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_Nova_Pair* nova_datastruct_Nova_HashMap_Nova_value);
char nova_datastruct_Nova_HashMap_Nova_any(nova_datastruct_Nova_HashMap* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_Nova_HashMap_closure3_Nova_func nova_datastruct_Nova_HashMap_Nova_func, void* nova_datastruct_Nova_HashMap_ref_Nova_func, void* func_context);
char nova_datastruct_Nova_HashMap_Nova_all(nova_datastruct_Nova_HashMap* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_Nova_HashMap_closure6_Nova_func nova_datastruct_Nova_HashMap_Nova_func, void* nova_datastruct_Nova_HashMap_ref_Nova_func, void* func_context);
nova_datastruct_list_Nova_Array* nova_datastruct_Nova_HashMap_Nova_map(nova_datastruct_Nova_HashMap* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_Nova_HashMap_closure9_Nova_func nova_datastruct_Nova_HashMap_Nova_func, void* nova_datastruct_Nova_HashMap_ref_Nova_func, void* func_context);
nova_datastruct_list_Nova_Array* nova_datastruct_Nova_HashMap_Nova_filter(nova_datastruct_Nova_HashMap* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_Nova_HashMap_closure12_Nova_func nova_datastruct_Nova_HashMap_Nova_func, void* nova_datastruct_Nova_HashMap_ref_Nova_func, void* func_context);
nova_Nova_String* nova_datastruct_Nova_HashMap_Nova_join(nova_datastruct_Nova_HashMap* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* nova_datastruct_Nova_HashMap_Nova_delimiter);
nova_datastruct_Nova_Pair* nova_datastruct_Nova_HashMap_Nova_skip(nova_datastruct_Nova_HashMap* this, nova_exception_Nova_ExceptionData* exceptionData, int nova_datastruct_Nova_HashMap_Nova_num);
nova_datastruct_Nova_Pair* nova_datastruct_Nova_HashMap_Nova_take(nova_datastruct_Nova_HashMap* this, nova_exception_Nova_ExceptionData* exceptionData, int nova_datastruct_Nova_HashMap_Nova_num);
nova_datastruct_list_Nova_Array* nova_datastruct_Nova_HashMap_Nova_reverse(nova_datastruct_Nova_HashMap* this, nova_exception_Nova_ExceptionData* exceptionData);
nova_datastruct_Nova_Pair* nova_datastruct_Nova_HashMap_Nova_firstWhere(nova_datastruct_Nova_HashMap* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_Nova_HashMap_closure15_Nova_func nova_datastruct_Nova_HashMap_Nova_func, void* nova_datastruct_Nova_HashMap_ref_Nova_func, void* func_context);
void nova_datastruct_Nova_HashMap_Nova_forEach(nova_datastruct_Nova_HashMap* this, nova_exception_Nova_ExceptionData* exceptionData, nova_datastruct_Nova_HashMap_closure18_Nova_func nova_datastruct_Nova_HashMap_Nova_func, void* nova_datastruct_Nova_HashMap_ref_Nova_func, void* func_context);
nova_datastruct_Nova_HashMap* nova_datastruct_Nova_HashMap_0_Nova_put(nova_datastruct_Nova_HashMap* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_Object* nova_datastruct_Nova_HashMap_Nova_key, nova_Nova_Object* nova_datastruct_Nova_HashMap_Nova_value);
nova_Nova_Object* nova_datastruct_Nova_HashMap_Nova_get(nova_datastruct_Nova_HashMap* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_Object* nova_datastruct_Nova_HashMap_Nova_key);
nova_Nova_Object* nova_datastruct_Nova_HashMap_Nova_remove(nova_datastruct_Nova_HashMap* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_Object* nova_datastruct_Nova_HashMap_Nova_key);
char nova_datastruct_Nova_HashMap_Nova_containsKey(nova_datastruct_Nova_HashMap* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_Object* nova_datastruct_Nova_HashMap_Nova_key);
void nova_datastruct_Nova_HashMap_Nova_super(nova_datastruct_Nova_HashMap* this, nova_exception_Nova_ExceptionData* exceptionData);
nova_datastruct_Nova_HashMap* nova_datastruct_Nova_HashMap_virtual1_Nova_put(nova_datastruct_Nova_HashMap* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_Object* nova_datastruct_Nova_HashMap_Nova_key, nova_Nova_Object* nova_datastruct_Nova_HashMap_Nova_value);

#endif
