#pragma once
#ifndef FILE_nova_Nova_String_NOVA
#define FILE_nova_Nova_String_NOVA

typedef struct nova_Nova_String nova_Nova_String;

typedef struct nova_exception_Nova_ExceptionData nova_exception_Nova_ExceptionData;

typedef char (*nova_Nova_String_closure1_Nova_transform)(void*, nova_exception_Nova_ExceptionData*, char, int, void*);
typedef char (*nova_Nova_String_closure2_Nova_transform)(void*, nova_exception_Nova_ExceptionData*, char, int, void*);
typedef char (*nova_Nova_String_closure3_Nova_transform)(void*, nova_exception_Nova_ExceptionData*, char, int, void*);

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
#include <nova/datastruct/nova_datastruct_Nova_Comparable.h>
#include <nova/datastruct/nova_datastruct_Nova_HashSet.h>


typedef struct nova_Extension_VTable_String nova_Extension_VTable_String;
struct nova_Extension_VTable_String
{
	nova_Interface_VTable itable;
	nova_Nova_String* (*nova_Nova_Object_virtual1_Nova_toString)(nova_Nova_String*, nova_exception_Nova_ExceptionData*);
	char (*nova_operators_Nova_Equals_virtual0_Nova_equals)(nova_Nova_String*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);
	long_long (*nova_Nova_Object_virtual_Accessor_Nova_hashCodeLong)(nova_Nova_String*, nova_exception_Nova_ExceptionData*);
	nova_Nova_String* (*nova_Nova_String_virtual1_Nova_concat)(nova_Nova_String*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);
	int (*nova_datastruct_Nova_Comparable_virtual0_Nova_compareTo)(nova_Nova_String*, nova_exception_Nova_ExceptionData*, nova_Nova_String*);
};

extern nova_Extension_VTable_String nova_Extension_VTable_String_val;


CCLASS_CLASS
(
	nova_Nova_String, 
	
	nova_Extension_VTable_String* vtable;
	int nova_Nova_String_Nova_count;
	nova_datastruct_list_Nova_CharArray* nova_Nova_String_Nova_chars;
)

void nova_Nova_String_Nova_init_static(nova_exception_Nova_ExceptionData* exceptionData);
nova_Nova_String* nova_Nova_String_0_Nova_construct(nova_Nova_String* this, nova_exception_Nova_ExceptionData* exceptionData, char nova_Nova_String_Nova_c);
nova_Nova_String* nova_Nova_String_1_Nova_construct(nova_Nova_String* this, nova_exception_Nova_ExceptionData* exceptionData, char* nova_Nova_String_Nova_chars);
void nova_Nova_String_Nova_destroy(nova_Nova_String** this, nova_exception_Nova_ExceptionData* exceptionData);
void nova_Nova_String_1_Nova_this(nova_Nova_String* this, nova_exception_Nova_ExceptionData* exceptionData, char nova_Nova_String_Nova_c);
void nova_Nova_String_2_Nova_this(nova_Nova_String* this, nova_exception_Nova_ExceptionData* exceptionData, char* nova_Nova_String_Nova_chars);
nova_Nova_String* nova_Nova_String_0_Nova_concat(nova_Nova_String* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* nova_Nova_String_Nova_str);
char nova_Nova_String_Nova_equals(nova_Nova_String* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* nova_Nova_String_Nova_other);
nova_Nova_String* nova_Nova_String_Nova_replace(nova_Nova_String* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* nova_Nova_String_Nova_search, nova_Nova_String* nova_Nova_String_Nova_replace);
char nova_Nova_String_Nova_startsWith(nova_Nova_String* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* nova_Nova_String_Nova_search);
char nova_Nova_String_Nova_endsWith(nova_Nova_String* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* nova_Nova_String_Nova_search);
char nova_Nova_String_Nova_contains(nova_Nova_String* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* nova_Nova_String_Nova_search);
int nova_Nova_String_1_Nova_indexOf(nova_Nova_String* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* nova_Nova_String_Nova_search);
int nova_Nova_String_2_Nova_indexOf(nova_Nova_String* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* nova_Nova_String_Nova_search, int nova_Nova_String_Nova_start);
int nova_Nova_String_Nova_lastIndexOf(nova_Nova_String* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* nova_Nova_String_Nova_search);
nova_Nova_String* nova_Nova_String_0_Nova_substring(nova_Nova_String* this, nova_exception_Nova_ExceptionData* exceptionData, int nova_Nova_String_Nova_start, int nova_Nova_String_Nova_end);
nova_Nova_String* nova_Nova_String_1_Nova_substring(nova_Nova_String* this, nova_exception_Nova_ExceptionData* exceptionData, int nova_Nova_String_Nova_start);
char nova_Nova_String_Nova_lastChar(nova_Nova_String* this, nova_exception_Nova_ExceptionData* exceptionData);
char nova_Nova_String_Nova_charAt(nova_Nova_String* this, nova_exception_Nova_ExceptionData* exceptionData, int nova_Nova_String_Nova_index);
nova_Nova_String* nova_Nova_String_Nova_trim(nova_Nova_String* this, nova_exception_Nova_ExceptionData* exceptionData);
nova_Nova_String* nova_Nova_String_Nova_toLowerCase(nova_Nova_String* this, nova_exception_Nova_ExceptionData* exceptionData);
nova_Nova_String* nova_Nova_String_Nova_toUpperCase(nova_Nova_String* this, nova_exception_Nova_ExceptionData* exceptionData);
nova_Nova_String* nova_Nova_String_Nova_capitalize(nova_Nova_String* this, nova_exception_Nova_ExceptionData* exceptionData);
nova_Nova_String* nova_Nova_String_Nova_transform(nova_Nova_String* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String_closure3_Nova_transform nova_Nova_String_Nova_transform, void* nova_Nova_String_ref_Nova_transform, void* transform_context);
nova_Nova_String* nova_Nova_String_0_Nova_getStringBetween(nova_Nova_String* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* nova_Nova_String_Nova_before, nova_Nova_String* nova_Nova_String_Nova_after);
nova_Nova_String* nova_Nova_String_1_Nova_getStringBetween(nova_Nova_String* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* nova_Nova_String_Nova_before, nova_Nova_String* nova_Nova_String_Nova_after, int nova_Nova_String_Nova_start);
int nova_Nova_String_Nova_compareTo(nova_Nova_String* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* nova_Nova_String_Nova_other);
nova_Nova_String* nova_Nova_String_0_Nova_toString(nova_Nova_String* this, nova_exception_Nova_ExceptionData* exceptionData);
long_long nova_Nova_String_Accessor_Nova_hashCodeLong(nova_Nova_String* this, nova_exception_Nova_ExceptionData* exceptionData);
void nova_Nova_String_Nova_super(nova_Nova_String* this, nova_exception_Nova_ExceptionData* exceptionData);
nova_Nova_String* nova_Nova_String_virtual1_Nova_concat(nova_Nova_String* this, nova_exception_Nova_ExceptionData* exceptionData, nova_Nova_String* nova_Nova_String_Nova_str);

#endif
